package org.Calculadora.model;

public class Calculadora {
    private final Memoria memoria = new Memoria();
    private final Historial historial = new Historial();

    public double sumar(double a, double b)          { return registrar(a, b, '+'); }
    public double restar(double a, double b)         { return registrar(a, b, '-'); }
    public double multiplicar(double a, double b)    { return registrar(a, b, '*'); }
    public double dividir(double a, double b)        { return registrar(a, b, '/'); }

    public double porcentaje(double v)  { return v / 100.0; }
    public double cambiarSigno(double v) { return -v; }

    private double registrar(double a, double b, char op) {
        Operacion operacion = new Operacion(a, b, op);
        double resultado = operacion.ejecutar();
        historial.agregar(operacion);
        return resultado;
    }

    public Memoria getMemoria()     { return memoria; }
    public Historial getHistorial() { return historial; }
}