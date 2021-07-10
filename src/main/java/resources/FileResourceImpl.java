package resources;

import contracts.Constants;
import models.FIleResult;
import models.FileInfoResult;
import services.FileService;

import java.io.InputStream;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * Created by spetrov on 07/08/2021.
 */

@Path(Constants.FILE_RESOURCE_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class FileResourceImpl implements FileResource {

    private final FileService fileService;

    @Inject
    public FileResourceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    @PUT
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(
            @FormDataParam(Constants.FORM_DATA_KEY) InputStream uploadedInputStream,
            @FormDataParam(Constants.FORM_DATA_KEY) FormDataContentDisposition fileDetail) {

        FileInfoResult fileInfo = fileService.saveToDefaultFolder(uploadedInputStream, fileDetail);
        return Response.ok(fileInfo).build();
    }

    @Override
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
    public Response download(@QueryParam(Constants.FILE_QUERY_PARAM) String file) {

        FIleResult fileDownload = fileService.getFromDefaultFolder(file);

        Response.ResponseBuilder response = Response.ok(fileDownload);
        response.header("Content-Disposition", "filename=" + file);

        return response.build();
    }

    @Override
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam(Constants.FILE_QUERY_PARAM) String file) {

        fileService.deleteFromDefaultFolder(file);

        return Response.noContent().build();
    }

}
