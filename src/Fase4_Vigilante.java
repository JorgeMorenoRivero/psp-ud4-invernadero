import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Fase4_Vigilante {

    public static void main(String[] args) throws InterruptedException {
        Temperatura temperatura = new Temperatura();
        Humedad humedad = new Humedad();
        Luz luz = new Luz();
        ProgramadorRiego riego = new ProgramadorRiego();

        Thread hiloTemperatura = new Thread(temperatura, "sensor-Temperatura");
        Thread hiloHumedad = new Thread(humedad, "sensor-Humedad");
        Thread hiloLuz = new Thread(luz, "sensor-Luz");
        Thread[] hilos = {hiloTemperatura, hiloHumedad, hiloLuz, riego};

        // Aquí apuntamos todos los estados distintos que se ven
        Set<Thread.State> vistos = Collections.synchronizedSet(new LinkedHashSet<>());

        Thread vigilante = new Thread(() -> {
            while (true) {
                mostrarEstados("vigilante", hilos, vistos);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }, "vigilante");
        vigilante.setDaemon(true); // OBLIGATORIO antes de start()

        System.out.println("=== FASE 4 · Vigilante de estados ===");
        mostrarEstados("main (antes)", hilos, vistos);

        for (Thread h : hilos) {
            h.start();
        }
        vigilante.start();

        for (Thread h : hilos) {
            h.join();
        }

        mostrarEstados("main (después)", hilos, vistos);
        System.out.println("Estados observados: " + vistos);
        System.out.println("main termina; el vigilante sigue en su while(true) pero es demonio.");
    }

    private static void mostrarEstados(String quien, Thread[] hilos, Set<Thread.State> vistos) {
        StringBuilder sb = new StringBuilder();
        for (Thread h : hilos) {
            Thread.State estado = h.getState();
            vistos.add(estado);
            sb.append(h.getName()).append("=").append(estado).append("  ");
        }
        System.out.println("[" + quien + "] " + sb);
    }
}