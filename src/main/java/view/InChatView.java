package view;

import entity.ChatEntry;
import entity.Message;
import interface_adapter.enter_chat.EnterChatController;
import interface_adapter.enter_chat.InChatState;
import interface_adapter.enter_chat.InChatViewModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.time.LocalTime;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class InChatView extends JPanel implements PropertyChangeListener {

    private String viewName = "in chat";
    private final InChatViewModel inChatViewModel;

    private JPanel chatArea;
    private JScrollPane chatAreaScrollPane;
    private JButton backButton = new JButton("Back");

    private JPanel topPanel = new JPanel(new BorderLayout());
    private JLabel otherUser = new JLabel();

    private JPanel bottomPanel = new JPanel(new BorderLayout());
    private JTextField textEntryField = new JTextField();
    private JButton sendButton = new JButton("Send");

    private EnterChatController enterChatController;

    public InChatView(InChatViewModel inChatViewModel) {

        this.inChatViewModel = inChatViewModel;
        this.inChatViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        chatArea = new JPanel();
        chatArea.setLayout(new BoxLayout(chatArea, BoxLayout.Y_AXIS));
        chatAreaScrollPane = new JScrollPane(chatArea);
        chatAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(otherUser, BorderLayout.EAST);

        bottomPanel.add(textEntryField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

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
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(sendButton)) {
                            final InChatState currentState = inChatViewModel.getState();
                            currentState.addMessage(new Message(currentState.getChatEntry().getCurrentUser(),
                                    textEntryField.getText(), LocalTime.now()));
                            textEntryField.setText("");
                            refreshMessages(currentState.getChatEntry());
                            inChatViewModel.setState(currentState);
                        }
                    }
                }
        );

        chatAreaScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
            }
        });

        this.add(topPanel, BorderLayout.NORTH);
        this.add(chatAreaScrollPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

    }

    public void refreshMessages(ChatEntry chatEntry) {
        chatArea.removeAll();

        for (Message message : chatEntry.getMessages()) {
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

        final JLabel sender = new JLabel(message.getUser().getName());
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
        otherUser.setText("Chat with " + state.getChatEntry().getOtherUser() + " ");
        refreshMessages(state.getChatEntry());
    }

    public void setEnterChatController(EnterChatController enterChatController) {
        this.enterChatController = enterChatController;
    }

}
