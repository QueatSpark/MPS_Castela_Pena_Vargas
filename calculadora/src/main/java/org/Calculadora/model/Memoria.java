package org.Calculadora.model;

public class Memoria {
    private double valor = 0;
    private boolean activa = false;

    public void sumar(double v)    { valor += v; activa = true; }
    public void restar(double v)   { valor -= v; activa = true; }
    public double recuperar()      { return valor; }
    public void limpiar()          { valor = 0; activa = false; }
    public boolean estaActiva()    { return activa; }
    //public double getValor()       { return valor; }
}