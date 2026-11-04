import java.util.Locale;

public class ProgramadorRiego extends Thread {

    private static final Locale ES = Locale.forLanguageTag("es-ES");
    private static final long PERIODO_MS = 700;
    private static final double LITROS_POR_APERTURA = 2.5;

    private final int ciclos;
    private int ciclosHechos = 0;
    private double litros = 0;

    public ProgramadorRiego(int ciclos) {
        super("riego"); // le pone nombre al hilo
        this.ciclos = ciclos;
    }

    public ProgramadorRiego() {
        this(5);
    }

    @Override
    public void run() {
        for (int i = 1; i <= ciclos; i++) {
            boolean abre = (i % 2 == 1); // ciclo 1 abre, 2 cierra, 3 abre...
            if (abre) {
                litros += LITROS_POR_APERTURA;
            }
            ciclosHechos++;
            Log.escribir(String.format(ES, "Riego ciclo %d/%d: válvula %s",
                    i, ciclos, abre ? "ABIERTA (+2,5 L)" : "cerrada"));

            try {
                Thread.sleep(PERIODO_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public String informe() {
        return String.format(ES, "%-12s %d ciclos | %.1f litros", "Riego", ciclosHechos, litros);
    }
}