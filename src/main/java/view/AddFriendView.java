package view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.add_friend.AddFriendController;
import interface_adapter.add_friend.AddFriendState;
import interface_adapter.add_friend.AddFriendViewModel;
import interface_adapter.login.LoginController;

public class AddFriendView extends JPanel implements PropertyChangeListener {

    private final String viewName = "add friend";
    private final AddFriendViewModel addFriendViewModel;
    private AddFriendController addFriendController;
    private LoginController loginController;

    private final JTextField userSearchField = new JTextField(15);
    private final JButton createNewChatButton;
    private final JButton backButton;

    public AddFriendView(AddFriendViewModel addFriendViewModel) {
        this.addFriendViewModel = addFriendViewModel;
        this.addFriendViewModel.addPropertyChangeListener(this);

        final LabelTextPanel userSearch = new LabelTextPanel(
                new JLabel("Search for user: "), userSearchField);

        createNewChatButton = new JButton("Create new chat");

        createNewChatButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(createNewChatButton)) {
                        final AddFriendState currentState = addFriendViewModel.getState();

                        addFriendController.execute(
                                currentState.getUsername()
                        );
                    }
                }
        );

        backButton = new JButton("Back");

        backButton.addActionListener(evt -> addFriendController.switchToLoggedInView());

        userSearchField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final AddFriendState currentState = addFriendViewModel.getState();
                currentState.setUsername(userSearchField.getText());
                addFriendViewModel.setState(currentState);
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

        final JPanel buttons = new JPanel();
        buttons.add(backButton);
        buttons.add(createNewChatButton);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(45));
        this.add(userSearch);
        this.add(buttons);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        final AddFriendState state = (AddFriendState) evt.getNewValue();

        if (state.getAddFriendError() != null) {
            JOptionPane.showMessageDialog(this, state.getAddFriendError());
        }

    }

    public String getViewName() {
        return viewName;
    }

    public void setAddFriendController(AddFriendController addFriendController) {
        this.addFriendController = addFriendController;
    }
}
