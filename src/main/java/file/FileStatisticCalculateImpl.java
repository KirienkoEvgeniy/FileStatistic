package file;

import row.RowStatistic;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileStatisticCalculateImpl implements IFileStatisticCalculate {
    /**
     * Получение списка текстовых файлов
     * @param path путь к директории
     */
    @Override
    public List<File> getTxtFiles(List<File> result, String path) {
        File root = new File(path);
        File[] list = root.listFiles();
        if (list == null)
            return result;
        for (File file : list) {
            if (file.isDirectory()) {
                getTxtFiles(result, file.getAbsolutePath());
            } else {
                String m = file.getName();
                if (m.endsWith(".txt")) {
                    result.add(file);
                }
            }
        }
        return result;
     }

    /**
     * Подсчёт статистики по файлу
     * на основании статистики по строкам файла
     * @param list Список объектов статистики по строкам
     * @return
     */
    @Override
    public FileStatistic calculateStatFile(List<RowStatistic> list) {
        FileStatistic result = new FileStatistic();
        int lenghtFile = 0;
        int countLines = list.size();
        float averf = 0;
        float averfile = 0;
        List<Integer> maxf = new ArrayList<Integer>();
        List<Integer> minf = new ArrayList<Integer>();

        for (RowStatistic line : list) {
            lenghtFile += line.getLineLenght();
                averf = line.getAverage();
                maxf.add(line.getMax().length());
                minf.add(line.getMin().length());
        }
//        if (countLines > 0) {
//            averfile = averf / countLines;
//        }
        result.setMinfile(Collections.min(minf));
        result.setMaxfile(Collections.max(maxf));
        result.setAverfile(averf);
        result.setLenghtFile(lenghtFile);

        return result;
    }
}
