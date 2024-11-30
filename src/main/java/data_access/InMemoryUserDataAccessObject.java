package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import entity.*;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.chat_list.ChatListUserDataAccessInterface;
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
        ChatListUserDataAccessInterface,
        EnterChatUserDataAccessInterface,
        SendMessageUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();

    private String currentUsername;
    private DemoRestfulApi demoRestfulApi;
    private final UserFactory userFactory;

    public InMemoryUserDataAccessObject(DemoRestfulApi demoRestfulApi, UserFactory userFactory) {
        this.demoRestfulApi = demoRestfulApi;
        this.userFactory = userFactory;
    }

    private Map<String, User> getUsers() {
        final JsonArray usersJson = JsonParser.parseString(demoRestfulApi.getAllUsers()).getAsJsonArray();
        final Map<String, User> users1 = new HashMap<>();
        usersJson.forEach(item -> {
            final String id = item.getAsJsonObject().get("userId").getAsString();
            final String username = item.getAsJsonObject().get("userName").getAsString();
            final String password = item.getAsJsonObject().get("password").getAsString();
            final String email = item.getAsJsonObject().get("email").getAsString();
            final User userToAdd = userFactory.create(username, password, email);
            users1.put(username, userToAdd);
        });
        return users1;
    }

    /*@Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }*/

    @Override
    public boolean existsByName(String identifier) {
        return getUsers().containsKey(identifier);
    }

    /*@Override
    public void save(User user) {
        users.put(user.getName(), user);
    }*/

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

    /*@Override
    public User get(String username) {
        return users.get(username);
    }*/

    @Override
    public User get(String username) {
        return getUsers().get(username);
    }

    public String getId(String username) {
        return get(username).getId();
    }

    /*@Override
    public void changePassword(User user) {
        // Replace the old entry with the new password
        users.put(user.getName(), user);
    }*/

    @Override
    public void changePassword(User user) {
        // Replace the old entry with the new password
        demoRestfulApi.updateUser(user.getId(), user.getName(), user.getPassword(), user.getEmail());
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
