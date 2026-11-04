import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class Log {

    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    private Log() {
    }

    public static void escribir(String mensaje) {
        String hilo = Thread.currentThread().getName();
        System.out.println("[" + LocalTime.now().format(HORA) + "] [" + hilo + "] " + mensaje);
    }
}