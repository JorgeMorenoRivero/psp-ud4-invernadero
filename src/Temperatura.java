import java.util.concurrent.ThreadLocalRandom;

public class Temperatura extends Sensor {

    public Temperatura() {
        // nombre, unidad, periodo (ms), lecturas, umbral mínimo, umbral máximo
        super("Temperatura", "ºC", 400, 5, 18, 30);
    }

    @Override
    public double medir() {
        return ThreadLocalRandom.current().nextDouble(15.0, 35.0);
    }
}