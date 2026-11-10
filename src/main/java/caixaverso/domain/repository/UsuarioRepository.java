package caixaverso.domain.repository;

import caixaverso.infrastructure.persistence.entity.UsuarioEntity;
import java.util.Optional;

public interface UsuarioRepository {

    Optional<UsuarioEntity> findByUsername(String username);
}
