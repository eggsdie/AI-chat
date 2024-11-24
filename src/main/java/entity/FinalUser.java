
package entity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FinalUser {
    private String username;
    private String email;
    private String password;
    private File profilePicture;
    private Map<Friend, Chat> chatList;

    public FinalUser(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.profilePicture = new File("images/default_profile_picture.jpeg");
        this.chatList = new HashMap<>();
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public File getProfilePicture() {
        return profilePicture;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeProfilePicture(File newProfilePicture) {
        this.profilePicture = newProfilePicture;
    }

    public Map<Friend, Chat> getConversationUsers() {
        return chatList;
    }

    public void addChat(Friend otherUser, Chat chatHistory) {
        chatList.put(otherUser, chatHistory);
    }
}
