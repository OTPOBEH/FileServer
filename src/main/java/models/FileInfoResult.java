package models;

public class FileInfoResult {

    private String filePath;

    public FileInfoResult() {
        filePath = "";
    }

    public FileInfoResult(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
