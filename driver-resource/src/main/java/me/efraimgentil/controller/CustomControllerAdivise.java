package me.efraimgentil.controller;

import me.efraimgentil.exception.InvalidaModelException;
import me.efraimgentil.exception.NotFoundException;
import me.efraimgentil.validator.ValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 10/07/16.
 */
@ControllerAdvice
public class CustomControllerAdivise {


  @Autowired
  MessageSource messageSource;

  @ExceptionHandler(InvalidaModelException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public List<ValidationResult> handleInvalidModel( InvalidaModelException ime ){
    List<ValidationResult> validationResults = new ArrayList<>();
    List<FieldError> fieldErrors = ime.getResult().getFieldErrors();
    for(FieldError fr : fieldErrors){
      validationResults.add(new ValidationResult( fr.getField() , getMessage(fr , null)));
    }
    return validationResults;
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public List<ValidationResult> handleInvalidModel( NotFoundException nne ){
    List<ValidationResult> validationResults = new ArrayList<>();
    validationResults.add( new ValidationResult( "global" , messageSource.getMessage("record.notfound", new Object[]{}, null)  ) );
    return validationResults;
  }

  protected String getMessage( FieldError fr, Locale locale ){
    String[] codes = fr.getCodes();
    for( String code : codes ){
      try {
        return messageSource.getMessage(code, fr.getArguments(), locale);
      }catch(Exception e ){
        //just ignore when there is not message for the code
      }
    }
    //if doesn't find any message throws a runtime
    throw new RuntimeException("Invalide message erro code");
  }


}
