package org.example.DATASERVICE;

import org.example.MODELO.Pelicula;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvDataService {



    public static List<Pelicula> leerPeliculas(String archivo){
        List<Pelicula> peliculasLeidas = new ArrayList<>();
try(BufferedReader bf=new BufferedReader(new FileReader(archivo))){
    String s;
    while((s =bf.readLine())!=null){
        String[] trozos = s.split(",");
        if (trozos.length==5){
            int id =Integer.parseInt(trozos[0]);
            int año=Integer.parseInt(trozos[2]);
            Pelicula p=new Pelicula(id,trozos[1],año,trozos[3],trozos[4]);
            peliculasLeidas.add(p);

        }else{
            System.out.println("Línea con formato incorrecto");
        }
    }
} catch (IOException e) {
    throw new RuntimeException(e);
}
return peliculasLeidas;
    }

}
