package services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import javax.ws.rs.core.Response;
import models.FileResult;
import models.FileInfoResult;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resources.FileResource;

/**
 * Created by spetrov on 07/12/2021.
 */

public class FileResourceImplTest {

  private String defaultFileFolderPath;
  private FileServiceImpl fileService;
  private InputStream inputStream;
  private FormDataContentDisposition fileDetail;
  private String testFileName;
  public String testFileContent;

  FileResource fileResource;

  @BeforeEach
  public void setUp() {
    defaultFileFolderPath = "F:/foo/bar";
    testFileName = "Test.txt";
    testFileContent = "Lorem ipsum";

    inputStream = mock(InputStream.class);
    fileDetail = mock(FormDataContentDisposition.class);
    fileService = mock(FileServiceImpl.class);
    fileResource = new FileResource(fileService);

    FileInfoResult fileInfo = new FileInfoResult(defaultFileFolderPath + testFileName);

    when(fileService
        .saveToDefaultFolder(any(InputStream.class), any(FormDataContentDisposition.class)))
        .thenReturn(fileInfo);

    FileResult fIleResult = new FileResult();
    fIleResult.setFileContent(testFileContent);

    when(fileService
        .getFromDefaultFolder(anyString()))
        .thenReturn(fIleResult);

    doNothing().when(fileService).delete(anyString(), anyString());

  }

  @Test
  public void saveTest() {

    Response result = fileResource.upload(inputStream, fileDetail);

    assertEquals(((FileInfoResult) result.getEntity()).getFilePath(),
        defaultFileFolderPath + testFileName);

    assertEquals(200, result.getStatus());

  }

  @Test
  public void getTest() {
    Response result = fileResource.download(testFileName);

    assertEquals(((FileResult) result.getEntity()).getFileContent(),
        testFileContent);

    assertEquals(200, result.getStatus());

  }

  @Test
  public void deleteTest() {
    Response result = fileResource.delete(testFileName);

    assertEquals(204, result.getStatus());

  }

}
