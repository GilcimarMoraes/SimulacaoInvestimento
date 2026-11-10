package caixaverso.infrastructure.mapper;

import caixaverso.application.dto.ProdutoDTO;

import caixaverso.infrastructure.persistence.entity.ProdutoEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoMapper {

    public ProdutoDTO toDTO(ProdutoEntity entity) {
        if (entity == null) return null;

        return new ProdutoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getTipo(),
                entity.getRentabilidade(),
                entity.getRisco()
        );
    }

}
