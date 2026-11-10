package caixaverso.infrastructure.persistence.repository;

import caixaverso.domain.repository.UsuarioRepository;
import caixaverso.infrastructure.persistence.entity.UsuarioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class UsuarioRepositoryImpl implements PanacheRepository<UsuarioEntity>, UsuarioRepository {

    @Override
    public Optional<UsuarioEntity> findByUsername(String username) {
        return find("usuario", username).firstResultOptional();
    }
}
