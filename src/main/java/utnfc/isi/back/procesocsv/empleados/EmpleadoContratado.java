package utnfc.isi.back.procesocsv.empleados;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class EmpleadoContratado extends Empleado{
    LocalDate fechaContratacion;

    public EmpleadoContratado(Categoria categoria,
        int legajo,
        String nombre,
        double montoBase, LocalDate fechaContratacion) {
    super(categoria, legajo, nombre, montoBase);
    this.fechaContratacion = fechaContratacion;
}

    @Override 
    public double calcularSueldo(){
        return getMontoBase() * getCategoria().getCoeficiente() * (1 + calcularIncremento());
    }

    public double calcularIncremento(){
        Map<String, Double> incrementos = new HashMap<>();
        incrementos.put("A", 0.1);
        incrementos.put("B", 0.05);
        incrementos.put("C", 0.02);
        return incrementos.get(getCategoria().getNombre());
    }
}
