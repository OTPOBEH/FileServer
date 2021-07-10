package errors;

import errors.codes.ErrorCode;
import errors.mappers.ApiExceptionMapper;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by spetrov on 07/09/2021.
 */

public class ErrorHandlingUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionMapper.class);
  private static final String BUNDLE_NAME = "ErrorMessages";

  public static String logMessage(ApiException exception) {
    long id = ThreadLocalRandom.current().nextLong();
    String logId = String.format("%016x", id);
    LOGGER.info("Log Id: {}", logId);

    String message = "Error handling a request: " + logId;
    LOGGER.error(message, exception);

    return logId;
  }

  public static ErrorMessage toErrorMessage(ApiException exception) {
    return new ErrorMessage(exception.getErrorCode(), exception.getMessage());
  }

  public static Response getResponse(ApiException apiException) {
    String logId = ErrorHandlingUtil.logMessage(apiException);
    ErrorMessage errorMessage = ErrorHandlingUtil.toErrorMessage(apiException);
    errorMessage.setLogId(logId);

    return Response.status(errorMessage.getStatusCode())
        .entity(errorMessage)
        .build();
  }

  public static String getMessage(ErrorCode errorCode) {
    ResourceBundle errorMessages = ResourceBundle.getBundle(BUNDLE_NAME);
    return errorMessages.getString(errorCode.getCode());
  }

  public static String getMessage(ErrorCode errorCode, Object... arguments) {
    ResourceBundle errorMessages = ResourceBundle.getBundle(BUNDLE_NAME);
    String format = errorMessages.getString(errorCode.getCode());
    return String.format(format, arguments);
  }

}
