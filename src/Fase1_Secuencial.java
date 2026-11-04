public class Fase1_Secuencial {

    public static void main(String[] args) {
        Temperatura temperatura = new Temperatura();
        Humedad humedad = new Humedad();
        Luz luz = new Luz();
        ProgramadorRiego riego = new ProgramadorRiego();

        System.out.println("=== FASE 1 · Versión secuencial ===");
        long inicio = System.currentTimeMillis();

        temperatura.run();
        humedad.run();
        luz.run();
        riego.run(); // run(), no start(): lo ejecuta main

        long total = System.currentTimeMillis() - inicio;

        System.out.println();
        System.out.println("--- Informe ---");
        System.out.println(temperatura.informe());
        System.out.println(humedad.informe());
        System.out.println(luz.informe());
        System.out.println(riego.informe());
        System.out.println("Tiempo total (secuencial): " + total + " ms");
    }
}