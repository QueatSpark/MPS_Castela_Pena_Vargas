package org.Calculadora.view;

import org.Calculadora.model.Historial;
import org.Calculadora.model.Operacion;

import javax.swing.*;
import java.awt.*;

public class HistorialView extends JDialog {

    public HistorialView(JFrame padre, Historial historial) {
        super(padre, "Historial de operaciones", true);
        setSize(320, 400);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 14));
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (historial.estaVacio()) {
            area.setText("El historial está vacío.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Operacion op : historial.obtener()) {
                sb.append(op.toString()).append("\n");
            }
            area.setText(sb.toString());
        }

        add(new JScrollPane(area), BorderLayout.CENTER);

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dispose());
        JPanel sur = new JPanel();
        sur.add(cerrar);
        add(sur, BorderLayout.SOUTH);
    }
}