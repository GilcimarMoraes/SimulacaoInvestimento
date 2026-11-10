package caixaverso.application.dto;

import java.time.Instant;
import java.time.OffsetDateTime;

public record SimulacaoResponseDTO(
        SimulacaoProdutoValidadoDTO produtoValidado,
        ResultadoSimulacaoDTO resultadoSimulacao,
        Instant dataSimulacao) {}