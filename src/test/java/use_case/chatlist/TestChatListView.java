import javax.swing.*;
import use_case.ChatList.ChatListManager;
import view.ChatListView;

public class TestChatListView {
    public static void main(String[] args) {
        // Create a ChatListManager with some dummy data
        ChatListManager chatListManager = new ChatListManager();
        chatListManager.addChat("Alice", "Hi Alice!");
        chatListManager.addChat("Bob", "Hello Bob!");
        chatListManager.addChat("Charlie", "How are you?");

        // Create a frame to host the ChatListView for testing
        JFrame testFrame = new JFrame("Test ChatListView");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(400, 600);

        // Create the ChatListView and add it to the frame
        ChatListView chatListView = new ChatListView(chatListManager);
        testFrame.add(chatListView);

        // Make the frame visible
        testFrame.setVisible(true);
    }
}
