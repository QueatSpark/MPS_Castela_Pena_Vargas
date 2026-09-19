package org.Calculadora.model;

public class OperacionesBasicas {

    public static double suma(double sumando1 , double sumando2) {
        return sumando1 + sumando2;
    }

    public static double resta (double minuendo , double sustraendo) {
        return minuendo - sustraendo;
    }

    public static double multiplicacion (double factor1 , double factor2) {
        return factor1 * factor2;
    }

    public static double division (double dividendo , double divisor) {
        if (divisor == 0) throw new ArithmeticException("No se puede dividir entre 0");
        return dividendo / divisor;
    }
}

