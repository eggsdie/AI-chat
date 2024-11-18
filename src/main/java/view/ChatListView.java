package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import data_access.InMemoryFriendRepository;
import entity.ChatEntry;
import interface_adapter.chat_list.ChatListController;
import interface_adapter.chat_list.ChatListState;
import interface_adapter.chat_list.ChatListViewModel;
import use_case.chat_list.ChatListManager;
import use_case.chat_list.ChatListOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ChatListView extends JPanel implements PropertyChangeListener {
    private final String viewName = "chat list";

    private JFrame frame;
    private JPanel chatListPanel;
    private ChatListManager chatListManager;
    private JTextField chatSearchField;
    private String searchPlaceholder = "Search chats...";
    private ChatListViewModel chatListViewModel;
    private ChatListController chatListController;
    private InMemoryFriendRepository friendRepository;
    private ChatListOutputBoundary chatListOutputBoundary;

    private final JButton addFriendButton;

    public ChatListView(InMemoryFriendRepository friendRepository, ChatListOutputBoundary chatListOutputBoundary,
                       ChatListViewModel chatListViewModel) {
        this.chatListViewModel = chatListViewModel;
        this.chatListViewModel.addPropertyChangeListener(this);
        this.friendRepository = friendRepository;
        this.chatListOutputBoundary = chatListOutputBoundary;

        frame = new JFrame("Chat Messenger");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        this.setLayout(new BorderLayout());

        // Initialize Use Case
        chatListManager = new ChatListManager(friendRepository, chatListOutputBoundary);

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
        JButton returnButton = new JButton("Return to Chat List");
        JButton settingsButton = new JButton("Settings");

        bottomPanel.add(returnButton, BorderLayout.WEST);
        bottomPanel.add(settingsButton, BorderLayout.EAST);

        this.add(bottomPanel, BorderLayout.SOUTH);

        // Action for adding a friend
        addFriendButton.addActionListener(e -> {
            final ChatListState currentState = chatListViewModel.getState();
            String friendName = JOptionPane.showInputDialog(this, "Enter Friend's Name:");

            currentState.setUsername(friendName);
            chatListViewModel.setState(currentState);

            if (currentState.getUsername() != null && !currentState.getUsername().trim().isEmpty()) {
                chatListController.addChat(currentState.getUsername(), "Hello! This is a new conversation.");
            }

        });

        // Initial rendering
        refreshChatList(""); // Empty query for the full list
    }

    // Refresh chat list display
    private void refreshChatList(String query) {
        chatListPanel.removeAll();
        List<ChatEntry> chats = chatListManager.getAllChats();

        // Filter chats by query
        for (ChatEntry chat : chats) {
            if (query.isEmpty() || chat.getName().toLowerCase().contains(query.toLowerCase())) {
                JPanel chatItemPanel = createChatItemPanel(chat);
                chatListPanel.add(chatItemPanel);
            }
        }
        chatListPanel.revalidate();
        chatListPanel.repaint();
    }

    // Creates a styled panel for each chat entry
    private JPanel createChatItemPanel(ChatEntry chatEntry) {
        JPanel chatItemPanel = new JPanel(new BorderLayout());
        chatItemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        chatItemPanel.setBackground(Color.WHITE);
        chatItemPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Set fixed height and dynamic width for each chat item
        chatItemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); // Dynamic width, fixed height
        chatItemPanel.setPreferredSize(new Dimension(0, 60)); // 0 width allows resizing
        chatItemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel(chatEntry.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel timeLabel = new JLabel(chatEntry.getLastMessageTime());
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
                openChatWindow(chatEntry);
            }
        });

        return chatItemPanel;
    }

    // Opens a new chat window for the selected friend
    private void openChatWindow(ChatEntry chatEntry) {
        JFrame chatFrame = new JFrame("Chat with " + chatEntry.getName());
        chatFrame.setSize(400, 400);
        chatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setText("Chat with " + chatEntry.getName() + "\n\n" + chatEntry.getLastMessagePreview());
        chatFrame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        chatFrame.setVisible(true);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ChatListState state = (ChatListState) evt.getNewValue();

        if (state.getAddFriendError() != null) {
            JOptionPane.showMessageDialog(this, state.getAddFriendError());
        }

    }

    public void setChatListController(ChatListController chatListController) {
        this.chatListController = chatListController;
    }

}
