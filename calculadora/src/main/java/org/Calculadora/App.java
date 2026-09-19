package org.Calculadora;

import org.Calculadora.controller.CalculadoraController;
import org.Calculadora.view.CalculadoraView;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraView vista = new CalculadoraView();
            new CalculadoraController(vista); // el controlador conecta los eventos
            vista.setVisible(true);
        });
    }
}
/*
import java.util.Scanner;
import org.Calculadora.model.*;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            mostrarMenu();
            opcion = (int) pedirOpcion(sc,"Elige una opción: ");

            if (opcion >= 1 && opcion <= 4) {
                double a = pedirNumero(sc, "Ingresa el primer valor: ");
                double b = pedirNumero(sc, "Ingresa el segundo valor: ");

                double resultado = 0;
                switch (opcion) {
                    case 1:
                        resultado = OperacionesBasicas.suma(a, b);
                        break;
                    case 2:
                        resultado = OperacionesBasicas.resta(a, b);
                        break;
                    case 3:
                        resultado = OperacionesBasicas.multiplicacion(a, b);
                        break;
                    case 4:
                        try {
                            resultado = OperacionesBasicas.division(a, b);
                            System.out.println("El resultado es: " + resultado);
                        } catch (ArithmeticException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                }
                System.out.println("El resultado es: " + resultado);
            } else if (opcion != 5) {
                System.out.println("Opción no válida. Intenta de nuevo.");
            }

        } while (opcion != 5);

        System.out.println("Saliendo... ¡Hasta luego!");
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- CALCULADORA ---");
        System.out.println("1. Sumar");
        System.out.println("2. Restar");
        System.out.println("3. Multiplicar");
        System.out.println("4. Dividir");
        System.out.println("5. Salir");
    }

    // Pide un número y lo devuelve
    private static double pedirNumero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (sc.hasNextDouble()) {
                return sc.nextDouble();
            } else {
                System.out.println("Entrada no válida, intenta de nuevo.");
                sc.next();
            }
        }
    }
    private static double pedirOpcion(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (sc.hasNextInt()) {
                return sc.nextInt();
            } else {
                System.out.println("Entrada no válida, intenta de nuevo.");
                sc.next();
            }
        }
    }
}
*/