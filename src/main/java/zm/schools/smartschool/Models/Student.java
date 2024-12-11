package zm.schools.smartschool.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Student {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne
        @JoinColumn(name = "app_user_id", nullable = false)
        private AppUser appUser;

        @ManyToOne
        @JoinColumn(name = "grade_id", nullable = false)
        private Grade grade;

        @ManyToOne
        @JoinColumn(name = "section_id", nullable = false)
        private Section section;

        @Column(unique = true, nullable = false)
        private String examinationNumber;

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "guardian_id", referencedColumnName = "id")
        private Guardian guardian;

        @PrePersist
        @PreUpdate
        public void validateExaminationNumber() {
            String gradeName = grade.getMasterGrade().getName();
            if ((gradeName.equals("8") || gradeName.equals("9") || gradeName.equals("10") || gradeName.equals("11") || gradeName.equals("12")) &&
                (examinationNumber == null || examinationNumber.isEmpty())) {
                throw new IllegalArgumentException("Examination number cannot be null or empty for students in grade 8 and above");
            }
        }
    }
