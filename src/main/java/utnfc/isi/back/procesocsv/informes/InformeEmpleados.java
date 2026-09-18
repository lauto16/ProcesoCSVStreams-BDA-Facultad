package utnfc.isi.back.procesocsv.informes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import utnfc.isi.back.procesocsv.empleados.Empleado;
import utnfc.isi.back.procesocsv.empleados.EmpleadoContratado;
import utnfc.isi.back.procesocsv.empleados.EmpleadoPermanente;

public class InformeEmpleados {

    private List<Empleado> empleados;

    public InformeEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public Map<String, Double> mayorYMenorSueldo() {
        Map<String, Double> mapa = new HashMap<>();

        double mayor = this.empleados.stream().mapToDouble(Empleado::calcularSueldo).max().getAsDouble();
        double menor = this.empleados.stream().mapToDouble(Empleado::calcularSueldo).min().getAsDouble();

        mapa.put("Mayor", mayor);
        mapa.put("Menor", menor);

        return mapa;
    }

    public Map<String, Double> totalSueldosPorTipo() {
        return this.empleados.stream().collect(
                Collectors.groupingBy(p -> p instanceof EmpleadoContratado ? "Contratado" : "Permanente",
                        Collectors.summingDouble(Empleado::calcularSueldo))

        );
    }

    public double porcentajeContratados() {
        double totalEmpleados = this.empleados.size();
        double totalContratados = (double) this.empleados.stream().filter(p -> p instanceof EmpleadoContratado).count();

        return (totalContratados * 100) / totalEmpleados;
    }

    public double antiguedadPromedioPermanentes() {
        return this.empleados.stream()
                .filter(p -> p instanceof EmpleadoPermanente)
                .mapToDouble(p -> ((EmpleadoPermanente) p).calcularAntiguedad()).average().orElse(0);
    }
}