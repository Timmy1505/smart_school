package zm.schools.smartschool.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zm.schools.smartschool.Models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByBillingRecord_Student_Id(Long studentId);
}
