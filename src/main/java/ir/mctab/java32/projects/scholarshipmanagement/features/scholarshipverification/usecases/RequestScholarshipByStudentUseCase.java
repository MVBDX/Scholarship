package ir.mctab.java32.projects.scholarshipmanagement.features.scholarshipverification.usecases;

import ir.mctab.java32.projects.scholarshipmanagement.core.annotations.UseCase;

@UseCase
public interface RequestScholarshipByStudentUseCase {
    boolean request(Long id, String name, String family, String nationalCode, String lastUni, String lastDegree,
                 String lastField, Float lastScore, String applyUni, String applyDegree, String applyField, String applyDate);
}
