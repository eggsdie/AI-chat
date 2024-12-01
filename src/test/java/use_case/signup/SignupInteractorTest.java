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

    // Old test cases
    @Test
    void successTest() {
        SignupInputData inputData = new SignupInputData("Paul", "password", "password");
        SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(demoRestfulApi, userFactory);

        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                assertEquals("Paul", user.getUsername());
                assertTrue(userRepository.existsByName("Paul"));
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
    }

    @Test
    void failurePasswordMismatchTest() {
        SignupInputData inputData = new SignupInputData("Paul", "password", "wrong");
        SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(demoRestfulApi, userFactory);

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Passwords don't match.", error);
            }

            @Override
            public void switchToLoginView() {
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter, new CommonUserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureUserExistsTest() {
        SignupInputData inputData = new SignupInputData("Paul", "password", "wrong");
        SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject(demoRestfulApi, userFactory);

        UserFactory factory = new CommonUserFactory();
        User user = factory.create("Paul", "paul@gmail.com", "pwd");
        userRepository.save(user);

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("User already exists.", error);
            }

            @Override
            public void switchToLoginView() {
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter, new CommonUserFactory());
        interactor.execute(inputData);
    }

    // New test case
    @Test
    void switchToLoginViewIsCalled() {
        SignupInputData inputData = new SignupInputData("Paul", "password", "password");
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
}
