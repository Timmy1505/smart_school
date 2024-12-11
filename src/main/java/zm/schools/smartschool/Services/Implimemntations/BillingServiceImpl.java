package zm.schools.smartschool.Services.Implimemntations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import zm.schools.smartschool.DTOs.StatementEntry;
import zm.schools.smartschool.Models.BillingRecord;
import zm.schools.smartschool.Models.Fee;
import zm.schools.smartschool.Models.Grade;
import zm.schools.smartschool.Models.Payment;
import zm.schools.smartschool.Models.Student;
import zm.schools.smartschool.Repositories.BillingRecordRepository;
import zm.schools.smartschool.Repositories.FeeRepository;
import zm.schools.smartschool.Repositories.GradeRepository;
import zm.schools.smartschool.Repositories.PaymentRepository;
import zm.schools.smartschool.Repositories.StudentRepository;
import zm.schools.smartschool.Services.BillingService;

@Service
@AllArgsConstructor
public class BillingServiceImpl implements BillingService {
    private final FeeRepository feeRepository;
    private final BillingRecordRepository billingRecordRepository;
    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    @Override
    public void billNewStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Student ID"));
        List<Fee> compulsoryFees = feeRepository.findAllByCompulsory(true);

        List<Fee> allFees = new ArrayList<>();
        allFees.addAll(compulsoryFees);

        for (Fee fee : allFees) {
            BillingRecord billingRecord = new BillingRecord();
            billingRecord.setStudent(student);
            billingRecord.setFee(fee);
            billingRecord.setBillingDate(new Date());
            billingRecord.setAmount(fee.getAmount());
            billingRecord.setPaid(false);

            billingRecordRepository.save(billingRecord);
        }
    }

    @Override
    public void billAllStudentsInGrade(Long gradeId) {
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Grade ID"));
        List<Student> students = studentRepository.findAllByGrade(grade);

        for (Student student : students) {
            billNewStudent(student.getId());
        }
    }

    @Override
    public void processPayment(Long billingRecordId, double amount, String referenceNumber) {
        BillingRecord billingRecord = billingRecordRepository.findById(billingRecordId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Billing Record ID"));
        Payment payment = new Payment();
        payment.setBillingRecord(billingRecord);
        payment.setPaymentDate(new Date());
        payment.setAmount(amount);
        payment.setReferenceNumber(referenceNumber);

        paymentRepository.save(payment);

        billingRecord.setPaid(true);
        billingRecord.setPaymentDate(new Date());
        billingRecordRepository.save(billingRecord);
    }

    @Override
    public List<StatementEntry> generateStudentStatement(Long studentId) {
        List<BillingRecord> billingRecords = billingRecordRepository.findAllByStudentId(studentId);
        List<Payment> payments = paymentRepository.findAllByBillingRecord_Student_Id(studentId);

        List<StatementEntry> statementEntries = new ArrayList<>();
        double runningBalance = 0;

        for (BillingRecord record : billingRecords) {
            StatementEntry entry = new StatementEntry();
            entry.setDate(record.getBillingDate());
            entry.setReference("Billing: " + record.getId());
            entry.setDescription("Billing: " + record.getFee().getName());
            entry.setDebit(record.getAmount());
            entry.setCredit(0);
            runningBalance += entry.getDebit();
            entry.setRunningBalance(runningBalance);

            statementEntries.add(entry);
        }

        for (Payment payment : payments) {
            StatementEntry entry = new StatementEntry();
            entry.setDate(payment.getPaymentDate());
            entry.setReference("Payment: " + payment.getId());
            entry.setDescription("Payment");
            entry.setDebit(0);
            entry.setCredit(payment.getAmount());
            runningBalance -= entry.getCredit();
            entry.setRunningBalance(runningBalance);

            statementEntries.add(entry);
        }

        return statementEntries;
    }

    @Override
    public double getRunningBalanceByExaminationNumber(String examinationNumber) {
        Student student = studentRepository.findByExaminationNumber(examinationNumber)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Examination Number"));

        List<BillingRecord> billingRecords = billingRecordRepository.findAllByStudentId(student.getId());
        List<Payment> payments = paymentRepository.findAllByBillingRecord_Student_Id(student.getId());

        double runningBalance = 0;

        for (BillingRecord record : billingRecords) {
            runningBalance += record.getAmount();
        }

        for (Payment payment : payments) {
            runningBalance -= payment.getAmount();
        }

        return runningBalance;
    }
}
