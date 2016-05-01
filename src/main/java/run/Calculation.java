package run;

import file.FileStatistic;
import file.FileStatisticCalculateImpl;
import row.RowStatistic;
import row.RowStatisticCalculateImpl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Calculation {
    private static Charset charset = Charset.forName("ISO-8859-1");
    private static AtomicLong ref = new AtomicLong(System.currentTimeMillis());
    private static String DEFAULT_REFERENCE = "0000000000000";

    public static void main(String[] args) {
        FileStatisticCalculateImpl fileStatisticCalculate = new FileStatisticCalculateImpl();
        RowStatisticCalculateImpl rowStatisticCalculate = new RowStatisticCalculateImpl();
        DatabaseWorker databaseWorker = new DatabaseWorker();

        List<File> files = new ArrayList<File>();
        fileStatisticCalculate.getTxtFiles(files, "D:\\Test");

        for (File file : files) {
            System.out.println(file);
            String newID = Long.toString(ref.getAndIncrement(), 36)
                    .toUpperCase();
            newID = DEFAULT_REFERENCE.replace(
                    DEFAULT_REFERENCE.substring(0, newID.length()),
                    newID);

            List<RowStatistic> rowStatistics = new LinkedList<>();
            FileStatistic fileStatistic = null;
            try {
                rowStatistics.addAll(rowStatisticCalculate.calculateStatFileRows(file, charset));
            } catch (Exception e) {
                System.out.println("Ошибка при подсчёте статистики по строкам");
            }
            if (rowStatistics.size() > 0) {
                fileStatistic = fileStatisticCalculate.calculateStatFile(rowStatistics);
            }
            try {
                databaseWorker.statFile(newID, file.getName(), fileStatistic);
            } catch (SQLException e) {
                System.out.println("Ошибка записи статистики по файлу");
            }
            try {
                databaseWorker.statRow(newID, rowStatistics);
            } catch (SQLException e) {
                System.out.println("Ошибка записи статистики по строкам");
            }

        }
        databaseWorker.stopConnection();
    }
}