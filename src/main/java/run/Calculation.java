package run;

import file.FileStatistic;
import file.FileStatisticCalculateImpl;
import row.RowStatistic;
import row.RowStatisticCalculateImpl;

import java.io.File;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Calculation {
    private static final String url = "jdbc:mysql://localhost:3306/contactdb";
    private static final String user = "root";
    private static final String password = "admin";
    private static Charset charset = Charset.forName("ISO-8859-1");
    private static final String queryFile = "INSERT INTO statfile (id, name, maxf, minf, averf, lenghtfile) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String queryRow = "INSERT INTO statRow (ID, maxwordr, minwordr, averwordr, lenghtrow) VALUES (?, ?, ?, ?, ?)";
    private static AtomicLong ref = new AtomicLong(System.currentTimeMillis());
    private static String DEFAULT_REFERENCE = "0000000000000";

    private static void statRow(PreparedStatement stmt, String newID, List<RowStatistic> rowStatistic) throws SQLException {
        for (RowStatistic statistic : rowStatistic) {
            stmt.setString(1, newID);
            stmt.setInt(2, statistic.getMax().length());
            stmt.setInt(3, statistic.getMin().length());
            stmt.setFloat(4, statistic.getAverage());
            stmt.setInt(5, statistic.getLineLenght());
            stmt.addBatch();
        }
        stmt.executeUpdate();
    }

    private static void statFile(PreparedStatement stmtf, String newID, String fileName, FileStatistic fileStatistic) throws SQLException {
        stmtf.setString(1, newID);
        stmtf.setString(2, fileName);
        stmtf.setInt(3, fileStatistic.getMaxfile());
        stmtf.setInt(4, fileStatistic.getMinfile());
        stmtf.setFloat(5, fileStatistic.getAverfile());
        stmtf.setInt(6, fileStatistic.getLenghtFile());
        stmtf.executeUpdate();
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
//        создаём соединение с базой данных
            FileStatisticCalculateImpl fileStatisticCalculate = new FileStatisticCalculateImpl();
            RowStatisticCalculateImpl rowStatisticCalculate = new RowStatisticCalculateImpl();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

            List<File> files = fileStatisticCalculate.getTxtFiles("D:\\Test");
            for (File file : files) {
                String newID = Long.toString(ref.getAndIncrement(), 36)
                        .toUpperCase();
                newID=DEFAULT_REFERENCE.replace(
                        DEFAULT_REFERENCE.substring(0,newID.length()),
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
                    PreparedStatement preparedStatement = connection.prepareStatement(queryFile);
                    statFile(preparedStatement, newID, file.getName(), fileStatistic);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(queryRow);
                    statRow(preparedStatement,newID, rowStatistics);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
