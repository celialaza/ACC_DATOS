package org.example.UTIL;

import org.example.MODELO.Contacto;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CSVUtils {

    public static void exportarCSV(List<Contacto> contactos, String rutaArchivo) {
        try(BufferedWriter bw=new BufferedWriter(new FileWriter(rutaArchivo)) ){
            bw.write("id; nombre; email; teléfono");
            bw.newLine();
            for(
            Contacto contacto:contactos){
                bw.write(contacto.getId() + ";"
                        + contacto.getNombre() + ";"
                        + contacto.getEmail() + ";"
                        + contacto.getTelefono());

                bw.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<Contacto> importarCSV(String rutaArchivo) {
        List<Contacto> contactos = new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader(rutaArchivo))) {
            br.readLine();
            String linea;
            while((linea=br.readLine())!=null){
                String[] campos = linea.split(";");
                if (campos.length == 4) {
                    int id = Integer.parseInt(campos[0]);
                    String nombre = campos[1];
                    String email = campos[2];
                    String telefono = campos[3];
                    contactos.add(new Contacto(id, nombre, email, telefono));
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contactos;

}




}
