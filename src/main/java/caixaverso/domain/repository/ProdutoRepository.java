package caixaverso.domain.repository;

import caixaverso.domain.enums.PerfilRiscoEnum;
import caixaverso.infrastructure.persistence.entity.ProdutoEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository {

    List<ProdutoEntity> listAllProdutos();

    Optional<ProdutoEntity> findByIdOptional(Integer id);

    Optional<ProdutoEntity> findByName(String nome);

    List<ProdutoEntity> findValidByParams(BigDecimal valor, int prazoMeses, String tipoProduto);

    ProdutoEntity persistProduto(ProdutoEntity produto);

    List<ProdutoEntity> buscarPorPerfil(PerfilRiscoEnum perfil);

    List<ProdutoEntity> buscarPorRisco(List<String> riscos);

}
