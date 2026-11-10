package caixaverso.infrastructure.mapper;

import caixaverso.application.dto.ResultadoSimulacaoDTO;
import caixaverso.domain.entity.Simulacao;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResultadoSimulacaoMapper {

    public ResultadoSimulacaoDTO toDTO(Simulacao result) {
        if (result == null) return null;

        return new ResultadoSimulacaoDTO(
                result.getValorFinal(),
                result.getValorInvestido(),
                result.getPrazoMeses()
        );
    }
}
