package org.Calculadora.controller;

import org.Calculadora.model.Calculadora;
import org.Calculadora.model.Operacion;
import org.Calculadora.view.CalculadoraView;
import org.Calculadora.view.HistorialView;

public class CalculadoraController {

    private final CalculadoraView vista;
    private final Calculadora modelo = new Calculadora();

    private double primerNumero = 0;
    private String operacionPendiente = null;
    private boolean empezandoNuevoNumero = true;

    public CalculadoraController(CalculadoraView vista) {
        this.vista = vista;
        conectarEventos();
    }

    private void conectarEventos() {
        // Función
        vista.getBotonAC().addActionListener(e -> limpiarTodo());
        vista.getBotonC().addActionListener(e -> limpiarEntrada());
        vista.getBotonBackspace().addActionListener(e -> borrarUltimo());
        vista.getBotonSigno().addActionListener(e -> cambiarSigno());
        vista.getBotonPorcentaje().addActionListener(e -> aplicarPorcentaje());
        vista.getBotonIgual().addActionListener(e -> calcularResultado());
        vista.getBotonHistorial().addActionListener(e -> mostrarHistorial());

        // Memoria
        vista.getBotonMemoriaMas().addActionListener(e -> memoriaSumar());
        vista.getBotonMemoriaMenos().addActionListener(e -> memoriaRestar());
        vista.getBotonMR().addActionListener(e -> memoriaRecall());
        vista.getBotonMC().addActionListener(e -> memoriaClear());

        // Números
        for (int i = 0; i <= 9; i++) {
            final String n = String.valueOf(i);
            vista.getBotonNumero(n).addActionListener(e -> agregarDigito(n));
        }
        vista.getBotonPunto().addActionListener(e -> agregarDigito("."));

        // Operadores
        vista.getBotonOperador("+").addActionListener(e -> seleccionarOperacion("+"));
        vista.getBotonOperador("-").addActionListener(e -> seleccionarOperacion("-"));
        vista.getBotonOperador("×").addActionListener(e -> seleccionarOperacion("×"));
        vista.getBotonOperador("÷").addActionListener(e -> seleccionarOperacion("÷"));
    }

    // ---------- Entrada ----------
    private void agregarDigito(String digito) {
        if (empezandoNuevoNumero) {
            vista.setPantalla(digito.equals(".") ? "0." : digito);
            empezandoNuevoNumero = false;
        } else {
            String actual = vista.getPantalla();
            if (digito.equals(".") && actual.contains(".")) return;
            vista.setPantalla(actual + digito);
        }
    }

    private void seleccionarOperacion(String op) {
        if (!empezandoNuevoNumero || operacionPendiente == null) {
            primerNumero = Double.parseDouble(vista.getPantalla());
        }
        operacionPendiente = op;
        empezandoNuevoNumero = true;
    }

    private void calcularResultado() {
        if (operacionPendiente == null) return;

        double segundoNumero = Double.parseDouble(vista.getPantalla());
        try {
            double resultado;
            switch (operacionPendiente) {
                case "+": resultado = modelo.sumar(primerNumero, segundoNumero); break;
                case "-": resultado = modelo.restar(primerNumero, segundoNumero); break;
                case "×": resultado = modelo.multiplicar(primerNumero, segundoNumero); break;
                case "÷": resultado = modelo.dividir(primerNumero, segundoNumero); break;
                default:  resultado = segundoNumero; break;
            }
            vista.setPantalla(formatear(resultado));
            primerNumero = resultado;
            operacionPendiente = null;
            empezandoNuevoNumero = true;
        } catch (ArithmeticException ex) {
            vista.setPantalla("Error");
            primerNumero = 0;
            operacionPendiente = null;
            empezandoNuevoNumero = true;
        }
    }

    // ---------- Funciones ----------
    private void limpiarTodo() {
        vista.setPantalla("0.00");
        primerNumero = 0;
        operacionPendiente = null;
        empezandoNuevoNumero = true;
    }

    private void limpiarEntrada() {
        vista.setPantalla("0.00");
        empezandoNuevoNumero = true;
    }

    private void borrarUltimo() {
        String actual = vista.getPantalla();
        if (actual.length() > 1 && !actual.equals("0.00")) {
            String nuevo = actual.substring(0, actual.length() - 1);
            if (nuevo.isEmpty() || nuevo.equals("-")) nuevo = "0";
            vista.setPantalla(nuevo);
        } else {
            vista.setPantalla("0");
            empezandoNuevoNumero = true;
        }
    }

    private void cambiarSigno() {
        double v = Double.parseDouble(vista.getPantalla());
        vista.setPantalla(formatear(modelo.cambiarSigno(v)));
    }

    private void aplicarPorcentaje() {
        double v = Double.parseDouble(vista.getPantalla());
        vista.setPantalla(formatear(modelo.porcentaje(v)));
        empezandoNuevoNumero = true;
    }

    private void mostrarHistorial() {
        new HistorialView(vista, modelo.getHistorial()).setVisible(true);
    }

    // ---------- Memoria ----------
    private void memoriaSumar() {
        modelo.getMemoria().sumar(Double.parseDouble(vista.getPantalla()));
        vista.setIndicadorMemoria(modelo.getMemoria().estaActiva());
        empezandoNuevoNumero = true;
    }

    private void memoriaRestar() {
        modelo.getMemoria().restar(Double.parseDouble(vista.getPantalla()));
        vista.setIndicadorMemoria(modelo.getMemoria().estaActiva());
        empezandoNuevoNumero = true;
    }

    private void memoriaRecall() {
        if (modelo.getMemoria().estaActiva()) {
            vista.setPantalla(formatear(modelo.getMemoria().recuperar()));
            empezandoNuevoNumero = true;
        }
    }

    private void memoriaClear() {
        modelo.getMemoria().limpiar();
        vista.setIndicadorMemoria(false);
    }

    // ---------- Formato ----------
    private String formatear(double v) {
        return String.format("%.2f", v);
    }
}