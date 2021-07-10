package errors.mappers;

import errors.ApiException;
import errors.ErrorHandlingUtil;
import io.dropwizard.jersey.errors.LoggingExceptionMapper;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Created by spetrov on 07/09/2021.
 */


@Provider
public class ApiExceptionMapper extends LoggingExceptionMapper<ApiException> {

  @Override
  public Response toResponse(ApiException ex) {
    return ErrorHandlingUtil.getResponse(ex);
  }

}
