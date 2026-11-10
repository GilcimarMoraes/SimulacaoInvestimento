package caixaverso.persistence.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import caixaverso.domain.enums.PerfilRiscoEnum;
import caixaverso.infrastructure.persistence.entity.ProdutoEntity;
import caixaverso.infrastructure.persistence.repository.ProdutoRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoRepositoryImplTest {

    @InjectMocks
    ProdutoRepositoryImpl repository;

    @Mock
    EntityManager em;

    @Mock
    TypedQuery<ProdutoEntity> typedQuery;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listAllProdutos_deveRetornarLista() {
        List<ProdutoEntity> produtos = List.of(new ProdutoEntity(), new ProdutoEntity());

        ProdutoRepositoryImpl spyRepo = Mockito.spy(repository);
        doReturn(produtos).when(spyRepo).listAll();

        List<ProdutoEntity> result = spyRepo.listAllProdutos();

        assertEquals(2, result.size());
        verify(spyRepo, times(1)).listAll();
    }

    @Test
    void findByIdOptional_quandoEncontrado_deveRetornarOptional() {
        ProdutoEntity entity = new ProdutoEntity();

        ProdutoRepositoryImpl spyRepo = spy(repository);

        @SuppressWarnings("unchecked")
        PanacheQuery<ProdutoEntity> mockQuery = mock(PanacheQuery.class);

        when(mockQuery.firstResultOptional()).thenReturn(Optional.of(entity));

        doReturn(mockQuery).when(spyRepo).find("id", 1);

        Optional<ProdutoEntity> result = spyRepo.findByIdOptional(1);

        assertTrue(result.isPresent());
        assertSame(entity, result.get());
    }

    @Test
    void findByIdOptional_quandoNaoEncontrado_deveRetornarOptionalEmpty() {
        ProdutoRepositoryImpl spyRepo = spy(repository);

        @SuppressWarnings("unchecked")
        PanacheQuery<ProdutoEntity> mockQuery = mock(PanacheQuery.class);

        when(mockQuery.firstResultOptional()).thenReturn(Optional.empty());
        doReturn(mockQuery).when(spyRepo).find("id", 1);

        Optional<ProdutoEntity> result = spyRepo.findByIdOptional(1);

        assertTrue(result.isEmpty());
    }

    @Test
    void findByName_deveRetornarOptional() {
        ProdutoEntity entity = new ProdutoEntity();
        ProdutoRepositoryImpl spyRepo = spy(repository);

        @SuppressWarnings("unchecked")
        PanacheQuery<ProdutoEntity> mockQuery = mock(PanacheQuery.class);

        when(mockQuery.firstResultOptional()).thenReturn(Optional.of(entity));
        doReturn(mockQuery).when(spyRepo).find("nome", "CDB");

        Optional<ProdutoEntity> result = spyRepo.findByName("CDB");

        assertTrue(result.isPresent());
        assertSame(entity, result.get());
    }

    @Test
    void findValidByParams_comTipo_deveRetornarLista() {

        ProdutoRepositoryImpl spyRepo = spy(repository);
        doReturn(em).when(spyRepo).getEntityManager();

        when(em.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(typedQuery);

        when(typedQuery.setParameter(eq("valor"), any(BigDecimal.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("prazo"), anyInt())).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("tipo"), eq("CDB"))).thenReturn(typedQuery);

        when(typedQuery.getResultList()).thenReturn(List.of(new ProdutoEntity()));

        List<ProdutoEntity> result = spyRepo.findValidByParams(new BigDecimal("1000"), 12, "CDB");

        assertEquals(1, result.size());
        verify(em).createQuery(anyString(), eq(ProdutoEntity.class));
        verify(typedQuery).getResultList();
    }

    @Test
    void findValidByParams_semTipo_deveRetornarLista() {
        ProdutoRepositoryImpl spyRepo = spy(repository);
        doReturn(em).when(spyRepo).getEntityManager();

        when(em.createQuery(anyString(), eq(ProdutoEntity.class))).thenReturn(typedQuery);

        when(typedQuery.setParameter(eq("valor"), any(BigDecimal.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("prazo"), anyInt())).thenReturn(typedQuery);

        when(typedQuery.getResultList()).thenReturn(List.of(new ProdutoEntity(), new ProdutoEntity()));

        List<ProdutoEntity> result = spyRepo.findValidByParams(new BigDecimal("5000"), 6, null);

        assertEquals(2, result.size());
        verify(em).createQuery(anyString(), eq(ProdutoEntity.class));
        verify(typedQuery).getResultList();
    }

    @Test
    void persistProduto_devePersistirEDevolverMesmaInstancia() {
        ProdutoEntity entity = new ProdutoEntity();

        ProdutoRepositoryImpl spyRepo = spy(repository);

        doNothing().when(spyRepo).persist(entity);

        ProdutoEntity result = spyRepo.persistProduto(entity);

        assertSame(entity, result);
        verify(spyRepo, times(1)).persist(entity);
    }

    @Test
    void buscarPorPerfil_conservador_deveChamarBuscarPorRisco() {
        ProdutoRepositoryImpl spyRepo = spy(repository);

        List<ProdutoEntity> lista = List.of(new ProdutoEntity());
        doReturn(lista).when(spyRepo).buscarPorRisco(List.of("Baixo"));

        List<ProdutoEntity> result = spyRepo.buscarPorPerfil(PerfilRiscoEnum.CONSERVADOR);

        assertEquals(lista, result);
        verify(spyRepo, times(1)).buscarPorRisco(List.of("Baixo"));
    }

    @Test
    void buscarPorPerfil_moderado_deveChamarBuscarPorRisco() {
        ProdutoRepositoryImpl spyRepo = spy(repository);

        List<ProdutoEntity> lista = List.of(new ProdutoEntity());
        doReturn(lista).when(spyRepo).buscarPorRisco(List.of("Médio"));

        List<ProdutoEntity> result = spyRepo.buscarPorPerfil(PerfilRiscoEnum.MODERADO);

        assertEquals(lista, result);
    }

    @Test
    void buscarPorPerfil_agressivo_deveChamarBuscarPorRisco() {
        ProdutoRepositoryImpl spyRepo = spy(repository);

        List<ProdutoEntity> lista = List.of(new ProdutoEntity());
        doReturn(lista).when(spyRepo).buscarPorRisco(List.of("Alto"));

        List<ProdutoEntity> result = spyRepo.buscarPorPerfil(PerfilRiscoEnum.AGRESSIVO);

        assertEquals(lista, result);
    }

    @Test
    void buscarPorRisco_deveChamarListDoPanache() {
        ProdutoRepositoryImpl spyRepo = spy(repository);
        List<String> riscos = List.of("Baixo");

        List<ProdutoEntity> lista = List.of(new ProdutoEntity());

        doReturn(lista).when(spyRepo).list("risco in ?1", riscos);

        List<ProdutoEntity> result = spyRepo.buscarPorRisco(riscos);

        assertEquals(lista, result);
        verify(spyRepo).list("risco in ?1", riscos);
    }
}
