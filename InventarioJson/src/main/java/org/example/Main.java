package org.example;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //1.Inicializar ObjectMapper y el nombre del archivo
        ObjectMapper mapper = new ObjectMapper();
        //Esto hace el json de salida se vea bonito
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String nombreArchivo ="inventario.json";

        //2.Crear la lista de objetos java
        System.out.println("---Creando lista productos---");
        List<Producto> inventario = new ArrayList<>();
        inventario.add(new Producto("Laptop", 1200.50, 15));
        inventario.add(new Producto("Mouse USB", 25.99, 50));
        inventario.add(new Producto("Teclado Mecánico", 79.99, 30));

        System.out.println("Datos originales:");
        inventario.forEach(producto -> System.out.println(producto));

        // --- 3. SERIALIZACIÓN (Escribir la lista a JSON) ---
        System.out.println("\n--- 2. Escribiendo lista en " + nombreArchivo + " ---");
        try {
            // Escribimos la lista 'inventario' en el archivo
            mapper.writeValue(new File(nombreArchivo), inventario);
            System.out.println("¡Inventario guardado con éxito!");

        } catch (IOException e) {
            System.err.println("Error al escribir el JSON: " + e.getMessage());
            e.printStackTrace();
        }
        // --- 4. DESERIALIZACIÓN (Leer la lista desde JSON) ---
        System.out.println("\n--- 3. Leyendo lista desde " + nombreArchivo + " ---");
        try {
            // ¡Esta es la parte clave para leer LISTAS!
            // No podemos usar 'Producto.class' porque el JSON es un Array [...]
            // Usamos TypeReference para decirle a Jackson el tipo genérico exacto (List<Producto>)

            List<Producto> inventarioLeido = mapper.readValue(
                    new File(nombreArchivo),
                    new TypeReference<List<Producto>>() {} // ¡La magia está aquí!
            );

            System.out.println("Datos leídos del archivo:");
            inventarioLeido.forEach(producto -> System.out.println(producto));

        } catch (IOException e) {
            System.err.println("Error al leer el JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    }
