package de.hitec.nhplus.sessions;

import de.hitec.nhplus.model.User;

/**
 * Singleton class that manages the user session.
 */
public class Session {

    // The single instance of Session
    private static Session instance;

    // The current user session
    private User userSession;

    /**
     * Private constructor to prevent instantiation.
     */
    private Session() {
    }

    /**
     * Returns the single instance of the Session class.
     *
     * @return the single instance of Session
     */
    public static synchronized Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    /**
     * Returns the current user session.
     *
     * @return the current User session
     */
    public User getUserSession() {
        return userSession;
    }

    /**
     * Sets the current user session.
     *
     * @param userSession the User session to set
     */
    public void setUserSession(User userSession) {
        this.userSession = userSession;
    }
}