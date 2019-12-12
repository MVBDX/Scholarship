package ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.Service;
import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.ScholarshipLog;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.RequestScholarshipByStudentUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.sql.*;

@Service
public class RequestScholarshipByStudentUseCaseImpl implements RequestScholarshipByStudentUseCase {
    @Override
    public boolean request(Long id, String name, String family, String nationalCode, String lastUni, String lastDegree,
                           String lastField, Float lastScore, String applyUni, String applyDegree, String applyField, String applyDate) {
        User user = AuthenticationService.getInstance().getLoginUser();
        if (user != null && user.getRole().equals("Student")) {
            try {
                Connection connection = DatabaseConfig.getDatabaseConnection();
                String sql = "INSERT INTO scholarship (userId, status, name, family, nationalCode, lastUni, lastDegree, " +
                        "lastField, lastScore, applyUni, applyDegree, applyField, applyDate)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setLong(1, id);
                preparedStatement.setString(2, "RequestedByStudent");
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, family);
                preparedStatement.setString(5, nationalCode);
                preparedStatement.setString(6, lastUni);
                preparedStatement.setString(7, lastDegree);
                preparedStatement.setString(8, lastField);
                preparedStatement.setFloat(9, lastScore);
                preparedStatement.setString(10, applyUni);
                preparedStatement.setString(11, applyDegree);
                preparedStatement.setString(12, applyField);
                preparedStatement.setString(13, applyDate);

                if (preparedStatement.executeUpdate() == 1) {
                    ResultSet resultSet = preparedStatement.getGeneratedKeys();
                    if (resultSet.next()) {
                        System.out.println(resultSet.getLong(1));
                        ScholarshipLog scholarshipLog = new ScholarshipLog();
                        scholarshipLog.log(user.getId(), resultSet.getLong(1), "RequestedByStudent", "-");
                        return true;
                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
