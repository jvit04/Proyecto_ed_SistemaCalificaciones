package ClasesPrincipales;

import java.util.Stack;

public class CalculadoraConPilas {

    public static double evaluar(Calculo calculo, ListaCompuesta<Entrega, Entrega> entregasDelEstudiante) {
        Stack<Double> pila = new Stack<>();

        // Le agregamos un espacio al final a la fórmula para asegurarnos de que el ciclo
        // procese la última palabra o número correctamente.
        String formula = calculo.getFormula().replace("\r", "") + " ";
        String elementoTemporal = "";

        // Recorremos la fórmula letra por letra
        for (int i = 0; i < formula.length(); i++) {
            char letra = formula.charAt(i);

            // Si encontramos un espacio, tabulación o salto de línea significa que terminamos de leer una palabra o número
            if (letra == ' ' || letra == '\t' || letra == '\n') {

                // Solo procesamos si la palabra no está vacía
                if (elementoTemporal.isEmpty() == false) {

                    // 1. Verificamos si es un operador matemático
                    if (elementoTemporal.equals("+") || elementoTemporal.equals("-") ||
                            elementoTemporal.equals("*") || elementoTemporal.equals("/")) {

                        // Validamos que haya al menos 2 números en la pila para poder operar
                        if (pila.size() >= 2) {
                            double operandoDerecho = pila.pop();
                            double operandoIzquierdo = pila.pop();
                            double resultado = 0.0;

                            if (elementoTemporal.equals("+")) {
                                resultado = operandoIzquierdo + operandoDerecho;
                            } else if (elementoTemporal.equals("-")) {
                                resultado = operandoIzquierdo - operandoDerecho;
                            } else if (elementoTemporal.equals("*")) {
                                resultado = operandoIzquierdo * operandoDerecho;
                            } else if (elementoTemporal.equals("/")) {
                                if (operandoDerecho == 0) {
                                    System.err.println("   [!] Advertencia: Se intentó dividir por cero en la fórmula. Asignando 0 por defecto.");
                                    resultado = 0.0;
                                } else {
                                    resultado = operandoIzquierdo / operandoDerecho;
                                }
                            }

                            pila.push(resultado);
                        }
                    }
                    // 2. Verificamos si es un número (Ej. el "2" en un promedio)
                    else if (esNumero(elementoTemporal)) {
                        double numero = Double.parseDouble(elementoTemporal);
                        pila.push(numero);
                    }
                    // 3. Si no es operador ni número, tiene que ser el nombre de una actividad
                    else {
                        double notaObtenida = buscarNota(elementoTemporal, entregasDelEstudiante);
                        pila.push(notaObtenida);
                    }

                    // Limpiamos la variable para empezar a armar la siguiente palabra
                    elementoTemporal = "";
                }
            } else {
                // Si no es un espacio, seguimos armando la palabra letra por letra
                elementoTemporal = elementoTemporal + letra;
            }
        }

        // Al final, devolvemos el resultado usando if/else tradicional
        if (pila.isEmpty()) {
            return 0.0;
        } else {
            return pila.pop();
        }
    }

    private static boolean esNumero(String texto) {
        try {
            Double.parseDouble(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static double buscarNota(String nombreActividadBuscada, ListaCompuesta<Entrega, Entrega> entregas) {
        if (entregas == null) {
            return 0.0;
        }

        NodoCompuesto<Entrega, Entrega> actual = entregas.getHeader();

        while (actual != null) {
            Entrega entrega = actual.getData();


            if (entrega.getActividad() != null) {
                if (entrega.getActividad().getNombre().equalsIgnoreCase(nombreActividadBuscada)) {

                    // Verificamos si la nota es -1 (Sin calificar) usando if/else normal
                    if (entrega.getNota() == -1.0) {
                        return 0.0;
                    } else {
                        return entrega.getNota();
                    }
                }
            }
            actual = actual.getNext();
        }

        return 0.0;
    }

    // Construye una fórmula postfija automáticamente basada en una operación. Para evitar usar TDAS
    // que no corresponden al proyecto se tomará ventaja del TDA ListaCompuesta.
    public static String generar(String operacion, ListaCompuesta<String, String> nombres) {
        if (nombres == null || nombres.isEmpty()) return "0";
        if (nombres.getSize() == 1) return nombres.getHeader().getData();

        String formulaFinal = "";
        NodoCompuesto<String, String> actual = nombres.getHeader();

        // Agregamos la primera actividad a la fórmula
        formulaFinal = formulaFinal + actual.getData();
        actual = actual.getNext();

        // Agregamos las demás actividades con el signo +
        while (actual != null) {
            formulaFinal = formulaFinal + " " + actual.getData() + " +";
            actual = actual.getNext();
        }

        // Si es promedio, le agregamos la división al final
        if (operacion.equals("Promedio")) {
            formulaFinal = formulaFinal + " " + nombres.getSize() + " /";
        }

        return formulaFinal;
    }
}