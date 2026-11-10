package caixaverso.interfaceadapter.resource;

import caixaverso.application.usecase.ProdutoUseCase;
import caixaverso.application.dto.ProdutoDTO;
import caixaverso.domain.enums.PerfilRiscoEnum;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/produtos-recomendados")
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class ProdutosResource {

    private final ProdutoUseCase produtoUseCase;

    public ProdutosResource(ProdutoUseCase produtoUseCase) {
        this.produtoUseCase = produtoUseCase;
    }

    @GET
    @Path("/{perfil}")
    @RolesAllowed({"Admin", "User"})
    public Response recomendar(@PathParam("perfil") PerfilRiscoEnum perfil) {
        List<ProdutoDTO> produtos = produtoUseCase.buscarProdutosPorPerfil(perfil);
        if (produtos.isEmpty()) {
            return Response.ok("Nenhum produto encontrato").build();
        }
        return Response.ok(produtos).build();
    }
}
