import errors.mappers.ApiExceptionMapper;
import errors.mappers.RuntimeExceptionMapper;
import healthchecks.FileServerHealthCheck;
import repositories.FileRepository;
import resources.FileResource;
import services.FileService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import repositories.FileRepositoryImpl;
import services.FileServiceImpl;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by spetrov on 07/09/2021.
 */


public class FileServerApplication extends Application<FileServerConfiguration> {
    public static void main(String[] args) throws Exception {

        new FileServerApplication().run(args);
    }

    public void run(final FileServerConfiguration configuration, final Environment environment) {


        final FileRepository fileRepository = new FileRepositoryImpl();

        String defaultFolderPath = configuration.getDefaultFolderPath().replace("\\", File.separator);

        Set<String> supportedFileExtensions =
                new HashSet<>(Arrays.asList(configuration.getSupportedFileExtensions().split(",")));

        final FileService fileService =
                new FileServiceImpl(fileRepository, defaultFolderPath, supportedFileExtensions);
        final FileResource fileResource = new FileResource(fileService);

        environment
                .jersey()
                .register(
                        new AbstractBinder() {
                            @Override
                            protected void configure() {
                                bind(fileService).to(FileService.class);
                                bind(fileResource).to(FileResource.class);
                            }
                        });

        environment.jersey().register(fileService);
        environment.jersey().register(fileResource);
        environment.jersey().register(MultiPartFeature.class);
        environment.jersey().register(new ApiExceptionMapper());
        environment.jersey().register(new RuntimeExceptionMapper());
        environment.healthChecks().register("APIHealthCheck", new FileServerHealthCheck(defaultFolderPath));
    }
}
