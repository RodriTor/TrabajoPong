package clases;

import javax.swing.JFrame;

public class Ventana extends JFrame {

    private static final long serialVersionUID = 1L;

    private final int ancho = 800;
    private final int alto = 500; 
    private Tablero lamina;
    private Hilo hilo;

    public Ventana() {
        setTitle("PONG");
        setSize(ancho, alto);
        setLocationRelativeTo(null);
        setResizable(false);

        lamina = new Tablero();
        add(lamina);

        addKeyListener(new Teclado());
        setFocusable(true);
        requestFocusInWindow();

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        hilo = new Hilo(lamina);
        hilo.start();
    }

    public static void main(String[] args) {
        new Ventana();
    }
}

