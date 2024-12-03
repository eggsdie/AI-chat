package use_case.enter_chat;

import data_access.InMemoryUserDataAccessObject;
import data_access.DemoRestfulApi;
import entity.ChatEntry;
import entity.CommonUserFactory;
import entity.UserFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnterChatInteractorTest {

    DemoRestfulApi database = new DemoRestfulApi();
    UserFactory factory = new CommonUserFactory();

    @Test
    void successTest() {
        EnterChatInputData inputData = new EnterChatInputData("User1", "User2");
        EnterChatUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(database, factory);

        EnterChatOutputBoundary successPresenter = new EnterChatOutputBoundary() {
            @Override
            public void prepareSuccessView(EnterChatOutputData outputData) {
                assertEquals("User1", outputData.getSender());
                assertEquals("User2", outputData.getReceiver());
                assertEquals(userRepository.messagesByChat("User1", "User2").size(),
                        outputData.getMessages().size());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToChatListView(ArrayList<ChatEntry> chatList) {
                assertEquals(userRepository.allChatsWithUser("User1").size(), chatList.size());
            }
        };

        EnterChatInteractor interactor = new EnterChatInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
        interactor.switchToChatListView(inputData.getSender());

    }
}

