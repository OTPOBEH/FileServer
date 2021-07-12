package models;

public class FileResult {

    private String fileContent;

    public FileResult() {fileContent = "";}

    public FileResult(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}
