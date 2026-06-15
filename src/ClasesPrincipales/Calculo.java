package ClasesPrincipales;

/**
 * Representa un cálculo agregado personalizado.
 * Su única responsabilidad es almacenar el nombre del cálculo y su fórmula matemática expresada en Notación Postfija (Separada por espacios).
 */
public class Calculo {
    private String nombre;
    private String formula;

    /**
     * Constructor del cálculo.
     * @param nombre El nombre del cálculo (Ej: "Promedio_Tareas")
     * @param formula La fórmula postfija (Ej: "Tarea_1 Tarea_2 + 2 /")
     */
    public Calculo(String nombre, String formula) {
        this.nombre = nombre;
        this.formula = formula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFormula() {
        return formula;
    }
}