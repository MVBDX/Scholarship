package ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.Service;
import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.ScholarshipLog;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.AcceptScholarshipByManagerUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class AcceptScholarshipByManagerUseCaseImpl implements AcceptScholarshipByManagerUseCase {
    @Override
    public boolean accept(Long scholarshipId) {
        User user = AuthenticationService.getInstance().getLoginUser();

        if (user != null && user.getRole().equals("Manager")) {
            try {
                Connection connection = DatabaseConfig.getDatabaseConnection();
                String sql = "UPDATE scholarship SET status = 'AcceptedByManager' " +
                        "WHERE id = ? && status = 'AcceptedBySupervisor'";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, scholarshipId);
                if (preparedStatement.executeUpdate() == 1) {
                    ScholarshipLog scholarshipLog = new ScholarshipLog();
                    scholarshipLog.log(user.getId(), scholarshipId, "AcceptedByManager", "AcceptedBySupervisor");
                    return true;
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}