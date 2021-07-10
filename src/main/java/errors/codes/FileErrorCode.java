package errors.codes;

import javax.ws.rs.core.Response.Status;

/**
 * Created by spetrov on 07/09/2021.
 */

public enum FileErrorCode implements ErrorCode {

  FILE_NOT_EXISTS("FILE-401", Status.NOT_FOUND),
  PROBLEM_PATH("FILE-402", Status.INTERNAL_SERVER_ERROR),
  UNSUPPORTED_EXTENSION("FILE-403", Status.BAD_REQUEST),
  WRONG_DATA_FORM_KEY("FILE-404", Status.BAD_REQUEST);

  private final String code;
  private final Status status;

  FileErrorCode(String code) {
    this.code = code;
    status = Status.INTERNAL_SERVER_ERROR;
  }

  FileErrorCode(String code, Status status) {
    this.code = code;
    this.status = status;
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public Status getStatus() {
    return status;
  }
}