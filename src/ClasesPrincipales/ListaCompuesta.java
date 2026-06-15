package ClasesPrincipales;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Clase genérica que representa una Lista Enlazada Compuesta.
 * Contiene una lista principal de elementos de tipo E, donde cada nodo
 * puede contener a su vez una sublista de elementos de tipo F.
 *
 * @param <E> El tipo de dato principal (Ej. ClasesPrincipales.Estudiante, ClasesPrincipales.Actividad)
 * @param <F> El tipo de dato secundario de las sublistas (Ej. ClasesPrincipales.Entrega)
 */
public class ListaCompuesta<E, F> implements Serializable {

    private NodoCompuesto<E, F> header;
    private NodoCompuesto<E, F> tail;
    private int size;

    public ListaCompuesta() {
        setHeader(null);
        setTail(null);
        this.size = 0;
    }

    // ==========================================
    // Métodos Básicos: Setters y getters
    // ==========================================

    public void setHeader(NodoCompuesto<E, F> nodo) { this.header = nodo; }
    public void setTail(NodoCompuesto<E, F> nodo) { this.tail = nodo; }
    public NodoCompuesto<E, F> getHeader() { return this.header; }
    public NodoCompuesto<E, F> getTail() { return this.tail; }
    public int getSize() { return this.size; }
    public boolean isEmpty() { return this.size == 0 && this.header == null; }

    // ==========================================
    // Métodos de Inserción
    // ==========================================

    /**
     * Inserta un nuevo nodo al final de la lista principal.
     * @param nodo El nodo compuesto a agregar.
     */
    public void add(NodoCompuesto<E, F> nodo) {
        if (size == 0) {
            setHeader(nodo);
            setTail(nodo);
        } else {
            getTail().setNext(nodo);
            setTail(nodo);
        }
        size++;
    }

    /**
     * Inserta un elemento en la sublista (lista secundaria) de un nodo específico.
     * Si el nodo no tiene una sublista, se crea una nueva automáticamente.
     *
     * @param nodo El nodo principal al que se le agregará el elemento secundario.
     * @param elemento El elemento de tipo F a agregar.
     */
    public void addElementInSecondaryList(NodoCompuesto<E, F> nodo, F elemento) {
        if (nodo.getReferenciaLista() == null) {
            nodo.SetListaCompuesta(new ListaCompuesta<F, F>());
        }
        nodo.getReferenciaLista().add(new NodoCompuesto<F, F>(elemento));
    }

    @Override
    public String toString() {
        String texto = "";
        for (NodoCompuesto<E, F> nodo = this.getHeader(); nodo != null; nodo = nodo.getNext()) {
            texto += "\n" + nodo.getData() + " ";
            if (nodo.getReferenciaLista() != null) {
                texto += ":" + nodo.getReferenciaLista().toString();
            }
        }
        return texto;
    }

    // ==========================================
    // Métodos de búsqueda y filtrado
    // ==========================================

    /**
     * Busca el primer nodo en la lista principal que sea menor al dato proporcionado de acuerdo a un criterio.
     * @param c El comparador con el criterio de búsqueda.
     * @param data El dato E de referencia.
     * @return El primer ClasesPrincipales.NodoCompuesto que cumpla la condición, o null si no encuentra ninguno.
     */
    public NodoCompuesto<E, F> buscarPrimero(Comparator<E> c, E data) {
        for (NodoCompuesto<E, F> p = header; p != null; p = p.getNext()) {
            if (c.compare(p.getData(), data) < 0) return p;
        }
        return null;
    }

    /**
     * Busca el primer nodo en la lista principal que sea exactamente igual al dato proporcionado de acuerdo a un criterio.
     * @param c El comparador con el criterio de igualdad (devuelve 0).
     * @param data El dato E de referencia.
     * @return El primer ClasesPrincipales.NodoCompuesto idéntico, o null si no lo encuentra.
     */
    public NodoCompuesto<E, F> buscarExacto(Comparator<E> c, E data) {
        if (this.isEmpty() || data == null) {
            return null;
        }
        for (NodoCompuesto<E, F> p = header; p != null; p = p.getNext()) {
            if (c.compare(p.getData(), data) == 0) return p;
        }
        return null;
    }

