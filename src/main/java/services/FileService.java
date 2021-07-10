package services;

import models.FIleResult;
import models.FileInfoResult;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.InputStream;

/**
 * Created by spetrov on 07/08/2021.
 */
public interface FileService {

    FileInfoResult saveToDefaultFolder(InputStream inputStream, FormDataContentDisposition filename);

    FileInfoResult save(InputStream inputStream, String destinationPath, FormDataContentDisposition detail);

    FIleResult get(String filePath, String filename);

    FIleResult getFromDefaultFolder(String filename);

    void delete(String filePath, String filename);

    void deleteFromDefaultFolder(String filename);

}
