package row;

/**
 * Created by Evgeniy
 * Объект хранящий статистику по строке
 */
public class RowStatistic {
    private String max;
    private String min;
    private Float average;
    private Integer lineLenght;

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }

    public Integer getLineLenght() {
        return lineLenght;
    }

    public void setLineLenght(Integer lineLenght) {
        this.lineLenght = lineLenght;
    }
}
