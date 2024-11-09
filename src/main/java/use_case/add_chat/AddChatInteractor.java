package use_case.add_chat;

import entity.Chat;
import entity.ChatFactory;

public class AddChatInteractor {

    private final AddChatUserDataAccessInterface addChatUserDataAccessInterface;
    private final AddChatOutputBoundary chatPresenter;
    private final ChatFactory chatFactory;

    public AddChatInteractor(AddChatUserDataAccessInterface addChatUserDataAccessInterface,
                            AddChatOutputBoundary addChatOutputBoundary, ChatFactory chatFactory) {
        this.addChatUserDataAccessInterface = addChatUserDataAccessInterface;
        this.chatPresenter = addChatOutputBoundary;
        this.chatFactory = chatFactory;
    }

    public void execute(AddChatInputData addChatInputData) {
        if (addChatUserDataAccessInterface.chatExists(addChatInputData.getUsers())) {
            chatPresenter.prepareFailView("Chat name already exists.");
        }
        else {
            final Chat chat = chatFactory.create(addChatInputData.getUsers(), addChatInputData.getChatName());
            addChatUserDataAccessInterface.save(chat);

            final AddChatOutputData addChatOutputData = new AddChatOutputData(false);
            chatPresenter.prepareSuccessView(addChatOutputData);
        }
    }
}
