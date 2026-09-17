package utnfc.isi.back.procesocsv.empleados;

import java.time.LocalDate;

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
        return 0.0;
    }

    public double calcularIncremento(){
        return 0.0;
    }
}
