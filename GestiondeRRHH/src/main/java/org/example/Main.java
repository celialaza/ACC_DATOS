package org.example;

import org.example.departamento.Departamento;
import org.example.departamento.DepartamentoRepository;
import org.example.empleado.Empleado;
import org.example.empleado.EmpleadoRepository;
import org.example.utils.DataProvider;
import org.hibernate.SessionFactory;

import java.util.List;


public class Main {
    public static void main(String[] args){
        SessionFactory sessionFactory = DataProvider.getSessionFactory();

        DepartamentoRepository depRep = new DepartamentoRepository(sessionFactory);
        EmpleadoRepository empRep = new EmpleadoRepository(sessionFactory);

        System.out.println("=== ESTADÍSTICAS DE LA EMPRESA ===");

        // 1. Contar (lo que ya tenías)
        System.out.println("Total Departamentos: " + depRep.count());
        System.out.println("Total Empleados: " + empRep.count());

        // 2. Calcular gasto total en salarios
        List<Empleado> todosLosEmpleados = empRep.findAll();
        double gastoTotal = 0.0;

        for (Empleado e : todosLosEmpleados) {
            if (e.getSalary() != null) {
                gastoTotal += e.getSalary();
            }
        }

        System.out.println("Gasto Total en Salarios: " + gastoTotal + " €");

        // 3. Listar empleados por departamento
        // Esto prueba que la relación @OneToMany funciona bien
        System.out.println("\n--- Detalle por Departamento ---");
        List<Departamento> departamentos = depRep.findAll();
        for (Departamento d : departamentos) {
            System.out.println("Depto: " + d.getName() + " (" + d.getLocation() + ")");
            for (Empleado e : d.getEmpleados()) {
                System.out.println("   - " + e.getFull_name() + " [" + e.getPosition() + "]");
            }
        }
        }
    }

