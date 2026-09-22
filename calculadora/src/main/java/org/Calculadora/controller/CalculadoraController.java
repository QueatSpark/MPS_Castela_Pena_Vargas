package org.Calculadora.controller;

import org.Calculadora.model.OperacionesBasicas;
import org.Calculadora.view.CalculadoraView;

public class CalculadoraController {

    private CalculadoraView vista;

    // Estado de la operación
    private double primerNumero = 0;
    private String operacionPendiente = null;
    private boolean empezandoNuevoNumero = true;

    // Estado de la memoria
    private double memoria = 0;
    private boolean memoriaActiva = false;
    private boolean mrcPresionadoReciente = false;

    public CalculadoraController(CalculadoraView vista) {
        this.vista = vista;
        conectarEventos();
    }

    /** Conecta cada botón de la vista con su acción en el controlador. */
    private void conectarEventos() {
        // Botones de función
        vista.getBotonAC().addActionListener(e -> limpiarTodo());
        vista.getBotonC().addActionListener(e -> limpiarEntrada());
        vista.getBotonSigno().addActionListener(e -> cambiarSigno());
        vista.getBotonPorcentaje().addActionListener(e -> aplicarPorcentaje());
        vista.getBotonIgual().addActionListener(e -> calcularResultado());

        // Botones de memoria
        vista.getBotonMemoriaMas().addActionListener(e -> memoriaSumar());
        vista.getBotonMemoriaMenos().addActionListener(e -> memoriaRestar());
        vista.getBotonMRC().addActionListener(e -> memoriaRecallOClear());

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
        // Si el usuario empieza a escribir, se rompe la "cadena" del MRC
        mrcPresionadoReciente = false;

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
        mrcPresionadoReciente = false;

        if (!empezandoNuevoNumero || operacionPendiente == null) {
            primerNumero = Double.parseDouble(vista.getPantalla());
        }
        operacionPendiente = op;
        empezandoNuevoNumero = true;
    }

    private void calcularResultado() {
        mrcPresionadoReciente = false;

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
        mrcPresionadoReciente = false;
        vista.setPantalla("0");
        primerNumero = 0;
        operacionPendiente = null;
        empezandoNuevoNumero = true;
    }

    private void limpiarEntrada() {
        mrcPresionadoReciente = false;
        vista.setPantalla("0");
        empezandoNuevoNumero = true;
    }

    private void cambiarSigno() {
        mrcPresionadoReciente = false;
        double valor = Double.parseDouble(vista.getPantalla());
        vista.setPantalla(formatear(-valor));
    }

    private void aplicarPorcentaje() {
        mrcPresionadoReciente = false;
        double valor = Double.parseDouble(vista.getPantalla());
        vista.setPantalla(formatear(valor / 100));
        empezandoNuevoNumero = true;
    }

    // ---------- Lógica de la memoria ----------

    /** +M : suma el valor actual a la memoria. */
    private void memoriaSumar() {
        double valor = Double.parseDouble(vista.getPantalla());
        memoria += valor;
        memoriaActiva = true;
        mrcPresionadoReciente = false;

        actualizarIndicadorMemoria();
        empezandoNuevoNumero = true; // el próximo dígito empieza un número nuevo
    }

    /** -M : resta el valor actual a la memoria. */
    private void memoriaRestar() {
        double valor = Double.parseDouble(vista.getPantalla());
        memoria -= valor;
        memoriaActiva = true;
        mrcPresionadoReciente = false;

        actualizarIndicadorMemoria();
        empezandoNuevoNumero = true;
    }

    /**
     * MRC :
     *   - Primer clic: recuerda (muestra) el valor en memoria.
     *   - Segundo clic consecutivo: borra la memoria.
     */
    private void memoriaRecallOClear() {
        if (mrcPresionadoReciente) {
            // Segundo clic seguido -> borrar memoria
            memoria = 0;
            memoriaActiva = false;
            mrcPresionadoReciente = false;
            actualizarIndicadorMemoria();
        } else {
            // Primer clic -> mostrar valor en memoria
            if (memoriaActiva) {
                vista.setPantalla(formatear(memoria));
                empezandoNuevoNumero = true;
            }
            mrcPresionadoReciente = true;
        }
    }

    /** Muestra u oculta el indicador "M" en la pantalla. */
    private void actualizarIndicadorMemoria() {
        vista.setIndicadorMemoria(memoriaActiva);
    }

    private String formatear(double valor) {
        if (valor == (long) valor) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }
}
/*
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
*/