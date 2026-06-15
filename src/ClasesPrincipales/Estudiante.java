package ClasesPrincipales;

import Archivos.CodigoEstudiantil;

import java.io.Serializable;

public class Estudiante implements Serializable {
    //Atributos
    private String codigo;
    private String cedula;
    private String nombre;
    private String apellido;

    //Constructor
    public Estudiante(String nombre, String apellido, String cedula) {
        CodigoEstudiantil codigoEstudiantil = new CodigoEstudiantil();
        this.codigo = codigoEstudiantil.generarCodigoNuevo();
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
    }

    //Método toString
    public String toString(){
        return "Código de estudiante: " + codigo + "\nNombre: " + nombre + "\nApellido: " + apellido;
    }

    //Getters y Setters
    public String getCodigo() {
        return codigo;
    }
    public String getApellido() {
        return apellido;
    }
    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }
}


