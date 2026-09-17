package utnfc.isi.back.procesocsv.strategy;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;

/**
 * Estrategia que implementa el Paso 2 del procesamiento del archivo
 * empleados.csv.
 *
 * En esta estrategia se utiliza la librería OpenCSV en su forma más simple:
 * usando la clase CSVReader para leer el archivo completo como una lista de
 * arreglos de cadenas (List<String[]>).
 *
 * Cada fila del archivo es convertida a un array de Strings, en el cual cada
 * posición representa una columna de la fila original. A partir de allí se
 * realiza la conversión manual a objetos de tipo Empleado.
 *
 * Esta clase forma parte del patrón Strategy aplicado en el proyecto y
 * encapsula este paso particular como una estrategia intercambiable.
 */
public class Step02OpenCSVList implements StepStrategy {

    /**
     * Ejecuta el procesamiento del archivo empleados.csv usando
     * CSVReader.readAll().
     *
     * El archivo debe tener encabezado en la primera línea. Esta línea es leída
     * y descartada manualmente. Luego, cada línea es parseada y transformada en
     * una instancia de Empleado (permanente o contratado). Finalmente, se
     * imprime el nombre y el sueldo calculado.
     */
    public void ejecutar() {
        int contador = 0;

        try (CSVReader reader = new CSVReader(new FileReader(".\\src\\main\\data\\empleados.csv"))) {
            // Nuevamente tenemos que saltar la línea de los encabezados
            reader.readNext();

            // ahora sí procesamos el resto del archivo
            List<String[]> filas = reader.readAll();

            for (String[] campos : filas) {
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

        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

    }
}
