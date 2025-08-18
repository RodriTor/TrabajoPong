package clases;

public class Hilo extends Thread {
    private final Tablero lamina;
    private boolean running = true;

    public Hilo(Tablero lamina) {
        this.lamina = lamina;
    }

    public void detener() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            lamina.actualizar();   
            lamina.repaint();     

            try {
                Thread.sleep(6);  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
