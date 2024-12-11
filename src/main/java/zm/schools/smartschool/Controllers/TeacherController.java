package zm.schools.smartschool.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zm.schools.smartschool.Services.TeacherService;

import java.util.List;

@Controller
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    @PostMapping("/{id}/assign-grades")
    public String assignGradesToTeacher(@PathVariable Long id, @RequestParam List<Long> gradeIds, RedirectAttributes redirectAttributes) {
        teacherService.assignGradesToTeacher(id, gradeIds);
        redirectAttributes.addFlashAttribute("message", "Grades assigned to teacher successfully");
        return "redirect:/teachers";
    }

    @PostMapping("/{id}/assign-subjects")
    public String assignSubjectsToTeacher(@PathVariable Long id, @RequestParam List<Long> subjectIds, RedirectAttributes redirectAttributes) {
        teacherService.assignSubjectsToTeacher(id, subjectIds);
        redirectAttributes.addFlashAttribute("message", "Subjects assigned to teacher successfully");
        return "redirect:/teachers";
    }
}
