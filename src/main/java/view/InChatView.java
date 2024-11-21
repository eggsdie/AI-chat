package view;

import entity.ChatEntry;

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

    public InChatView(ChatEntry chatEntry) {

        this.setLayout(new BorderLayout());
        chatArea.setEditable(false);
        chatArea.setText("Chat with " + chatEntry.getOtherUser() + "\n\n" + chatEntry.getLastMessagePreview());
        bottomPanel.add(textEntryField, BorderLayout.CENTER);
        this.add(chatArea, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    public String getViewName() {
        return this.viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
