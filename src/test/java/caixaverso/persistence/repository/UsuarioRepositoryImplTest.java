package caixaverso.persistence.repository;

import caixaverso.infrastructure.persistence.entity.UsuarioEntity;
import caixaverso.infrastructure.persistence.repository.UsuarioRepositoryImpl;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioRepositoryImplTest {

    private UsuarioRepositoryImpl repository;

    @BeforeEach
    void setup() {
        repository = spy(new UsuarioRepositoryImpl());
    }

    @Test
    void findByUsername_quandoEncontrado_deveRetornarOptional() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsuario("ivan");

        // mock do PanacheQuery
        PanacheQuery<UsuarioEntity> query = mock(PanacheQuery.class);

        // mock do find()
        doReturn(query)
                .when(repository)
                .find("usuario", "ivan");

        when(query.firstResultOptional())
                .thenReturn(Optional.of(usuario));

        Optional<UsuarioEntity> result = repository.findByUsername("ivan");

        assertTrue(result.isPresent());
        assertEquals("ivan", result.get().getUsuario());
    }

    @Test
    void findByUsername_quandoNaoEncontrado_deveRetornarEmpty() {
        PanacheQuery<UsuarioEntity> query = mock(PanacheQuery.class);

        doReturn(query)
                .when(repository)
                .find("usuario", "naoexiste");

        when(query.firstResultOptional())
                .thenReturn(Optional.empty());

        Optional<UsuarioEntity> result = repository.findByUsername("naoexiste");

        assertTrue(result.isEmpty());
    }
}
