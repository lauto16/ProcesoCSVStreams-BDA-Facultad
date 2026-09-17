package utnfc.isi.back.procesocsv.empleados;

import java.time.LocalDate;

public class EmpleadoPermanente extends Empleado {
    LocalDate fechaIngreso;

    public EmpleadoPermanente(Categoria categoria,
            int legajo,
            String nombre,
            double montoBase, LocalDate fechaIngreso) {
        super(categoria, legajo, nombre, montoBase);
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public double calcularSueldo() {
        return getMontoBase() * getCategoria().getCoeficiente() * (1 + 0.02 * calcularAntiguedad());
    }

    public int calcularAntiguedad() {
        return LocalDate.now().getYear() - fechaIngreso.getYear();
    }
}
