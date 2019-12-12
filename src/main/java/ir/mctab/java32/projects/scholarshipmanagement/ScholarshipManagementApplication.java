package ir.mctab.java32.projects.scholarshipmanagement;

import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.impl.*;
import ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases.*;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.impl.LoginUseCaseImpl;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.impl.LogoutUseCaseImpl;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.usecases.LoginUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.usecases.LogoutUseCase;
import ir.mctab.java32.projects.scholarshipmanagement.model.Scholarship;
import ir.mctab.java32.projects.scholarshipmanagement.model.User;

import java.util.List;
import java.util.Scanner;

public class ScholarshipManagementApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command = "";
        LoginUseCase loginUseCase = null;
        User user = null;
        while (!command.equals("exit")) {
            if (user != null) {
                switch (user.getRole()) {
                    case "Student":
                        System.out.println("[ request | find | log | logout ]");
                        break;
                    case "Manager":
                        System.out.println("[ mlist | maccept | mreject | mreport | logout ]");
                        break;
                    case "Supervisor":
                        System.out.println("[ svlist | svaccept | svreject | logout ]");
                        break;
                    case "University":
                        System.out.println("[ unilist | uniaccept | logout ]");
                        break;
                }
            } else {
                System.out.println("What do you want to do?\n[ login | exit ]");
            }

            command = scanner.nextLine();
            // Logout
            if (command.equals("logout")) {
                LogoutUseCase logoutUseCase = new LogoutUseCaseImpl();
                logoutUseCase.logout();
                user = null;
                System.out.println("Logout successful.");
            }
            // Login
            if (command.equals("login")) {
                System.out.println("Username : ");
                String username = scanner.nextLine();
                System.out.println("Password : ");
                String password = scanner.nextLine();
                loginUseCase = new LoginUseCaseImpl();
                user = loginUseCase.login(username, password);
                if (user != null) {
                    System.out.println("Login successful by " + user.getRole());
                } else {
                    System.out.println("Login failed!");
                }
            }

            // +++++++ STUDENT
            //Log scholarship by student
            if (command.equals("log")) {
                ReportScholarshipByStudentUseCase reportScholarshipByStudentUseCase
                        = new ReportScholarshipByStudentUseCaseImpl();
                System.out.println("Scholarship ID: ");
                String scholarshipId = scanner.nextLine();
                System.out.println(reportScholarshipByStudentUseCase.report(Long.parseLong(scholarshipId)));

            }
            //Find scholarship by student
            if (command.equals("find")) {
                FindScholarshipByStudentUseCase findScholarshipByStudentUseCase
                        = new FindScholarshipByStudentUseCaseImpl();
                List<Scholarship> scholarships = findScholarshipByStudentUseCase
                        .listScholarships(user.getId());
                for (Scholarship scholarship : scholarships) {
                    System.out.println(scholarship);
                }
            }
            // request scholarship by student
            if (command.equals("request")) {
                RequestScholarshipByStudentUseCase requestScholarshipByStudentUseCase
                        = new RequestScholarshipByStudentUseCaseImpl();
                System.out.println("Name: ");
                String name = scanner.nextLine();
                System.out.println("Family: ");
                String family = scanner.nextLine();
                System.out.println("National Code: ");
                String nationalCode = scanner.nextLine();
                System.out.println("Last University: ");
                String lastUniversity = scanner.nextLine();
                System.out.println("Degree: ");
                String degree = scanner.nextLine();
                System.out.println("Field: ");
                String field = scanner.nextLine();
                System.out.println("Score: ");
                String score = scanner.nextLine();
                System.out.println("Apply University: ");
                String applyUniversity = scanner.nextLine();
                System.out.println("Apply Degree: ");
                String applyDegree = scanner.nextLine();
                System.out.println("Apply Field: ");
                String applyField = scanner.nextLine();
                System.out.println("Apply Date: ");
                String date = scanner.nextLine();

                if (requestScholarshipByStudentUseCase.request(user.getId(), name, family, nationalCode, lastUniversity, degree, field,
                        Float.parseFloat(score), applyUniversity, applyDegree, applyField, date))
                    System.out.println("Requested by Student Done.");
                else
                    System.out.println("Requested by Student Failed!");

            }

            // +++++++SUPERVISOR
            // find scholarship by supervisor
            if (command.equals("svlist")) {
                FindScholarshipBySupervisorUseCase findScholarshipBySupervisorUseCase
                        = new FindScholarshipBySupervisorUseCaseImpl();

                List<Scholarship> scholarships = findScholarshipBySupervisorUseCase
                        .listScholarships();
                for (int i = 0; i < scholarships.size(); i++) {
                    System.out.println(scholarships.get(i));
                }
            }
            // accept scholarship by supervisor
            if (command.equals("svaccept")) {
                AcceptScholarshipBySupervisorUseCase acceptScholarshipBySupervisorUseCase
                        = new AcceptScholarshipBySupervisorUseCaseImpl();
                System.out.println("Scholarship ID: ");
                String scholarshipId = scanner.nextLine();
                if (acceptScholarshipBySupervisorUseCase.accept(Long.parseLong(scholarshipId)))
                    System.out.println("Accepted by Supervisor Done.");
                else
                    System.out.println("Accepted by Supervisor Failed!");

            }
            //Reject scholarship by supervisor
            if (command.equals("svreject")) {
                RejectScholarshipBySupervisorUseCase rejectScholarshipBySupervisorUseCase
                        = new RejectScholarshipBySupervisorUseCaseImpl();
                System.out.println("Scholarship ID: ");
                String scholarshipId = scanner.nextLine();
                rejectScholarshipBySupervisorUseCase.reject(Long.parseLong(scholarshipId));
                System.out.println("Rejected by Supervisor Done.");
            }

            // ++++++++++++++ MANAGER
            //Find scholarship by manager
            if (command.equals("mlist")) {
                FindScholarshipByManagerUseCase findScholarshipByManagerUseCase
                        = new FindScholarshipByManagerUseCaseImpl();
                List<Scholarship> scholarships = findScholarshipByManagerUseCase
                        .listScholarships();
                for (int i = 0; i < scholarships.size(); i++) {
                    System.out.println(scholarships.get(i));
                }
            }
            //Accept scholarship by Manager
            if (command.equals("maccept")) {
                AcceptScholarshipByManagerUseCase acceptScholarshipByManagerUseCase
                        = new AcceptScholarshipByManagerUseCaseImpl();
                System.out.println("Scholarship ID: ");
                String scholarshipId = scanner.nextLine();
                if (acceptScholarshipByManagerUseCase.accept(Long.parseLong(scholarshipId)))
                    System.out.println("Accepted by Manager Done.");
                else
                    System.out.println("Accepted by Manager Failed.");

            }
            //Reject scholarship by Manager
            if (command.equals("mreject")) {
                RejectScholarshipByManagerUseCase rejectScholarshipByManagerUseCase
                        = new RejectScholarshipByManagerUseCaseImpl();
                System.out.println("Scholarship ID: ");
                String scholarshipId = scanner.nextLine();
                rejectScholarshipByManagerUseCase.reject(Long.parseLong(scholarshipId));
                System.out.println("Rejected by Manager Done.");
            }
            //Report scholarship by Manager
            if (command.equals("mreport")) {
                ReportScholarshipByManagerUseCase reportScholarshipByManagerUseCase
                        = new ReportScholarshipByManagerUseCaseImpl();
                System.out.println(reportScholarshipByManagerUseCase.report());
            }

            // +++++++ UNIVERSITY
            //Find scholarship by University
            if (command.equals("unilist")) {
                FindScholarshipByUniversityUseCase findScholarshipByUniversityUseCase
                        = new FindScholarshipByUniversityUseCaseImpl();
                List<Scholarship> scholarships = findScholarshipByUniversityUseCase
                        .listScholarships();
                for (int i = 0; i < scholarships.size(); i++) {
                    System.out.println(scholarships.get(i));
                }
            }

            //todo accep by uni
        }
    }
}
