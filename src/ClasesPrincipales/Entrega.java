package ClasesPrincipales;

import java.io.Serializable;
import java.time.LocalDate;

public class Entrega implements Serializable {
    //Atributos
    private double nota;
    private String contenido;
    private LocalDate fechaEntrega;
    private String comentarios;
    private Estudiante estudiante;
    private Actividad actividad;

    //Constructores
    public Entrega(double nota, String comentarios, Estudiante estudiante, Actividad actividad) {
        this.nota = nota;
        this.fechaEntrega = LocalDate.now();
        this.comentarios = comentarios;
        this.estudiante = estudiante;
        this.actividad = actividad;
    }
    //Entregas de valor -1 significan que no han sido calificadas.
    public Entrega(double nota) {
        this.nota = nota;
    }

    //Getters y Setters
    public String getContenido() {
        return contenido;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public String getComentarios() {
        return comentarios;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public double getNota() {
        return nota;
    }

    @Override
    public String toString() {
        // Recuperamos los datos de forma segura (manejando nulos)
        String nombreEst = (estudiante != null) ? estudiante.getNombre() + " " + estudiante.getApellido() : "N/A";
        String nombreAct = (actividad != null) ? actividad.getNombre() : "N/A";
        String fecha = (fechaEntrega != null) ? fechaEntrega.toString() : "N/A";
        String comment = (comentarios != null) ? comentarios : "Sin comentarios";

        return "ClasesPrincipales.Entrega {" +
                "ClasesPrincipales.Actividad='" + nombreAct + '\'' +
                ", ClasesPrincipales.Estudiante='" + nombreEst + '\'' +
                ", Nota=" + nota +
                ", Fecha=" + fecha +
                ", Comentarios='" + comment + '\'' +
                '}';
    }


    //Método equals para la clase ClasesPrincipales.Entrega, su implementación fue necesaria, ya que es la forma la que se reconoce
    //si la nota de dos Entregas son iguales.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrega entrega = (Entrega) o;
        return nota == entrega.nota;
    }

}
