package org.example;

import org.example.CONEXION.DataManager;
import org.example.DAO.TareaDAO;
import org.example.MODELO.Tarea;
import org.w3c.dom.ls.LSOutput;

import javax.sql.DataSource;


public class Main {
    public static void main(String[] args) {
        DataSource ds= DataManager.getDataSource();
        TareaDAO dao = new TareaDAO(ds);
        Tarea tarea = new Tarea("Pasear al perro",1);
        Tarea tarea2 = new Tarea("Hacer la compra",0);
        Tarea tarea3 = new Tarea("Visitar abuelos",0);
        Tarea tarea4 = new Tarea("Entrenar",0);
        Tarea tarea5 = new Tarea("Remar",1);

        System.out.println("---Coprobando tareas iniciales---");
        System.out.println(dao.findAll());



        System.out.println("---Creando tareas---");
        System.out.println(dao.save(tarea5));


        System.out.println("---Ver tareas---");
        System.out.println(dao.findAll());

        System.out.println("---Actualizando tareas---");
        tarea= new Tarea(1,"Comprar regalos navidad",1);
        dao.update(tarea);



        System.out.println("---Eliminando tareas---");
        dao.delete(5);
        dao.delete(6);
        dao.delete(7);
        dao.delete(8);


        System.out.println("---Buscar tarea por id---");
        System.out.println(dao.findById(1));
        }



        }



