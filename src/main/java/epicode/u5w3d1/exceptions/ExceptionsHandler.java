package epicode.u5w3d1.exceptions;

import cosminpetrea.u5w2d5.payloads.errors.ErrorsPayloadWithList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsPayloadWithList handleBadRequest(BadRequestException e) {
        List<String> errorsMessages = new ArrayList<>();
        if (e.getErrorList() != null)
            errorsMessages = e.getErrorList().stream().map(error -> error.getDefaultMessage()).toList();
        return new ErrorsPayloadWithList(e.getMessage(), new Date(), errorsMessages);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsPayload handleNotFound(NotFoundException e){
        return new ErrorsPayload(e.getMessage(), LocalDate.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsPayload handleGenericProblem(Exception e){
        e.printStackTrace();
        return new ErrorsPayload("Something's off, we'll check as soon as possible", LocalDate.now());
    }

}
