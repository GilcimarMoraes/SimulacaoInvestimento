package caixaverso.domain.repository;

import caixaverso.infrastructure.persistence.entity.InvestimentoEntity;

import java.util.List;

public interface InvestimentoRepository {

    List<InvestimentoEntity> listarPorCliente(Long clienteId);
}
