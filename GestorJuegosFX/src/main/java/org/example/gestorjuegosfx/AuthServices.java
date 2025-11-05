package org.example.gestorjuegosfx;

import org.example.gestorjuegosfx.user.User;
import org.example.gestorjuegosfx.user.UserDAO;

import java.util.Optional;
import java.util.logging.Logger;

public class AuthServices {
    private static UserDAO userDAO;
    private Logger logger = Logger.getLogger(AuthServices.class.getName());
    private static User currentUser;

    public AuthServices(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public  Optional<User> login(String email, String password) {
        logger.info("Iniciando login");
        var user = userDAO.findByEmailAndPassword(email, password);
        if (user.isPresent()) {
            currentUser = user.get();
            logger.info("User " + email + " logeado.");
        } else{
            logger.warning("Fallo en el login del user " + email);
        }
        return Optional.ofNullable(currentUser);
        }


    public void logout() {
        logger.info("Realizando logout");
        currentUser = null;
    }

    public Optional <User> getCurrentUser() {
        logger.info("Iniciando getCurrentUser"+currentUser.toString());
       return Optional.ofNullable(currentUser);
    }
}
