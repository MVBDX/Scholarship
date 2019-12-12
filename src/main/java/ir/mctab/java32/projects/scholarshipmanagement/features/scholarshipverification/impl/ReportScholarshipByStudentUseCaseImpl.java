package ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.Service;
import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.ReportScholarshipByManagerUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.ReportScholarshipByStudentUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class ReportScholarshipByStudentUseCaseImpl implements ReportScholarshipByStudentUseCase {
    @Override
    public String report(Long sutdentId) {
        String reportString = "";
        User loginUser = AuthenticationService.getInstance().getLoginUser();
        if (loginUser != null) {
            if (loginUser.getRole().equals("Student")) {
                Connection connection = null;
                try {
                    connection = DatabaseConfig.getDatabaseConnection();
                    String sql = "SELECT * FROM log WHERE studentid = " + sutdentId;
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        reportString += "Status: " + resultSet.getString("statuschanged")
                                + ", Date: " + resultSet.getString("date") + "\n";
                    }
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return reportString;
    }
}
