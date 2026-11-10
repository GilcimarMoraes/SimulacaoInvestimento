package caixaverso.application.usecase;

import caixaverso.infrastructure.mapper.InvestimentoMapper;
import caixaverso.domain.entity.Investimento;
import caixaverso.infrastructure.persistence.repository.InvestimentoRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class InvestimentoUseCaseTest {

    @Mock
    InvestimentoRepositoryImpl investimentoRepository;

    InvestimentoMapper investimentoMapper = new InvestimentoMapper();

    InvestimentoUseCase useCase;

    @BeforeEach
    void setup() {
        useCase = new InvestimentoUseCase(investimentoRepository, investimentoMapper);
    }

    @Test
    void deveListarInvestimentosPorCliente() {
        Investimento inv1 = new Investimento();
        inv1.setId(1L);
        inv1.setClienteId(10L);
        inv1.setTipo("CDB");
        inv1.setValor(BigDecimal.valueOf(5000));
        inv1.setRentabilidade(BigDecimal.valueOf(0.12));
        inv1.setData(LocalDate.now());

        Investimento inv2 = new Investimento();
        inv2.setId(2L);
        inv2.setClienteId(10L);
        inv2.setTipo("Fundo");
        inv2.setValor(BigDecimal.valueOf(3000));
        inv2.setRentabilidade(BigDecimal.valueOf(0.08));
        inv2.setData(LocalDate.now());

        when(investimentoRepository.listarPorCliente(10L))
                .thenReturn(Stream.of(inv1, inv2).map(investimentoMapper::modeltoEntity).toList());

        var result = useCase.listarPorCliente(10L);

        assertEquals(2, result.size());
        assertEquals("CDB", result.get(0).tipo());
        assertEquals("Fundo", result.get(1).tipo());

        verify(investimentoRepository).listarPorCliente(10L);
    }
}

