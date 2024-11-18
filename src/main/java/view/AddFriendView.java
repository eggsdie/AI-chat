//package view;
//
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
//
//import javax.swing.*;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//
//import interface_adapter.add_friend.ChatListController;
//import interface_adapter.add_friend.ChatListState;
//import interface_adapter.add_friend.ChatListViewModel;
//import interface_adapter.login.LoginController;
//
//public class AddFriendView extends JPanel implements PropertyChangeListener {
//
//    private final String viewName = "add friend";
//    private final ChatListViewModel chatListViewModel;
//    private ChatListController chatListController;
//    private LoginController loginController;
//
//    private final JTextField userSearchField = new JTextField(15);
//    private final JButton createNewChatButton;
//    private final JButton backButton;
//
//    public AddFriendView(ChatListViewModel chatListViewModel) {
//        this.chatListViewModel = chatListViewModel;
//        this.chatListViewModel.addPropertyChangeListener(this);
//
//        final LabelTextPanel userSearch = new LabelTextPanel(
//                new JLabel("Search for user: "), userSearchField);
//
//        createNewChatButton = new JButton("Create new chat");
//
//        createNewChatButton.addActionListener(
//                evt -> {
//                    if (evt.getSource().equals(createNewChatButton)) {
//                        final ChatListState currentState = chatListViewModel.getState();
//
//                        chatListController.execute(
//                                currentState.getUsername()
//                        );
//                    }
//                }
//        );
//
//        backButton = new JButton("Back");
//
//        backButton.addActionListener(evt -> chatListController.switchToLoggedInView());
//
//        userSearchField.getDocument().addDocumentListener(new DocumentListener() {
//
//            private void documentListenerHelper() {
//                final ChatListState currentState = chatListViewModel.getState();
//                currentState.setUsername(userSearchField.getText());
//                chatListViewModel.setState(currentState);
//            }
//
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                documentListenerHelper();
//            }
//        });
//
//        final JPanel buttons = new JPanel();
//        buttons.add(backButton);
//        buttons.add(createNewChatButton);
//
//        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        this.add(Box.createVerticalStrut(45));
//        this.add(userSearch);
//        this.add(buttons);
//    }
//
//    public void propertyChange(PropertyChangeEvent evt) {
//        final ChatListState state = (ChatListState) evt.getNewValue();
//
//        if (state.getAddFriendError() != null) {
//            JOptionPane.showMessageDialog(this, state.getAddFriendError());
//        }
//
//    }
//
//    public String getViewName() {
//        return viewName;
//    }
//
//    public void setAddFriendController(ChatListController chatListController) {
//        this.chatListController = chatListController;
//    }
//}
