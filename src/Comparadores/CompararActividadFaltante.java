package Comparadores;

import ClasesPrincipales.Actividad;
import ClasesPrincipales.Entrega;
import ClasesPrincipales.ListaCompuesta;
import ClasesPrincipales.NodoCompuesto;
import java.util.Comparator;

/**
 * Comparador que evalúa si una Actividad del curso FALTA en la lista de entregas de un estudiante.
 * Recibe la sublista de entregas del estudiante a través del constructor.
 */
public class CompararActividadFaltante implements Comparator<Actividad> {
    private ListaCompuesta<Entrega, Entrega> entregasDelEstudiante;

    /**
     * Constructor del comparador.
     * @param entregasDelEstudiante Recibe una lista compuesta con las entregas que ya realizó un estudiante.
     */
    public CompararActividadFaltante(ListaCompuesta<Entrega, Entrega> entregasDelEstudiante) {
        this.entregasDelEstudiante = entregasDelEstudiante;
    }

    /**
     * Compara una Actividad del curso para ver si existe dentro de las entregas del estudiante.
     * @param actividadCurso La actividad del curso que estamos verificando.
     * @param dummy Un parámetro de relleno que no se utiliza pero es requerido por la interfaz Comparator.
     * @return Retorna 0 si la actividad falta (para que sea como una coincidencia). Retorna 1 si la actividad ya fue entregada).
     */
    @Override
    public int compare(Actividad actividadCurso, Actividad dummy) {
        // Si el estudiante no tiene entregas, automáticamente le falta esta actividad (match = 0)
        if (entregasDelEstudiante == null || entregasDelEstudiante.isEmpty()) {
            return 0;
        }

        // Buscamos si el nombre de esta actividad está en sus entregas
        NodoCompuesto<Entrega, Entrega> actual = entregasDelEstudiante.getHeader();
        while (actual != null) {
            // Verificamos que la entrega tenga una actividad asociada
            if (actual.getData().getActividad() != null &&
                    actual.getData().getActividad().getNombre().equalsIgnoreCase(actividadCurso.getNombre())) {
                return 1; // sí la entregó y devolvemos 1 para que el buscador la ignore
            }
            actual = actual.getNext();
        }

        // Si terminó de buscar en toda la sublista y no la encontró, es porque le falta.
        return 0;
    }
}