package org.Calculadora.model;

public class Operacion {
    private final double operando1;
    private final double operando2;
    private final char operador;
    private double resultado;

    public Operacion(double operando1, double operando2, char operador) {
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operador = operador;
    }

    public double ejecutar() {
        switch (operador) {
            case '+': resultado = OperacionesBasicas.suma(operando1, operando2); break;
            case '-': resultado = OperacionesBasicas.resta(operando1, operando2); break;
            case '*': resultado = OperacionesBasicas.multiplicacion(operando1, operando2); break;
            case '/': resultado = OperacionesBasicas.division(operando1, operando2); break;
            default: throw new IllegalArgumentException("Operador no válido: " + operador);
        }
        return resultado;
    }

    public double getResultado() { return resultado; }

    @Override
    public String toString() {
        return String.format("%.2f %c %.2f = %.2f", operando1, operador, operando2, resultado);
    }
}