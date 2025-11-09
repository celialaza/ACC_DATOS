package org.example.MODELO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Tarea {
    int id;
    String descripcion;
    int completada;

    public Tarea( String descripcion, int completada) {
        this.descripcion = descripcion;
        this.completada = completada;
    }
}
