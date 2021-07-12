package repositories;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by spetrov on 07/09/2021.
 */

public class FileRepositoryImpl implements FileRepository {

    @Override
    public void save(InputStream inputStream, String location) throws IOException {

        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(location))) {
            int partSize = 1 * 1024 * 1024;

            byte[] data = new byte[partSize];

            int length;
            while ((length = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, length);
            }

            outputStream.flush();
        }
    }

    public File get(String filePath) {
        File fileDownload = new File(filePath);

        return fileDownload;
    }

    public void delete(String filePath) throws IOException {
        Path fileToDeletePath = Paths.get(filePath);
        Files.delete(fileToDeletePath);
    }

}
