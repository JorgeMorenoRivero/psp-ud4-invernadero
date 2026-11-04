public class Fase3_Experimentos {

    public static void main(String[] args) throws InterruptedException {
        experimentoA();
        experimentoB();
        experimentoC();
    }

    // A) Informe sin esperar a que el hilo termine
    private static void experimentoA() throws InterruptedException {
        System.out.println("=== EXPERIMENTO A · informe() sin join() ===");
        Humedad humedad = new Humedad();
        Thread hilo = new Thread(humedad, "sensor-Humedad");
        hilo.start();

        System.out.println("Informe justo después de start():");
        System.out.println("  " + humedad.informe());

        hilo.join();
        System.out.println("Informe después de join():");
        System.out.println("  " + humedad.informe());
        System.out.println();
    }

    // B) run() en lugar de start()
    private static void experimentoB() {
        System.out.println("=== EXPERIMENTO B · run() en vez de start() ===");
        ProgramadorRiego riego = new ProgramadorRiego(3);

        long inicio = System.currentTimeMillis();
        riego.run();
        long total = System.currentTimeMillis() - inicio;

        System.out.println("Tiempo: " + total + " ms");
        System.out.println("Estado del objeto riego: " + riego.getState());
        System.out.println();
    }

    // C) start() dos veces sobre el mismo objeto
    private static void experimentoC() throws InterruptedException {
        System.out.println("=== EXPERIMENTO C · start() dos veces ===");
        ProgramadorRiego riego = new ProgramadorRiego(2);
        riego.start();
        riego.join();
        System.out.println("Estado tras terminar: " + riego.getState());

        try {
            riego.start();
        } catch (IllegalThreadStateException e) {
            System.out.println("Excepción capturada: " + e);
        }
    }
}