    /**
     * Devuelve una nueva lista con todos los nodos principales que sean iguales al dato dado de acuerdo a un criterio.
     * @param c El comparador con el criterio de igualdad (debe devolver 0).
     * @param data El dato principal (tipo E) de referencia a buscar.
     * @return Una nueva ClasesPrincipales.ListaCompuesta con los elementos que coincidan.
     */
    public ListaCompuesta<E, F> buscarIgualesPrincipal(Comparator<E> c, E data) {
        if (this.isEmpty() || data == null) return new ListaCompuesta<>();
        ListaCompuesta<E, F> resultado = new ListaCompuesta<>();
        for (NodoCompuesto<E, F> p = this.header; p != null; p = p.getNext()) {
            if (c.compare(p.getData(), data) == 0) {
                NodoCompuesto<E, F> nodoNuevo = new NodoCompuesto<>(p.getData());
                nodoNuevo.SetListaCompuesta(p.getReferenciaLista()); // Salvamos sublista
                resultado.add(nodoNuevo);
            }
        }
        return resultado;
    }

    /**
     * Devuelve una nueva lista con todos los nodos principales que sean menores al dato dado de acuerdo a un criterio.
     * @param c El comparador con el criterio de evaluación.
     * @param data El dato principal (tipo E) de referencia a comparar.
     * @return  Una nueva ClasesPrincipales.ListaCompuesta con los elementos menores al dato proporcionado.
     */
    public ListaCompuesta<E, F> buscarMenoresPrincipal(Comparator<E> c, E data) {
        if (this.isEmpty() || data == null) return new ListaCompuesta<>();
        ListaCompuesta<E, F> nueva = new ListaCompuesta<>();
        for (NodoCompuesto<E, F> p = header; p != null; p = p.getNext()) {
            if (c.compare(p.getData(), data) < 0) {
                NodoCompuesto<E, F> nodoNuevo = new NodoCompuesto<>(p.getData());
                nodoNuevo.SetListaCompuesta(p.getReferenciaLista()); // Salvamos sublista
                nueva.add(nodoNuevo);
            }
        }
        return nueva;
    }

    /**
     * Devuelve una nueva lista con todos los nodos principales que sean mayores al dato dado de acuerdo a un criterio.
     * @param c El comparador con el criterio de evaluación.
     * @param data El dato principal (tipo E) de referencia a comparar.
     * @return Una nueva ClasesPrincipales.ListaCompuesta con los elementos mayores al dato proporcionado.
     */
    public ListaCompuesta<E, F> buscarMayoresPrincipal(Comparator<E> c, E data) {
        if (this.isEmpty() || data == null) return new ListaCompuesta<>();
        ListaCompuesta<E, F> nueva = new ListaCompuesta<>();
        for (NodoCompuesto<E, F> p = header; p != null; p = p.getNext()) {
            if (c.compare(p.getData(), data) > 0) {
                NodoCompuesto<E, F> nodoNuevo = new NodoCompuesto<>(p.getData());
                nodoNuevo.SetListaCompuesta(p.getReferenciaLista()); // Salvamos sublista
                nueva.add(nodoNuevo);
            }
        }
        return nueva;
    }

    /**
     * Filtra la lista principal devolviendo solo a los padres (elementos tipo E) que contengan en su sublista al menos un elemento hijo (F) que sea menor al dato proporcionado de acuerdo a un criterio.
     * @param c El comparador para evaluar los elementos de la sublista.
     * @param data El dato secundario (tipo F) de referencia.
     * @return Una nueva ClasesPrincipales.ListaCompuesta con los nodos principales que cumplan la condición.
     */
    public ListaCompuesta<E, F> buscarTodosMenoresEnListaSecundaria(Comparator<F> c, F data) {
        if (this.isEmpty() || data == null) return new ListaCompuesta<>();
        ListaCompuesta<E, F> nueva = new ListaCompuesta<>();
        for (NodoCompuesto<E, F> p = this.header; p != null; p = p.getNext()) {
            ListaCompuesta<F, F> sublista = p.getReferenciaLista();
            if (sublista != null && sublista.buscarPrimero(c, data) != null) {
                NodoCompuesto<E, F> nodoNuevo = new NodoCompuesto<>(p.getData());
                nodoNuevo.SetListaCompuesta(sublista);
                nueva.add(nodoNuevo);
            }
        }
        return nueva;
    }

    /**
     * Extrae y devuelve una lista con todos los elementos secundarios (tipo F) de todas las sublistas de los nodos que sean exactamente iguales al dato F proporcionado.
     * @param c El comparador con el criterio de igualdad para la sublista.
     * @param data El dato secundario (tipo F) a buscar.
     * @return Una nueva ClasesPrincipales.ListaCompuesta (de tipo <F,F>) con los elementos secundarios encontrados.
     */
    public ListaCompuesta<F, F> buscarIgualesEnListaSecundaria(Comparator<F> c, F data) {
        if (this.isEmpty() || data == null) return new ListaCompuesta<>();
        ListaCompuesta<F, F> nueva = new ListaCompuesta<>();
        for (NodoCompuesto<E, F> p = this.header; p != null; p = p.getNext()) {
            ListaCompuesta<F, F> sublista = p.getReferenciaLista();
            if (sublista != null) {
                for (NodoCompuesto<F, F> i = sublista.header; i != null; i = i.getNext()) {
                    if (c.compare(i.getData(), data) == 0) {
                        nueva.add(new NodoCompuesto<>(i.getData()));
                    }
                }
            }
        }
        return nueva;
    }

