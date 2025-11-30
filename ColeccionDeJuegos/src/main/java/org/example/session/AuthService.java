package org.example.session;



import org.example.user.User;
import org.example.user.UserRepository;

import java.util.Optional;

public class AuthService {

    UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> validateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                return user;
            } else  {
                return Optional.empty();
            }
        }
        return user;
    }

}