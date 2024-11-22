package view;

import entity.ChatEntry;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.enter_chat.EnterChatController;
import interface_adapter.enter_chat.InChatState;
import interface_adapter.enter_chat.InChatViewModel;
import interface_adapter.login.LoginController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class InChatView extends JPanel implements PropertyChangeListener {

    private String viewName = "in chat";
    private JPanel bottomPanel = new JPanel(new BorderLayout());
    private JTextField textEntryField = new JTextField();
    private JTextArea chatArea = new JTextArea();
    private EnterChatController enterChatController;
    private ChatEntry chatEntry;

    public InChatView(InChatViewModel inChatViewModel) {

        this.setLayout(new BorderLayout());
        chatArea.setEditable(false);
        // chatArea.setText("Chat with " + chatEntry.getOtherUser() + "\n\n" + chatEntry.getLastMessagePreview());
        bottomPanel.add(textEntryField, BorderLayout.CENTER);
        this.add(chatArea, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public String getViewName() {
        return this.viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final InChatState state = (InChatState) evt.getNewValue();
        chatEntry = state.getChatEntry();
    }

    public void setEnterChatController(EnterChatController enterChatController) {
        this.enterChatController = enterChatController;
    }
}
