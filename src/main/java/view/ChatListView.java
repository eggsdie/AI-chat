package view;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;

import entity.ChatEntry;
import interface_adapter.add_friend.AddFriendController;
import interface_adapter.add_friend.ChatListState;
import interface_adapter.add_friend.ChatListViewModel;
import interface_adapter.enter_chat.EnterChatController;
import interface_adapter.login.LoginController;
import interface_adapter.settings.SettingsController;

public class ChatListView extends JPanel implements PropertyChangeListener {
    private final String viewName = "chat list";

    private JFrame frame;
    private JPanel chatListPanel;
    private JTextField chatSearchField;
    private String searchPlaceholder = "Search chats...";
    private ChatListViewModel chatListViewModel;
    private AddFriendController addFriendController;
    private EnterChatController enterChatController;
    private SettingsController settingsController;
    private LoginController loginController;

    private final JButton addFriendButton;

    public ChatListView(ChatListViewModel chatListViewModel) {
        this.chatListViewModel = chatListViewModel;
        this.chatListViewModel.addPropertyChangeListener(this);

        frame = new JFrame("Chat Messenger");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        this.setLayout(new BorderLayout());

        // Top panel with chat search and add friend button
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        chatSearchField = new JTextField(searchPlaceholder);
        chatSearchField.setForeground(Color.GRAY);

        // Add focus behavior for placeholder
        chatSearchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (chatSearchField.getText().equals(searchPlaceholder)) {
                    chatSearchField.setText("");
                    chatSearchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (chatSearchField.getText().isEmpty()) {
                    chatSearchField.setText(searchPlaceholder);
                    chatSearchField.setForeground(Color.GRAY);
                }
            }
        });

        // Add key listener for real-time search
        chatSearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String query = chatSearchField.getText().trim();
                if (!query.equals(searchPlaceholder)) {
                    refreshChatList(query);
                }
            }
        });

        addFriendButton = new JButton("Add Friend");
        addFriendButton.setPreferredSize(new Dimension(120, 30));

        topPanel.add(chatSearchField, BorderLayout.CENTER);
        topPanel.add(addFriendButton, BorderLayout.EAST);

        this.add(topPanel, BorderLayout.NORTH);

        // Middle panel for chat list
        chatListPanel = new JPanel();
        chatListPanel.setLayout(new BoxLayout(chatListPanel, BoxLayout.Y_AXIS));
        JScrollPane chatListScrollPane = new JScrollPane(chatListPanel);
        chatListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.add(chatListScrollPane, BorderLayout.CENTER);

        // Bottom panel with return and settings buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton returnButton = new JButton("Refresh");
        JButton settingsButton = new JButton("Settings");

        bottomPanel.add(returnButton, BorderLayout.WEST);
        bottomPanel.add(settingsButton, BorderLayout.EAST);

        this.add(bottomPanel, BorderLayout.SOUTH);

        // Action for return button
        returnButton.addActionListener(e -> {
            resetSearch();
        });

        // Action for adding a friend
        addFriendButton.addActionListener(e -> {
            ChatListState currentState = chatListViewModel.getState();
            final String friendName = JOptionPane.showInputDialog(this, "Enter Friend's Name:");

            if (friendName != null && !friendName.trim().isEmpty()) {
                addFriendController.execute(currentState.getActiveUser(), friendName,
                        "Hello! This is a new conversation.");
                refreshChatList("");
            }
            chatListViewModel.setState(currentState);
        });

        settingsButton.addActionListener(evt -> {
            if (evt.getSource().equals(settingsButton)) {
                final ChatListState currentState = chatListViewModel.getState();
                this.settingsController.execute(currentState.getActiveUser());
            }
        });

        // Initial rendering
        refreshChatList(""); // Empty query for the full list
    }

    // Refresh chat list display
    private void refreshChatList(String query) {
        chatListPanel.removeAll();
        final ChatListState currentState = chatListViewModel.getState();

        // Get chats in reverse order to show the newest at the top
        final ArrayList<ChatEntry> chats = currentState.getChatList();

        // Filter and add chats
        if (chats != null) {
            for (int i = chats.size() - 1; i >= 0; i--) { // Iterate in reverse order
                ChatEntry chat = chats.get(i);
                if (query.isEmpty() || chat.getUser2().toLowerCase().contains(query.toLowerCase())) {
                    JPanel chatItemPanel = createChatItemPanel(chat);
                    chatListPanel.add(chatItemPanel);
                }
            }
        }
        chatListPanel.revalidate();
        chatListPanel.repaint();
    }

    // Reset search bar and refresh the full chat list
    private void resetSearch() {
        chatSearchField.setText(searchPlaceholder);
        chatSearchField.setForeground(Color.GRAY);

        final ChatListState currentState = chatListViewModel.getState();
        loginController.execute(currentState.getCurrentUsername(), currentState.getActiveUser().getPassword());
        refreshChatList("");
    }

    // Creates a styled panel for each chat entry
    private JPanel createChatItemPanel(ChatEntry chatEntry) {
        final ChatListState state = chatListViewModel.getState();

        JPanel chatItemPanel = new JPanel(new BorderLayout());
        chatItemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        chatItemPanel.setBackground(Color.WHITE);
        chatItemPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Set fixed height and dynamic width for each chat item
        chatItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); // Dynamic width, fixed height
        chatItemPanel.setPreferredSize(new Dimension(0, 60)); // 0 width allows resizing
        chatItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel nameLabel;
        if (chatEntry.getUser1().equals(state.getCurrentUsername())) {
            nameLabel = new JLabel(chatEntry.getUser2());
        }
        else {
            nameLabel = new JLabel(chatEntry.getUser1());
        }
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel timeLabel = new JLabel(chatEntry.getTime());
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timeLabel.setForeground(Color.GRAY);

        JLabel messageLabel = new JLabel("<html><i>" + chatEntry.getLastMessagePreview() + "</i></html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setForeground(Color.DARK_GRAY);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(nameLabel, BorderLayout.NORTH);
        textPanel.add(messageLabel, BorderLayout.CENTER);

        chatItemPanel.add(textPanel, BorderLayout.CENTER);
        chatItemPanel.add(timeLabel, BorderLayout.EAST);

        // Add click event for chat block
        chatItemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (chatEntry.getUser1().equals(state.getCurrentUsername())) {
                    enterChatController.execute(chatEntry.getUser1(), chatEntry.getUser2());
                }
                else {
                    enterChatController.execute(chatEntry.getUser2(), chatEntry.getUser1());
                }
            }
        });

        return chatItemPanel;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ChatListState state = (ChatListState) evt.getNewValue();
        if (state.getAddFriendError() != null) {
            JOptionPane.showMessageDialog(this, state.getAddFriendError());
            state.setAddFriendError(null);
        }
        refreshChatList("");
        chatListViewModel.setState(state);
    }

    public void setAddFriendController(AddFriendController addFriendController) {
        this.addFriendController = addFriendController;
    }

    public void setEnterChatController(EnterChatController enterChatController) {
        this.enterChatController = enterChatController;
    }

    public void setSettingsController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    public AbstractButton getAddFriendButton() {
        return addFriendButton;
    }

    public void getRefreshChatList() {

    }

    /**
     * Retrieves the button used to refresh the chat list.
     *
     * @return the `AbstractButton` used for refreshing the chat list.
     */
//    public AbstractButton getRefreshChatList() {
//        return returnButton;
//    }

}