package org.example.session;





import org.example.user.User;
import org.example.utils.SimpleSessionService;

import java.util.HashMap;

public class SessionService implements SimpleSessionService<User> {

    // STATIC: Significa que esta variable es compartida por TODA la aplicación.
    // Si un usuario hace login, se guarda aquí y se queda en memoria RAM mientras el programa corra.
    private static User activeUser = null;

    // Un pequeño almacén temporal para guardar cosas extra si hace falta.
    private static HashMap<String,Object> data = new HashMap<String,Object>();

    public void login(User user) {
        activeUser = user;
    }

    public void update(User user) {
        activeUser = user;
    }

    public boolean isLoggedIn(){
        return activeUser != null;
    }

    public void logout() {
        activeUser = null;
        data.clear();
    }

    @Override
    public User getActive() { return activeUser; }

    @Override
    public void setObject(String key, Object o) { data.put(key,o); }

    @Override
    public Object getObject(String key) { return data.get(key); }

}
