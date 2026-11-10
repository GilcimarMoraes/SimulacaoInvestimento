package caixaverso.application.dto;

import java.math.BigDecimal;

public record SimulacaoProdutoValidadoDTO(
        Integer id,
        String nome,
        String tipo,
        BigDecimal rentabilidade,
        String risco) {}
