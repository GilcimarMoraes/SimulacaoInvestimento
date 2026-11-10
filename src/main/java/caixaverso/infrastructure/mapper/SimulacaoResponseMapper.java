package caixaverso.infrastructure.mapper;

import caixaverso.application.dto.ResultadoSimulacaoDTO;
import caixaverso.application.dto.SimulacaoProdutoValidadoDTO;
import caixaverso.application.dto.SimulacaoResponseDTO;
import caixaverso.infrastructure.persistence.entity.ProdutoEntity;
import caixaverso.infrastructure.persistence.entity.SimulacaoEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SimulacaoResponseMapper {

    public SimulacaoResponseDTO toResponse(SimulacaoEntity entity, ProdutoEntity produto) {
        return new SimulacaoResponseDTO(
                toProdutoValidadoDTO(produto),
                toResultadoDTO(entity),
                entity.getDataSimulacao()
        );
    }

    private SimulacaoProdutoValidadoDTO toProdutoValidadoDTO(ProdutoEntity entity) {
        return new SimulacaoProdutoValidadoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getTipo(),
                entity.getRentabilidade(),
                entity.getRisco()
        );
    }

    private ResultadoSimulacaoDTO toResultadoDTO(SimulacaoEntity entity) {
        return new ResultadoSimulacaoDTO(
                entity.getValorFinal(),
                entity.getRentabilidadeEfetiva(),
                entity.getPrazoMeses()
        );
    }
}
