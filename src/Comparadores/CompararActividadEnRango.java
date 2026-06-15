package Comparadores;

import ClasesPrincipales.Actividad;
import java.time.LocalDate;
import java.util.Comparator;

/**
 * Comparador que evalúa si la fecha límite de una actividad se encuentra dentro de un rango de fechas específico.
 * Utiliza el constructor para establecer las fechas de inicio y fin del rango esperado.
 */
public class CompararActividadEnRango implements Comparator<Actividad> {
    private LocalDate inicio;
    private LocalDate fin;

    /**
     * Constructor del comparador.
     * @param inicio La fecha inicial del rango (inclusiva).
     * @param fin La fecha final del rango (inclusiva).
     */
    public CompararActividadEnRango(LocalDate inicio, LocalDate fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    /**
     * Compara una actividad para verificar si su fecha límite entra en el rango establecido.
     * @param o1 La actividad a evaluar.
     * @param dummy Objeto de relleno requerido por la interfaz Comparator (no se utiliza).
     * @return 0 si la fecha de la actividad está dentro del rango especificado, -1 en caso contrario.
     */
    @Override
    public int compare(Actividad o1, Actividad dummy) {
        LocalDate fecha = o1.getFechaLimite();

        // Verificamos que la fecha no sea nula y que se encuentre entre el inicio y el fin
        if (fecha != null && !fecha.isBefore(inicio) && !fecha.isAfter(fin)) {
            return 0; // Está en el rango
        }
        return -1; // Fuera del rango
    }
}