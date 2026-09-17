package utnfc.isi.back.procesocsv;

import java.util.HashMap;
import java.util.Map;

import utnfc.isi.back.procesocsv.informes.InformeEmpleados;
import utnfc.isi.back.procesocsv.strategy.Step01Manual;
import utnfc.isi.back.procesocsv.strategy.Step02OpenCSVList;
import utnfc.isi.back.procesocsv.strategy.Step03OpenCSVMap;
import utnfc.isi.back.procesocsv.strategy.StepStrategy;

/**
 * Clase principal del proyecto.
 * Ejecuta uno de los pasos de procesamiento de empleados en función del argumento recibido.
 *
 * Este diseño implementa el patrón Strategy, que permite encapsular diferentes algoritmos (en este caso, formas de procesar el CSV)
 * y seleccionar dinámicamente cuál ejecutar en tiempo de ejecución, sin modificar esta clase.
 *
 * Cada estrategia se implementa como una clase concreta que extiende la interfaz StepStrategy.
 * Esto permite escalar fácilmente el sistema agregando nuevas estrategias sin cambiar el código de esta clase.
 */
public class App 
{
    public static void main( String[] args )
    {
        // Verifica que el usuario haya pasado un argumento (ej. "step01", "step02", etc.)
        if (args.length == 0) {
            System.out.println("Debe indicar el paso a ejecutar. Ejemplo:");
            System.out.println("  mvn compile exec:java -Dexec.args=\"paso01\"");
            return;
        }

        // Se obtiene el argumento en minúsculas por consistencia
        String paso = args[0].toLowerCase();

        // Mapa que asocia nombres de pasos con sus implementaciones (estrategias)
        Map<String, StepStrategy> estrategias = new HashMap<>();
        estrategias.put("paso01", new Step01Manual());
        estrategias.put("paso02", new Step02OpenCSVList());
        estrategias.put("paso03", new Step03OpenCSVMap());

        // Obtiene la estrategia correspondiente al paso indicado
        StepStrategy strategy = estrategias.get(paso);

        if (strategy == null) {
            // Si no se encuentra una estrategia correspondiente, se notifica al usuario
            System.out.println("Paso no reconocido: " + paso);
            System.out.println("Pasos disponibles: step01, step02, step03, step04");
            return;
        }

        // Ejecuta la estrategia seleccionada
        strategy.ejecutar();

        InformeEmpleados informe = new InformeEmpleados(strategy.getEmpleados());
        
        informe.antiguedadPromedioPermanentes();
        informe.mayorYMenorSueldo();
        informe.porcentajeContratados();
        informe.totalSueldosPorTipo();
    }
}
