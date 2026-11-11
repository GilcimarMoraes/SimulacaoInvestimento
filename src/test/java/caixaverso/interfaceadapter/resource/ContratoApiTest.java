package caixaverso.interfaceadapter.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Valida o contrato dos endpoints descritos na solicitacao.
 * Os valores que dependem do seed (ids, nome do produto, datas) sao verificados
 * pela estrutura/tipo, nao por igualdade literal.
 */
@QuarkusTest
class ContratoApiTest {

    private static final String SIMULACAO_REQUEST = """
            {"clienteId": 123, "valor": 10000.00, "prazoMeses": 12, "tipoProduto": "CDB"}
            """;
    private static final String DATA_HORA_ISO = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z";
    private static final String DATA_ISO = "\\d{4}-\\d{2}-\\d{2}";

    @Test
    void simulacaoRespeitaContrato() {
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(SIMULACAO_REQUEST)
                .when()
                .post("/simular-investimento")
                .then()
                .statusCode(200)
                .body("produtoValidado.id", not(nullValue()))
                .body("produtoValidado.nome", not(nullValue()))
                .body("produtoValidado.tipo", is("CDB"))
                .body("produtoValidado.rentabilidade", not(nullValue()))
                .body("produtoValidado.risco", not(nullValue()))
                .body("resultadoSimulacao.valorFinal", not(nullValue()))
                .body("resultadoSimulacao.rentabilidadeEfetiva", not(nullValue()))
                .body("resultadoSimulacao.prazoMeses", is(12))
                .body("dataSimulacao", matchesPattern(DATA_HORA_ISO));

        BigDecimal rentabilidade = new BigDecimal(
                response.extract().path("produtoValidado.rentabilidade").toString());
        BigDecimal valorFinal = new BigDecimal(
                response.extract().path("resultadoSimulacao.valorFinal").toString());
        BigDecimal esperado = new BigDecimal("10000.00")
                .multiply(BigDecimal.ONE.add(rentabilidade))
                .setScale(2, RoundingMode.HALF_UP);

        assertEquals(0, esperado.compareTo(valorFinal),
                "valorFinal deve refletir a rentabilidade do produto validado");
    }

    @Test
    void historicoDeSimulacoesRespeitaContrato() {
        criarSimulacao();
        String token = adminToken();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/simulacoes")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].id", not(nullValue()))
                .body("[0].clienteId", not(nullValue()))
                .body("[0].produto", not(nullValue()))
                .body("[0].valorInvestido", not(nullValue()))
                .body("[0].valorFinal", not(nullValue()))
                .body("[0].prazoMeses", not(nullValue()))
                .body("[0].dataSimulacao", matchesPattern(DATA_HORA_ISO));
    }

    @Test
    void agregacaoPorProdutoDiaRespeitaContrato() {
        criarSimulacao();
        String token = adminToken();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/simulacoes/por-produto-dia")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].produto", not(nullValue()))
                .body("[0].data", matchesPattern(DATA_ISO))
                .body("[0].quantidadeSimulacoes", not(nullValue()))
                .body("[0].mediaValorFinal", not(nullValue()));
    }

    @Test
    void telemetriaRespeitaContrato() {
        criarSimulacao();
        String token = adminToken();

        // tambem gera telemetria para o servico perfil-risco
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/perfil-risco/1")
                .then()
                .statusCode(200);

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/telemetria")
                .then()
                .statusCode(200)
                .body("servicos.size()", greaterThan(0))
                .body("servicos[0].nome", not(nullValue()))
                .body("servicos[0].quantidadeChamadas", not(nullValue()))
                .body("servicos[0].mediaTempoRespostaMs", not(nullValue()))
                .body("periodo.inicio", matchesPattern(DATA_ISO))
                .body("periodo.fim", matchesPattern(DATA_ISO));
    }

    @Test
    void perfilRiscoRespeitaContrato() {
        String token = adminToken();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/perfil-risco/1")
                .then()
                .statusCode(200)
                .body("clienteId", is(1))
                .body("perfil", anyOf(is("Conservador"), is("Moderado"), is("Agressivo")))
                .body("pontuacao", not(nullValue()))
                .body("descricao", not(nullValue()));
    }

    @Test
    void produtosRecomendadosRespeitaContratoEAceitaPerfilComQualquerCaixa() {
        String token = adminToken();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/produtos-recomendados/Conservador")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].id", not(nullValue()))
                .body("[0].nome", not(nullValue()))
                .body("[0].tipo", not(nullValue()))
                .body("[0].rentabilidade", not(nullValue()))
                .body("[0].risco", not(nullValue()));

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/produtos-recomendados/conservador")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/produtos-recomendados/Agressivo")
                .then()
                .statusCode(200)
                .body("risco", hasItems("Baixo", "Alto"));
    }

    @Test
    void investimentosRespeitaContratoSemClienteId() {
        String token = adminToken();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/investimentos/1")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("[0].id", not(nullValue()))
                .body("[0].tipo", not(nullValue()))
                .body("[0].valor", not(nullValue()))
                .body("[0].rentabilidade", not(nullValue()))
                .body("[0].data", matchesPattern(DATA_ISO))
                .body("[0].clienteId", nullValue());
    }

    private void criarSimulacao() {
        given()
                .contentType("application/json")
                .body(SIMULACAO_REQUEST)
                .when()
                .post("/simular-investimento")
                .then()
                .statusCode(200);
    }

    private String adminToken() {
        return given()
                .contentType("application/json")
                .body("{\"usuario\":\"admin\",\"senha\":\"admin\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .asString();
    }
}
