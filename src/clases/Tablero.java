 package clases;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Tablero extends JPanel {

    private static final long serialVersionUID = 1L;

    private Pelota pelota = new Pelota(0, 0);
    private Raqueta r1 = new Raqueta(10, 200);
    private Raqueta r2 = new Raqueta(0, 200); // se ajusta en actualizar()

    private int punj1 = 0;
    private int puntj2 = 0;
    private final int punmax = 5;
    private boolean juegoTerminado = false;

    public Tablero() {
        setBackground(Color.BLACK);
        resetearPelota();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.WHITE);
        g2.fill(pelota.getPelota());
        g2.fill(r1.getRaqueta());
        g2.fill(r2.getRaqueta());

        g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 36));
        
        
        String textoP1 = String.valueOf(punj1);
        int anchoTextoP1 = g2.getFontMetrics().stringWidth(textoP1);
        g2.drawString(textoP1, getWidth() / 4 - anchoTextoP1 / 2, 50);

      
        String textoP2 = String.valueOf(puntj2);
        int anchoTextoP2 = g2.getFontMetrics().stringWidth(textoP2);
        g2.drawString(textoP2, getWidth() * 3 / 4 - anchoTextoP2 / 2, 50);

      
        if (juegoTerminado) {
            String ganador = punj1 >= punmax ? "GANA JUDAGOR 1" : "GANA JUGADO 2";
            g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 48));
            int anchoGanador = g2.getFontMetrics().stringWidth(ganador);
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

        if (punj1 >= punmax || puntj2 >= punmax) {
            juegoTerminado = true;
        }
    }

    private boolean colision(Rectangle2D raqueta) {
        return pelota.getPelota().intersects(raqueta);
    }

    private void resetearPelota() {
        pelota.setX(getWidth() / 2 - pelota.getAncho() / 2);
        pelota.setY(getHeight() / 2 - pelota.getAlto() / 2);
        pelota.invertirDireccion();
    }
}
