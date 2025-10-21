package org.example.SERVICIOFILTRADO;

import org.example.DATASERVICE.CsvDataService;
import org.example.MODELO.Pelicula;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.Main.archivo;

public class ServicioFiltrado {
    private List<Pelicula> todasLasPeliculas;


    public ServicioFiltrado(List<Pelicula> peliculas) {
        this.todasLasPeliculas = peliculas;
    }
    public void filtrarPorTitulo(String cadena){
        String tituloPelicula="";

        List<Pelicula> peliculasFiltradas =new ArrayList<>();
        final String cadenaBusqueda=cadena.toLowerCase();
        for(Pelicula p : todasLasPeliculas){
            tituloPelicula=p.getTitulo().toLowerCase();

            if(tituloPelicula.contains(cadenaBusqueda)){
                peliculasFiltradas.add(p);
            }
        }
        try (BufferedWriter bw =new BufferedWriter(new FileWriter("peliculasFiltradaTitulo.csv"))){
            for(Pelicula p:peliculasFiltradas){
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void filtrarPorAño(int anio){

        List<Pelicula> peliculasFiltradas =new ArrayList<>();
        final int anioBusqueda=anio;
        for(Pelicula p : todasLasPeliculas){
            int anioPelicula=p.getAño();

            if(anioPelicula==anioBusqueda){
                peliculasFiltradas.add(p);
            }
        }
        try (BufferedWriter bw =new BufferedWriter(new FileWriter("peliculasFiltradaAño.csv"))){
            for(Pelicula p:peliculasFiltradas){
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
