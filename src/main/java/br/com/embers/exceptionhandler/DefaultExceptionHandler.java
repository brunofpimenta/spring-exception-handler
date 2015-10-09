package br.com.embers.exceptionhandler;

import br.com.embers.exceptionhandler.exception.BusinessException;
import br.com.embers.exceptionhandler.factory.ResponseEntityFactory;
import br.com.embers.exceptionhandler.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Enumeration;

@ControllerAdvice
public class DefaultExceptionHandler {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    private SecureRandom random = new SecureRandom();

    @Autowired
    private ResponseEntityFactory responseEntityFactory;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Message> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        logInfo(request, ex, "Returning specific error with HTTP status code [{}].", ex.getStatusCode());
        return responseEntityFactory.create(ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> handleGenericException(Exception ex, HttpServletRequest request) {
        logInfo(request, ex, "Returning generic error.");
        return responseEntityFactory.createGeneric(ex);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleRequestParameterErrorException(MissingServletRequestParameterException ex, HttpServletRequest request)
            throws MissingServletRequestParameterException {

        logInfo(request, ex, "Parameter [{}] of type [{}] not found.", ex.getParameterName(), ex.getParameterType());

        throw ex;
    }

    private void logInfo(HttpServletRequest request, Exception ex, String errorMessage, Object... parameters) {
        String errorId = getErrorIdentifier();
        String completeURL = request.getRequestURL().toString();
        if (request.getQueryString() != null && !"".equalsIgnoreCase(request.getQueryString().trim())) {
            completeURL += "?" + request.getQueryString();
        }
        Enumeration<String> headerNames = request.getHeaderNames();
        Enumeration<String> parameterNames = request.getParameterNames();

        LOG.error("START of error id: [{}]", errorId);
        LOG.info("Exception type: [{}]", ex.getClass());
        LOG.info("Request URL: [{}]", completeURL);
        LOG.info("Error Description: " + errorMessage, parameters);
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            LOG.info("HEADER [{}] with value [{}]", headerName, headerValue);
        }
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameterValue = request.getHeader(parameterName);
            LOG.info("PARAMETER [{}] with value [{}]", parameterName, parameterValue);
        }
        LOG.error("END of error id: [{}]", errorId);

    }

    public String getErrorIdentifier() {
        return new BigInteger(130, random).toString(32);
    }
}
