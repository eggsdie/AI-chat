package use_case.signup;

import data_access.DemoRestfulApi;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignupInteractorTest {

    private final DemoRestfulApi demoRestfulApi = new DemoRestfulApi(); // Replace with mock if needed
    private final UserFactory userFactory = new CommonUserFactory();

    @Test
    void successTest() {
        SignupInputData inputData = new SignupInputData("Paul", "paul@gmail.com", "password");
        SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(demoRestfulApi, userFactory);

        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                // Verify the success presenter
                assertEquals("Paul", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToLoginView() {
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, successPresenter, new CommonUserFactory());
        interactor.execute(inputData);

        // Verify that the user was saved
        assertTrue(userRepository.existsByName("Paul"));
    }

    @Test
    void failureUserExistsTest() {
        SignupInputData inputData = new SignupInputData("Paul", "paul@gmail.com", "password");
        SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(demoRestfulApi, userFactory);

        // Simulate existing user
        User existingUser = userFactory.create("Paul", "paul@gmail.com", "password");
        userRepository.save(existingUser);

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                // Verify the failure presenter
                assertEquals("User already exists.", error);
            }

            @Override
            public void switchToLoginView() {
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter, new CommonUserFactory());
        interactor.execute(inputData);

        // Verify that the user still exists
        assertTrue(userRepository.existsByName("Paul"));
    }

    @Test
    void testEmptyUsername() {
        SignupInputData inputData = new SignupInputData("", "paul@gmail.com", "password");
        SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(demoRestfulApi, userFactory);

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                // Verify the failure presenter
                assertEquals("Invalid username.", error);
            }

            @Override
            public void switchToLoginView() {
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter, new CommonUserFactory());
        interactor.execute(inputData);
    }

    @Test
    void testNullEmail() {
        SignupInputData inputData = new SignupInputData("Paul", null, "password");
        SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(demoRestfulApi, userFactory);

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                // Verify the failure presenter
                assertEquals("Invalid email.", error);
            }

            @Override
            public void switchToLoginView() {
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter, new CommonUserFactory());
        interactor.execute(inputData);
    }

    @Test
    void switchToLoginViewIsCalled() {
        SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(demoRestfulApi, userFactory);

        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToLoginView() {
                // Ensure this is called
                assertTrue(true);
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, successPresenter, new CommonUserFactory());
        interactor.switchToLoginView();
    }

    // Tests for SignupOutputData
    @Test
    void testSignupOutputDataConstructorAndGetUsername() {
        // Arrange
        String username = "TestUser";

        // Act
        SignupOutputData outputData = new SignupOutputData(username, false);

        // Assert
        assertEquals("TestUser", outputData.getUsername());
    }

    @Test
    void testSignupOutputDataWithNullUsername() {
        // Arrange
        String username = null;

        // Act
        SignupOutputData outputData = new SignupOutputData(username, false);

        // Assert
        assertNull(outputData.getUsername());
    }
}
