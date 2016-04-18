package row;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

public interface IRowStatisticCalculate {
    /**
     * Подсчёт статистики по строкам определённого файла
     * @param file текстовый файл
     * @param charset
     */
    public List<RowStatistic> calculateStatFileRows(File file, Charset charset) throws Exception;
}
