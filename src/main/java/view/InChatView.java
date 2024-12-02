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

    private final JPanel bottomPanel = new JPanel(new BorderLayout());
    private final JTextField textEntryField = new JTextField();
    private final JButton sendButton = new JButton("Send");

    private EnterChatController enterChatController;
    private SendMessageController sendMessageController;

    public InChatView(InChatViewModel inChatViewModel) {

        this.inChatViewModel = inChatViewModel;
        this.inChatViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        chatArea = new JPanel();
        chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));

        chatAreaScrollPane = new JScrollPane(chatArea);
        chatAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        verticalScroll = chatAreaScrollPane.getVerticalScrollBar();

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(otherUser, BorderLayout.EAST);

        bottomPanel.add(textEntryField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        final InChatState state = inChatViewModel.getState();
        backButton.addActionListener(evt -> {
            enterChatController.switchToChatListView(state.getSender());
            timer.stop();
        });

        textEntryField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {

            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });

        sendButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(sendButton)) {
                        final InChatState currentState = inChatViewModel.getState();
                        sendMessageController.execute(currentState.getSender(), currentState.getReceiver(),
                                textEntryField.getText());
                        enterChatController.execute(currentState.getSender(), currentState.getReceiver());
                        textEntryField.setText("");

                        verticalScroll.revalidate();
                        verticalScroll.setValue(verticalScroll.getMaximum());

                        inChatViewModel.setState(currentState);
                        refreshMessages(currentState.getMessages());
                    }
                }
        );

        final ActionListener refresh = evt -> {
            final InChatState currentState = inChatViewModel.getState();
            if (currentState.getMessages() != null) {
                enterChatController.execute(currentState.getSender(), currentState.getReceiver());
                refreshMessages(currentState.getMessages());
            }
        };
        timer = new Timer(500, refresh);
        timer.start();

        this.add(topPanel, BorderLayout.NORTH);
        this.add(chatAreaScrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        verticalScroll.setValue(verticalScroll.getMaximum());

    }

    public void refreshMessages(ArrayList<Message> messages) {
        chatArea.removeAll();

        for (Message message : messages) {
            final JPanel messagePanel = createMessagePanel(message);
            chatArea.add(messagePanel);
        }

    }

    public JPanel createMessagePanel(Message message) {
        final JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
        messagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        messagePanel.setPreferredSize(new Dimension(0, 60));
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

        final JPanel westPanel = new JPanel(new BorderLayout());
        final ImageIcon icon = new ImageIcon(message.getSenderPic());
        final Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        final ImageIcon scaledIcon = new ImageIcon(scaledImage);
        westPanel.add(new JLabel(scaledIcon), BorderLayout.WEST);

        messagePanel.add(westPanel, BorderLayout.WEST);
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
