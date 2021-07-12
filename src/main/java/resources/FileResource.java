package resources;

import contracts.Constants;
import models.FileResult;
import models.FileInfoResult;
import services.FileService;

import java.io.InputStream;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * Created by spetrov on 07/08/2021.
 */

@Path(Constants.FILE_RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class FileResource {

    private final FileService fileService;

    @Inject
    public FileResource(FileService fileService) {
        this.fileService = fileService;
    }

    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(
            @FormDataParam(Constants.FORM_DATA_KEY) InputStream uploadedInputStream,
            @FormDataParam(Constants.FORM_DATA_KEY) FormDataContentDisposition fileDetail) {

        FileInfoResult fileInfo = fileService.saveToDefaultFolder(uploadedInputStream, fileDetail);
        return Response.ok(fileInfo).build();
    }

    @GET
    @Path(Constants.FILENAME_PATH_PARAM_PLACEHOLDER)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response download(@NotNull @PathParam(Constants.FILENAME_PATH_PARAM) String filename) {

        FileResult fileDownload = fileService.getFromDefaultFolder(filename);

        Response.ResponseBuilder response = Response.ok(fileDownload);
        response.header(HttpHeaders.CONTENT_DISPOSITION, "filename=" + filename);

        return response.build();
    }

    @DELETE
    @Path(Constants.FILENAME_PATH_PARAM_PLACEHOLDER)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@NotNull @PathParam(Constants.FILENAME_PATH_PARAM) String filename) {

        fileService.deleteFromDefaultFolder(filename);

        return Response.noContent().build();
    }

}
