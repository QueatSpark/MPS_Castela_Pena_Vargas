package org.Calculadora.model;

import java.util.ArrayList;
import java.util.List;

public class Historial {
    private final List<Operacion> operaciones = new ArrayList<>();

    public void agregar(Operacion op) { operaciones.add(op); }
    public List<Operacion> obtener()  { return new ArrayList<>(operaciones); }
    public void limpiar()             { operaciones.clear(); }
    public boolean estaVacio()        { return operaciones.isEmpty(); }
    public int tamano()               { return operaciones.size(); }
}