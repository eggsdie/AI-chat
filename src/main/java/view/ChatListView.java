package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import entity.ChatEntry;
import use_case.ChatList.ChatListManager;

public class ChatListView extends JPanel {
    private final JPanel chatListPanel;
    private final ChatListManager chatListManager;
    private final JTextField chatSearchField;
    private final String searchPlaceholder = "Search chats...";

    public ChatListView(ChatListManager chatListManager) {
        this.chatListManager = chatListManager;

        this.setLayout(new BorderLayout());

        // Top panel with chat search and add friend button
        JPanel topPanel = new JPanel(new BorderLayout());
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

        JButton addFriendButton = new JButton("Add Friend");
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

        // Action for return button
        returnButton.addActionListener(e -> {
            resetSearch();
        });

        bottomPanel.add(returnButton, BorderLayout.WEST);
        bottomPanel.add(settingsButton, BorderLayout.EAST);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // Action for adding a friend
        addFriendButton.addActionListener(e -> {
            String friendName = JOptionPane.showInputDialog(this, "Enter Friend's Name:");
            if (friendName != null && !friendName.trim().isEmpty()) {
                boolean added = chatListManager.addChat(friendName, "Hello! This is a new conversation.");
                if (added) {
                    refreshChatList(""); // Refresh the full list and place the new chat at the top
                } else {
                    JOptionPane.showMessageDialog(this, "Chat already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // Initial rendering
        refreshChatList(""); // Empty query for the full list
    }

    // Refresh chat list display
    // Refresh chat list display
    private void refreshChatList(String query) {
        chatListPanel.removeAll();

        // Get chats in reverse order to show the newest at the top
        List<ChatEntry> chats = chatListManager.getAllChats();

        // Filter and add chats
        for (int i = chats.size() - 1; i >= 0; i--) { // Iterate in reverse order
            ChatEntry chat = chats.get(i);
            if (query.isEmpty() || chat.getName().toLowerCase().contains(query.toLowerCase())) {
                JPanel chatItemPanel = createChatItemPanel(chat);
                chatListPanel.add(chatItemPanel);
            }
        }
        chatListPanel.revalidate();
        chatListPanel.repaint();
    }

    // Reset search bar and refresh the full chat list
    private void resetSearch() {
        chatSearchField.setText(searchPlaceholder);
        chatSearchField.setForeground(Color.GRAY);
        refreshChatList(""); // Refresh the full list
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

        return chatItemPanel;
    }
}
