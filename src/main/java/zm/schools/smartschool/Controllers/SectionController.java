package zm.schools.smartschool.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import lombok.AllArgsConstructor;
import zm.schools.smartschool.DTOs.GradeDTO;
import zm.schools.smartschool.DTOs.SectionDto;
import zm.schools.smartschool.Services.GradeService;
import zm.schools.smartschool.Services.SectionService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequestMapping("/sections")
@AllArgsConstructor
public class SectionController {

    private final SectionService sectionService;
    private final GradeService gradeService;

    @GetMapping("/view")
    public String getSections(Model model) {
        model.addAttribute("section", new SectionDto());
        List<SectionDto> sections = sectionService.listOfSections();
        // TODO: update this section in order to search for Grades based on loged in user
        List <GradeDTO> grades = gradeService.getAllGrades();
        model.addAttribute("sections", sections);
        model.addAttribute("grades", grades);
        return "dashboard/sections/create"; 
    }

    @PostMapping("/create")
    public String createSection(@ModelAttribute SectionDto sectionDto, Model model) {
        SectionDto createdSection = sectionService.createSection(sectionDto);
        System.out.println(sectionDto);
        model.addAttribute("section", createdSection);
        return "redirect:/sections/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        SectionDto section = sectionService.getSectionById(id);
        model.addAttribute("section", section);
        return "dashboard/sections/edit"; 
    }

 

    @PostMapping("/delete/{id}")
    public String deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return "redirect:/sections/view";
    }
}
