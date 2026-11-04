import java.util.Locale;

public abstract class Sensor implements Runnable {

    private static final Locale ES = Locale.forLanguageTag("es-ES");

    private final String nombre;
    private final String unidad;
    private final long periodoMs;
    private final int numLecturas;
    private final double umbralMin;
    private final double umbralMax;

    // Estadísticas propias de CADA sensor (no son static)
    private int lecturasTomadas = 0;
    private double suma = 0;
    private double minimo = Double.MAX_VALUE;
    private double maximo = -Double.MAX_VALUE;
    private int alertas = 0;

    protected Sensor(String nombre, String unidad, long periodoMs, int numLecturas,
                     double umbralMin, double umbralMax) {
        this.nombre = nombre;
        this.unidad = unidad;
        this.periodoMs = periodoMs;
        this.numLecturas = numLecturas;
        this.umbralMin = umbralMin;
        this.umbralMax = umbralMax;
    }

    public abstract double medir();

    @Override
    public void run() {
        for (int i = 0; i < numLecturas; i++) {
            double valor = medir();
            boolean fueraDeRango = valor < umbralMin || valor > umbralMax;
            registrar(valor, fueraDeRango);

            String linea = String.format(ES, "%s: %.1f %s", nombre, valor, unidad);
            if (fueraDeRango) {
                linea += "  ⚠ ALERTA";
            }
            Log.escribir(linea);

            try {
                Thread.sleep(periodoMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void registrar(double valor, boolean fueraDeRango) {
        lecturasTomadas++;
        suma += valor;
        if (valor < minimo) {
            minimo = valor;
        }
        if (valor > maximo) {
            maximo = valor;
        }
        if (fueraDeRango) {
            alertas++;
        }
    }

    public String informe() {
        if (lecturasTomadas == 0) {
            return String.format(ES, "%-12s 0 lecturas (todavía sin datos)", nombre);
        }
        double media = suma / lecturasTomadas;
        return String.format(ES, "%-12s %d lecturas | media %.1f %s | mín %.1f | máx %.1f | alertas %d",
                nombre, lecturasTomadas, media, unidad, minimo, maximo, alertas);
    }

    public String getNombre() {
        return nombre;
    }
}