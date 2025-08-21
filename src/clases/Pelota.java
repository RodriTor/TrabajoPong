package clases;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

public class Pelota {
    private int x;
    private int y;
    private int dx = 2, dy = 2; 
    private final int anc = 15, alt = 15; 

    public Pelota(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle2D getPelota() {
        return new Rectangle2D.Double(x, y, anc, alt);
    }


    public int mover(Rectangle limites, boolean colisionR1, boolean colisionR2, Raqueta r1, Raqueta r2) {
        x += dx;
        y += dy;

        int pushBack = 5; 

        if (colisionR1) {
            dx = Math.abs(dx);
          
            if (dx < 10) { 
                dx += 1; 
            }
            x = r1.getRightEdge() + pushBack;
        } else if (colisionR2) {
            dx = -Math.abs(dx);
         
            if (Math.abs(dx) < 10) { 
                dx -= 1; 
            }
            x = r2.getLeftEdge() - anc - pushBack;
        }


        if (y < 0) {
            dy = Math.abs(dy);
            y = 0;
        }
        if (y > limites.getMaxY() - alt) {
            dy = -Math.abs(dy);
            y = (int) (limites.getMaxY() - alt);
        }

       
        if (x < 0) {
            return 1; 
        }
        if (x > limites.getMaxX() - anc) {
            return 2; 
        }

        return 0; 
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAncho() {
        return anc;
    }

    public int getAlto() {
        return alt;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void invertirDireccion() {
        dx = -dx;
    }
}




