package caixaverso.infrastructure.persistence.repository;

import caixaverso.application.dto.TelemetriaItemDTO;
import caixaverso.domain.repository.TelemetriaRepository;
import caixaverso.infrastructure.persistence.entity.TelemetriaEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TelemetriaRepositoryImpl implements PanacheRepositoryBase<TelemetriaEntity, Long>, TelemetriaRepository {


    @Transactional
    public TelemetriaEntity salvar(TelemetriaEntity entity) {
        persist(entity);
        return entity;
    }

    public List<TelemetriaItemDTO> agrupaPorServicoEPeriodo(LocalDate inicio, LocalDate fim) {
        String jpql = "SELECT t.nomeServico, COUNT(t), AVG(t.tempoRespostaMs) FROM TelemetriaEntity t "
                + "WHERE t.dataChamada BETWEEN :inicio AND :fim GROUP BY t.nomeServico";
        return getEntityManager()
                .createQuery(jpql, TelemetriaItemDTO.class)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getResultList();
    }

}
