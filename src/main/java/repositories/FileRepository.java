package repositories;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by spetrov on 07/08/2021.
 */

public interface FileRepository {

    void save(InputStream inputStream, String location) throws IOException;

    File get(String filePath);

    void delete(String filePath) throws IOException;

}
