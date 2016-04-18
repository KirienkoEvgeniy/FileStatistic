package file;

/**
 * Created by Evgeniy
 * Объект хранящий статистику по файлу
 */
public class FileStatistic {
    private String fileName;
    private Integer maxfile;
    private Integer minfile;
    private Float averfile;
    private Integer lenghtFile;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getMaxfile() {
        return maxfile;
    }

    public void setMaxfile(Integer maxfile) {
        this.maxfile = maxfile;
    }

    public Integer getMinfile() {
        return minfile;
    }

    public void setMinfile(Integer minfile) {
        this.minfile = minfile;
    }

    public Float getAverfile() {
        return averfile;
    }

    public void setAverfile(Float averfile) {
        this.averfile = averfile;
    }

    public Integer getLenghtFile() {
        return lenghtFile;
    }

    public void setLenghtFile(Integer lenghtFile) {
        this.lenghtFile = lenghtFile;
    }
}
