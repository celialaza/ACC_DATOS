package org.example;



import org.example.game.Game;
import org.example.game.GameRepository;
import org.example.session.AuthService;
import org.example.session.SessionService;
import org.example.user.User;
import org.example.user.UserRepository;
import org.example.utils.DataProvider;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Inicializar la conexión y las dependencias
        System.out.println("Iniciando aplicación...");
        SessionFactory sessionFactory = DataProvider.getSessionFactory();

        // Repositorios
        UserRepository userRepo = new UserRepository(sessionFactory);
        GameRepository gameRepo = new GameRepository(sessionFactory);

        // Servicios
        AuthService authService = new AuthService(userRepo);
        SessionService sessionService = new SessionService();

        Scanner scanner = new Scanner(System.in);

        // 2. Proceso de Login (Obligatorio)
        System.out.println("=== SISTEMA DE GESTIÓN DE JUEGOS ===");

        while (!sessionService.isLoggedIn()) {
            System.out.println("\nPor favor, inicie sesión para continuar.");

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            // Intentamos validar al usuario
            Optional<User> userOptional = authService.validateUser(email, password);

            if (userOptional.isPresent()) {
                sessionService.login(userOptional.get());
                System.out.println("¡Login exitoso! Bienvenido, " + userOptional.get().getEmail());
            } else {
                System.out.println("ERROR: Credenciales incorrectas. Inténtalo de nuevo.");
            }
        }

        // 3. Probar funcionalidades de GameRepository (Solo accesibles tras login)
        System.out.println("\n--- Accediendo al repositorio de juegos ---");
        User currentUser = sessionService.getActive();

        if (currentUser.getIsAdmin()) {
            System.out.println("(Modo Admin activo)");
        }

        // A) Contar juegos
        Long totalGames = gameRepo.count();
        System.out.println("Total de juegos en la base de datos: " + totalGames);

        // B) Listar todos los juegos
        System.out.println("\nListado de Juegos:");
        List<Game> games = gameRepo.findAll();
        if (games.isEmpty()) {
            System.out.println("No hay juegos registrados.");
        } else {
            for (Game g : games) {
                // Nota: g.getUser().getEmail() funciona porque @ManyToOne carga EAGER por defecto
                System.out.printf("- ID: %d | Título: %s | Plataforma: %s | Dueño: %s%n",
                        g.getId(), g.getTitle(), g.getPlatform(), g.getUser().getEmail());
            }
        }

        // C) Buscar un juego por ID específico
        System.out.print("\nIntroduce el ID de un juego para ver detalles (o 0 para salir): ");
        try {
            String input = scanner.nextLine();
            Long idBusqueda = Long.parseLong(input);

            if (idBusqueda != 0) {
                Optional<Game> gameOpt = gameRepo.findById(idBusqueda);
                if (gameOpt.isPresent()) {
                    System.out.println("Juego encontrado: " + gameOpt.get());
                } else {
                    System.out.println("No se encontró ningún juego con ID: " + idBusqueda);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
        
         System.out.print("\nIntroduce el año: ");
        try {
            String input = scanner.nextLine();
            int anio = Integer.parseInt(input);

            if (anio != 0) {
                List<Game> juegos = gameRepo.findAllByYear(anio);

                if (!juegos.isEmpty()) {
                    System.out.println("Juegos encontrados del año " + anio + ":");

                    for (Game g : juegos) {
                        System.out.println(g); 
                    }
                } else {
                    System.out.println("No se encontró ningún juego del año " + anio);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Debes introducir un número válido.");
        }

        System.out.print("\nIntroduce un año límite (te mostraré los juegos ANTERIORES a ese año): ");
        try {
            int anioLimite = Integer.parseInt(scanner.nextLine());

            // Llamamos al nuevo método
            List<Game> juegosAntiguos = gameRepo.findGamesBeforeYear(anioLimite);

            if (!juegosAntiguos.isEmpty()) {
                System.out.println("--- Juegos Clásicos encontrados ---");
                for (Game g : juegosAntiguos) {
                    // Imprimimos el año y el título para verlo claro
                    System.out.println("[" + g.getYear() + "] " + g.getTitle());
                }
            } else {
                System.out.println("No hay juegos anteriores al año " + anioLimite);
            }

        } catch (NumberFormatException e) {
            System.out.println("Error: Introduce un año válido.");
        }


        // 4. Cerrar sesión y recursos
        sessionService.logout();
        System.out.println("\nSesión cerrada. ¡Hasta luego!");
        sessionFactory.close();
        scanner.close();
    }
}
