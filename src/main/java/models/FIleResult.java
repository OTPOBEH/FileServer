package models;

public class FIleResult {

    private String fileContent;

    public FIleResult() {fileContent = "";}

    public FIleResult(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}
