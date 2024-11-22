package view;

import entity.ChatEntry;
import interface_adapter.enter_chat.EnterChatController;
import interface_adapter.enter_chat.InChatState;
import interface_adapter.enter_chat.InChatViewModel;
import interface_adapter.login.LoginState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalTime;

public class InChatView extends JPanel implements PropertyChangeListener {

    private String viewName = "in chat";
    private final InChatViewModel inChatViewModel;
    private JPanel bottomPanel = new JPanel(new BorderLayout());
    private JTextField textEntryField = new JTextField();
    private JTextArea chatArea = new JTextArea();
    private EnterChatController enterChatController;
    private JLabel otherUser = new JLabel();
    private JButton enterButton = new JButton("Send");

    public InChatView(InChatViewModel inChatViewModel) {
        this.inChatViewModel = inChatViewModel;
        this.inChatViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        //chatArea.setEditable(false);
        //chatArea.setPreferredSize(new Dimension(300, 150));

        this.add(otherUser, BorderLayout.NORTH);
        bottomPanel.add(textEntryField, BorderLayout.CENTER);
        bottomPanel.add(enterButton, BorderLayout.EAST);
        this.add(chatArea, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

    }

    public String getViewName() {
        return this.viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final InChatState state = (InChatState) evt.getNewValue();
        otherUser.setText(state.getChatEntry().getOtherUser());
    }

    public void setEnterChatController(EnterChatController enterChatController) {
        this.enterChatController = enterChatController;
    }
}
