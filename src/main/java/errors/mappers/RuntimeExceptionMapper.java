package errors.mappers;

import errors.ApiException;
import errors.ErrorHandlingUtil;
import errors.codes.InternalErrorCode;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by spetrov on 07/09/2021.
 */

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RuntimeExceptionMapper.class);


  @Override
  public Response toResponse(RuntimeException runtimeException) {

    // Specific handling check
    if (runtimeException instanceof WebApplicationException) {
      /* If the API does not have method @Path matching the URI of the incoming request
       Jersey will automatically return a 404 'NOT FOUND' to the client.
       As per requirement a 501 'NOT IMPLEMENTED' should be returned */
      WebApplicationException webAppException = (WebApplicationException) runtimeException;
      if (webAppException.getResponse().getStatusInfo().getStatusCode() == 404) {
        ApiException apiException = new ApiException(InternalErrorCode.ILLEGAL_OPERATION);

        return ErrorHandlingUtil.getResponse(apiException);
      }
    }

    // Build default response
    Response defaultResponse = Response
        .serverError()
        .entity(runtimeException.getMessage())
        .build();

    LOGGER.error(runtimeException.getMessage(), runtimeException);
    return defaultResponse;
  }

}
