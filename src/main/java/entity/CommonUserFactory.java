package entity;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactory implements UserFactory {

    @Override
    public User create(String name, String email, String password, String picture) {
        return new CommonUser(name, email, password, picture);
    }

}
