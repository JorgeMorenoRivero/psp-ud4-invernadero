public class Fase2_Concurrente {

    public static void main(String[] args) throws InterruptedException {
        // Objetos nuevos para esta ejecución
        Temperatura temperatura = new Temperatura();
        Humedad humedad = new Humedad();
        Luz luz = new Luz();
        ProgramadorRiego riego = new ProgramadorRiego();

        Thread hiloTemperatura = new Thread(temperatura, "sensor-Temperatura");
        Thread hiloHumedad = new Thread(humedad, "sensor-Humedad");
        Thread hiloLuz = new Thread(luz, "sensor-Luz");

        System.out.println("=== FASE 2 · Versión concurrente ===");
        long inicio = System.currentTimeMillis();

        hiloTemperatura.start();
        hiloHumedad.start();
        hiloLuz.start();
        riego.start(); // no hace falta new Thread(...): riego ya es un hilo

        // main espera a que terminen los cuatro antes de seguir
        hiloTemperatura.join();
        hiloHumedad.join();
        hiloLuz.join();
        riego.join();

        long total = System.currentTimeMillis() - inicio;

        System.out.println();
        System.out.println("--- Informe ---");
        System.out.println(temperatura.informe());
        System.out.println(humedad.informe());
        System.out.println(luz.informe());
        System.out.println(riego.informe());
        System.out.println("Tiempo total (concurrente): " + total + " ms");
    }
}