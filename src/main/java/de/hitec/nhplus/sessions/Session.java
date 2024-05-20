package de.hitec.nhplus.sessions;

import de.hitec.nhplus.model.User;

public class Session {

    private static Session instance;
    private User userSession;
    private Session() {
    }
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public User getUserSession() {
        return userSession;
    }

    public void setUserSession(User userSession) {
        this.userSession = userSession;
    }
}