    // ==========================================
    // Métodos de conjuntos
    // ==========================================

    /**
     * Une los elementos de la lista actual con los de lista2, sin repetidos de acuerdo a un criterio de comparación.
     * @param lista2 La segunda ClasesPrincipales.ListaCompuesta con la que se realizará la unión.
     * @param c El comparador para determinar si un elemento está repetido.
     * @return Una nueva ClasesPrincipales.ListaCompuesta con los elementos de ambas listas unidos.
     */
    public ListaCompuesta<E, F> union(ListaCompuesta<E, F> lista2, Comparator<E> c) {
        if (this.isEmpty()) return new ListaCompuesta<>();
        ListaCompuesta<E, F> resultado = new ListaCompuesta<>();

        // 1. Agregar todos los elementos de la lista actual
        for (NodoCompuesto<E, F> p = this.header; p != null; p = p.getNext()) {
            NodoCompuesto<E, F> nodoNuevo = new NodoCompuesto<>(p.getData());
            nodoNuevo.SetListaCompuesta(p.getReferenciaLista());
            resultado.add(nodoNuevo);
        }

        // 2. Agregar los de lista2 solo si no existen ya en el resultado
        if (lista2 != null) {
            for (NodoCompuesto<E, F> q = lista2.getHeader(); q != null; q = q.getNext()) {
                if (resultado.buscarExacto(c, q.getData()) == null) {
                    NodoCompuesto<E, F> nodoNuevo = new NodoCompuesto<>(q.getData());
                    nodoNuevo.SetListaCompuesta(q.getReferenciaLista());
                    resultado.add(nodoNuevo);
                }
            }
        }
        return resultado;
    }

    /**
     * Devuelve los elementos que tienen en común ambas listas de acuerdo a un criterio
     * @param lista2 La segunda ClasesPrincipales.ListaCompuesta con la que se buscarán elementos en común.
     * @param c El comparador que define el criterio de igualdad.
     * @return Una nueva ClasesPrincipales.ListaCompuesta con la intersección de ambos conjuntos.
     * */
    public ListaCompuesta<E, F> interseccion(ListaCompuesta<E, F> lista2, Comparator<E> c) {
        ListaCompuesta<E, F> resultado = new ListaCompuesta<>();
        if (lista2 == null||this.isEmpty()) return resultado;


        for (NodoCompuesto<E, F> p = this.header; p != null; p = p.getNext()) {
            if (lista2.buscarExacto(c, p.getData()) != null) {
                NodoCompuesto<E, F> nodoNuevo = new NodoCompuesto<>(p.getData());
                nodoNuevo.SetListaCompuesta(p.getReferenciaLista());
                resultado.add(nodoNuevo);
            }
        }
        return resultado;
    }

    /**
     * Devuelve los elementos que están en la lista actual, pero no en la lista2 de acuerdo a un criterio de igualdad.
     * @param lista2 La lista cuyos elementos queremos excluir del resultado.
     * @param comparator El comparador que define cuándo dos elementos son iguales.
     * @return Una nueva ClasesPrincipales.ListaCompuesta con los elementos exclusivos de la primera lista.
     */
    public ListaCompuesta<E, F> diferencia(ListaCompuesta<E, F> lista2, Comparator<E> comparator) {
        ListaCompuesta<E, F> diferencia = new ListaCompuesta<>();
        //Si mi lista principal está vacía, no hay nada que comparar.
        if (this.isEmpty()) {
            return diferencia;
        }
        // Si la lista2 es nula o está vacía, la diferencia es TODA mi lista principal.
        if (lista2 == null || lista2.isEmpty()) {
            for (NodoCompuesto<E, F> i = this.getHeader(); i != null; i = i.getNext()) {
                NodoCompuesto<E, F> nodoNuevo = new NodoCompuesto<>(i.getData());
                nodoNuevo.SetListaCompuesta(i.getReferenciaLista()); // Salvamos tu sublista
                diferencia.add(nodoNuevo);
            }
            return diferencia;
        }

        for (NodoCompuesto<E, F> i = this.getHeader(); i != null; i = i.getNext()) {
            // Si el elemento no existe en la lista2, se agrega a la diferencia
            if (lista2.buscarExacto(comparator, i.getData()) == null) {
                NodoCompuesto<E, F> nodoNuevo = new NodoCompuesto<>(i.getData());
                nodoNuevo.SetListaCompuesta(i.getReferenciaLista());
                diferencia.add(nodoNuevo);
            }
        }
        return diferencia;
    }

