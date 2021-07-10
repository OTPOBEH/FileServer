package resources;

import java.io.InputStream;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import contracts.Constants;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * Created by spetrov on 07/09/2021.
 */

public interface FileResource {

    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response upload(
            @FormDataParam(Constants.FORM_DATA_KEY) InputStream uploadedInputStream,
            @FormDataParam(Constants.FORM_DATA_KEY) FormDataContentDisposition fileDetail);

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    Response download(@QueryParam(Constants.FILE_QUERY_PARAM) String file);

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    Response delete(@QueryParam(Constants.FILE_QUERY_PARAM) String file);
}
