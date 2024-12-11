package zm.schools.smartschool.Services;

import java.util.List;

import zm.schools.smartschool.DTOs.StatementEntry;

public interface BillingService {
    void billNewStudent(Long studentId);
    void billAllStudentsInGrade(Long gradeId);
    void processPayment(Long billingRecordId, double amount,String referenceNumber);
    List<StatementEntry> generateStudentStatement(Long studentId);
    double getRunningBalanceByExaminationNumber(String examinationNumber);
}
