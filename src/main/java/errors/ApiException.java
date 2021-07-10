package errors;

import errors.codes.ErrorCode;

public class ApiException extends RuntimeException {

  private static final long serialVersionUID = 1L; // Do not remove. This is required since base class implements Serializable.
  private final ErrorCode errorCode;

  public ApiException(ErrorCode errorCode) {
    this(errorCode, (Throwable) null);
  }

  public ApiException(ErrorCode errorCode, Object... arguments) {
    this(errorCode, null, arguments);
  }

  public ApiException(ErrorCode errorCode, Throwable t) {
    super(ErrorHandlingUtil.getMessage(errorCode), t);
    this.errorCode = errorCode;
  }

  public ApiException(ErrorCode errorCode, Throwable t, Object... arguments) {
    super(ErrorHandlingUtil.getMessage(errorCode, arguments), t);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

}
