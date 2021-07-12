package services;

import contracts.Constants;
import errors.ApiException;
import errors.codes.FileErrorCode;
import errors.codes.InternalErrorCode;

import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;

import models.FileResult;
import models.FileInfoResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import repositories.FileRepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resources.FileResource;

public class FileServiceImpl implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileResource.class);
    private final String defaultFileFolderPath;
    private final FileRepository fileRepository;
    private final Set<String> supportedFileExtensions;

    @Inject
    public FileServiceImpl(
            FileRepository fileRepository,
            String defaultFileFolderPath,
            Set<String> supportedFileExtensions) {
        this.fileRepository = fileRepository;
        this.defaultFileFolderPath = defaultFileFolderPath;
        this.supportedFileExtensions = supportedFileExtensions;
    }

    //region Public Methods

    public FileInfoResult saveToDefaultFolder(InputStream inputStream, FormDataContentDisposition fileDetail) {
        String destinationPath = formatPath(defaultFileFolderPath);
        return save(inputStream, destinationPath, fileDetail);
    }

    public FileInfoResult save(InputStream inputStream, String destinationPath, FormDataContentDisposition fileDetail) {

        validateFormDataKey(fileDetail);

        String filename = fileDetail.getFileName();

        validateFileExtension(filename);

        destinationPath = formatPath(destinationPath);

        String location = getAbsolutePath(destinationPath + filename);

        LOGGER.info("START: Uploading file {} to location: {}", filename, location);

        try {
            fileRepository.save(inputStream, location);
        } catch (FileNotFoundException e) {
            throw new ApiException(FileErrorCode.FILE_NOT_EXISTS, filename, location);
        } catch (IOException e) {
            throw new ApiException(InternalErrorCode.GENERAL_ERROR, e);
        }

        LOGGER.info("END: Uploading file {} to location: {}", filename, location);

        return new FileInfoResult(location);
    }

    public FileResult get(String filePath, String filename) {

        validateFileExtension(filename);

        filePath = formatPath(filePath);

        String location = getAbsolutePath(filePath + filename);

        LOGGER.info("START: Downloading file {} from location: {}", filename, location);
        File fileDownload = fileRepository.get(location);

        LOGGER.info("END: Downloading file {} from location: {}", filename, location);

        if (!fileDownload.exists()) {
            throw new ApiException(FileErrorCode.FILE_NOT_EXISTS, filename, location);
        }

        return createFileResult(fileDownload);
    }

    public FileResult getFromDefaultFolder(String filename) {
        return get(defaultFileFolderPath, filename);
    }

    public void delete(String filePath, String filename) {

        validateFileExtension(filename);

        filePath = formatPath(filePath);

        String location = getAbsolutePath(filePath + filename);

        try {
            fileRepository.delete(location);
        } catch (NoSuchFileException ex) {
            throw new ApiException(FileErrorCode.FILE_NOT_EXISTS, filename, location);
        } catch (Exception ex) {
            throw new ApiException(InternalErrorCode.GENERAL_ERROR, ex);
        }
    }

    public void deleteFromDefaultFolder(String filename) {
        delete(defaultFileFolderPath, filename);
    }

    //endregion

    //region Private Methods

    private String getAbsolutePath(String relativePath) {
        File folder = new File(relativePath);

        try {
            return folder.getCanonicalPath();
        } catch (IOException ex) {
            throw new ApiException(FileErrorCode.PROBLEM_PATH, ex);
        }
    }

    private String formatPath(String filePath) {
        if (!filePath.endsWith(File.separator)) {
            filePath += File.separator;
        }
        return filePath;
    }

    private void validateFileExtension(String filename) {

        String fileExtension = FilenameUtils.getExtension(filename);

        if (!supportedFileExtensions.contains(fileExtension.toUpperCase())) {
            throw new ApiException(FileErrorCode.UNSUPPORTED_EXTENSION, fileExtension);
        }
    }

    private void validateFormDataKey(FormDataContentDisposition fileDetail) {
        if (fileDetail == null) {
            throw new ApiException(FileErrorCode.WRONG_DATA_FORM_KEY, Constants.FORM_DATA_KEY);
        }
    }

    private FileResult createFileResult(File file) {
        String fileContent;
        try {
            fileContent = FileUtils.readFileToString(file);
        } catch (IOException e) {
            throw new ApiException(InternalErrorCode.GENERAL_ERROR);
        }
        return new FileResult(fileContent);
    }

    //endregion
}
