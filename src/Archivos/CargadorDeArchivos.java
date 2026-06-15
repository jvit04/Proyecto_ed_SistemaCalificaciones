package Archivos;

import ClasesPrincipales.*;
import Exceptions.ActividadNoEncontradaException;
import Exceptions.EstudianteNoEncontradoException;
import Exceptions.FormatoArchivoException;

import java.time.format.DateTimeParseException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class CargadorDeArchivos {

    // Método estático para no tener que instanciar la clase
    public static ListaCompuesta<Estudiante, Entrega> cargarEstudiantes(String rutaArchivo) {
        ListaCompuesta<Estudiante, Entrega> lista = new ListaCompuesta<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int numLinea = 1; // Contador para saber en qué línea falla
            while ((linea = br.readLine()) != null) {
                // Asumiendo que el archivo dice: Nombre,Apellido,Cedula
                // Ejemplo: Pau,Pau,099999
                if (linea.trim().isEmpty()) continue; // Ignorar líneas en blanco
                // TRY-CATCH INTERNO: Protege la lectura línea por línea
                try {
                String[] datos = linea.split(",");
                if(datos.length < 3){
                    throw new FormatoArchivoException("Faltan datos en la línea " + numLinea);
                }
                Estudiante e = new Estudiante(datos[0].trim(), datos[1].trim(), datos[2].trim());
                lista.add(new NodoCompuesto<>(e));
                } catch (FormatoArchivoException e) {
                    // Si la línea falla, entra aquí, imprime el aviso y el while sigue con la próxima línea
                    System.err.println("[Aviso Estudiantes] " + e.getMessage() + ". Se omitió esta línea.");
                }

                numLinea++; // Aumentamos el contador para la siguiente vuelta
            }
        } catch (IOException e) {
            System.out.println("Error leyendo archivo: " + e.getMessage());
        }
        return lista;
    }

    // Método estático para no tener que instanciar la clase
    public static ListaCompuesta<Actividad,Entrega> cargarActividades(String rutaArchivo) {
        ListaCompuesta<Actividad, Entrega> lista = new ListaCompuesta<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int numLinea = 1; // Contador de líneas igual que en estudiantes
            while ((linea = br.readLine()) != null) {
                // Formato esperado en el archivo: Nombre,Descripcion,Fecha,NotaMaxima, Tipo
                // Ejemplo: Taller 1,Ejercicios de pilas,2026-02-20,10, Taller
                if (linea.trim().isEmpty()) continue;

                // TRY-CATCH INTERNO: Protege errores de formato, fechas y números
                try {
                    String[] datos = linea.split(",");

                    // Verificamos que tenga las 5 columnas
                    if (datos.length < 5) {
                        throw new FormatoArchivoException("Faltan datos en la línea " + numLinea);
                    }

                    String nombre = datos[0].trim();
                    String descripcion = datos[1].trim();
                    LocalDate fechaLimite = LocalDate.parse(datos[2].trim()); // Esto puede fallar si la fecha está mal
                    int notaMaxima = Integer.parseInt(datos[3].trim());       // Esto puede fallar si hay letras en vez de números
                    String tipo = datos[4].trim();

                    Actividad a = new Actividad(nombre, descripcion, fechaLimite, notaMaxima, tipo);
                    lista.add(new NodoCompuesto<>(a));

                } catch (DateTimeParseException e) {
                    System.err.println("[Aviso Actividades L" + numLinea + "] Formato de fecha incorrecto.");
                } catch (NumberFormatException e) {
                    System.err.println("[Aviso Actividades L" + numLinea + "] La nota máxima debe ser un número entero.");
                } catch (FormatoArchivoException e) {
                    System.err.println("[Aviso Actividades] " + e.getMessage());
                }
                numLinea++; // Aumentamos la línea
            }
        } catch (IOException e) {
            System.err.println("[Error] No se pudo leer el archivo de actividades: " + e.getMessage());
        }
        return lista;
    }

    // Método para cargar Entregas y asignarlas a los estudiantes correspondientes
    public static void cargarEntregas(String rutaArchivo,
                                      ListaCompuesta<Estudiante, Entrega> listaEstudiantes,
                                      ListaCompuesta<Actividad, Entrega> listaActividades) {

        // Validación de seguridad ANTES de intentar asignar entregas
        if (listaEstudiantes == null || listaEstudiantes.isEmpty() || listaActividades == null || listaActividades.isEmpty()) {
            System.err.println("[Error] No se pueden cargar entregas porque las listas base están vacías.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int numLinea = 1; // Contador de líneas
            while ((linea = br.readLine()) != null) {
                // Formato: Cedula,NombreActividad,Nota,Comentario
                if (linea.trim().isEmpty()) continue;

                // TRY-CATCH INTERNO
                try {
                    String[] datos = linea.split(",");

                    if(datos.length < 4) {
                        throw new FormatoArchivoException("Faltan datos en la línea " + numLinea);
                    }
                    String cedulaBusq = datos[0].trim();
                    String nombreActBusq = datos[1].trim();
                    double nota = Double.parseDouble(datos[2].trim());
                    String comentario = datos[3].trim();

                    // 1. Buscar al ClasesPrincipales.Estudiante por Cédula
                    NodoCompuesto<Estudiante, Entrega> nodoEst = null;
                    for(NodoCompuesto<Estudiante, Entrega> p = listaEstudiantes.getHeader(); p != null; p = p.getNext()){
                        if(p.getData().getCedula().equals(cedulaBusq)){
                            nodoEst = p;
                            break;
                        }
                    }
                    // Si no lo encuentra, disparamos el error y saltamos a la siguiente línea
                    if (nodoEst == null) {
                        throw new EstudianteNoEncontradoException("No existe estudiante con cédula " + cedulaBusq);
                    }

                    // 2. Buscar la ClasesPrincipales.Actividad por Nombre
                    NodoCompuesto<Actividad,Entrega> nodoActividad = null; //nodo para guardar el valor de la actividad a asociar con la entrega
                    Actividad actEncontrada = null;
                    for(NodoCompuesto<Actividad, Entrega> a = listaActividades.getHeader(); a != null; a = a.getNext()){
                        if(a.getData().getNombre().equalsIgnoreCase(nombreActBusq)){
                            actEncontrada = a.getData();
                            nodoActividad = a;
                            break;
                        }
                    }
                    // Si no la encuentra, disparamos el error
                    if (actEncontrada == null) {
                        throw new ActividadNoEncontradaException("No existe la actividad '" + nombreActBusq + "'");
                    }
                    // 3. Si ambos existen, creamos la entrega y la vinculamos
                        Estudiante est = nodoEst.getData();
                        Entrega nuevaEntrega = new Entrega(nota, comentario, est, actEncontrada);

                        // Agregamos a la lista secundaria del estudiante
                        listaEstudiantes.addElementInSecondaryList(nodoEst, nuevaEntrega);
                        listaActividades.addElementInSecondaryList(nodoActividad, nuevaEntrega);
                    }catch (NumberFormatException e) {
                    System.err.println("[Aviso Entregas L" + numLinea + "] La nota debe ser un número. Se omitió.");
                } catch (EstudianteNoEncontradoException | ActividadNoEncontradaException | FormatoArchivoException e) {
                    // Aquí atrapamos nuestras excepciones personalizadas y mostramos el mensaje
                    System.err.println("[Aviso Entregas L" + numLinea + "] " + e.getMessage() + ". Se omitió.");
                }

                numLinea++; // Aumentamos la línea

            }
            System.out.println(">> Entregas cargadas desde archivo correctamente.");
        } catch (IOException e) {
            System.out.println("Error leyendo entregas: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error procesando datos de entregas: " + e.getMessage());
        }
    }

    /**
     * Carga los cálculos agregados desde el archivo de texto calculos.txt
     * @param rutaArchivo La ruta del archivo.
     * @return ListaCompuesta con los objetos Calculo instanciados.
     */
    public static ListaCompuesta<Calculo, String> cargarCalculos(String rutaArchivo) {
        ListaCompuesta<Calculo, String> lista = new ListaCompuesta<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int numLinea = 1;
            while ((linea = br.readLine()) != null) {
                // Formato esperado: NombreCalculo,Formula_Postfija
                try {
                    String[] datos = linea.split(",");
                    if (datos.length < 2) {
                        throw new FormatoArchivoException("Faltan datos de la fórmula en la línea " + numLinea);
                    }
                    Calculo c = new Calculo(datos[0].trim(), datos[1].trim());
                    lista.add(new NodoCompuesto<>(c));

                } catch (FormatoArchivoException e) {
                    System.err.println("[Aviso Cálculos] " + e.getMessage() + ". Se omitió esta línea.");
                }
                numLinea++;
            }
            System.out.println(">> Cálculos cargados desde archivo: " + lista.getSize());
        } catch (IOException e) {
            System.out.println("Error leyendo archivo de cálculos: " + e.getMessage());
        }
        return lista;
    }
}