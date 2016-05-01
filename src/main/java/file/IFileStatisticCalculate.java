package file;

import row.RowStatistic;

import java.io.File;
import java.util.List;

/**
 * Created by Evgeniy
 */
public interface IFileStatisticCalculate {

    /**
     * Получение списка текстовых файлов
     * @param result список текстовых файлов - результат работы метода
     * @param path путь к директории
     */
    public List<File> getTxtFiles(List<File> result, String path);

    /**
     * Подсчёт статистики по файлу
     * на основании статистики по строкам файла
     * @param list Список объектов статистики по строкам
     * @return
     */
    public FileStatistic calculateStatFile(List<RowStatistic> list);

}
