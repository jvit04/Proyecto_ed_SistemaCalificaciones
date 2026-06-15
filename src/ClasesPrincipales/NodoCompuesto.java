package ClasesPrincipales;

import java.io.Serializable;

public class NodoCompuesto<E, F> implements Serializable {
    // de dato principal tendra dato de tipo E
    private E data;
    private NodoCompuesto<E, F> next;
    // pero la lista a que tendra referencia sera una lista de tipo F, y no sabemos de que más adicionalmente por eso F,F
    private ListaCompuesta<F, F> referenciaLista;

    public NodoCompuesto(E dato){
        this.data = dato;
        this.next = null;
        this.referenciaLista = null;
    }

    public NodoCompuesto<E, F> getNext(){return this.next;}
    public void setNext(NodoCompuesto<E, F> nodo){this.next = nodo;}
    public void SetListaCompuesta(ListaCompuesta<F, F> lista){this.referenciaLista = lista;}
    public ListaCompuesta<F, F> getReferenciaLista(){return this.referenciaLista;}
    public E getData(){return this.data;}

}