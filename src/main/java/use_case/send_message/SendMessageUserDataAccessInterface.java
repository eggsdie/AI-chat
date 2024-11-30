package use_case.send_message;

import entity.Message;

import java.util.ArrayList;

public interface SendMessageUserDataAccessInterface {

    void addMessage(String sender, String receiver, String content);

}
