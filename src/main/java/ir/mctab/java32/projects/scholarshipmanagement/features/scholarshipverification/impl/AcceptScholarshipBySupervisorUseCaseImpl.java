package ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.Service;
import ir.mctab.java32.projects.scholarshipmanagement.core.config.DatabaseConfig;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.ScholarshipLog;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.AcceptScholarshipBySupervisorUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class AcceptScholarshipBySupervisorUseCaseImpl implements AcceptScholarshipBySupervisorUseCase {
    @Override
    public boolean accept(Long scholarshipId) {
        User user = AuthenticationService.getInstance().getLoginUser();

        if (user != null && user.getRole().equals("Supervisor")) {
            try {
                Connection connection = DatabaseConfig.getDatabaseConnection();
                String sql = "UPDATE scholarship SET status = 'AcceptedBySupervisor' " +
                        "WHERE id = ? && status = 'RequestedByStudent'";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, scholarshipId);
                if (preparedStatement.executeUpdate() == 1) {
                    ScholarshipLog scholarshipLog = new ScholarshipLog();
                    scholarshipLog.log(user.getId(), scholarshipId, "AcceptedBySupervisor", "RequestedByStudent");
                    return true;
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
