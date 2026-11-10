package caixaverso.interfaceadapter.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;

@QuarkusTest
class AutenticacaoResourceTest {

    @Test
    void loginComCredenciaisValidasRetornaToken() {
        given()
                .contentType("application/json")
                .body("{\"usuario\":\"admin\",\"senha\":\"admin\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body(not(emptyString()));
    }

    @Test
    void loginComSenhaInvalidaRetorna401() {
        given()
                .contentType("application/json")
                .body("{\"usuario\":\"admin\",\"senha\":\"senha-errada\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);
    }

    @Test
    void tokenDeAdminAcessaEndpointProtegido() {
        String token = given()
                .contentType("application/json")
                .body("{\"usuario\":\"admin\",\"senha\":\"admin\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().asString();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/investimentos/1")
                .then()
                .statusCode(200);
    }

    @Test
    void tokenDeUserNaoAcessaEndpointAdmin() {
        String token = given()
                .contentType("application/json")
                .body("{\"usuario\":\"user\",\"senha\":\"user\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().asString();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/investimentos/1")
                .then()
                .statusCode(403);
    }
}
