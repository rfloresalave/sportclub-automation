package cl.iplacex.sportclub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba UNITARIA del servicio de socios de SportClub.
 * Se ejecuta con: mvn test
 * Plugin responsable: maven-surefire-plugin
 */
@DisplayName("Pruebas unitarias - ServicioSocio")
public class ServicioSocioTest {

    private ServicioSocio servicio;

    @BeforeEach
    void setUp() {
        // Se instancia el servicio antes de cada prueba
        servicio = new ServicioSocio();
    }

    @Test
    @DisplayName("Registrar socio con datos válidos debe retornar true")
    void testRegistrarSocioValido() {
        boolean resultado = servicio.registrar("Juan Pérez", "juan@email.com", 25);
        assertTrue(resultado, "El registro de un socio válido debe ser exitoso");
    }

    @Test
    @DisplayName("Registrar socio con email nulo debe lanzar excepción")
    void testRegistrarSocioEmailNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.registrar("Ana García", null, 30);
        }, "Debe lanzar excepción cuando el email es nulo");
    }

    @Test
    @DisplayName("Registrar socio menor de edad debe retornar false")
    void testRegistrarSocioMenorDeEdad() {
        boolean resultado = servicio.registrar("Carlos López", "carlos@email.com", 15);
        assertFalse(resultado, "No se debe permitir el registro de menores de 18 años");
    }

    @Test
    @DisplayName("Calcular cuota mensual debe retornar valor correcto")
    void testCalcularCuotaMensual() {
        double cuota = servicio.calcularCuota("BASICO");
        assertEquals(15000.0, cuota, 0.01, "La cuota básica debe ser $15.000");
    }
}
