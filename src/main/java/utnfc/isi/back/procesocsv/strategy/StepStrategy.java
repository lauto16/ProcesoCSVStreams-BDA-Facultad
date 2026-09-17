package utnfc.isi.back.procesocsv.strategy;

import java.util.List;

import utnfc.isi.back.procesocsv.empleados.Empleado;

/**
 * Interfaz común para todas las estrategias de ejecución.
 *
 * Esta interfaz representa el contrato que deben cumplir todas las clases que encapsulen
 * una forma distinta de cargar y procesar el archivo empleados.csv.
 *
 * Esto es parte del patrón Strategy: definir una familia de algoritmos (pasos),
 * encapsularlos, e intercambiarlos dinámicamente.
 */
public interface StepStrategy {
  void ejecutar();
  public List<Empleado> getEmpleados();
}
