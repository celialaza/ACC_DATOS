package org.example.empleado;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.departamento.Departamento;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="employee")
public class Empleado implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int  id;
    private String full_name;
    private String position;
    private Double salary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")// HIBERNATE: "Crea una columna que será la CLAVE FORÁNEA para conectar con la tabla department".
    private Departamento departamento;

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", full_name='" + full_name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", departamento=" + departamento.getName() + //solo se imprime el nombre
                '}';
    }
}
