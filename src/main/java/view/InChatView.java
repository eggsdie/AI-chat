package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import entity.Message;
import interface_adapter.enter_chat.EnterChatController;
import interface_adapter.enter_chat.InChatState;
import interface_adapter.enter_chat.InChatViewModel;
import interface_adapter.send_message.SendMessageController;

public class InChatView extends JPanel implements PropertyChangeListener {

    private final String viewName = "in chat";
    private final InChatViewModel inChatViewModel;

    private Timer timer;
    private final JPanel chatArea;
    private final JScrollPane chatAreaScrollPane;
    private final JScrollBar verticalScrollBar;
    private final JButton backButton = new JButton("Back");

    private final JPanel topPanel = new JPanel(new BorderLayout());
    private final JLabel otherUser = new JLabel();

    private final JPanel bottomPanel = new JPanel();
    private final JTextField textEntryField = new JTextField();
    private final JButton sendButton = new JButton("Send");
    private final JButton generateResponseButton = new JButton("Generate Response");

    private EnterChatController enterChatController;
    private SendMessageController sendMessageController;

    private int lastMessageCount = 0;
    private boolean userIsNearBottom = true;

    public InChatView(InChatViewModel inChatViewModel) {

        this.inChatViewModel = inChatViewModel;
        this.inChatViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        // Chat area setup
        chatArea = new JPanel();
        chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));

        chatAreaScrollPane = new JScrollPane(chatArea);
        chatAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        verticalScrollBar = chatAreaScrollPane.getVerticalScrollBar();

        // Top panel setup
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(otherUser, BorderLayout.EAST);

        // Bottom panel setup
        JPanel textAndSendPanel = new JPanel(new BorderLayout());
        textAndSendPanel.add(textEntryField, BorderLayout.CENTER);
        textAndSendPanel.add(sendButton, BorderLayout.EAST);

        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(textAndSendPanel);
        bottomPanel.add(Box.createVerticalStrut(10)); // Add spacing
        generateResponseButton.setPreferredSize(new Dimension(300, 50)); // Make the button large
        generateResponseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(generateResponseButton);

        // Back button action
        backButton.addActionListener(evt -> {
            final InChatState state = inChatViewModel.getState();
            enterChatController.switchToChatListView(state.getSender());
            timer.stop();
        });

        // Send button action
        sendButton.addActionListener(evt -> sendMessage());

        // Generate response button action
        generateResponseButton.addActionListener(evt -> generateResponse());

        // Timer for refreshing messages
        final ActionListener refresh = evt -> {
            final InChatState currentState = inChatViewModel.getState();
            if (currentState.getMessages() != null) {
                enterChatController.execute(currentState.getSender(), currentState.getReceiver());
            }
        };
        timer = new Timer(500, refresh);
        timer.start();

        // Add components to main panel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(chatAreaScrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // Initialize scroll bar adjustment
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());

        // Add adjustment listener to the scroll bar
        verticalScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int extent = verticalScrollBar.getModel().getExtent();
                int maximum = verticalScrollBar.getMaximum();
                int value = verticalScrollBar.getValue();

                int scrollBarPosition = value + extent;
                int scrollBarMax = maximum;

                // Threshold to consider if the user is near the bottom (e.g., 50 pixels)
                int threshold = 50;

                userIsNearBottom = (scrollBarMax - scrollBarPosition) <= threshold;
            }
        });
    }

    private void sendMessage() {
        if (!textEntryField.getText().isEmpty()) {
            final InChatState currentState = inChatViewModel.getState();
            sendMessageController.execute(
                    currentState.getSender(),
                    currentState.getReceiver(),
                    textEntryField.getText()
            );
            textEntryField.setText("");

            // Fetch updated messages
            enterChatController.execute(
                    currentState.getSender(),
                    currentState.getReceiver()
            );
        }
    }

    private void generateResponse() {
        final InChatState currentState = inChatViewModel.getState();
        String generatedResponse = "This is a generated response."; // Replace with actual response generation logic
        sendMessageController.execute(
                currentState.getSender(),
                currentState.getReceiver(),
                generatedResponse
        );

        // Fetch updated messages
        enterChatController.execute(
                currentState.getSender(),
                currentState.getReceiver()
        );
    }

    public void refreshMessages(ArrayList<Message> messages) {
        int totalMessages = messages.size();

        if (totalMessages > lastMessageCount) {
            List<Message> newMessages = messages.subList(lastMessageCount, totalMessages);

            for (Message message : newMessages) {
                final JPanel messagePanel = createMessagePanel(message);
                chatArea.add(messagePanel);
            }

            lastMessageCount = totalMessages;

            chatArea.revalidate();
            chatArea.repaint();

            scrollToBottomIfNeeded();
        }
    }

    private void scrollToBottomIfNeeded() {
        if (userIsNearBottom) {
            SwingUtilities.invokeLater(() -> {
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
            });
        }
    }

    public JPanel createMessagePanel(Message message) {
        final JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
        messagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        messagePanel.setPreferredSize(new Dimension(0, 60)); // 0 width allows resizing
        messagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JPanel northPanel = new JPanel(new BorderLayout());

        final JLabel sender = new JLabel(message.getSender());
        sender.setFont(sender.getFont().deriveFont(Font.BOLD));
        northPanel.add(sender, BorderLayout.WEST);
        final JTextArea content = new JTextArea(message.getContent());
        content.setLineWrap(true);
        content.setEditable(false);
        content.setWrapStyleWord(true);
        final JLabel time = new JLabel(message.getTime());

        messagePanel.add(northPanel, BorderLayout.NORTH);
        messagePanel.add(content, BorderLayout.CENTER);
        messagePanel.add(time, BorderLayout.EAST);
        return messagePanel;
    }

    public String getViewName() {
        return this.viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final InChatState state = (InChatState) evt.getNewValue();
        otherUser.setText("Chat with " + state.getReceiver() + " ");

        // Reset message count and clear chat area if the chat partner has changed
        if (stateChangedToNewChat(evt)) {
            lastMessageCount = 0;
            chatArea.removeAll();
        }

        refreshMessages(state.getMessages());
        timer.start();
    }

    private boolean stateChangedToNewChat(PropertyChangeEvent evt) {
        InChatState oldState = (InChatState) evt.getOldValue();
        InChatState newState = (InChatState) evt.getNewValue();
        if (oldState == null) {
            return true;
        }
        return !oldState.getReceiver().equals(newState.getReceiver());
    }

    public void setEnterChatController(EnterChatController enterChatController) {
        this.enterChatController = enterChatController;
    }

    public void setSendMessageController(SendMessageController sendMessageController) {
        this.sendMessageController = sendMessageController;
    }
}