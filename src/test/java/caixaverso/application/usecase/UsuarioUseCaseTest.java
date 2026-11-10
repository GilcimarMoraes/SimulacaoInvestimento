package caixaverso.application.usecase;

import caixaverso.infrastructure.persistence.entity.UsuarioEntity;
import caixaverso.infrastructure.persistence.repository.UsuarioRepositoryImpl;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioUseCaseTest {

    @Mock
    private UsuarioRepositoryImpl usuarioRepository;

    private UsuarioUseCase useCase;

    @BeforeEach
    void setup() {
        useCase = new UsuarioUseCase(usuarioRepository);
    }

    @Test
    void authenticate_usuarioNaoEncontrado_retornaEmpty() {

        when(usuarioRepository.findByUsername("ivan"))
                .thenReturn(Optional.empty());

        Optional<String> result = useCase.autenticaEGeraToken("ivan", "123");

        assertTrue(result.isEmpty());
    }

    @Test
    void authenticate_senhaIncorreta_retornaEmpty() {

        UsuarioEntity user = new UsuarioEntity();
        user.setUsuario("ivan");
        user.setSenha("HASH-SENHA");
        user.setPapel("admin,user");

        when(usuarioRepository.findByUsername("ivan"))
                .thenReturn(Optional.of(user));

        try (var bcryptMock = Mockito.mockStatic(BcryptUtil.class)) {
            bcryptMock.when(() -> BcryptUtil.matches("senhaErrada", "HASH-SENHA"))
                    .thenReturn(false);

            Optional<String> result = useCase.autenticaEGeraToken("ivan", "senhaErrada");

            assertTrue(result.isEmpty());
        }
    }

    @Test
    void authenticate_credenciaisCorretas_retornaTokenJWT() {

        UsuarioEntity usuario = mock(UsuarioEntity.class);
        when(usuario.getUsuario()).thenReturn("ivan");
        when(usuario.getSenha()).thenReturn(BcryptUtil.bcryptHash("123"));
        when(usuario.getPapel()).thenReturn("USER");

        when(usuarioRepository.findByUsername("ivan"))
                .thenReturn(Optional.of(usuario));


        when(usuarioRepository.findByUsername("ivan"))
                .thenReturn(Optional.of(usuario));

        try (MockedStatic<Jwt> jwtMock = Mockito.mockStatic(Jwt.class)) {

            // mock da cadeia de builder
            JwtClaimsBuilder builder = mock(JwtClaimsBuilder.class);

            jwtMock.when(() -> Jwt.issuer("caixaverso")).thenReturn(builder);

            when(builder.upn("ivan")).thenReturn(builder);
            when(builder.groups(anySet())).thenReturn(builder);
            when(builder.expiresIn(any())).thenReturn(builder);
            when(builder.sign()).thenReturn("TOKEN_ASSINADO");

            Optional<String> token = useCase.autenticaEGeraToken("ivan", "123");

            assertTrue(token.isPresent());
            assertEquals("TOKEN_ASSINADO", token.get());
        }
    }
}
