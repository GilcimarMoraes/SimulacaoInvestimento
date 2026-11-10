package caixaverso.application.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;

public record SimulacaoHistoricoDTO(
        Long id,
        Long clienteId,
        String produto,
        BigDecimal valorInvestido,
        BigDecimal valorFinal,
        Integer prazoMeses,
        Instant dataSimulacao) {}