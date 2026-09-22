package org.Calculadora.controller;

import org.Calculadora.view.CalculadoraView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculadoraControllerTest {

    private CalculadoraView vista;
    private CalculadoraController controller;
    private AtomicReference<String> pantalla;
    private AtomicReference<Boolean> indicador;
    private Map<String, JButton> botones;

    @BeforeEach
    void setUp() {
        vista = mock(CalculadoraView.class);
        pantalla = new AtomicReference<>("0.00");
        indicador = new AtomicReference<>(false);
        botones = new HashMap<>();

        when(vista.getPantalla()).thenAnswer(i -> pantalla.get());
        doAnswer(i -> { pantalla.set(i.getArgument(0)); return null; })
                .when(vista).setPantalla(anyString());
        doAnswer(i -> { indicador.set(i.getArgument(0)); return null; })
                .when(vista).setIndicadorMemoria(anyBoolean());

        String[] nombres = {
                "AC", "C", "⌫", "±", "%", "=", ".", "+", "-", "×", "÷",
                "0","1","2","3","4","5","6","7","8","9",
                "M+","M-","MR","MC","Historial"
        };
        for (String n : nombres) botones.put(n, new JButton(n));

        when(vista.getBotonAC()).thenReturn(botones.get("AC"));
        when(vista.getBotonC()).thenReturn(botones.get("C"));
        when(vista.getBotonBackspace()).thenReturn(botones.get("⌫"));
        when(vista.getBotonSigno()).thenReturn(botones.get("±"));
        when(vista.getBotonPorcentaje()).thenReturn(botones.get("%"));
        when(vista.getBotonIgual()).thenReturn(botones.get("="));
        when(vista.getBotonPunto()).thenReturn(botones.get("."));
        when(vista.getBotonHistorial()).thenReturn(botones.get("Historial"));
        when(vista.getBotonMemoriaMas()).thenReturn(botones.get("M+"));
        when(vista.getBotonMemoriaMenos()).thenReturn(botones.get("M-"));
        when(vista.getBotonMR()).thenReturn(botones.get("MR"));
        when(vista.getBotonMC()).thenReturn(botones.get("MC"));
        when(vista.getBotonNumero(anyString())).thenAnswer(i -> botones.get(i.getArgument(0)));
        when(vista.getBotonOperador(anyString())).thenAnswer(i -> botones.get(i.getArgument(0)));

        controller = new CalculadoraController(vista);
    }

    @Test @DisplayName("U-01: 5 + 3 = 8.00")
    void suma() {
        escribir("5"); presionar("+"); escribir("3"); presionar("=");
        assertEquals("8.00", pantalla.get());
    }

    @Test @DisplayName("U-02: 10 - 4 = 6.00")
    void resta() {
        escribir("10"); presionar("-"); escribir("4"); presionar("=");
        assertEquals("6.00", pantalla.get());
    }

    @Test @DisplayName("U-03: 6 × 5 = 30.00")
    void multiplicacion() {
        escribir("6"); presionar("×"); escribir("5"); presionar("=");
        assertEquals("30.00", pantalla.get());
    }

    @Test @DisplayName("U-04: 20 ÷ 4 = 5.00")
    void division() {
        escribir("20"); presionar("÷"); escribir("4"); presionar("=");
        assertEquals("5.00", pantalla.get());
    }

    @Test @DisplayName("U-05: 10 ÷ 0 = Error")
    void divisionEntreCero() {
        escribir("10"); presionar("÷"); escribir("0"); presionar("=");
        assertEquals("Error", pantalla.get());
    }

    @Test @DisplayName("U-06: 2.5 + 1.5 = 4.00")
    void decimales() {
        escribir("2.5"); presionar("+"); escribir("1.5"); presionar("=");
        assertEquals("4.00", pantalla.get());
    }

    @Test @DisplayName("U-07: 2.5.3 → 2.53")
    void puntoRepetido() {
        escribir("2.5.3");
        assertEquals("2.53", pantalla.get());
    }

    @Test @DisplayName("U-08: 5 → ± → -5.00")
    void cambioSigno() {
        escribir("5"); presionar("±");
        assertEquals("-5.00", pantalla.get());
    }

    @Test @DisplayName("U-09: 50 → % → 0.50")
    void porcentaje() {
        escribir("50"); presionar("%");
        assertEquals("0.50", pantalla.get());
    }

    @Test @DisplayName("U-11: 123 → ⌫ → 12")
    void backspace() {
        escribir("123"); presionar("⌫");
        assertEquals("12", pantalla.get());
    }

    @Test @DisplayName("U-12: AC reinicia a 0.00")
    void clearTodo() {
        escribir("123"); presionar("AC");
        assertEquals("0.00", pantalla.get());
    }

    @Test @DisplayName("U-13: 10 + M+ guarda 10")
    void mPlus() {
        escribir("10"); presionar("M+");
        presionar("MR");
        assertEquals("10.00", pantalla.get());
        assertTrue(indicador.get());
    }

    @Test @DisplayName("U-14: memoria 10, valor 3, M- deja 7")
    void mMinus() {
        escribir("10"); presionar("M+");
        escribir("3"); presionar("M-");
        presionar("MR");
        assertEquals("7.00", pantalla.get());
    }

    @Test @DisplayName("U-15: MR recupera valor guardado")
    void mRecall() {
        escribir("42"); presionar("M+");
        presionar("AC");
        presionar("MR");
        assertEquals("42.00", pantalla.get());
    }

    @Test @DisplayName("U-16: MC limpia la memoria y oculta M")
    void mClear() {
        escribir("42"); presionar("M+");
        presionar("MC");
        assertFalse(indicador.get());
    }

    @Test @DisplayName("I-11: recuperación tras error")
    void recuperacionError() {
        escribir("10"); presionar("÷"); escribir("0"); presionar("=");
        assertEquals("Error", pantalla.get());
        presionar("AC");
        escribir("2"); presionar("+"); escribir("3"); presionar("=");
        assertEquals("5.00", pantalla.get());
    }

    // ---------- Utilidades ----------
    private void escribir(String numero) {
        for (char c : numero.toCharArray()) presionar(c == '.' ? "." : String.valueOf(c));
    }

    private void presionar(String texto) {
        JButton b = botones.get(texto);
        assertNotNull(b, "Botón no encontrado: " + texto);
        b.doClick();
    }
}