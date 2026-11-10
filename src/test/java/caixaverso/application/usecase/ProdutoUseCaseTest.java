package caixaverso.application.usecase;

import caixaverso.application.dto.ProdutoDTO;
import caixaverso.domain.enums.PerfilRiscoEnum;
import caixaverso.infrastructure.mapper.ProdutoMapper;
import caixaverso.infrastructure.persistence.entity.ProdutoEntity;
import caixaverso.infrastructure.persistence.repository.ProdutoRepositoryImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoUseCaseTest {

    @Mock
    ProdutoRepositoryImpl produtoRepository;

    @Mock
    ProdutoMapper produtoMapper;

    @InjectMocks
    ProdutoUseCase produtoUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // -------------------------------------------------------------
    // listar()
    // -------------------------------------------------------------
    @Test
    void deveListarProdutos() {
        var produto = new ProdutoEntity();
        when(produtoRepository.listAllProdutos()).thenReturn(List.of(produto));

        var result = produtoUseCase.listar();

        assertEquals(1, result.size());
        verify(produtoRepository).listAllProdutos();
    }

    // -------------------------------------------------------------
    // buscarPorId()
    // -------------------------------------------------------------
    @Test
    void deveBuscarProdutoPorId() {
        var produto = new ProdutoEntity();
        when(produtoRepository.findByIdOptional(1)).thenReturn(Optional.of(produto));

        var result = produtoUseCase.buscarPorId(1);

        assertNotNull(result);
        verify(produtoRepository).findByIdOptional(1);
    }

    @Test
    void deveLancarErroQuandoProdutoNaoEncontrado() {
        when(produtoRepository.findByIdOptional(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> produtoUseCase.buscarPorId(99));
    }

    // -------------------------------------------------------------
    // criarProduto()
    // -------------------------------------------------------------
    @Test
    void deveCriarProduto() {
        var produto = new ProdutoEntity();

        var result = produtoUseCase.criarProduto(produto);

        verify(produtoRepository).persistProduto(produto);
        assertEquals(produto, result);
    }

    // -------------------------------------------------------------
    // atualizar()
    // -------------------------------------------------------------
    @Test
    void deveAtualizarProduto() {
        var atual = new ProdutoEntity();
        atual.setNome("Antigo");
        atual.setTipo("Renda Fixa");

        var novo = new ProdutoEntity();
        novo.setNome("Novo Nome");
        novo.setTipo("Ações");

        when(produtoRepository.findByIdOptional(1)).thenReturn(Optional.of(atual));

        var result = produtoUseCase.atualizar(1, novo);

        assertEquals("Novo Nome", result.getNome());
        assertEquals("Ações", result.getTipo());
    }

    // -------------------------------------------------------------
    // deletar()
    // -------------------------------------------------------------
    @Test
    void deveDeletarProduto() {
        var produto = new ProdutoEntity();
        when(produtoRepository.findByIdOptional(1)).thenReturn(Optional.of(produto));

        produtoUseCase.deletar(1);

        verify(produtoRepository).delete(produto);
    }

    // -------------------------------------------------------------
    // sugerirProdutos()
    // -------------------------------------------------------------
    @Test
    void deveSugerirProdutos() {
        var produto = new ProdutoEntity();

        when(produtoRepository.findValidByParams(new BigDecimal("1000"), 12, "CDB"))
                .thenReturn(List.of(produto));

        var result = produtoUseCase.sugerirProdutos(new BigDecimal("1000"), 12, "CDB");

        assertEquals(1, result.size());
        verify(produtoRepository).findValidByParams(new BigDecimal("1000"), 12, "CDB");
    }

    // -------------------------------------------------------------
    // buscarProdutosPorPerfil()
    // -------------------------------------------------------------
    @Test
    void deveBuscarProdutosPorPerfil() {
        var entity = new ProdutoEntity();
        var dto = new ProdutoDTO(
                101,
                "CDB Caixa 2026",
                "CDB",
                new BigDecimal("0.12"),
                "Baixo"
        );

        when(produtoRepository.buscarPorPerfil(PerfilRiscoEnum.AGRESSIVO))
                .thenReturn(List.of(entity));

        when(produtoMapper.toDTO(entity)).thenReturn(dto);

        var result = produtoUseCase.buscarProdutosPorPerfil(PerfilRiscoEnum.AGRESSIVO);

        assertEquals(1, result.size());
        verify(produtoRepository).buscarPorPerfil(PerfilRiscoEnum.AGRESSIVO);
        verify(produtoMapper).toDTO(entity);
    }
}
