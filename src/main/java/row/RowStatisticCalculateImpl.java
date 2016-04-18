package row;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RowStatisticCalculateImpl implements IRowStatisticCalculate {

    public List<RowStatistic> calculateStatFileRows(File file, Charset charset) throws Exception {
        List<RowStatistic> listResult = new LinkedList<RowStatistic>();
        List<String> lines = new LinkedList<String>();
        try {
            lines.addAll(Files.readAllLines(Paths.get(file.getCanonicalPath()), charset));
        } catch (IOException e) {
            System.out.println("Ошибка при получении строк файла");
            throw new Exception("Ошибка при получении строк файла", e);
        }

        for (String line : lines) {
            RowStatistic rowStatistic = new RowStatistic();
            int count = 0;
            float sum = 0;
            float average = 0;
            String[] res = line.split(" ");
            String max = res[0];
            String min = res[0];
            for (String word : res) {
                double wordLength = word.length();
                sum += wordLength;
                count++;
                if (max.length() < word.length())
                    max = word;
                if (min.length() > word.length())
                    min = word;
            }
            if (count > 0) {
                average = sum / count;
            }

            rowStatistic.setMax(max);
            rowStatistic.setMin(min);
            rowStatistic.setAverage(average);
            rowStatistic.setLineLenght(line.length());
            listResult.add(rowStatistic);
        }

        return listResult;
    }
}
