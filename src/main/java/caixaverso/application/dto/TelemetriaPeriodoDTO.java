package caixaverso.application.dto;

import java.time.LocalDate;

public record TelemetriaPeriodoDTO(
        LocalDate inicio,
        LocalDate fim) {}