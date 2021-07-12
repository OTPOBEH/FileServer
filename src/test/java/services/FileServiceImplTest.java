package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import errors.ApiException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.HashSet;
import java.util.Set;
import models.FileResult;
import models.FileInfoResult;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import repositories.FileRepository;
import repositories.FileRepositoryImpl;

/**
 * Created by spetrov on 07/12/2021.
 */

public class FileServiceImplTest {

  private FileRepository fileRepository;
  private Set<String> supportedFileExtensions;
  private InputStream inputStream;
  private FormDataContentDisposition fileDetail;
  private File fileMock;
  private String testFileName;
  private String testFolderPath;
  private String defaultFileFolderPath;
  private String fileContent;
  private MockedStatic<FileUtils> fileUtilsMock;

  FileServiceImpl fileService;

  @BeforeEach
  public void setUp() throws IOException {
    testFileName = "Test.txt";
    testFolderPath = "F:\\randomFolder\\bar";
    defaultFileFolderPath = "F:\\foo\\bar";
    fileContent = "FooBar";

    supportedFileExtensions = mock(HashSet.class);
    fileMock = mock(File.class);
    fileRepository = mock(FileRepositoryImpl.class);
    inputStream = mock(InputStream.class);
    fileDetail = mock(FormDataContentDisposition.class);

    when(fileDetail.getFileName()).thenReturn(testFileName);
    when(supportedFileExtensions.contains(anyString())).thenReturn(true);
    when(fileRepository.get(anyString())).thenReturn(fileMock);
    when(fileMock.exists()).thenReturn(true);
    doNothing().when(fileRepository).delete(anyString());
    doNothing().when(fileRepository).save(any(InputStream.class), anyString());

    fileUtilsMock =  Mockito.mockStatic(FileUtils.class);
    fileUtilsMock.when(() -> FileUtils.readFileToString(fileMock)).thenReturn("FooBar");

    fileService = new FileServiceImpl(fileRepository, defaultFileFolderPath, supportedFileExtensions);
  }

  @AfterEach
  public void close(){
    fileUtilsMock.close();
  }

  @Test
  public void saveTestDefaultFolder(){

    FileInfoResult result = fileService.save(inputStream, defaultFileFolderPath, fileDetail);

    assertEquals(result.getFilePath(), defaultFileFolderPath + "\\" + testFileName);

  }

  @Test
  public void saveTest(){

    FileInfoResult result = fileService.save(inputStream, testFolderPath, fileDetail);

    assertEquals(result.getFilePath(), testFolderPath + "\\" + testFileName);

  }

  @Test
  public void getTest(){

    FileResult result = fileService.get(testFolderPath, testFileName);

    assertEquals(result.getFileContent(), fileContent);

  }

  @Test
  public void getShouldThrowNotFoundExceptionTest(){
    when(fileMock.exists()).thenReturn(false);

    Assertions.assertThrows(ApiException.class, () -> {
      fileService.get(testFolderPath, testFileName);
    });
  }


  @Test
  public void deleteTest() throws IOException {

    fileService.delete(testFolderPath, testFileName);

    Mockito.verify(fileRepository, times(1)).delete(anyString());

  }

  @Test
  public void deleteShouldThrowNotFoundExceptionTest() throws IOException {

    Mockito.doThrow(NoSuchFileException.class).when(fileRepository).delete(anyString());

    Assertions.assertThrows(ApiException.class, () -> {
      fileService.delete(testFolderPath, testFileName);
    });

  }

}
