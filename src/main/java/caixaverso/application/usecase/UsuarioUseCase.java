package caixaverso.application.usecase;

import caixaverso.infrastructure.persistence.repository.UsuarioRepositoryImpl;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class UsuarioUseCase {

    private final UsuarioRepositoryImpl usuarioRepository;

    public UsuarioUseCase(UsuarioRepositoryImpl usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<String> autenticaEGeraToken(String username, String password) {
        return usuarioRepository.findByUsername(username)
                .filter(user -> BcryptUtil.matches(password, user.getSenha()))
                .map(user -> {
                    Set<String> roles = new HashSet<>(Arrays.asList(user.getPapel().split(",")));
                    return Jwt.issuer("caixaverso")
                            .upn(user.getUsuario())
                            .groups(roles)
                            .expiresIn(Duration.ofHours(24))
                            .sign();
                });
    }
}
