package errors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import errors.codes.ErrorCode;

/**
 * Created by spetrov on 07/09/2021.
 */

public class ErrorMessage {

  @JsonIgnore
  private int statusCode;

  private String code;
  private String message;
  private String logId;

  public ErrorMessage() {
  }

  public ErrorMessage(ErrorCode errorCode, String message) {
    statusCode = errorCode.getStatus().getStatusCode();
    code = errorCode.getCode();
    this.message = message;
  }

  //region getters and setters

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getLogId() {
    return logId;
  }

  public void setLogId(String logId) {
    this.logId = logId;
  }

  //endregion
}
