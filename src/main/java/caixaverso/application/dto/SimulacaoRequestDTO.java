package caixaverso.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SimulacaoRequestDTO(
        Long clienteId,

        @Positive(message = "O valor investido deve ser positivo.")
        BigDecimal valorInvestido,

        @Min(value = 1, message = "O prazo em meses deve ser pelo menos 1.")
        Integer prazoMeses,

        @NotBlank
        String produto) {}
