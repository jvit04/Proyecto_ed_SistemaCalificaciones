package Archivos;

import java.time.Year;
import java.util.Random;

public class CodigoEstudiantil {
    public String generarCodigoNuevo() {
        int anno = Year.now().getValue();
        Random random = new Random();
        int numeroAleatorio = random.nextInt(1000000);
        return String.format("%d%06d", anno, numeroAleatorio);
    }
}
