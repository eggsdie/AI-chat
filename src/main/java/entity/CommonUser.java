package entity;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name;
    private final String email;
    private final String password;
    private final String profilePicture;

    public CommonUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profilePicture = "img/default.jpg";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getPicture() {
        return profilePicture;
    }
}
