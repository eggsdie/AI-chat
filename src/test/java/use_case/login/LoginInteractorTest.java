package use_case.login;

import data_access.DemoRestfulApi;
import data_access.InMemoryUserDataAccessObject;
import entity.ChatEntry;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    private final DemoRestfulApi demoRestfulApi = new DemoRestfulApi(); // Replace with mock if needed
    private final UserFactory userFactory = new CommonUserFactory();

    @Test
    void successTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password");
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(demoRestfulApi, userFactory);

        User user = userFactory.create("Paul", "paul@gmail.com", "password");
        userRepository.save(user);

        LoginInputBoundary interactor = getLoginInputBoundary(userRepository);
        interactor.execute(inputData);
    }

    @Test
    void failureUserDoesNotExistTest() {
        LoginInputData inputData = new LoginInputData("NonExistentUser", "password");
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(demoRestfulApi, userFactory);

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Unexpected success for a non-existent user.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("NonExistentUser: Account does not exist.", error, "Error message should indicate user does not exist.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void testEmptyUsername() {
        LoginInputData inputData = new LoginInputData("", "password");
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(demoRestfulApi, userFactory);

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Unexpected success for empty username.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals(": Account does not exist.", error, "Error message should indicate username is missing.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void successUserLoggedInTest() {
        LoginInputData inputData = new LoginInputData("Paul", "password");
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(demoRestfulApi, userFactory);

        User user = userFactory.create("Paul", "paul@gmail.com", "password");
        userRepository.save(user);

        LoginInputBoundary interactor = getInputBoundary(userRepository);
        assertNull(userRepository.getCurrentUsername(), "Username should be null before login.");

        interactor.execute(inputData);
        assertEquals("Paul", userRepository.getCurrentUsername(), "Username should be set after login.");
    }

    @Test
    void failurePasswordMismatchTest() {
        LoginInputData inputData = new LoginInputData("Paul", "wrong");
        LoginUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(demoRestfulApi, userFactory);

        User user = userFactory.create("Paul", "paul@gmail.com", "password");
        userRepository.save(user);

        LoginOutputBoundary failurePresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Unexpected success when password mismatch occurs.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for \"Paul\".", error, "Error message should indicate password mismatch.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void testLoginOutputData() {
        // Arrange
        String username = "TestUser";
        ArrayList<ChatEntry> chatEntries = new ArrayList<>();
        User user = userFactory.create("TestUser", "test@gmail.com", "password"); // Use factory to create user
        // Act
        LoginOutputData outputData = new LoginOutputData(username, chatEntries, user, false);

        // Assert
        assertEquals("TestUser", outputData.getUsername());
        assertEquals(user, outputData.getUser());
        assertEquals(chatEntries, outputData.getChatEntries());
    }

    @Test
    void testLoginOutputDataNullFields() {
        // Arrange
        String username = null;
        ArrayList<ChatEntry> chatEntries = null;
        User user = null;

        // Act
        LoginOutputData outputData = new LoginOutputData(username, chatEntries, user, true);

        // Assert
        assertNull(outputData.getUsername());
        assertNull(outputData.getUser());
        assertNull(outputData.getChatEntries());
    }

    @NotNull
    private static LoginInputBoundary getLoginInputBoundary(LoginUserDataAccessInterface userRepository) {
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", user.getUsername(), "Username should match after successful login.");
            }

            @Override
            public void prepareFailView(String error) {
                fail("Unexpected failure: " + error);
            }
        };

        return new LoginInteractor(userRepository, successPresenter);
    }

    @NotNull
    private static LoginInputBoundary getInputBoundary(LoginUserDataAccessInterface userRepository) {
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertEquals("Paul", userRepository.getCurrentUsername(), "Logged-in username should be set.");
            }

            @Override
            public void prepareFailView(String error) {
                fail("Unexpected failure: " + error);
            }
        };

        return new LoginInteractor(userRepository, successPresenter);
    }
}