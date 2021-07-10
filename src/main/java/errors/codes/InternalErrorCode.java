package errors.codes;

import javax.ws.rs.core.Response.Status;

/**
 * Created by spetrov on 07/09/2021.
 */

public enum InternalErrorCode implements ErrorCode {

  GENERAL_ERROR("INTERNAL-500", Status.INTERNAL_SERVER_ERROR),
  ILLEGAL_OPERATION("INTERNAL-501", Status.BAD_REQUEST);

  private final String code;
  private final Status status;

  InternalErrorCode(String code) {
    this.code = code;
    status = Status.INTERNAL_SERVER_ERROR;
  }

  InternalErrorCode(String code, Status status) {
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