package utnfc.isi.back.procesocsv.strategy;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

import com.opencsv.CSVReaderHeaderAware;

/**
 * Estrategia que implementa el Paso 3 del procesamiento del archivo
 * empleados.csv.
 *
 * En este paso se utiliza la clase CSVReaderHeaderAware de OpenCSV, que permite
 * leer cada fila del archivo como un Map<String, String>, donde la clave es el
 * nombre de la columna (obtenido del encabezado del archivo CSV).
 *
 * Este enfoque es más robusto que el uso de arrays por posición, ya que no
 * depende del orden de las columnas y permite acceder directamente a los datos
 * por nombre, como si fueran atributos de un objeto.
 */
public class Step03OpenCSVMap implements StepStrategy {

    /**
     * Ejecuta la estrategia de lectura usando un Map<String, String> por fila,
     * imprimiendo los primeros 10 registros para verificación.
     */
    @Override
    public void ejecutar() {

        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(".\\src\\main\\data\\empleados.csv"))) {
            Map<String, String> fila;
            int contador = 0;

            while ((fila = reader.readMap()) != null) {
                int legajo = Integer.parseInt(fila.get("legajo"));
                String nombre = fila.get("nombre");
                String tipo = fila.get("tipo");
                String categoria = fila.get("categoria");
                LocalDate fecha = LocalDate.parse(fila.get("fecha"));
                double montoBase = Double.parseDouble(fila.get("montoBase"));

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
