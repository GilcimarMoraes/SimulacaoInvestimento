package caixaverso.infrastructure.mapper;

import caixaverso.application.dto.InvestimentoDTO;
import caixaverso.domain.entity.Investimento;
import caixaverso.infrastructure.persistence.entity.InvestimentoEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InvestimentoMapper {

    public InvestimentoDTO modeltoDTO(Investimento entity) {
        if (entity == null) return null;

        return new InvestimentoDTO(
                entity.getId(),
                entity.getClienteId(),
                entity.getTipo(),
                entity.getValor(),
                entity.getRentabilidade(),
                entity.getData()
        );
    }

    public Investimento entityToModel(InvestimentoEntity entity) {
        if (entity == null) return null;

        Investimento model = new Investimento();
        model.setId(entity.getId());
        model.setClienteId(entity.getClienteId());
        model.setTipo(entity.getTipo());
        model.setValor(entity.getValor());
        model.setRentabilidade(entity.getRentabilidade());
        model.setData(entity.getData());

        return model;
    }

    public InvestimentoEntity modeltoEntity(Investimento model) {
        if (model == null) return null;

        InvestimentoEntity entity = new InvestimentoEntity();
        entity.setId(model.getId());
        entity.setClienteId(model.getClienteId());
        entity.setTipo(model.getTipo());
        entity.setValor(model.getValor());
        entity.setRentabilidade(model.getRentabilidade());
        entity.setData(model.getData());

        return entity;
    }

}
