package org.example.MODELO;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class Pelicula {
        private int id;
        private String titulo;
        private int año;
        private String director;
        private String genero;

        public Pelicula() {
        }
    }

