package zm.schools.smartschool.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import zm.schools.smartschool.Models.BillingRecord;

public interface BillingRecordRepository extends JpaRepository<BillingRecord, Long> {
    List<BillingRecord> findAllByStudentId(Long studentId);
}
