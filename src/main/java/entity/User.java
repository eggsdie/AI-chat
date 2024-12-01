package entity;

/**
 * The representation of a user in our program.
 */
public interface User {

    String getName();

    /**
     * Returns the email of the user.
     * @return the email of the user.
     */
    String getEmail();

    /**
     * Returns the password of the user.
     * @return the password of the user.
     */
    String getPassword();
}
