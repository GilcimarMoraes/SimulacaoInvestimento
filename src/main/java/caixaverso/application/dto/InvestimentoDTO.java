package caixaverso.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InvestimentoDTO(
        Long id,
        Long clienteId,
        String tipo,
        BigDecimal valor,
        BigDecimal rentabilidade,
        LocalDate data) {}
