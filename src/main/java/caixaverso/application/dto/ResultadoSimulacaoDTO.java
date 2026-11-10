package caixaverso.application.dto;

import java.math.BigDecimal;

public record ResultadoSimulacaoDTO(
        BigDecimal valorFinal,
        BigDecimal rentabilidadeEfetiva,
        Integer prazoMeses) {}