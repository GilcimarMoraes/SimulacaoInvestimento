package caixaverso.application.dto;

import java.util.List;

public record TelemetriaResponseDTO(
        List<TelemetriaItemDTO> servicos,
        TelemetriaPeriodoDTO periodo) {}