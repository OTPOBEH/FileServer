import io.dropwizard.Configuration;

import javax.validation.constraints.NotEmpty;

/**
 * Created by spetrov on 07/09/2021.
 */

public class FileServerConfiguration extends Configuration {

    @NotEmpty
    private String defaultFolderPath;

    @NotEmpty
    private String supportedFileExtensions;


    String getDefaultFolderPath() {
        return defaultFolderPath;
    }

    public void setDefaultFolderPath(String defaultFolderPath) {
        this.defaultFolderPath = defaultFolderPath;
    }

    String getSupportedFileExtensions() {
        return supportedFileExtensions;
    }

    void setSupportedFileExtensions(String supportedFileExtensions) {
        this.supportedFileExtensions = supportedFileExtensions;
    }
}
