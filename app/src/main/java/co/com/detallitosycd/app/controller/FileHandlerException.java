package co.com.detallitosycd.app.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class FileHandlerException {

    @ExceptionHandler(MultipartException.class)
    public String handleMultipartException(){
        return "redirect:/product/?fileError";
    }
}
