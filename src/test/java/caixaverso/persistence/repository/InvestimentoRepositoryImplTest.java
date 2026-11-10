package caixaverso.persistence.repository;

import caixaverso.infrastructure.persistence.entity.InvestimentoEntity;
import caixaverso.infrastructure.persistence.repository.InvestimentoRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InvestimentoRepositoryImplTest {

    @Mock
    EntityManager em;

    @Mock
    TypedQuery<InvestimentoEntity> typedQuery;

    @InjectMocks
    InvestimentoRepositoryImpl repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarPorCliente_deveRetornarListaDoBanco() {

        Long clienteId = 1L;

        InvestimentoEntity inv1 = new InvestimentoEntity();
        InvestimentoEntity inv2 = new InvestimentoEntity();
        List<InvestimentoEntity> listaFake = List.of(inv1, inv2);

        // Mock da query JPQL
        when(em.createQuery(anyString(), eq(InvestimentoEntity.class)))
                .thenReturn(typedQuery);

        when(typedQuery.setParameter("clienteId", clienteId))
                .thenReturn(typedQuery);

        when(typedQuery.getResultList())
                .thenReturn(listaFake);

        // Execução
        List<InvestimentoEntity> result = repository.listarPorCliente(clienteId);

        // Validações
        assertEquals(2, result.size());
        verify(em, times(1)).createQuery(anyString(), eq(InvestimentoEntity.class));
        verify(typedQuery, times(1)).setParameter("clienteId", clienteId);
        verify(typedQuery, times(1)).getResultList();
    }

    @Test
    void listarPorCliente_semResultados_deveRetornarListaVazia() {

        Long clienteId = 5L;

        when(em.createQuery(anyString(), eq(InvestimentoEntity.class)))
                .thenReturn(typedQuery);

        when(typedQuery.setParameter("clienteId", clienteId))
                .thenReturn(typedQuery);

        when(typedQuery.getResultList())
                .thenReturn(List.of());

        List<InvestimentoEntity> result = repository.listarPorCliente(clienteId);

        assertEquals(0, result.size());
        verify(em, times(1)).createQuery(anyString(), eq(InvestimentoEntity.class));
        verify(typedQuery, times(1)).setParameter("clienteId", clienteId);
        verify(typedQuery, times(1)).getResultList();
    }
}
