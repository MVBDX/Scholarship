package ir.mctab.java32.projects.scholarshipmanagement.core.share;

import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScholarshipLog {
    public void log(Long userId, Long studentId, String statusChanged, String statusPrevious){

        Connection connection = null;
        try {
            connection = DatabaseConfig.getDatabaseConnection();
            String insertQuery = "INSERT INTO log (userid, studentid, statuschanged, statusprevious, date)" +
                    " VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, studentId);
            preparedStatement.setString(3, statusChanged);
            preparedStatement.setString(4, statusPrevious);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            preparedStatement.setString(5, dateFormat.format(date));
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
