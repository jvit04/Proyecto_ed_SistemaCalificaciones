package Comparadores;
import ClasesPrincipales.Actividad;
import java.util.Comparator;

/**
 * Comparador que evalúa la igualdad entre dos actividades basándose en su nombre.
 * La comparación ignora las diferencias entre letras mayúsculas y minúsculas.
 */
public class CompararActsPorNombre implements Comparator<Actividad> {

    /**
     * Compara los nombres de dos actividades para determinar si son idénticas
     * @param o1 La primera actividad a evaluar.
     * @param o2 La segunda actividad a evaluar.
     * @return 0 si los nombres de ambas actividades son iguales, 1 en caso contrario.
     */
    @Override
    public int compare(Actividad o1, Actividad o2) {
        if (o1.getNombre().equalsIgnoreCase(o2.getNombre())){
            return 0; // Si de nombre son iguales entonces retorna 0
        } else {
            return 1; // Si no lo son, retorna 1
        }
    }
}