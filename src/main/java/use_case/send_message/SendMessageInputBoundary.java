package use_case.send_message;

import use_case.change_password.ChangePasswordInputData;

public interface SendMessageInputBoundary {
    void execute(SendMessageInputData inputData);

}

