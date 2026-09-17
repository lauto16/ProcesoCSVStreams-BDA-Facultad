package utnfc.isi.back.procesocsv.empleados;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
    private String nombre;
    private double coeficiente;
}
