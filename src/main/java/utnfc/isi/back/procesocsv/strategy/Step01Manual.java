package utnfc.isi.back.procesocsv.strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

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

                Object[] datos = {legajo, nombre, tipo, categoria, fecha, montoBase};
                System.out.println(Arrays.toString((datos)));
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
