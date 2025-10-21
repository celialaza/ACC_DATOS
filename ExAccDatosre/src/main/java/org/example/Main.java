package org.example;

import org.example.DATASERVICE.CsvDataService;
import org.example.MODELO.Pelicula;
import org.example.SERVICIOFILTRADO.ServicioFiltrado;

import java.util.List;


public class Main {
   public static final String archivo="peliculas .csv";
    public static void main(String[] args) {

        List<Pelicula> misPeliculas =CsvDataService.leerPeliculas(archivo);

        ServicioFiltrado servicio=new ServicioFiltrado(misPeliculas);
        servicio.filtrarPorTitulo("Matrix");

        servicio.filtrarPorAño(1994);



        }
    }
