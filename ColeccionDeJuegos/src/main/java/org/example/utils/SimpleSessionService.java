package org.example.utils;


public interface SimpleSessionService<T> {
    void login(T u);
    boolean isLoggedIn();
    void logout();
    T getActive();

    void setObject( String key, Object o );
    Object getObject(String key);
}
