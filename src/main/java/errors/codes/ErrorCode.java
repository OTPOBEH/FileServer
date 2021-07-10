package errors.codes;

import javax.ws.rs.core.Response.Status;

/**
 * Created by spetrov on 07/09/2021.
 */


public interface ErrorCode {

  String getCode();

  Status getStatus();

}
