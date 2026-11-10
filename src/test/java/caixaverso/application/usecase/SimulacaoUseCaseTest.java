package caixaverso.application.usecase;

import caixaverso.application.dto.*;
import caixaverso.infrastructure.mapper.SimulacaoHistoricoMapper;
import caixaverso.infrastructure.mapper.SimulacaoResponseMapper;
import caixaverso.infrastructure.persistence.entity.ProdutoEntity;
import caixaverso.infrastructure.persistence.entity.SimulacaoEntity;
import caixaverso.infrastructure.persistence.repository.ProdutoRepositoryImpl;
import caixaverso.infrastructure.persistence.repository.SimulacaoRepositoryImpl;

import caixaverso.infrastructure.persistence.repository.TelemetriaRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimulacaoUseCaseTest {

    @Mock
    private ProdutoRepositoryImpl produtoRepository;

    @Mock
    private SimulacaoRepositoryImpl simulacaoRepository;

    @Mock
    private SimulacaoHistoricoMapper historicoMapper;

    @Mock
    private SimulacaoResponseMapper responseMapper;

    @Mock
    private TelemetriaRepositoryImpl telemetriaRepository;

    @InjectMocks
    private SimulacaoUseCase useCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveListarTodos() {
        SimulacaoEntity e1 = new SimulacaoEntity();
        SimulacaoEntity e2 = new SimulacaoEntity();

        when(simulacaoRepository.listarPaginado(0, 10))
                .thenReturn(List.of(e1, e2));

        when(historicoMapper.toDTO(any()))
                .thenReturn(mock(SimulacaoHistoricoDTO.class));

        var result = useCase.listarTodos(0, 10);

        assertEquals(2, result.size());
        verify(simulacaoRepository, times(1)).listarPaginado(0, 10);
        verify(historicoMapper, times(2)).toDTO(any());
    }

    @Test
    void deveSimularInternoComSucesso() {
        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome("CDB Premium");
        produto.setRentabilidade(new BigDecimal("0.12")); // 12% aa

        when(produtoRepository.findByName("CDB Premium"))
                .thenReturn(Optional.of(produto));

        SimulacaoEntity salvo = new SimulacaoEntity();
        doNothing().when(simulacaoRepository).salvar(any());

        SimulacaoEntity result = useCase.simularInterno(
                1L,
                new BigDecimal("1000"),
                12,
                "CDB Premium"
        );

        assertNotNull(result);
        assertEquals("CDB Premium", result.getProduto());
        assertEquals(new BigDecimal("1000"), result.getValorInvestido());
        assertEquals(12, result.getPrazoMeses());
        verify(simulacaoRepository, times(1)).salvar(any());
    }

    @Test
    void deveFalharSimularInternoQuandoProdutoNaoExiste() {
        when(produtoRepository.findByName("XPTO"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                useCase.simularInterno(1L, BigDecimal.TEN, 10, "XPTO")
        );
    }

    @Test
    void deveSimularComSucesso() {
        SimulacaoRequestDTO req =
                new SimulacaoRequestDTO(1L, new BigDecimal("1000"), 12, "CDB Caixa 2026");

        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome("CDB Caixa 2026");
        produto.setTipo("CDB");
        produto.setRentabilidade(new BigDecimal("0.10"));
        produto.setRisco("Baixo");

        when(produtoRepository.findByName("CDB Caixa 2026"))
                .thenReturn(Optional.of(produto));

        doNothing().when(simulacaoRepository).salvar(any(SimulacaoEntity.class));

        SimulacaoProdutoValidadoDTO produtoValidado =
                new SimulacaoProdutoValidadoDTO(1,"CDB Caixa 2026", "CDB", new BigDecimal("0.10"),
                        "Baixo");

        ResultadoSimulacaoDTO resultado =
                new ResultadoSimulacaoDTO(new BigDecimal("1000"), new BigDecimal("0.10"), 10);

        Instant agora = Instant.now();

        when(responseMapper.toResponse(any(SimulacaoEntity.class), eq(produto)))
                .thenReturn(new SimulacaoResponseDTO(produtoValidado, resultado, agora));

        SimulacaoResponseDTO result = useCase.simular(req);

        assertNotNull(result);
        assertEquals("CDB", result.produtoValidado().tipo());

        verify(responseMapper).toResponse(any(SimulacaoEntity.class), eq(produto));
    }

    @Test
    void deveRetornarAgregacaoPorProdutoDia() {
        when(simulacaoRepository.agruparPorProdutoDia())
                .thenReturn(List.of(mock(SimulacaoAgrupadaResponseDTO.class)));

        var result = useCase.agregacaoPorProdutoDia();

        assertEquals(1, result.size());
        verify(simulacaoRepository, times(1)).agruparPorProdutoDia();
    }

    @Test
    void deveConverterTaxaAnualParaMensal() throws Exception {
        Method m = SimulacaoUseCase.class.getDeclaredMethod(
                "converterTaxaAnualParaMensal", BigDecimal.class);
        m.setAccessible(true);

        BigDecimal taxa = (BigDecimal) m.invoke(useCase, new BigDecimal("0.12"));
        assertNotNull(taxa);
        assertTrue(taxa.doubleValue() > 0);
    }

    @Test
    void deveCalcularJurosCompostos() throws Exception {
        Method m = SimulacaoUseCase.class.getDeclaredMethod(
                "calcularJurosCompostos", BigDecimal.class, BigDecimal.class, int.class);
        m.setAccessible(true);

        BigDecimal valor = new BigDecimal("1000");
        BigDecimal taxa = new BigDecimal("0.01");
        int meses = 12;

        BigDecimal resultado = (BigDecimal) m.invoke(useCase, valor, taxa, meses);

        assertEquals(new BigDecimal("1126.83"), resultado);
    }
}
