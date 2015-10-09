package br.com.embers.exceptionhandler.factory;

import br.com.embers.exceptionhandler.exception.BusinessException;
import br.com.embers.exceptionhandler.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseEntityFactory {
    @Autowired
    MessageFactory messageFactory;

    public ResponseEntity<Message> create(BusinessException ex) {
        Message message = messageFactory.create(ex);

        HttpStatus httpStatus = HttpStatus.valueOf(ex.getStatusCode());

        return new ResponseEntity<Message>(message, httpStatus);
    }

    public ResponseEntity<Message> createGeneric(Exception ex) {
        Message message = messageFactory.create(ex);

        return new ResponseEntity<Message>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
