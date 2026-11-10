package caixaverso.domain.repository;

import caixaverso.application.dto.TelemetriaItemDTO;
import caixaverso.infrastructure.persistence.entity.TelemetriaEntity;

import java.time.LocalDate;
import java.util.List;

public interface TelemetriaRepository {
    TelemetriaEntity salvar(TelemetriaEntity telemetriaEntity);

    List<TelemetriaItemDTO> agrupaPorServicoEPeriodo(LocalDate inicio, LocalDate fim);
}
