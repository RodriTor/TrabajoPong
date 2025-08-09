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
            lamina.actualizar();   // Mover pelota y raquetas
            lamina.repaint();      // esto para que no se intecalen la eplota y kla raqueta

            try {
                Thread.sleep(6);   // para que no hande con lag
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
