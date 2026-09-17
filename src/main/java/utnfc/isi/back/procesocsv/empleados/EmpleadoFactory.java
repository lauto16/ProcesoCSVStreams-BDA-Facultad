package utnfc.isi.back.procesocsv.empleados;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EmpleadoFactory {
    public static Empleado createEmpleado(int legajo,
            String nombre,
            String tipo,
            String categoria,
            LocalDate fecha,
            double montoBase) throws Exception {

        Map<String, Categoria> categorias = new HashMap<>();

        categorias.put("A", new Categoria("A", 1.2));
        categorias.put("B", new Categoria("B", 1.0));
        categorias.put("C", new Categoria("C", 0.9));

        if (tipo.toUpperCase().equals("PERMANENTE")) {
            return new EmpleadoPermanente(categorias.get(categoria), legajo, nombre, montoBase, fecha);
        } else if (tipo.toUpperCase().equals("CONTRATADO")) {
            return new EmpleadoContratado(categorias.get(categoria), legajo, nombre, montoBase, fecha);
        } else {
            throw new Exception("El empleado debe ser CONTRATADO ó PERMANENTE");
        }
    }
}
