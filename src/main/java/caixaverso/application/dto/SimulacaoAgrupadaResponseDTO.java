package caixaverso.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SimulacaoAgrupadaResponseDTO(
        String produto,
        LocalDate data,
        Long quantidadeSimulacoes,
        BigDecimal mediaValorFinal) {}
