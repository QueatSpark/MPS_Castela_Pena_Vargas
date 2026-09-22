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
    private static final Color COLOR_INDICADOR   = new Color(0xFF, 0x4C, 0x4C);

    private JTextField pantalla;
    private JLabel indicadorMemoria;

    private JButton botonAC, botonC, botonBackspace, botonSigno, botonPorcentaje,
            botonIgual, botonPunto, botonHistorial,
            botonMR, botonMC, botonMemoriaMas, botonMemoriaMenos;
    private final Map<String, JButton> botonesNumero = new HashMap<>();
    private final Map<String, JButton> botonesOperador = new HashMap<>();

    public CalculadoraView() {
        setTitle("Calculadora");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(COLOR_FONDO);

        // --- Título con botón Historial a la derecha ---
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(0x1E, 0x2A, 0x4F));

        JLabel titulo = new JLabel("CALCULADORA", SwingConstants.CENTER);
        titulo.setForeground(new Color(0x5B, 0x7F, 0xD6));
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        panelTitulo.add(titulo, BorderLayout.CENTER);

        botonHistorial = new JButton("Historial");
        botonHistorial.setFont(new Font("Segoe UI", Font.BOLD, 12));
        botonHistorial.setBackground(COLOR_BOTON);
        botonHistorial.setForeground(COLOR_TEXTO);
        botonHistorial.setFocusPainted(false);
        botonHistorial.setBorderPainted(false);
        botonHistorial.setOpaque(true);
        botonHistorial.setPreferredSize(new Dimension(80, 40));
        JPanel panelHistorial = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelHistorial.setBackground(new Color(0x1E, 0x2A, 0x4F));
        panelHistorial.add(botonHistorial);
        panelTitulo.add(panelHistorial, BorderLayout.EAST);

        add(panelTitulo, BorderLayout.NORTH);

        // --- Pantalla con indicador M ---
        JPanel panelPantalla = new JPanel(new BorderLayout(5, 5));
        panelPantalla.setBackground(COLOR_FONDO);
        panelPantalla.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        indicadorMemoria = new JLabel("M");
        indicadorMemoria.setFont(new Font("Segoe UI", Font.BOLD, 20));
        indicadorMemoria.setForeground(COLOR_INDICADOR);
        indicadorMemoria.setPreferredSize(new Dimension(30, 30));
        indicadorMemoria.setHorizontalAlignment(SwingConstants.CENTER);
        indicadorMemoria.setVisible(false);

        pantalla = new JTextField("0.00");
        pantalla.setFont(new Font("Segoe UI", Font.BOLD, 32));
        pantalla.setHorizontalAlignment(JTextField.RIGHT);
        pantalla.setEditable(false);
        pantalla.setBackground(COLOR_PANTALLA);
        pantalla.setForeground(Color.BLACK);
        pantalla.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        pantalla.setPreferredSize(new Dimension(320, 70));

        panelPantalla.add(indicadorMemoria, BorderLayout.WEST);
        panelPantalla.add(pantalla, BorderLayout.CENTER);
        add(panelPantalla, BorderLayout.CENTER);

        // --- Panel de botones 5x5 ---
        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setBackground(COLOR_FONDO);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Fila 1: AC  C  ⌫  ÷  M+
        botonAC = crearBotonFuncion("AC");
        botonC = crearBotonFuncion("C");
        botonBackspace = crearBotonFuncion("<_");
        botonMemoriaMas = crearBotonFuncion("M+");
        colocar(panelBotones, gbc, botonAC, 0, 0, 1, 1);
        colocar(panelBotones, gbc, botonC, 1, 0, 1, 1);
        colocar(panelBotones, gbc, botonBackspace, 2, 0, 1, 1);
        agregarOperador(panelBotones, gbc, "÷", 3, 0, 1, 1);
        colocar(panelBotones, gbc, botonMemoriaMas, 4, 0, 1, 1);

        // Fila 2: 7  8  9  ×  M-
        agregarNumero(panelBotones, gbc, "7", 0, 1);
        agregarNumero(panelBotones, gbc, "8", 1, 1);
        agregarNumero(panelBotones, gbc, "9", 2, 1);
        agregarOperador(panelBotones, gbc, "×", 3, 1, 1, 1);
        botonMemoriaMenos = crearBotonFuncion("M-");
        colocar(panelBotones, gbc, botonMemoriaMenos, 4, 1, 1, 1);

        // Fila 3: 4  5  6  -  MR
        agregarNumero(panelBotones, gbc, "4", 0, 2);
        agregarNumero(panelBotones, gbc, "5", 1, 2);
        agregarNumero(panelBotones, gbc, "6", 2, 2);
        agregarOperador(panelBotones, gbc, "-", 3, 2, 1, 1);
        botonMR = crearBotonFuncion("MR");
        colocar(panelBotones, gbc, botonMR, 4, 2, 1, 1);

        // Fila 4: 1  2  3  +  MC
        agregarNumero(panelBotones, gbc, "1", 0, 3);
        agregarNumero(panelBotones, gbc, "2", 1, 3);
        agregarNumero(panelBotones, gbc, "3", 2, 3);
        agregarOperador(panelBotones, gbc, "+", 3, 3, 1, 1);
        botonMC = crearBotonFuncion("MC");
        colocar(panelBotones, gbc, botonMC, 4, 3, 1, 1);

        // Fila 5: ±  0  .  =  %
        botonSigno = crearBotonFuncion("±");
        colocar(panelBotones, gbc, botonSigno, 0, 4, 1, 1);
        agregarNumero(panelBotones, gbc, "0", 1, 4);
        botonPunto = crearBotonNumero(".");
        colocar(panelBotones, gbc, botonPunto, 2, 4, 1, 1);
        botonIgual = crearBotonIgual("=");
        colocar(panelBotones, gbc, botonIgual, 3, 4, 1, 1);
        botonPorcentaje = crearBotonOperador("%");
        colocar(panelBotones, gbc, botonPorcentaje, 4, 4, 1, 1);

        add(panelBotones, BorderLayout.SOUTH);

        pack();
        setSize(460, getHeight() + 40);
        setLocationRelativeTo(null);
    }

    // ---------- Accesores para el controlador ----------
    public String getPantalla() { return pantalla.getText(); }
    public void setPantalla(String texto) { pantalla.setText(texto); }
    public void setIndicadorMemoria(boolean activo) { indicadorMemoria.setVisible(activo); }

    public JButton getBotonAC() { return botonAC; }
    public JButton getBotonC() { return botonC; }
    public JButton getBotonBackspace() { return botonBackspace; }
    public JButton getBotonSigno() { return botonSigno; }
    public JButton getBotonPorcentaje() { return botonPorcentaje; }
    public JButton getBotonIgual() { return botonIgual; }
    public JButton getBotonPunto() { return botonPunto; }
    public JButton getBotonHistorial() { return botonHistorial; }
    public JButton getBotonMR() { return botonMR; }
    public JButton getBotonMC() { return botonMC; }
    public JButton getBotonMemoriaMas() { return botonMemoriaMas; }
    public JButton getBotonMemoriaMenos() { return botonMemoriaMenos; }
    public JButton getBotonNumero(String n) { return botonesNumero.get(n); }
    public JButton getBotonOperador(String op) { return botonesOperador.get(op); }

    // ---------- Fábricas ----------
    private void agregarNumero(JPanel p, GridBagConstraints gbc, String t, int x, int y) {
        JButton b = new JButton(t);
        configurarBoton(b, 22);
        botonesNumero.put(t, b);
        colocar(p, gbc, b, x, y, 1, 1);
    }

    private void agregarOperador(JPanel p, GridBagConstraints gbc, String t, int x, int y, int w, int h) {
        JButton b = new JButton(t);
        configurarBoton(b, 26);
        botonesOperador.put(t, b);
        colocar(p, gbc, b, x, y, w, h);
    }

    private JButton crearBotonNumero(String t) {
        JButton b = new JButton(t); configurarBoton(b, 22); return b;
    }
    private JButton crearBotonFuncion(String t) {
        JButton b = new JButton(t); configurarBoton(b, 18); return b;
    }
    private JButton crearBotonIgual(String t) {
        JButton b = new JButton(t); configurarBoton(b, 26); return b;
    }

    private JButton crearBotonOperador(String texto) {
        JButton b = new JButton(texto);
        configurarBoton(b, 26);
        return b;
    }

    private void configurarBoton(JButton b, int tam) {
        b.setFont(new Font("Segoe UI", Font.BOLD, tam));
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

    private void colocar(JPanel p, GridBagConstraints gbc, JButton b, int x, int y, int w, int h) {
        gbc.gridx = x; gbc.gridy = y; gbc.gridwidth = w; gbc.gridheight = h;
        p.add(b, gbc);
    }
}