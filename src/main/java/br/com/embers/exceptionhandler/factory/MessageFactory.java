package br.com.embers.exceptionhandler.factory;

import br.com.embers.exceptionhandler.exception.BusinessException;
import br.com.embers.exceptionhandler.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory {

    public Message create(String title, String pMessage, String messageKey, String errorCode) {
        Message message = new Message();
        message.setErrorCode(errorCode);
        message.setTitle(title);
        message.setMessage(pMessage);
        message.setMessageKey(messageKey);

        return message;
    }

    public Message create(BusinessException ex) {
        Message message = new Message();
        message.setMessage(ex.getMessage());
        message.setTitle(ex.getTitle());
        message.setErrorCode(ex.getErrorCode());
        message.setMessageKey(ex.getMessageKey());

        return message;
    }

    public Message create(Exception ex) {
        Message message = new Message();
        message.setMessage("Not expected error: " + ex.getMessage());
        message.setTitle("Error");

        return message;
    }

}
