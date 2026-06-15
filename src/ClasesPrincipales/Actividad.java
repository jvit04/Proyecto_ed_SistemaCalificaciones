package ClasesPrincipales;

import java.io.Serializable;
import java.time.LocalDate;

public class Actividad implements Serializable {
    //Atributos
    private String nombre;
    private String descripcion;
    private String tipo;
    private LocalDate fechaEnvio;
    private LocalDate fechaLimite;
    private int notaMaxima;

    //Constructor
    public Actividad(String nombre, String descripcion, LocalDate fechaLimite, int notaMaxima, String tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaEnvio = LocalDate.now();
        this.fechaLimite = fechaLimite;
        this.notaMaxima = notaMaxima;
        this.tipo = tipo;
    }

    public Actividad(int notaMaxima) {
        this.nombre = "ClasesPrincipales.Actividad Sin Nombre ";
        this.descripcion = "Sin Descripción";
        this.fechaEnvio = LocalDate.now();
        this.fechaLimite = null;
        this.notaMaxima = notaMaxima;
    }

    public Actividad(String nombre) {
        this.nombre = nombre;
        this.notaMaxima = 100;
        this.descripcion = "Sin Descripción";
        this.fechaEnvio = LocalDate.now();
        this.fechaLimite = null;
    }

    //Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }
    public LocalDate getFechaLimite() {
        return fechaLimite;
    }
    public int getNotaMaxima() {
        return notaMaxima;
    }

    //Método toString
    @Override
    public String toString() {
        return " -> ClasesPrincipales.Actividad: " + this.getNombre() + " | Fecha: " + this.getFechaLimite();
    }
}