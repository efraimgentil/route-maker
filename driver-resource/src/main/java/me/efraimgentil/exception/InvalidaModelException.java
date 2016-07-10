package me.efraimgentil.exception;

import org.springframework.validation.BindingResult;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 10/07/16.
 */
public class InvalidaModelException extends RuntimeException {

  private String message;
  private BindingResult result;


  public InvalidaModelException(String message, BindingResult result) {
    this.message = message;
    this.result = result;
  }

  public InvalidaModelException(BindingResult result) {
    this.result = result;
  }

  public BindingResult getResult() {
    return result;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