    // ==========================================
    // Métodos específicos para el proyecto
    // ==========================================

    /**
     * Busca elementos duplicados internos en las sublistas de cada nodo. Si encuentra dos elementos iguales (según el comparador), agrega este nodo a la lista de resultados.
     * @param c El comparador que define cuándo dos elementos secundarios son idénticos. El comparador debe ser del tipo de la sublista.
     * @return Una nueva ClasesPrincipales.ListaCompuesta con los nodos principales que tienen duplicados internos.
     */
    public ListaCompuesta<E, F> buscarNodosConDuplicadosEnSublista(Comparator<F> c) {
        ListaCompuesta<E, F> resultados = new ListaCompuesta<>();

        // 1. Recorro cada nodo de la lista principal para obtener su sublist
        for (NodoCompuesto<E, F> p = this.header; p != null; p = p.getNext()) {

            // 2. Tomo la sublista del nodo en cada iteración
            ListaCompuesta<F, F> sublista = p.getReferenciaLista();
            boolean tieneDuplicado = false;

            if (sublista != null) {
                // 3. Si la sublista tiene elementos entonces la empiezo a recorrer siempre y cuando haya elementos y no haya salido un duplicado
                for (NodoCompuesto<F, F> nodoA = sublista.getHeader(); nodoA != null && !tieneDuplicado; nodoA = nodoA.getNext()) {
                    // 4. Para cada nodo, vuelvo a recorrer esta misma sublista pero a partir del siguiente.
                    // Esto es para comparar el nodo A con todos los demás nodos que hay en la lista y no solo el siguiente.
                    for (NodoCompuesto<F, F> nodoB = nodoA.getNext(); nodoB != null; nodoB = nodoB.getNext()) {
                        if (c.compare(nodoA.getData(), nodoB.getData()) == 0) {
                            tieneDuplicado = true;
                            break;
                        }
                    }
                }
            }
            if (tieneDuplicado) {
                NodoCompuesto<E, F> nodoNuevo = new NodoCompuesto<>(p.getData());
                nodoNuevo.SetListaCompuesta(sublista);
                resultados.add(nodoNuevo);
            }
        }
        return resultados;
    }

    /**
     * Filtra los nodos dependiendo de cuántos elementos tengan en su sublista.
     * @param minElementos Cantidad mínima de elementos que debe tener la sublista.
     * @param maxElementos Cantidad máxima de elementos que debe tener la sublista.
     * @return             Una nueva ClasesPrincipales.ListaCompuesta con los nodos que entran en el rango de tamaño.
     */
    public ListaCompuesta<E, F> buscarPorRangoDeTamanoSublista(int minElementos, int maxElementos) {
        ListaCompuesta<E, F> listaResultado = new ListaCompuesta<>();
        NodoCompuesto<E, F> actual = this.header;
        while (actual != null) {
            int tamano = 0;
            if (actual.getReferenciaLista() != null) {
                tamano = actual.getReferenciaLista().getSize();
            }
            if (tamano >= minElementos && tamano <= maxElementos) {
                NodoCompuesto<E, F> nodoNuevo = new NodoCompuesto<>(actual.getData());
                nodoNuevo.SetListaCompuesta(actual.getReferenciaLista());
                listaResultado.add(nodoNuevo);
            }
            actual = actual.getNext();
        }
        return listaResultado;
    }

    // ==========================================
    // MÉTODOS DE MANEJO DE NODOS (Agregados por compañero)
    // ==========================================

    /**
     * Devuelve el nodo ANTERIOR al nodo proporcionado dentro de la lista principal.
     */
    public NodoCompuesto<E, F> getAnteriorListaPrincipal(NodoCompuesto<E, F> elemento) {
        for (NodoCompuesto<E, F> i = this.getHeader(); i != null; i = i.getNext()) {
            if (i.getNext() == null) return null;
            if (i.getNext().getData().equals(elemento.getData())) {
                return i;
            }
        }
        return null;
    }

    /**
     * Devuelve el nodo ANTERIOR a un nodo dado dentro de una lista secundaria específica.
     */
    public NodoCompuesto<E, F> getAnteriorListaSecundaria(ListaCompuesta<E, F> lista, NodoCompuesto<E, F> nodo) {
        return lista.getAnteriorListaPrincipal(nodo);
    }
}