package utnfc.isi.back.procesocsv.empleados;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Empleado {
    private Categoria categoria;
    private int legajo;
    private String nombre;
    private double montoBase;

    public double calcularSueldo() {
        return 0.0;
    }
}
