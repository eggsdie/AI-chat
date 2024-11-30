package entity;

/**
 * Factory interface for creating User objects. Provides methods to create
 * users with or without an email.
 */
public interface UserFactory {

    /**
     * Creates a new User with a name, email, and password.
     *
     * @param name     the name of the new user
     * @param email    the email of the new user
     * @param password the password of the new user
     * @return the new user
     */
    User create(String name, String email, String password);

    /**
     * Creates a new User with a name and password, intended for cases where
     * email is not required.
     *
     * @param name     the name of the new user
     * @param password the password of the new user
     * @return the new user
     */
//    User create(String name, String password);
}
