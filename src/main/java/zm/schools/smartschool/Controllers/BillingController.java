package zm.schools.smartschool.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import zm.schools.smartschool.DTOs.PaymentDTO;
import zm.schools.smartschool.DTOs.StatementEntry;
import zm.schools.smartschool.Services.BillingService;

@RestController
@RequestMapping("/api/billing")
@AllArgsConstructor
public class BillingController {
    private final BillingService billingService;

    @PostMapping("/newStudent/{studentId}")
    public ResponseEntity<Void> billNewStudent(@PathVariable Long studentId) {
        billingService.billNewStudent(studentId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/grade/{gradeId}")
    public ResponseEntity<Void> billAllStudentsInGrade(@PathVariable Long gradeId) {
        billingService.billAllStudentsInGrade(gradeId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/payment")
    public ResponseEntity<Void> processPayment(@RequestBody PaymentDTO paymentDTO) {
        billingService.processPayment(paymentDTO.getBillingRecordId(), paymentDTO.getAmount(), paymentDTO.getReferenceNumber());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/statement/{studentId}")
    public ResponseEntity<List<StatementEntry>> generateStudentStatement(@PathVariable Long studentId) {
        List<StatementEntry> statementEntries = billingService.generateStudentStatement(studentId);
        return new ResponseEntity<>(statementEntries, HttpStatus.OK);
    }

    @GetMapping("/balance/{examinationNumber}")
    public ResponseEntity<Double> getRunningBalance(@PathVariable String examinationNumber) {
        double balance = billingService.getRunningBalanceByExaminationNumber(examinationNumber);
        return ResponseEntity.ok(balance);
    }
}

