package Comparadores;
import ClasesPrincipales.Entrega;
import java.util.Comparator;

/**
 * Comparador que evalúa las notas de las entregas (atributo de ClasesPrincipales.Entrega).
 * Se utiliza para identificar notas iguales o para ordenar estructuras basándose en la calificación obtenida. (Clase trabajada en clase).
 */
public class CompararEntregasxNotas implements Comparator<Entrega>{

    /**
     * Compara numéricamente las calificaciones de dos entregas.
     * @param a La primera entrega a comparar.
     * @param b La segunda entrega a comparar.
     * @return 0 si ambas entregas tienen la misma nota, 1 si la primera es mayor, y -1 si la primera es menor.
     */
    public int compare(Entrega a, Entrega b){
        if (a.getNota()==b.getNota()) return 0;
        if (a.getNota() > b.getNota()) return 1;
        return -1;
    }
}