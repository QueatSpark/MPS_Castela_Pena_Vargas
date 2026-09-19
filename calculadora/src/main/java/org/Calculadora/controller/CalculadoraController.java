package org.Calculadora.controller;

import org.Calculadora.model.OperacionesBasicas;
import org.Calculadora.view.CalculadoraView;

public class CalculadoraController {

    private CalculadoraView vista;

    // Estado de la operación
    private double primerNumero = 0;
    private String operacionPendiente = null;
    private boolean empezandoNuevoNumero = true;

    public CalculadoraController(CalculadoraView vista) {
        this.vista = vista;
        conectarEventos();
    }

    // Conectar cada botón de la vista con su acción en el controlador.
    private void conectarEventos() {
        vista.getBotonAC().addActionListener(e -> limpiarTodo());
        vista.getBotonC().addActionListener(e -> limpiarEntrada());
        vista.getBotonSigno().addActionListener(e -> cambiarSigno());
        vista.getBotonPorcentaje().addActionListener(e -> aplicarPorcentaje());
        vista.getBotonIgual().addActionListener(e -> calcularResultado());

        // Números
        vista.getBotonNumero("0").addActionListener(e -> agregarDigito("0"));
        vista.getBotonNumero("1").addActionListener(e -> agregarDigito("1"));
        vista.getBotonNumero("2").addActionListener(e -> agregarDigito("2"));
        vista.getBotonNumero("3").addActionListener(e -> agregarDigito("3"));
        vista.getBotonNumero("4").addActionListener(e -> agregarDigito("4"));
        vista.getBotonNumero("5").addActionListener(e -> agregarDigito("5"));
        vista.getBotonNumero("6").addActionListener(e -> agregarDigito("6"));
        vista.getBotonNumero("7").addActionListener(e -> agregarDigito("7"));
        vista.getBotonNumero("8").addActionListener(e -> agregarDigito("8"));
        vista.getBotonNumero("9").addActionListener(e -> agregarDigito("9"));
        vista.getBotonPunto().addActionListener(e -> agregarDigito("."));

        // Operadores
        vista.getBotonOperador("+").addActionListener(e -> seleccionarOperacion("+"));
        vista.getBotonOperador("-").addActionListener(e -> seleccionarOperacion("-"));
        vista.getBotonOperador("×").addActionListener(e -> seleccionarOperacion("×"));
        vista.getBotonOperador("÷").addActionListener(e -> seleccionarOperacion("÷"));
    }

    // ---------- Lógica de la calculadora ----------

    private void agregarDigito(String digito) {
        if (empezandoNuevoNumero) {
            if (digito.equals(".")) {
                vista.setPantalla("0.");
            } else {
                vista.setPantalla(digito);
            }
            empezandoNuevoNumero = false;
        } else {
            String actual = vista.getPantalla();
            if (digito.equals(".") && actual.contains(".")) {
                return;
            }
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
        if (operacionPendiente == null) {
            return;
        }

        double segundoNumero = Double.parseDouble(vista.getPantalla());
        double resultado = 0;

        try {
            switch (operacionPendiente) {
                case "+":
                    resultado = OperacionesBasicas.suma(primerNumero, segundoNumero);
                    break;
                case "-":
                    resultado = OperacionesBasicas.resta(primerNumero, segundoNumero);
                    break;
                case "×":
                    resultado = OperacionesBasicas.multiplicacion(primerNumero, segundoNumero);
                    break;
                case "÷":
                    resultado = OperacionesBasicas.division(primerNumero, segundoNumero);
                    break;
                default:
                    resultado = segundoNumero;
                    break;
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

    private void limpiarTodo() {
        vista.setPantalla("0");
        primerNumero = 0;
        operacionPendiente = null;
        empezandoNuevoNumero = true;
    }

    private void limpiarEntrada() {
        vista.setPantalla("0");
        empezandoNuevoNumero = true;
    }

    private void cambiarSigno() {
        double valor = Double.parseDouble(vista.getPantalla());
        vista.setPantalla(formatear(-valor));
    }

    private void aplicarPorcentaje() {
        double valor = Double.parseDouble(vista.getPantalla());
        vista.setPantalla(formatear(valor / 100));
        empezandoNuevoNumero = true;
    }

    private String formatear(double valor) {
        if (valor == (long) valor) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }
}