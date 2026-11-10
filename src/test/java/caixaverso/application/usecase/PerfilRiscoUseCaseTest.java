package caixaverso.application.usecase;

import caixaverso.application.dto.PerfilRiscoResponseDTO;
import caixaverso.infrastructure.persistence.entity.InvestimentoEntity;
import caixaverso.infrastructure.persistence.repository.InvestimentoRepositoryImpl;
import caixaverso.infrastructure.persistence.repository.TelemetriaRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PerfilRiscoUseCaseTest {

    private InvestimentoRepositoryImpl investimentoRepository;
    private PerfilRiscoUseCase perfilRiscoUseCase;
    private TelemetriaRepositoryImpl telemetriaRepository;

    @BeforeEach
    void setup() {
        investimentoRepository = Mockito.mock(InvestimentoRepositoryImpl.class);
        telemetriaRepository = Mockito.mock(TelemetriaRepositoryImpl.class);
        perfilRiscoUseCase = new PerfilRiscoUseCase(investimentoRepository, telemetriaRepository);
    }

    @Test
    void deve_retornar_conservador_quando_sem_historico() {
        Long clienteId = 1L;

        when(investimentoRepository.listarPorCliente(clienteId))
                .thenReturn(List.of());

        PerfilRiscoResponseDTO resp = perfilRiscoUseCase.calcularPerfil(clienteId);

        assertEquals("Conservador", resp.perfil());
        assertEquals(20, resp.pontuacao());
    }

    @Test
    void deve_retornar_moderado_quando_pontuacao_media() {
        Long id = 1L;
        Long clienteId = 2L;

        List<InvestimentoEntity> hist = List.of(
                new InvestimentoEntity(id, clienteId, "Tesouro Selic", new BigDecimal("15000"),
                        new BigDecimal("5"), LocalDate.now()),
                new InvestimentoEntity(id, clienteId, "Ações", new BigDecimal("10000"),
                        new BigDecimal("6"), LocalDate.now()),
                new InvestimentoEntity(id, clienteId, "Fundo Multimercado", new BigDecimal("5000"),
                        new BigDecimal("10"), LocalDate.now())
        );

        when(investimentoRepository.listarPorCliente(clienteId))
                .thenReturn(hist);

        PerfilRiscoResponseDTO resp = perfilRiscoUseCase.calcularPerfil(clienteId);

        assertEquals("Moderado", resp.perfil());
        assertTrue(resp.pontuacao() >= 30 && resp.pontuacao() < 70);
    }

    @Test
    void deve_retornar_agressivo_quando_pontuacao_alta() {
        Long id = 1L;
        Long clienteId = 3L;

        List<InvestimentoEntity> hist = List.of(
                new InvestimentoEntity(id, clienteId, "Ações", new BigDecimal("30000"),
                        new BigDecimal("20"), LocalDate.now()),
                new InvestimentoEntity(id, clienteId, "Fundo Multimercado", new BigDecimal("15000"),
                        new BigDecimal("15"), LocalDate.now()),
                new InvestimentoEntity(id, clienteId, "Ações", new BigDecimal("20000"),
                        new BigDecimal("22"), LocalDate.now()),
                new InvestimentoEntity(id, clienteId, "Ações", new BigDecimal("10000"),
                        new BigDecimal("18"), LocalDate.now()),
                new InvestimentoEntity(id, clienteId, "Ações", new BigDecimal("20000"),
                        new BigDecimal("25"), LocalDate.now()),
                new InvestimentoEntity(id, clienteId, "Multimercado", new BigDecimal("15000"),
                        new BigDecimal("12"), LocalDate.now())
        );

        when(investimentoRepository.listarPorCliente(clienteId))
                .thenReturn(hist);

        PerfilRiscoResponseDTO resp= perfilRiscoUseCase.calcularPerfil(clienteId);

        assertEquals("Agressivo", resp.perfil());
        assertTrue(resp.pontuacao() >= 70);
    }

}

