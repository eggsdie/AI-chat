package use_case.add_friend;

import entity.User;
import entity.ChatEntry;
import entity.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class AddFriendTest {

    // Test data for AddFriendInputBoundary and AddFriendInputData
    private AddFriendInputBoundary addFriendInputBoundary;
    private AddFriendInputData addFriendInputData;
    private String messagePreview;

    // Test data for AddFriendInputData
    private User user;
    private String otherUser;

    // Mocks for AddFriendInteractor dependencies
    private AddFriendUserDataAccessInterface dataAccessInterface;
    private AddFriendOutputBoundary outputBoundary;
    private AddFriendInteractor addFriendInteractor;

    // Data for AddFriendOutputData
    private AddFriendOutputData addFriendOutputData;
    private ArrayList<ChatEntry> chatList;

    @BeforeEach
    void setUp() {
        // Setup test data for AddFriendInputBoundary
        addFriendInputBoundary = new AddFriendInputBoundary() {
            @Override
            public void execute(AddFriendInputData addFriendInputData, String messagePreview) {
                assertNotNull(addFriendInputData, "AddFriendInputData should not be null");
                assertNotNull(messagePreview, "MessagePreview should not be null");
            }
        };

        // Setup test data for AddFriendInputData
        user = new User() {
            @Override
            public String getName() {
                return "";
            }

            @Override
            public String getEmail() {
                return "";
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public String getPicture() {
                return "";
            }
        };
        otherUser = "JaneSmith";
        addFriendInputData = new AddFriendInputData(user, otherUser);
        messagePreview = "Hello! Let's connect.";

        // Setup mocks for AddFriendInteractor
        dataAccessInterface = new AddFriendUserDataAccessInterface() {
            @Override
            public boolean existsByName(String username) {
                return "JaneSmith".equals(username);
            }

            @Override
            public boolean chatWithYourself(String username) {
                return false;
            }

            @Override
            public ArrayList<Message> messagesWithUser(String username) {
                return new ArrayList<>(List.of(new Message("JohnDoe", "JaneSmith", "Hello", "JohnDoe", "10:00")));
            }

            @Override
            public ArrayList<ChatEntry> allChatsWithUser(String username) {
                return new ArrayList<>(List.of(new ChatEntry("JohnDoe", "JaneSmith", "10:00", "Hello")));
            }

            @Override
            public User get(String username) {
                return new User() {
                    @Override
                    public String getName() {
                        return "";
                    }

                    @Override
                    public String getEmail() {
                        return "";
                    }

                    @Override
                    public String getPassword() {
                        return "";
                    }

                    @Override
                    public String getPicture() {
                        return "";
                    }
                };
            }

            @Override
            public void addMessage(String sender, String receiver, String content) {
                // Simulate adding a message, no-op
            }
        };

        outputBoundary = new AddFriendOutputBoundary() {
            @Override
            public void prepareFailView(String errorMessage) {
                System.out.println("Fail: " + errorMessage);
            }

            @Override
            public void prepareSuccessView(AddFriendOutputData outputData) {
                System.out.println("Success: " + outputData);
            }
        };

        // Instantiate AddFriendInteractor
        addFriendInteractor = new AddFriendInteractor(dataAccessInterface, outputBoundary);

        // Initialize AddFriendOutputData
        chatList = new ArrayList<>(Arrays.asList(new ChatEntry("JohnDoe", "JaneSmith", "10:00", "Hello")));
        addFriendOutputData = new AddFriendOutputData(false, chatList, user);
    }

    // Test for AddFriendInputBoundary
    @Test
    void testExecute() {
        addFriendInputBoundary.execute(addFriendInputData, messagePreview);
    }

    // Test for AddFriendInputData
    @Test
    void testGetUser() {
        assertEquals(user, addFriendInputData.getUser(), "getUser should return the correct user.");
    }

    @Test
    void testGetUsername() {
        assertEquals("JohnDoe", addFriendInputData.getUsername(), "getUsername should return the user's name.");
    }

    @Test
    void testGetOtherUser() {
        assertEquals(otherUser, addFriendInputData.getOtherUser(), "getOtherUser should return the correct other user's name.");
    }

    // Test for AddFriendInteractor
    @Test
    void testExecuteUserDoesNotExist() {
        AddFriendInputData inputData = new AddFriendInputData(user, "NonExistentUser");
        addFriendInteractor.execute(inputData, messagePreview);
        // Verify failure view is called with the correct message
        // (You may need to adjust the outputBoundary implementation to track invocations)
    }

    @Test
    void testExecuteChatWithYourself() {
        AddFriendInputData inputData = new AddFriendInputData(user, user.getName());
        addFriendInteractor.execute(inputData, messagePreview);
        // Verify failure view is called with "Can't make a chat with yourself!"
    }

    @Test
    void testExecuteFriendAlreadyAdded() {
        AddFriendInputData inputData = new AddFriendInputData(user, "JaneSmith");
        addFriendInteractor.execute(inputData, messagePreview);
        // Verify failure view is called with "Friend already added."
    }

    // Test for AddFriendOutputBoundary
    @Test
    void testPrepareSuccessView() {
        AddFriendOutputData outputData = new AddFriendOutputData(false, chatList, user);
        outputBoundary.prepareSuccessView(outputData);
    }

    @Test
    void testPrepareFailView() {
        outputBoundary.prepareFailView("User does not exist.");
    }

    // Test for AddFriendOutputData
    @Test
    void testGetChatList() {
        assertEquals(chatList, addFriendOutputData.getChatList(), "getChatList should return the correct chat list.");
    }

    @Test
    void testGetActiveUser() {
        assertEquals(user, addFriendOutputData.getActiveUser(), "getActiveUser should return the correct active user.");
    }

    @Test
    void testIsUseCaseFailed() {
        assertFalse(addFriendOutputData.isUseCaseFailed(), "isUseCaseFailed should return the correct failure status.");
    }

    // Test for AddFriendUserDataAccessInterface

    @Test
    void testExistsByName() {
        // Test that the user exists
        assertTrue(dataAccessInterface.existsByName("JaneSmith"), "existsByName should return true for existing users.");
        // Test that the user does not exist
        assertFalse(dataAccessInterface.existsByName("NonExistingUser"), "existsByName should return false for non-existing users.");
    }

    @Test
    void testChatWithYourself() {
        // Test if the user can chat with themselves
        assertFalse(dataAccessInterface.chatWithYourself("JohnDoe"), "chatWithYourself should return false for different users.");
    }

    @Test
    void testMessagesWithUser() {
        ArrayList<Message> messages = dataAccessInterface.messagesWithUser("JaneSmith");
        assertNotNull(messages, "messagesWithUser should return a non-null list.");
        assertEquals(1, messages.size(), "messagesWithUser should return the correct number of messages.");
        assertEquals("Hello", messages.get(0).getContent(), "messagesWithUser should return the correct message content.");
    }

    @Test
    void testAllChatsWithUser() {
        ArrayList<ChatEntry> chats = dataAccessInterface.allChatsWithUser("JohnDoe");
        assertNotNull(chats, "allChatsWithUser should return a non-null list.");
        assertEquals(1, chats.size(), "allChatsWithUser should return the correct number of chats.");
        assertEquals("Hello", chats.get(0).getLastMessagePreview(), "allChatsWithUser should return the correct message preview.");
    }

    @Test
    void testGetUser_dataAccessInterface() {
        User retrievedUser = dataAccessInterface.get("JohnDoe");
        assertNotNull(retrievedUser, "get should return a non-null user.");
        assertEquals("JohnDoe", retrievedUser.getName(), "get should return the correct user by name.");
    }

    @Test
    void testAddMessage() {
        // Add a message to a chat entry and test the updates
        ChatEntry chatEntry = new ChatEntry("JohnDoe", "JaneSmith", "10:00", "Hello");
        Message newMessage = new Message("JohnDoe", "JaneSmith", "How are you?", "JohnDoe", "11.00");
        chatEntry.addMessage(newMessage);

        assertEquals("How are you?", chatEntry.getLastMessagePreview(), "addMessage should update the message preview.");
        assertEquals("How are you?", chatEntry.getLastMessagePreview(), "addMessage should update the time.");
    }

    @Test
    void testMatchesUsers() {
        // Create a new ChatEntry with user1 "JohnDoe" and user2 "JaneSmith"
        ChatEntry chatEntry = new ChatEntry("JohnDoe", "JaneSmith", "10:00", "Hello");

        // Test that the method returns true for matching user pairs in any order
        assertTrue(chatEntry.matchesUsers("JohnDoe", "JaneSmith"), "matchesUsers should return true when users match.");
        assertTrue(chatEntry.matchesUsers("JaneSmith", "JohnDoe"), "matchesUsers should return true when users match, even if the order is reversed.");

        // Test that the method returns false for non-matching user pairs
        assertFalse(chatEntry.matchesUsers("JohnDoe", "NonExistentUser"), "matchesUsers should return false when users do not match.");
        assertFalse(chatEntry.matchesUsers("NonExistentUser", "JaneSmith"), "matchesUsers should return false when users do not match.");
    }

}
