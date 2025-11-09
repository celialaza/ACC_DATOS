package org.example.MODELO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Contacto {
    private  int  id;
    private  String nombre;
    private  String email;
    private  String telefono;
}
