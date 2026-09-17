package utnfc.isi.back.procesocsv.strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utnfc.isi.back.procesocsv.empleados.Empleado;
import utnfc.isi.back.procesocsv.empleados.EmpleadoFactory;

/**
 * Estrategia que encapsula la ejecución del Paso 1: lectura manual del CSV
 * usando Scanner y split por coma, sin librerías externas.
 *
 * Esta clase implementa la interfaz StepStrategy y representa una de las
 * estrategias posibles. La ventaja de este diseño es que esta clase puede ser
 * modificada o reemplazada sin impactar el resto del sistema.
 */
public class Step01Manual implements StepStrategy {

    /**
     * Ejecuta la versión del paso 1 del proceso del csv. Este método realiza la
     * lectura manual del archivo y muestra el resultado por consola.
     */
    private List<Empleado> empleados = new ArrayList<>();

    public List<Empleado> getEmpleados(){
        return empleados;
    }
    
    @Override
    public void ejecutar() {
        int contador = 0;
        try (Scanner scanner = new Scanner(new File(".\\src\\main\\data\\empleados.csv"), "UTF-8")) {
            // Es necesario saltear la primera línea que contiene los encabezados
            scanner.nextLine();
            // Luego sí comienza el recorrido de los datos
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] campos = linea.split(",");

                int legajo = Integer.parseInt(campos[0]);
                String nombre = campos[1];
                String tipo = campos[2];
                String categoria = campos[3];
                LocalDate fecha = LocalDate.parse(campos[4]);
                double montoBase = Double.parseDouble(campos[5]);

                try {
                    Empleado empleado = EmpleadoFactory.createEmpleado(legajo, nombre, tipo, categoria, fecha,
                            montoBase);
                    empleados.add(empleado);

                } catch (Exception e) {
                    System.out.println(e);
                    continue;
                }
                contador++;
                if (contador > 10) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + e.getMessage());
        }

    }
}
