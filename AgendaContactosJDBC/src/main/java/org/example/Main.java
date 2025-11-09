package org.example;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.CONEXION.DataManager;
import org.example.DAO.ContactoDAO;
import org.example.DAO.ContactoDAOJDBC;
import org.example.MODELO.Contacto;
import org.example.UTIL.CSVUtils;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DataSource ds = DataManager.getDataSource();
        ContactoDAO<Contacto> dao = new ContactoDAOJDBC(ds);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("---MENÚ AGENDA---");
            System.out.println("1. Crear Contacto");
            System.out.println("2. Ver contactos");
            System.out.println("3. Eliminar Contacto");
            System.out.println("4. Actualizar Contacto");
            System.out.println("5. Buscar contacto por id");
            System.out.println("6. Exportar contactos");
            System.out.println("7. Importar contactos");
            System.out.println("0. Salir");

            System.out.println("Elige una opción: ");
            try{
            opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes introducir un número.");
                opcion = -1; //opción inválida para que el bucle repita
            }
            switch (opcion) {
                case 1:
                    crearContacto(dao, sc);
                    break;
                case 2:
                    findAllContactos(dao);
                    break;
                case 3:
                    eliminarContacto(dao, sc);
                    break;
                case 4:
                    actualizarContacto(dao, sc);
                    break;

                case 5:
                    buscarContactoPorId(dao,sc);
                    break;
                case 6:
                    exportarContactos(dao);
                    break;
                case 7:
                    importarContactos(dao, sc);
                    break;
                case 0:
                    System.out.println("Adiós");
                    return;

            }
        }
        sc.close();

    }

    private static void crearContacto(ContactoDAO<Contacto> dao, Scanner sc) {
        System.out.println("Introduce el nombre:");
        String nombre = sc.nextLine();

        System.out.println("Introduce el email:");
        String email = sc.nextLine();

        System.out.println("Introduce el teléfono:");
        String telefono = sc.nextLine();

        Contacto nuevo = new Contacto(0, nombre, email, telefono);
        // Llamamos al DAO
        try {
            dao.crearContacto(nuevo);
            System.out.println("¡Contacto creado con éxito!");
        } catch (SQLException e) {
            System.out.println("Error al guardar el contacto: " + e.getMessage());
        }
    }
    private static void eliminarContacto(ContactoDAO dao, Scanner sc) {
        System.out.println("Introduce el id del contacto:");
        try {
            int id = Integer.parseInt(sc.nextLine());

            // Llamamos al DAO
            dao.eliminarContacto(id);
            System.out.println("¡Contacto eliminado con éxito!");

        } catch (SQLException e) {
            System.out.println("Error de base de datos al eliminar: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Debes introducir un ID numérico.");
        }

    }
    private static void findAllContactos(ContactoDAO<Contacto> dao) {
        System.out.println("--- Lista de Contactos ---");
        try {
            List<Contacto> contactos = dao.findAllContactos();
            if (contactos.isEmpty()) {
                System.out.println("No hay contactos en la agenda.");
            } else {
                for (Contacto c : contactos) {
                    // Aquí es donde tu método toString() de Contacto es útil
                    System.out.println(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al leer los contactos: " + e.getMessage());
        }
}
private static void actualizarContacto(ContactoDAO<Contacto> dao, Scanner sc) {
        System.out.println("Introduce el id del contacto:");
        int id = Integer.parseInt(sc.nextLine());

        System.out.println("Introduce el nombre:");
        String nombre = sc.nextLine();

        System.out.println("Introduce el email:");
        String email = sc.nextLine();

        System.out.println("Introduce el teléfono:");
        String telefono = sc.nextLine();

        Contacto nuevo = new Contacto(id, nombre, email, telefono);
        // Llamamos al DAO
        try {
            dao.actualizarContacto(nuevo);
            System.out.println("¡Contacto actualizado con éxito!");
        } catch (SQLException e) {
            System.out.println("Error al guardar el contacto: " + e.getMessage());
        }


}
    private static void buscarContactoPorId(ContactoDAO<Contacto> dao,Scanner sc) {
        System.out.println("Introduce el id del contacto:");
        int id=Integer.parseInt(sc.nextLine());
        try {
            List<Contacto> contactos = dao.buscarContactoPorId(id);
            if (contactos.isEmpty()) {
                System.out.println("No hay contactos con este id.");
            } else {
                for (Contacto c : contactos) {
                    System.out.println(c);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al leer los contactos: " + e.getMessage());
        }
    }
    private static void exportarContactos(ContactoDAO<Contacto> dao) {
        try {
            List<Contacto> contactos = dao.findAllContactos();
            if (contactos.isEmpty()) {
                System.out.println("No hay contactos para exportar.");
                return;
            }

            String archivo = "contactos_exportados.csv";
            CSVUtils.exportarCSV(contactos, archivo);

            System.out.println("¡Contactos exportados con éxito a " + archivo + "!");

        } catch (SQLException e) {
            System.out.println("Error de BD al obtener contactos: " + e.getMessage());
        }
    }
    private static void importarContactos(ContactoDAO<Contacto> dao, Scanner sc) {
        System.out.print("Introduce el nombre del archivo CSV a importar (ej: contactos_exportados.csv): ");
        String archivo = sc.nextLine();

        try {

            List<Contacto> contactosParaImportar = CSVUtils.importarCSV(archivo);

            if (contactosParaImportar.isEmpty()) {
                System.out.println("El archivo está vacío o no se pudieron leer contactos.");
                return;
            }

            System.out.println("Contactos leídos del CSV: " + contactosParaImportar.size());

            // 2. Itera la lista y llama al DAO para GUARDAR cada contacto
            int contador = 0;
            for (Contacto contacto : contactosParaImportar) {
                try {
                    dao.crearContacto(contacto);
                    contador++;
                } catch (SQLException e) {
                    System.out.println("Error al guardar el contacto " + contacto.getNombre() + ": " + e.getMessage());
                }
            }
            System.out.println("¡Importación completada! Se guardaron " + contador + " contactos nuevos.");

        } catch (NumberFormatException e) {
            System.out.println("Error en el formato del archivo: un ID no era un número. " + e.getMessage());
        }
    }
}
