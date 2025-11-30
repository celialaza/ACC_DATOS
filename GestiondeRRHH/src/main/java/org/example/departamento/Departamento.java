package org.example.departamento;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.empleado.Empleado;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="department")
public class Departamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String location;

    // RELACIÓN UNO A MUCHOS: Un departament tiene MUCHOS empelados.
    // cascade = ALL: Si borro al departamento, borro sus empelados automáticamente.
    // fetch = EAGER: Cuando cargues al departamento de la BD, trae INMEDIATAMENTE su lista de empleados (no esperes a que te la pida).
    // mappedBy = "department": "La otra clase (employee) es la dueña de la relación.
    @OneToMany(cascade={CascadeType.ALL}, mappedBy = "departamento", fetch = FetchType.EAGER)
    private List<Empleado> empleados = new ArrayList<>();

    // Método auxiliar para mantener la coherencia en Java:
    // Al añadir un empleado a la lista, le decimos al departamento "tu dueño soy yo".
    public void addEmpleado(Empleado e){
        e.setDepartamento(this);
        this.empleados.add(e);
    }


}
