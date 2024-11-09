package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.add_friend.AddFriendController;
import interface_adapter.add_friend.AddFriendState;
import interface_adapter.add_friend.AddFriendViewModel;

public class AddFriendView extends JPanel {

    private final String viewName = "add friend";
    private AddFriendViewModel addFriendViewModel;
    private AddFriendController addFriendController;
    private JTextField userSearchField;
    private JButton createNewChatButton;
    private JButton backButton;

    public AddFriendView(AddFriendViewModel addFriendViewModel) {
        this.addFriendViewModel = addFriendViewModel;

        createNewChatButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(createNewChatButton)) {
                            final AddFriendState currentState = addFriendViewModel.getState();

                            addFriendController.execute(
                                    currentState.getUsername()
                            );
                        }
                    }
                }
        );

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

        // backButton.addActionListener() to be implemented when chat list view ready

    }

    public void propertyChange(PropertyChangeEvent evt) {
        final AddFriendState state = (AddFriendState) evt.getNewValue();
        if (state.getFriendAlreadyAddedError() != null) {
            JOptionPane.showMessageDialog(this, state.getFriendAlreadyAddedError());
        }
        if (state.getUserDoesNotExistError() != null) {
            JOptionPane.showMessageDialog(this, state.getFriendAlreadyAddedError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setAddFriendController(AddFriendController addFriendController) {
        this.addFriendController = addFriendController;
    }

}
