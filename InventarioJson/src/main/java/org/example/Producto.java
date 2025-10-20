package org.example;


import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class Producto {

    private String nombre;
    private double precio;
    private int stock;

    // --- IMPORTANTE ---
// Jackson necesita OBLIGATORIAMENTE un constructor sin argumentos
// para poder deserializar (crear objetos desde el JSON).
    public Producto() {
    }
}
