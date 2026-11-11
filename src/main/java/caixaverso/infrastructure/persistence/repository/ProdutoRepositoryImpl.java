package caixaverso.infrastructure.persistence.repository;

import caixaverso.domain.enums.PerfilRiscoEnum;
import caixaverso.domain.repository.ProdutoRepository;
import caixaverso.infrastructure.persistence.entity.ProdutoEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@ApplicationScoped
public class ProdutoRepositoryImpl implements ProdutoRepository, PanacheRepositoryBase<ProdutoEntity, Integer> {

    @Override
    public List<ProdutoEntity> listAllProdutos() {
        return listAll();
    }

    @Override
    public Optional<ProdutoEntity> findByIdOptional(Integer id) {
        return find("id", id).firstResultOptional();
    }

    @Override
    public Optional<ProdutoEntity> findByName(String nome) {
        return find("nome", nome).firstResultOptional();
    }

    @Override
    public Optional<ProdutoEntity> findByTipo(String tipo) {
        return find("lower(tipo) = ?1 order by id", tipo.toLowerCase(Locale.ROOT))
                .firstResultOptional();
    }

    @Override
    public List<ProdutoEntity> findValidByParams(BigDecimal valor, int prazoMeses, String tipoProduto) {
        String jpql = "FROM ProdutoEntity p WHERE p.minValor <= :valor "
                + "AND (p.maxValor IS NULL OR p.maxValor >= :valor) "
                + "AND p.minPrazo <= :prazo "
                + "AND (p.maxPrazo IS NULL OR p.maxPrazo >= :prazo)";

        if (tipoProduto != null && !tipoProduto.isBlank()) {
            jpql += " AND p.tipo = :tipo";
            return getEntityManager()
                    .createQuery(jpql, ProdutoEntity.class)
                    .setParameter("valor", valor)
                    .setParameter("prazo", prazoMeses)
                    .setParameter("tipo", tipoProduto)
                    .getResultList();
        } else {
            return getEntityManager()
                    .createQuery(jpql, ProdutoEntity.class)
                    .setParameter("valor", valor)
                    .setParameter("prazo", prazoMeses)
                    .getResultList();
        }
    }

    @Override
    public ProdutoEntity persistProduto(ProdutoEntity produto) {
        persist(produto);
        return produto;
    }

    @Override
    public List<ProdutoEntity> buscarPorPerfil(PerfilRiscoEnum perfil) {
        // Recomenda produtos até o limite de risco do perfil:
        // conservador = Baixo; moderado = Baixo + Médio; agressivo = todos.
        return switch (perfil) {
            case CONSERVADOR -> buscarPorRisco(List.of("Baixo"));
            case MODERADO -> buscarPorRisco(List.of("Baixo", "Médio"));
            case AGRESSIVO -> buscarPorRisco(List.of("Baixo", "Médio", "Alto"));
            default -> Collections.emptyList();
        };
    }

    @Override
    public List<ProdutoEntity> buscarPorRisco(List<String> riscos) {
        return list("risco in ?1", riscos);
    }
}
