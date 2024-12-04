package use_case.logout;

import data_access.DemoRestfulApi;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogoutTest {

    private final DemoRestfulApi demoRestfulApi = new DemoRestfulApi(); // Replace with mock if needed
    private final UserFactory userFactory = new CommonUserFactory();

    @Test
    void successTest() {
        LogoutInputData inputData = new LogoutInputData("Paul");
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject(demoRestfulApi, userFactory);

        // For the success test, we need to add Paul to the data access repository before we log in.
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("Paul", "paul@gmail.com", "password");
        userRepository.save(user);
        userRepository.setCurrentUsername("Paul");

        // This creates a successPresenter that tests whether the test case is as we expect.
        LogoutOutputBoundary successPresenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData user) {
                // check that the output data contains the username of who logged out
                assertEquals("Paul", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected..");
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(userRepository, successPresenter);
        interactor.execute(inputData);
        // check that the user was logged out
        assertNull(userRepository.getCurrentUsername());
    }

}