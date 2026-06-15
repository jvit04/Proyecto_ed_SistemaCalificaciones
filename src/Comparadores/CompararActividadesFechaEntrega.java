package Comparadores;
import java.util.Comparator;
import ClasesPrincipales.*;

/**
 * Comparador que evalúa y compara la fecha límite de entrega (atributo de Actividad).
 * Sirve para ordenar las actividades cronológicamente, colocando aquellas sin fecha
 * definida (null) al final de la estructura.
 */
public class CompararActividadesFechaEntrega implements Comparator<Actividad> {

    /**
     * Compara dos actividades basándose en su fecha límite.
     * @param a1 La primera actividad a comparar.
     * @param a2 La segunda actividad a comparar.
     * @return 0 si ambas fechas son nulas, un valor negativo si la primera fecha es anterior a la segunda, y un valor positivo si es posterior o nula.
     */
    @Override
    public int compare(Actividad a1, Actividad a2) {
        if (a1.getFechaLimite() == null && a2.getFechaLimite() == null) return 0;
        if (a1.getFechaLimite() == null) return 1; // Nulos al final
        if (a2.getFechaLimite() == null) return -1;
        return a1.getFechaLimite().compareTo(a2.getFechaLimite());
    }
}