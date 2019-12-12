package ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.impl;

import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.Service;
import ir.mctab.java32.projects.scholarshipmanagement.core.share.AuthenticationService;
import ir.mctab.java32.projects.scholarshipmanagement.features.usermanagement.usecases.LogoutUseCase;

@Service
public class LogoutUseCaseImpl implements LogoutUseCase {
    @Override
    public void logout() {
        AuthenticationService.getInstance().setLoginUser(null);
    }
}
