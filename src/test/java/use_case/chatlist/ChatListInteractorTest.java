import entity.ChatEntry;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_friend.AddFriendController;
import interface_adapter.add_friend.ChatListState;
import interface_adapter.add_friend.ChatListViewModel;
import interface_adapter.enter_chat.EnterChatController;
import interface_adapter.enter_chat.InChatState;
import interface_adapter.login.LoginController;
import interface_adapter.settings.SettingsController;
import org.junit.jupiter.api.*;
import use_case.add_friend.AddFriendInputBoundary;
import use_case.login.LoginInputBoundary;
import view.ChatListView;

import javax.swing.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatListViewTest {

    private ChatListView chatListView;
    private TestChatListViewModel testViewModel;
    private TestAddFriendController testAddFriendController;
    private TestEnterChatController testEnterChatController;

    @BeforeEach
    void setUp() {
        // Initialize the test dependencies
        testViewModel = new TestChatListViewModel();
        testAddFriendController = new TestAddFriendController();
        testEnterChatController = new TestEnterChatController();
        TestSettingsController testSettingsController = new TestSettingsController();
        TestLoginController testLoginController = new TestLoginController();

        // Create the ChatListView and set its dependencies
        chatListView = new ChatListView(testViewModel);
        chatListView.setAddFriendController(testAddFriendController);
        chatListView.setEnterChatController(testEnterChatController);
        chatListView.setSettingsController(testSettingsController);
        chatListView.setLoginController(testLoginController);
    }

    @Test
    void testInitialization() {
        // Verify the view name is correctly set
        assertEquals("chat list", chatListView.getViewName());
    }

    @Test
    void testAddFriendButton() {
        // Simulate clicking the "Add Friend" button
        chatListView.addFriendButton.doClick();

        // Verify that the controller was invoked
        assertTrue(testAddFriendController.wasExecuted);
    }

    @Test
    void testRefreshChatList() {
        // Add dummy data to the test view model
        testViewModel.addChat(new ChatEntry("Alice", "Bob", "Hello, Bob!", "12:00 PM"));

        // Call refreshChatList with an empty query
        chatListView.refreshChatList("");

        // Verify that the chat list panel contains the chat
        JPanel chatListPanel = (JPanel) ((JScrollPane) chatListView.getComponent(1)).getViewport().getView();
        assertEquals(1, chatListPanel.getComponentCount());
    }

    @Test
    void testChatSelection() {
        // Add dummy data to the test view model
        testViewModel.addChat(new ChatEntry("Alice", "Bob", "Hello, Bob!", "12:00 PM"));

        // Refresh the chat list to render the chat
        chatListView.refreshChatList("");

        // Simulate clicking the chat item
        JPanel chatListPanel = (JPanel) ((JScrollPane) chatListView.getComponent(1)).getViewport().getView();
        JPanel chatItem = (JPanel) chatListPanel.getComponent(0);
        chatItem.getMouseListeners()[0].mouseClicked(null);

        // Verify that the controller was executed
        assertTrue(testEnterChatController.wasExecuted);
    }

    // Manual mock implementations for the controllers and view model
    static class TestChatListViewModel extends ChatListViewModel {
        private final ArrayList<ChatEntry> chatList = new ArrayList<>();

        public void addChat(ChatEntry chatEntry) {
            chatList.add(chatEntry);
            setState(new ChatListState(chatList, null));
        }

        @Override
        public void setState(InChatState state) {
            super.getMessage(state);
            firePropertyChange("state", null, state);
        }

        private void firePropertyChange(String state, Object o, ChatListState state1) {
        }
    }

    static class TestAddFriendController extends AddFriendController {
        private static final AddFriendInputBoundary chatListUseCaseInteractor = ;
        boolean wasExecuted = false;

        public TestAddFriendController() {
            super(chatListUseCaseInteractor);
        }

        @Override
        public void execute(Object activeUser, String friendName, String initialMessage) {
            wasExecuted = true;
        }
    }

    static class TestEnterChatController extends EnterChatController {
        boolean wasExecuted = false;

        public TestEnterChatController() {
            super(enterChatUseCaseInteractor);
        }

        @Override
        public void execute(String user1, String user2) {
            wasExecuted = true;
        }
    }

    static class TestSettingsController extends SettingsController {
        boolean wasExecuted = false;

        public TestSettingsController() {
            super(settingsUseCaseInteractor);
        }

        @Override
        public void execute(Object activeUser) {
            wasExecuted = true;
        }
    }

    static class TestLoginController extends LoginController {
        boolean wasExecuted = false;

        public TestLoginController(LoginInputBoundary loginUseCaseInteractor, ViewManagerModel viewManagerModel) {
            super(loginUseCaseInteractor, viewManagerModel);
        }

        @Override
        public void execute(String username, String password) {
            wasExecuted = true;
        }
    }
}
