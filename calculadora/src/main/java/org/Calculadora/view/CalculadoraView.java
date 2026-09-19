package org.Calculadora.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CalculadoraView extends JFrame {

    private static final Color COLOR_FONDO       = new Color(0x2B, 0x3A, 0x67);
    private static final Color COLOR_PANTALLA    = new Color(0xD9, 0xD9, 0xD9);
    private static final Color COLOR_BOTON       = new Color(0x00, 0x00, 0x00);
    private static final Color COLOR_BOTON_HOVER = new Color(0x1A, 0x1A, 0x1A);
    private static final Color COLOR_TEXTO       = new Color(0x87, 0xCE, 0xFA);
    private static final Color COLOR_TEXTO_DIS   = new Color(0x44, 0x44, 0x44);

    private JTextField pantalla;

    // Referencias a los botones para que el controlador los use
    private JButton botonAC, botonC, botonSigno, botonPorcentaje, botonIgual, botonPunto;
    private final Map<String, JButton> botonesNumero = new HashMap<>();
    private final Map<String, JButton> botonesOperador = new HashMap<>();

    public CalculadoraView() {
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(COLOR_FONDO);

        // --- Título ---
        JLabel titulo = new JLabel("CALCULADORA", SwingConstants.CENTER);
        titulo.setOpaque(true);
        titulo.setBackground(new Color(0x1E, 0x2A, 0x4F));
        titulo.setForeground(new Color(0x5B, 0x7F, 0xD6));
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(titulo, BorderLayout.NORTH);

        // --- Pantalla ---
        pantalla = new JTextField("0");
        pantalla.setFont(new Font("Segoe UI", Font.BOLD, 32));
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setEditable(false);
        pantalla.setBackground(COLOR_PANTALLA);
        pantalla.setForeground(Color.BLACK);
        pantalla.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pantalla.setPreferredSize(new Dimension(380, 70));

        JPanel panelPantalla = new JPanel(new BorderLayout());
        panelPantalla.setBackground(COLOR_FONDO);
        panelPantalla.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        panelPantalla.add(pantalla, BorderLayout.CENTER);
        add(panelPantalla, BorderLayout.CENTER);

        // --- Panel de botones ---
        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Fila 1
        botonAC = crearBotonFuncion("AC");
        botonC = crearBotonFuncion("C");
        colocar(panelBotones, gbc, botonAC, 0, 0, 1, 1);
        colocar(panelBotones, gbc, botonC, 1, 0, 1, 1);
        colocar(panelBotones, gbc, crearBotonDeshabilitado("+M"), 2, 0, 1, 1);
        colocar(panelBotones, gbc, crearBotonDeshabilitado("-M"), 3, 0, 1, 1);

        // Fila 2
        agregarNumero(panelBotones, gbc, "7", 0, 1);
        agregarNumero(panelBotones, gbc, "8", 1, 1);
        agregarNumero(panelBotones, gbc, "9", 2, 1);
        agregarOperador(panelBotones, gbc, "÷", 3, 1, 1, 1);
        colocar(panelBotones, gbc, crearBotonDeshabilitado("MRC"), 4, 1, 1, 1);

        // Fila 3
        agregarNumero(panelBotones, gbc, "4", 0, 2);
        agregarNumero(panelBotones, gbc, "5", 1, 2);
        agregarNumero(panelBotones, gbc, "6", 2, 2);
        agregarOperador(panelBotones, gbc, "×", 3, 2, 1, 1);
        botonPorcentaje = crearBotonOperador("%");
        colocar(panelBotones, gbc, botonPorcentaje, 4, 2, 1, 1);

        // Fila 4
        agregarNumero(panelBotones, gbc, "1", 0, 3);
        agregarNumero(panelBotones, gbc, "2", 1, 3);
        agregarNumero(panelBotones, gbc, "3", 2, 3);
        agregarOperador(panelBotones, gbc, "+", 3, 3, 1, 2); // 2 filas
        agregarOperador(panelBotones, gbc, "-", 4, 3, 1, 1);

        // Fila 5
        botonSigno = crearBotonFuncion("±");
        colocar(panelBotones, gbc, botonSigno, 0, 4, 1, 1);

        agregarNumero(panelBotones, gbc, "0", 1, 4);

        botonPunto = crearBotonNumero(".");
        colocar(panelBotones, gbc, botonPunto, 2, 4, 1, 1);

        botonIgual = crearBotonIgual("=");
        colocar(panelBotones, gbc, botonIgual, 4, 4, 1, 1);

        add(panelBotones, BorderLayout.SOUTH);

        pack();
        setSize(420, getHeight() + 40);
        setLocationRelativeTo(null);
    }

    // ---------- Accesores para el controlador ----------

    public String getPantalla() {
        return pantalla.getText();
    }

    public void setPantalla(String texto) {
        pantalla.setText(texto);
    }

    public JButton getBotonAC() { return botonAC; }
    public JButton getBotonC() { return botonC; }
    public JButton getBotonSigno() { return botonSigno; }
    public JButton getBotonPorcentaje() { return botonPorcentaje; }
    public JButton getBotonIgual() { return botonIgual; }
    public JButton getBotonPunto() { return botonPunto; }
    public JButton getBotonNumero(String n) { return botonesNumero.get(n); }
    public JButton getBotonOperador(String op) { return botonesOperador.get(op); }

    // ---------- Fábricas de botones ----------

    private void agregarNumero(JPanel panel, GridBagConstraints gbc,
                               String texto, int x, int y) {
        JButton b = crearBotonNumero(texto);
        botonesNumero.put(texto, b);
        colocar(panel, gbc, b, x, y, 1, 1);
    }

    private void agregarOperador(JPanel panel, GridBagConstraints gbc,
                                 String texto, int x, int y, int w, int h) {
        JButton b = crearBotonOperador(texto);
        botonesOperador.put(texto, b);
        colocar(panel, gbc, b, x, y, w, h);
    }

    private JButton crearBotonNumero(String texto) {
        JButton b = new JButton(texto);
        configurarBoton(b, 22);
        return b;
    }

    private JButton crearBotonOperador(String texto) {
        JButton b = new JButton(texto);
        configurarBoton(b, 26);
        return b;
    }

    private JButton crearBotonFuncion(String texto) {
        JButton b = new JButton(texto);
        configurarBoton(b, 22);
        return b;
    }

    private JButton crearBotonIgual(String texto) {
        JButton b = new JButton(texto);
        configurarBoton(b, 26);
        return b;
    }

    private JButton crearBotonDeshabilitado(String texto) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.BOLD, 20));
        b.setBackground(COLOR_BOTON);
        b.setForeground(COLOR_TEXTO_DIS);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setEnabled(false);
        return b;
    }

    private void configurarBoton(JButton b, int tamanoFuente) {
        b.setFont(new Font("Segoe UI", Font.BOLD, tamanoFuente));
        b.setBackground(COLOR_BOTON);
        b.setForeground(COLOR_TEXTO);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (b.isEnabled()) b.setBackground(COLOR_BOTON_HOVER);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (b.isEnabled()) b.setBackground(COLOR_BOTON);
            }
        });
    }

    private void colocar(JPanel panel, GridBagConstraints gbc, JButton b,
                         int x, int y, int w, int h) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        panel.add(b, gbc);
    }
}