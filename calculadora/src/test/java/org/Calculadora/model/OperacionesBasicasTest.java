package org.Calculadora.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OperacionesBasicasTest {

    private static final double DELTA = 0.0001;

    @Test
    @DisplayName("U-01: Suma 5 + 3 = 8")
    void sumaBasica() {
        assertEquals(8.0, OperacionesBasicas.suma(5, 3), DELTA);
    }

    @Test
    @DisplayName("U-01b: Suma con negativos -5 + 3 = -2")
    void sumaConNegativos() {
        assertEquals(-2.0, OperacionesBasicas.suma(-5, 3), DELTA);
    }

    @Test
    @DisplayName("U-01c: Suma con cero 0 + 0 = 0")
    void sumaConCero() {
        assertEquals(0.0, OperacionesBasicas.suma(0, 0), DELTA);
    }

    @Test
    @DisplayName("U-02: Resta 10 - 4 = 6")
    void restaBasica() {
        assertEquals(6.0, OperacionesBasicas.resta(10, 4), DELTA);
    }

    @Test
    @DisplayName("U-02b: Resta que da negativo 3 - 10 = -7")
    void restaNegativa() {
        assertEquals(-7.0, OperacionesBasicas.resta(3, 10), DELTA);
    }

    @Test
    @DisplayName("U-03: Multiplicación 6 × 5 = 30")
    void multiplicacionBasica() {
        assertEquals(30.0, OperacionesBasicas.multiplicacion(6, 5), DELTA);
    }

    @Test
    @DisplayName("U-03b: Multiplicación por cero 6 × 0 = 0")
    void multiplicacionPorCero() {
        assertEquals(0.0, OperacionesBasicas.multiplicacion(6, 0), DELTA);
    }

    @Test
    @DisplayName("U-04: División 20 ÷ 4 = 5")
    void divisionBasica() {
        assertEquals(5.0, OperacionesBasicas.division(20, 4), DELTA);
    }

    @Test
    @DisplayName("U-04b: División no exacta 10 ÷ 3 ≈ 3.3333")
    void divisionNoExacta() {
        assertEquals(3.3333, OperacionesBasicas.division(10, 3), 0.001);
    }

    @Test
    @DisplayName("U-05: División 10 ÷ 0 lanza ArithmeticException")
    void divisionEntreCeroLanzaExcepcion() {
        assertThrows(ArithmeticException.class, () -> OperacionesBasicas.division(10, 0));
    }

    @Test
    @DisplayName("U-05b: El mensaje de error es el esperado")
    void mensajeDeErrorDivision() {
        ArithmeticException ex = assertThrows(ArithmeticException.class,
                () -> OperacionesBasicas.division(10, 0));
        assertEquals("No se puede dividir entre 0", ex.getMessage());
    }

    @Test
    @DisplayName("U-06: Decimales 2.5 + 1.5 = 4.0")
    void sumaDecimales() {
        assertEquals(4.0, OperacionesBasicas.suma(2.5, 1.5), DELTA);
    }

    @Test
    @DisplayName("U-06b: Multiplicación decimal 12.5 × 2.4 = 30.0")
    void multiplicacionDecimal() {
        assertEquals(30.0, OperacionesBasicas.multiplicacion(12.5, 2.4), DELTA);
    }
}