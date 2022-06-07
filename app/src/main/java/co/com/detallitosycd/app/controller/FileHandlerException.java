package co.com.detallitosycd.app.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import java.nio.file.FileSystemException;

@ControllerAdvice
public class FileHandlerException {

    @ExceptionHandler(MultipartException.class)
    public String handleMultipartException(){
        return "redirect:/product/?fileError";
    }

    @ExceptionHandler(FileSystemException.class)
    public String handleFileSystemException(){
        return "redirect:/product/?extensionError";
    }
}
