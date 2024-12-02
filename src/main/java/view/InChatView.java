package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
    private final JScrollBar verticalScroll;
    private final JButton backButton = new JButton("Back");

    private final JPanel topPanel = new JPanel(new BorderLayout());
    private final JLabel otherUser = new JLabel();

    private final JPanel bottomPanel = new JPanel();
    private final JTextField firstTextEntryField = new JTextField();
    private final JButton firstSendButton = new JButton("Send");
    private final JTextField secondTextEntryField = new JTextField();
    private final JButton secondSendButton = new JButton("Enter Prompt");

    private EnterChatController enterChatController;
    private SendMessageController sendMessageController;

    public InChatView(InChatViewModel inChatViewModel) {

        this.inChatViewModel = inChatViewModel;
        this.inChatViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        // Chat area setup
        chatArea = new JPanel();
        chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));

        chatAreaScrollPane = new JScrollPane(chatArea);
        chatAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        verticalScroll = chatAreaScrollPane.getVerticalScrollBar();

        // Top panel setup
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(otherUser, BorderLayout.EAST);

        // Bottom panel setup
        bottomPanel.setLayout(new GridLayout(2, 1, 5, 5)); // Two rows for two message bars

        // First message bar
        JPanel firstMessageBar = new JPanel(new BorderLayout());
        firstMessageBar.add(firstTextEntryField, BorderLayout.CENTER);
        firstMessageBar.add(firstSendButton, BorderLayout.EAST);

        // Second message bar
        JPanel secondMessageBar = new JPanel(new BorderLayout());
        secondMessageBar.add(secondTextEntryField, BorderLayout.CENTER);
        secondMessageBar.add(secondSendButton, BorderLayout.EAST);

        // Add both message bars to the bottom panel
        bottomPanel.add(firstMessageBar);
        bottomPanel.add(secondMessageBar);

        final InChatState state = inChatViewModel.getState();
        backButton.addActionListener(evt -> {
            enterChatController.switchToChatListView(state.getSender());
            timer.stop();
        });

        // Action listener for the first send button
        firstSendButton.addActionListener(
                evt -> sendMessage(firstTextEntryField)
        );

        // Action listener for the second send button
        secondSendButton.addActionListener(
                evt -> sendMessage(secondTextEntryField)
        );

        // Timer for refreshing messages
        final ActionListener refresh = evt -> {
            final InChatState currentState = inChatViewModel.getState();
            if (currentState.getMessages() != null) {
                enterChatController.execute(currentState.getSender(), currentState.getReceiver());
                refreshMessages(currentState.getMessages());
            }
        };
        timer = new Timer(500, refresh);
        timer.start();

        // Add components to main panel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(chatAreaScrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        verticalScroll.setValue(verticalScroll.getMaximum());
    }

    private void sendMessage(JTextField textEntryField) {
        if (!textEntryField.getText().isEmpty()) {
            final InChatState currentState = inChatViewModel.getState();
            sendMessageController.execute(
                    currentState.getSender(),
                    currentState.getReceiver(),
                    textEntryField.getText()
            );
            enterChatController.execute(
                    currentState.getSender(),
                    currentState.getReceiver()
            );
            textEntryField.setText("");

            verticalScroll.revalidate();
            verticalScroll.setValue(verticalScroll.getMaximum());

            inChatViewModel.setState(currentState);
            refreshMessages(currentState.getMessages());
        }
    }

    public void refreshMessages(ArrayList<Message> messages) {
        chatArea.removeAll();

        for (Message message : messages) {
            final JPanel messagePanel = createMessagePanel(message);
            chatArea.add(messagePanel);
        }

        chatArea.revalidate();
        chatArea.repaint();
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

        refreshMessages(state.getMessages());
        timer.start();
    }

    public void setEnterChatController(EnterChatController enterChatController) {
        this.enterChatController = enterChatController;
    }

    public void setSendMessageController(SendMessageController sendMessageController) {
        this.sendMessageController = sendMessageController;
    }
}
