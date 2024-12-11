package zm.schools.smartschool.Configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import zm.schools.smartschool.Models.MasterGrade;
import zm.schools.smartschool.Repositories.MasterGradeRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class MasterGradeSeeder implements CommandLineRunner {

    @Autowired
    private MasterGradeRepository masterGradeRepository;

    @Override
    public void run(String... args) throws Exception {
        seedMasterGrades();
    }

    private void seedMasterGrades() {
        List<String> gradeNames = Arrays.asList( "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");

        for (String gradeName : gradeNames) {
            if (!masterGradeRepository.existsByName(gradeName)) {
                MasterGrade masterGrade = new MasterGrade();
                masterGrade.setName(gradeName);
                masterGradeRepository.save(masterGrade);
            }
        }
    }
}
