package zm.schools.smartschool.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import zm.schools.smartschool.DTOs.GradeDTO;
import zm.schools.smartschool.DTOs.SubjectRequest;
import zm.schools.smartschool.DTOs.SubjectResponse;
import zm.schools.smartschool.Services.GradeService;
import zm.schools.smartschool.Services.SubjectService;

import java.util.List;


import lombok.AllArgsConstructor;


@Controller
@RequestMapping("/subjects")
@AllArgsConstructor
public class SubjectController {


    private final SubjectService subjectService;

    private final GradeService gradeService;
    
    @GetMapping("/view")
    public String showCreateForm(Model model) {
        SubjectRequest subject = new SubjectRequest();
        List<GradeDTO> grades = gradeService.getAllGrades();
        List<SubjectResponse> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        model.addAttribute("subject", subject);
        model.addAttribute("grades", grades);
        return "dashboard/Subjects/create";
    }

    @PostMapping
    public String createSubject(@ModelAttribute("subject") SubjectRequest subjectDTO) {
        subjectService.saveSubject(subjectDTO);
        return "redirect:/subjects/view";
    }

    @GetMapping
    public String listSubjects(Model model) {
        List<SubjectResponse> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "dashboard/Subjects/list";
    }

    @GetMapping("/{id}")
    public String getSubject(@PathVariable Long id, Model model) {
        SubjectResponse subject = subjectService.getSubject(id);
        model.addAttribute("subject", subject);
        return "dashboard/Subjects/view";
    }

    @DeleteMapping("/{id}")
    public String deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return "redirect:/subjects";
    }
}

