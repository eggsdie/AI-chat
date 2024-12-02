package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import entity.*;
import use_case.add_friend.AddFriendUserDataAccessInterface;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.change_picture.ChangePictureUserDataAccessInterface;
import use_case.enter_chat.EnterChatUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.send_message.SendMessageUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program.
 */
public class InMemoryUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        AddFriendUserDataAccessInterface,
        EnterChatUserDataAccessInterface,
        SendMessageUserDataAccessInterface,
        ChangePictureUserDataAccessInterface {

    private String currentUsername;
    private final DemoRestfulApi demoRestfulApi;
    private final UserFactory userFactory;

    public InMemoryUserDataAccessObject(DemoRestfulApi demoRestfulApi, UserFactory userFactory) {
        this.demoRestfulApi = demoRestfulApi;
        this.userFactory = userFactory;
    }

    private Map<String, User> getUsers() {
        final JsonArray usersJson = JsonParser.parseString(demoRestfulApi.getAllUsers()).getAsJsonArray();
        final Map<String, User> users = new HashMap<>();
        usersJson.forEach(item -> {
            final String username = item.getAsJsonObject().get("userName").getAsString();
            final String password = item.getAsJsonObject().get("password").getAsString();
            final String email = item.getAsJsonObject().get("email").getAsString();
            // Corrected parameter order
            final User userToAdd = userFactory.create(username, email, password);
            users.put(username, userToAdd);
        });
        return users;
    }

    private Map<String, String> getIds() {
        final JsonArray usersJson = JsonParser.parseString(demoRestfulApi.getAllUsers()).getAsJsonArray();
        final Map<String, String> ids = new HashMap<>();
        usersJson.forEach(item -> {
            final String username = item.getAsJsonObject().get("userName").getAsString();
            final String userId = item.getAsJsonObject().get("userId").getAsString();
            ids.put(username, userId);
        });
        return ids;
    }

    private String getId(String username) {
        return getIds().get(username);
    }

    @Override
    public boolean existsByName(String identifier) {
        return getUsers().containsKey(identifier);
    }

    @Override
    public void save(User user) {
        demoRestfulApi.createNewUser(generateUserId(), user.getName(), user.getPassword(), user.getEmail());
    }

    private String generateUserId() {
        final JsonArray jsonArray = JsonParser.parseString(demoRestfulApi.getAllUsers()).getAsJsonArray();
        int newId = 0;
        // Iterate over each element in the array
        for (JsonElement element : jsonArray) {
            if (element.getAsJsonObject().has("userId")) {
                final int curId = element.getAsJsonObject().get("userId").getAsInt();
                if (curId == newId) {
                    newId += 1;
                }
            }
        }
        return String.valueOf(newId);
    }

    @Override
    public void addMessage(String sender, String receiver, String content) {
        demoRestfulApi.createNewMessage(generateMsgId(), content, sender, receiver);
    }

    private String generateMsgId() {
        final JsonArray jsonArray = JsonParser.parseString(demoRestfulApi.getAllMessages()).getAsJsonArray();
        int newId = 0;
        // Iterate over each element in the array
        for (JsonElement element : jsonArray) {
            if (element.getAsJsonObject().has("messageId")) {
                final int curId = element.getAsJsonObject().get("messageId").getAsInt();
                if (curId == newId) {
                    newId += 1;
                }
            }
        }
        return String.valueOf(newId);
    }

    @Override
    public void changePicture(User user) {
        demoRestfulApi.updateUser(getId(user.getName()), user.getName(), user.getPassword(), user.getEmail());
    }

    @Override
    public User get(String username) {
        return getUsers().get(username);
    }

    @Override
    public void changePassword(User user) {
        // Replace the old entry with the new password
        demoRestfulApi.updateUser(getId(user.getName()), user.getName(), user.getPassword(), user.getEmail());
    }

    @Override
    public void setCurrentUsername(String name) {
        this.currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    private ArrayList<Message> getAllMessages() {
        final JsonArray msgsJson = JsonParser.parseString(demoRestfulApi.getAllMessages()).getAsJsonArray();
        final ArrayList<Message> messages = new ArrayList<>();
        msgsJson.forEach(item -> {
            final String messageId = item.getAsJsonObject().get("messageId").getAsString();
            final String content = item.getAsJsonObject().get("messageContent").getAsString();
            final String senderUsername = item.getAsJsonObject().get("senderUsername").getAsString();
            final String receiverUsername = item.getAsJsonObject().get("receiverUsername").getAsString();
            final String time = item.getAsJsonObject().get("createDate").getAsString();
            final Message message = new Message(messageId, senderUsername, content,
                    receiverUsername, time);
            messages.add(message);
        });
        return messages;
    }

    @Override
    public boolean chatWithYourself(String username) {
        return username.equals(currentUsername);
    }

    public ArrayList<Message> messagesWithUser(String username) {
        final ArrayList<Message> messages = new ArrayList<>();
        for (Message message : getAllMessages()) {
            if (message.getSender().equals(username) || message.getReceiver().equals(username)) {
                messages.add(message);
            }
        }
        return messages;
    }

    public ArrayList<Message> messagesByChat(String sender, String receiver) {
        final ArrayList<Message> messages = new ArrayList<>();
        for (Message message : getAllMessages()) {
            if ((message.getSender().equals(sender) && message.getReceiver().equals(receiver))
                    || (message.getSender().equals(receiver) && message.getReceiver().equals(sender))) {
                messages.add(message);
            }
        }
        return messages;
    }

    public ArrayList<ChatEntry> allChatsWithUser(String username) {
        final ArrayList<Message> messages = messagesWithUser(username);
        final ArrayList<ChatEntry> chatList = new ArrayList<>();
        for (Message message : messages) {
            boolean found = false;

            // Search for an existing ChatEntry
            for (ChatEntry chatEntry : chatList) {
                if (chatEntry.matchesUsers(message.getSender(), message.getReceiver())) {
                    chatEntry.addMessage(message);
                    chatList.remove(chatEntry);
                    chatList.add(chatEntry);
                    found = true;
                    break;
                }
            }

            // If no matching ChatEntry found, create a new one
            if (!found) {
                final ChatEntry newChatEntry = new ChatEntry(message.getSender(), message.getReceiver(),
                        message.getTime(), message.getContent());
                newChatEntry.addMessage(message);
                chatList.add(newChatEntry);
            }
        }
        return chatList;
    }
}
