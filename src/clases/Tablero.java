package clases;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Tablero extends JPanel {

    private static final long serialVersionUID = 1L;

    private Pelota pelota = new Pelota(385, 215);
    private Raqueta r1 = new Raqueta(10, 200);
    private Raqueta r2 = new Raqueta(765, 200);

    private int punj1 = 0;
    private int puntj2 = 0;
    private final int punmax = 5;
    private boolean juegoTerminado = false;

    private long tiempoInicio;
    private final long tiempoLimite = 60_000; 
    public Tablero() {
        setBackground(Color.BLACK);


    }
    
    

    public void iniciarTimer() {
        tiempoInicio = System.currentTimeMillis();
    }

    @Override
    
    
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Fondo con gradiente
        GradientPaint grad = new GradientPaint(0, 0, new Color(10, 10, 30), getWidth(), getHeight(), new Color(30, 0, 50));
        g2.setPaint(grad);
        g2.fillRect(0, 0, getWidth(), getHeight());


        g2.setColor(Color.GREEN);
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{15}, 0));
        g2.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        g2.setStroke(oldStroke);

        // Pelota color neón
        g2.setColor(new Color(0, 255, 200));
        g2.fill(pelota.getPelota());

        // Raquetas color neón
        g2.setColor(new Color(255, 100, 100));
        g2.fill(r1.getRaqueta());

        g2.setColor(new Color(100, 150, 255));
        g2.fill(r2.getRaqueta());

        // Marcador
        g2.setFont(new Font("Consolas", Font.BOLD, 40));
        g2.setColor(Color.WHITE);
        String textoP1 = String.valueOf(punj1);
        int anchoTextoP1 = g2.getFontMetrics().stringWidth(textoP1);
        g2.drawString(textoP1, getWidth() / 4 - anchoTextoP1 / 2, 60);

        String textoP2 = String.valueOf(puntj2);
        int anchoTextoP2 = g2.getFontMetrics().stringWidth(textoP2);
        g2.drawString(textoP2, getWidth() * 3 / 4 - anchoTextoP2 / 2, 60);

        // Mostrar tiempo restante en segundos arriba al centro
        long tiempoPasado = System.currentTimeMillis() - tiempoInicio;
        long tiempoRestante = Math.max(0, tiempoLimite - tiempoPasado);
        String textoTiempo = "Tiempo: " + (tiempoRestante / 1000) + "s";
        int anchoTiempo = g2.getFontMetrics().stringWidth(textoTiempo);
        g2.drawString(textoTiempo, getWidth() / 2 - anchoTiempo / 2, 60);

        // Texto ganador
        if (juegoTerminado) {
            String ganador;
            if (punj1 > puntj2) {
                ganador = "¡GANA JUGADOR 1!";
            } else if (puntj2 > punj1) {
                ganador = "¡GANA JUGADOR 2!";
            } else {
                ganador = "🏆 EMPATE 🏆";
            }
            g2.setFont(new Font("Consolas", Font.BOLD, 48));
            g2.setColor(Color.YELLOW);
            int anchoGanador = g2.getFontMetrics().stringWidth(ganador);

            
            g2.setColor(Color.BLACK);
            g2.drawString(ganador, getWidth() / 2 - anchoGanador / 2 + 3, getHeight() / 2 + 3);
            g2.setColor(Color.YELLOW);
            g2.drawString(ganador, getWidth() / 2 - anchoGanador / 2, getHeight() / 2);
        }
    }

    public void actualizar() {
        if (juegoTerminado) return;

        boolean colisionR1 = colision(r1.getRaqueta());
        boolean colisionR2 = colision(r2.getRaqueta());

        pelota.mover(getBounds(), colisionR1, colisionR2, r1, r2);

        int margen = 10;
        r2.setX(getWidth() - margen - r2.getAncho());

        if (Teclado.w) {
            r1.moverArriba(getHeight());
        }
        if (Teclado.s) {
            r1.moverAbajo(getHeight());
        }
        if (Teclado.up) {
            r2.moverArriba(getHeight());
        }
        if (Teclado.down) {
            r2.moverAbajo(getHeight());
        }

        if (pelota.getX() < 0) {
            puntj2++;
            resetearPelota();
        } else if (pelota.getX() > getWidth() - pelota.getAncho()) {
            punj1++;
            resetearPelota();
        }

        // Verificar fin: si alguno llegó a 5 o se acabó el tiempo
        long tiempoPasado = System.currentTimeMillis() - tiempoInicio;
        if (punj1 >= punmax || puntj2 >= punmax || tiempoPasado >= tiempoLimite) {
            juegoTerminado = true;
        }
    }

    private boolean colision(Rectangle2D raqueta) {
        return pelota.getPelota().intersects(raqueta);
    }

    private void resetearPelota() {
        pelota.setX(getWidth() / 2 - pelota.getAncho() / 2);
        pelota.setY(getHeight() / 2 - pelota.getAlto() / 2);
        Random rnd = new Random();
        pelota.setDx(rnd.nextBoolean() ? 2 : -2);
        pelota.setDy(rnd.nextBoolean() ? 2 : -2);
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public void reiniciarJuego() {
        punj1 = 0;
        puntj2 = 0;
        juegoTerminado = false;
        tiempoInicio = System.currentTimeMillis();
        resetearPelota();
        repaint();
    }

    public int getPuntajeJugador1() {
        return punj1;
    }

    public int getPuntajeJugador2() {
        return puntj2;
    }
}

