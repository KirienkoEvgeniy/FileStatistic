package run;

import file.FileStatistic;
import row.RowStatistic;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Evgeniy
 */
public class DatabaseWorker {
    private static final String url = "jdbc:mysql://localhost:3306/contactdb";
    private static final String user = "root";
    private static final String password = "admin";
    private static final String queryFile = "INSERT INTO statfile (id, name, maxf, minf, averf, lenghtfile) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String queryRow = "INSERT INTO statrow (ID, maxwordr, minwordr, averwordr, lenghtrow) VALUES (?, ?, ?, ?, ?)";
    private Connection connection = null;

    public DatabaseWorker() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Не найден драйвер");
        } catch (SQLException e) {
            System.out.println("Не установлено соединение с БД");
        }

    }

    public void statRow(String newID, List<RowStatistic> rowStatistic) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(queryRow);
        for (RowStatistic statistic : rowStatistic) {
            preparedStatement.setString(1, newID);
            preparedStatement.setInt(2, statistic.getMax().length());
            preparedStatement.setInt(3, statistic.getMin().length());
            preparedStatement.setFloat(4, statistic.getAverage());
            preparedStatement.setInt(5, statistic.getLineLenght());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        preparedStatement.close();
    }

    public void statFile(String newID, String fileName, FileStatistic fileStatistic) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(queryFile);
        preparedStatement.setString(1, newID);
        preparedStatement.setString(2, fileName);
        preparedStatement.setInt(3, fileStatistic.getMaxfile());
        preparedStatement.setInt(4, fileStatistic.getMinfile());
        preparedStatement.setFloat(5, fileStatistic.getAverfile());
        preparedStatement.setInt(6, fileStatistic.getLenghtFile());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void stopConnection() {

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке закрытия соединения");
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e1) {
                    System.out.println("Ошибка при повторной попытке закрытия соединения");
                }
            }
        }
    }
}
