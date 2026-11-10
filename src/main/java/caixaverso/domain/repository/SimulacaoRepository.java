package caixaverso.domain.repository;

import caixaverso.application.dto.SimulacaoAgrupadaResponseDTO;
import caixaverso.infrastructure.persistence.entity.SimulacaoEntity;

import java.util.List;

public interface SimulacaoRepository {

    void salvar(SimulacaoEntity simulacao);

    List<SimulacaoEntity> listarPaginado(int page, int size);

    List<SimulacaoAgrupadaResponseDTO> agruparPorProdutoDia();
}
