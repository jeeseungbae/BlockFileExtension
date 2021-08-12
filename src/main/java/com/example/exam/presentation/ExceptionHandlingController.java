package com.example.exam.presentation;

import com.example.exam.exception.DuplicateNameException;
import com.example.exam.exception.FixedSameDataException;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.hibernate.procedure.NamedParametersNotSupportedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

@ControllerAdvice
@Controller
public class ExceptionHandlingController {

    @ExceptionHandler(DuplicateNameException.class)
    public String duplicateHandler(Exception e, Model model) {
        setModel(model, e);
        return "error";
    }

    @ExceptionHandler(FixedSameDataException.class)
    public String FixedSameHandler(Exception e, Model model) {
        setModel(model, e);
        return "error";
    }

    @ExceptionHandler(TooManyResultsException.class)
    public String TooManySizeHandler(Exception e, Model model) {
        setModel(model, e);
        return "error";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String violationHandler(Exception e, Model model) {
        setModel(model, e);
        return "error";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String namingLimitHandler(Exception e, Model model) {
        setModel(model, e);
        return "error";
    }

    @ExceptionHandler(NamedParametersNotSupportedException.class)
    public String namingNotSupport(Exception e, Model model) {
        setModel(model, e);
        return "error";
    }

    private void setModel(Model model, Exception e) {
        model.addAttribute("Type", Arrays.toString(e.getStackTrace()));
        model.addAttribute("Message", e.getMessage());
    }
}
