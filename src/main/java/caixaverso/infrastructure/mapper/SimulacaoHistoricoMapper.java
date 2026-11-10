package caixaverso.infrastructure.mapper;

import caixaverso.application.dto.SimulacaoHistoricoDTO;
import caixaverso.infrastructure.persistence.entity.SimulacaoEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SimulacaoHistoricoMapper {

    public SimulacaoHistoricoDTO toDTO(SimulacaoEntity s) {
        return new SimulacaoHistoricoDTO(
                s.getId(),
                s.getClienteId(),
                s.getProduto(),
                s.getValorInvestido(),
                s.getValorFinal(),
                s.getPrazoMeses(),
                s.getDataSimulacao()
        );
    }
}
