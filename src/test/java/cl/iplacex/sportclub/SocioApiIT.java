package cl.iplacex.sportclub;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Prueba de INTEGRACIÓN para la API REST de SportClub.
 * Se ejecuta con: mvn verify
 * Plugin responsable: maven-failsafe-plugin
 *
 * Convención de nombre: termina en IT (Integration Test)
 * para que Surefire la excluya y Failsafe la incluya.
 */
@DisplayName("Pruebas de integración - API SportClub")
public class SocioApiIT {

    private static final String BASE_URL = "http://localhost:8080/api";

    @Test
    @DisplayName("GET /socios debe retornar lista con status 200")
    void testListarSociosRetorna200() {
        given()
            .baseUri(BASE_URL)
        .when()
            .get("/socios")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("POST /socios con datos válidos debe crear socio y retornar 201")
    void testCrearSocioRetorna201() {
        String body = """
            {
                "nombre": "María Rodríguez",
                "email": "maria@sportclub.cl",
                "edad": 28,
                "plan": "PREMIUM"
            }
            """;

        given()
            .baseUri(BASE_URL)
            .contentType("application/json")
            .body(body)
        .when()
            .post("/socios")
        .then()
            .statusCode(201)
            .body("nombre", equalTo("María Rodríguez"))
            .body("email", equalTo("maria@sportclub.cl"));
    }

    @Test
    @DisplayName("GET /socios/{id} con ID inexistente debe retornar 404")
    void testObtenerSocioInexistenteRetorna404() {
        given()
            .baseUri(BASE_URL)
        .when()
            .get("/socios/99999")
        .then()
            .statusCode(404);
    }
}
