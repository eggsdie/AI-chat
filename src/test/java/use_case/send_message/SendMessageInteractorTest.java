package use_case.send_message;

import data_access.InMemoryUserDataAccessObject;
import data_access.DemoRestfulApi;
import entity.ChatEntry;
import entity.CommonUserFactory;
import entity.UserFactory;
import org.junit.jupiter.api.Test;
import use_case.send_message.SendMessageInputData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SendMessageInteractorTest {

    DemoRestfulApi database = new DemoRestfulApi();
    UserFactory factory = new CommonUserFactory();

    @Test
    void successTest() {
        SendMessageInputData inputData = new SendMessageInputData("User1", "User2", "Hey User2!");
        SendMessageUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(database, factory);

        // Send message use case does not have a view or presenter, thus no success/fail view

        SendMessageInteracter interactor = new SendMessageInteracter(userRepository);
        interactor.execute(inputData);

    }
}

