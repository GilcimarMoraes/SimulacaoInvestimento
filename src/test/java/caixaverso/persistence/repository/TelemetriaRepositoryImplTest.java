package caixaverso.persistence.repository;

import caixaverso.application.dto.TelemetriaItemDTO;
import caixaverso.infrastructure.persistence.entity.SimulacaoEntity;
import caixaverso.infrastructure.persistence.entity.TelemetriaEntity;
import caixaverso.infrastructure.persistence.repository.TelemetriaRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelemetriaRepositoryImplTest {

    @Mock
    EntityManager em;

    @Mock
    TypedQuery<TelemetriaItemDTO> query;

    TelemetriaRepositoryImpl repository;



    @Test
    void salvar_devePersistirERetornarEntidade_unitario() {
        TelemetriaRepositoryImpl repository = Mockito.spy(new TelemetriaRepositoryImpl());

        doNothing().when(repository).persist(any(TelemetriaEntity.class));

        TelemetriaEntity entity = new TelemetriaEntity();
        TelemetriaEntity result = repository.salvar(entity);

        assertSame(entity, result);
        verify(repository, times(1)).persist(entity);
    }


    @Test
    void agrupaPorServicoEPeriodo_deveExecutarQueryEPopularLista() {

        repository = Mockito.spy(new TelemetriaRepositoryImpl());
        doReturn(em).when(repository).getEntityManager();

        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = LocalDate.of(2025, 1, 31);

        TelemetriaItemDTO dto =
                new TelemetriaItemDTO("servico-x", 10L, 150.0);

        when(em.createQuery(anyString(), eq(TelemetriaItemDTO.class)))
                .thenReturn(query);

        when(query.setParameter(eq("inicio"), eq(inicio))).thenReturn(query);
        when(query.setParameter(eq("fim"), eq(fim))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(dto));

        List<TelemetriaItemDTO> result =
                repository.agrupaPorServicoEPeriodo(inicio, fim);

        assertEquals(1, result.size());
        assertEquals("servico-x", result.get(0).nome());
        assertEquals(10L, result.get(0).quantidadeChamadas());
        assertEquals(150.0, result.get(0).mediaTempoRespostaMs());
    }

}
