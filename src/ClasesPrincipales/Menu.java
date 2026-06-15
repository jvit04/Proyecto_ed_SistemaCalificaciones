package ClasesPrincipales;

/**
 * Clase encargada de manejar la interfaz de texto del sistema (Vista).
 */
public class Menu {
    public static void mostrarOpciones() {
        System.out.println("\n=== SISTEMA DE CALIFICACIONES ===");
        System.out.println("1. Actividades cuya fecha de entrega límite ya feneció");
        System.out.println("2. Estudiantes que tienen la misma nota en dos actividades diferentes");
        System.out.println("3. Estudiantes cuyo porcentaje de entregas se encuentra en un rango dado");
        System.out.println("4. Estudiantes que no han respondido aún actividades (Faltantes)");
        System.out.println("5. Actividades con calificaciones en un rango de notas dado");
        System.out.println("6. Entregas en un rango de fecha dado que aún no reciben calificación");
        System.out.println("7. Actividades en los cuales las entregas estén incompletas");
        System.out.println("8. Agregar un nuevo cálculo agregado al curso");
        System.out.println("9. Generar Reporte Personalizado (Notas y Cálculos Agregados)");
        System.out.println("10. Cálculos que aún no se pueden ejecutar por falta de calificaciones");
        System.out.println("11. Cálculos que involucren una actividad dada");
        System.out.println("0. Salir");
        System.out.print("Elija una opción: ");
    }
}