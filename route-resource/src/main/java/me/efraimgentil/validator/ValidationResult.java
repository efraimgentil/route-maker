package me.efraimgentil.validator;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 10/07/16.
 */
public class ValidationResult {

  private String fieldName;
  private String fieldLabel;
  private String message;

  public ValidationResult() {
  }

  public ValidationResult(String fieldName, String message) {
    this.fieldName = fieldName;
    this.message = message;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ValidationResult{");
    sb.append("fieldLabel='").append(fieldLabel).append('\'');
    sb.append(", fieldName='").append(fieldName).append('\'');
    sb.append(", message='").append(message).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public String getFieldLabel() {
    return fieldLabel;
  }

  public void setFieldLabel(String fieldLabel) {
    this.fieldLabel = fieldLabel;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
