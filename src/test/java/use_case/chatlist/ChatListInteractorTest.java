package use_case.chatlist;

import entity.ChatEntry;
import interface_adapter.add_friend.AddFriendController;
import interface_adapter.add_friend.ChatListState;
import interface_adapter.add_friend.ChatListViewModel;
import interface_adapter.enter_chat.EnterChatController;
import interface_adapter.login.LoginController;
import interface_adapter.settings.SettingsController;
import org.junit.jupiter.api.*;
import use_case.add_friend.AddFriendInputBoundary;
import use_case.enter_chat.EnterChatInputBoundary;
import use_case.settings.SettingsInputBoundary;
import view.ChatListView;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatListInteractorTest {

    private ChatListView chatListView;
    private TestChatListViewModel testViewModel;
    private TestAddFriendController testAddFriendController;
    private TestEnterChatController testEnterChatController;

    @BeforeEach
    void setUp() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            testViewModel = new TestChatListViewModel();
            testAddFriendController = new TestAddFriendController();
            testEnterChatController = new TestEnterChatController();
            TestSettingsController testSettingsController = new TestSettingsController();
            TestLoginController testLoginController = new TestLoginController();

            chatListView = new ChatListView(testViewModel);
            chatListView.setAddFriendController(testAddFriendController);
            chatListView.setEnterChatController(testEnterChatController);
            chatListView.setSettingsController(testSettingsController);
            chatListView.setLoginController(testLoginController);
        });
    }

    @Test
    void testInitialization() {
        assertEquals("chat list", chatListView.getViewName());
    }

    @Test
    void testAddFriendButton() {
        SwingUtilities.invokeLater(() -> {
            chatListView.getAddFriendButton().doClick();
            assertTrue(testAddFriendController.wasExecuted);
        });
    }

    @Test
    void testRefreshChatList() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            try {
                // Add dummy data to the test view model
                testViewModel.addChat(new ChatEntry("Alice", "Bob", "Hello, Bob!", "12:00 PM"));

                // Access and invoke the private method using reflection
                java.lang.reflect.Method refreshMethod = ChatListView.class.getDeclaredMethod("refreshChatList", String.class);
                refreshMethod.setAccessible(true);
                refreshMethod.invoke(chatListView, "");

                // Verify chat list rendering
                JScrollPane scrollPane = (JScrollPane) chatListView.getComponent(1);
                JPanel chatListPanel = (JPanel) scrollPane.getViewport().getView();

                assertNotNull(chatListPanel, "Chat list panel should not be null.");
                assertEquals(1, chatListPanel.getComponentCount(), "Chat list should render one item.");
            } catch (Exception e) {
                fail("Exception occurred while accessing refreshChatList: " + e.getMessage());
            }
        });
    }


    @Test
    void testChatSelection() {
        SwingUtilities.invokeLater(() -> {
            testViewModel.addChat(new ChatEntry("Alice", "Bob", "Hello, Bob!", "12:00 PM"));
            chatListView.getRefreshChatList();

            JPanel chatListPanel = (JPanel) ((JScrollPane) chatListView.getComponent(1)).getViewport().getView();
            JPanel chatItem = (JPanel) chatListPanel.getComponent(0);

            MouseEvent dummyEvent = new MouseEvent(chatItem, MouseEvent.MOUSE_CLICKED,
                    System.currentTimeMillis(), 0, 0, 0, 1, false);
            chatItem.getMouseListeners()[0].mouseClicked(dummyEvent);

            assertTrue(testEnterChatController.wasExecuted);
        });
    }

    static class TestChatListViewModel extends ChatListViewModel {
        private final ArrayList<ChatEntry> chatList = new ArrayList<>();

        public void addChat(ChatEntry chatEntry) {
            chatList.add(chatEntry);
            setState(new ChatListState());
        }
    }

    static class TestAddFriendController extends AddFriendController {
        private static final AddFriendInputBoundary chatListUseCaseInteractor = null;
        boolean wasExecuted = false;

        public TestAddFriendController() {
            super(chatListUseCaseInteractor);
        }

//        @Override
//        public void execute(Object activeUser, String friendName, String initialMessage) {
//            wasExecuted = true;
//        }
    }

    static class TestEnterChatController extends EnterChatController {
        private static final EnterChatInputBoundary enterChatUseCaseInteractor = null;
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
        private static final Object settingsUseCaseInteractor = null;

        public TestSettingsController() {
            super((SettingsInputBoundary) settingsUseCaseInteractor);
        }
    }

    static class TestLoginController extends LoginController {
        public TestLoginController() {
            super(null, null);
        }
    }
}
