package zm.schools.smartschool.Controllers;

import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import zm.schools.smartschool.Configs.LocalDateEditor;
import zm.schools.smartschool.DTOs.AuthUserDTO;
import zm.schools.smartschool.Models.AuthUser;
import zm.schools.smartschool.Services.AuthUserService;
import zm.schools.smartschool.Services.GradeService;
import zm.schools.smartschool.Services.SubjectService;

@Controller
@RequestMapping("/v1/authorised/admin/users")
@AllArgsConstructor
public class AuthUserController {

    private final AuthUserService authUserService;
    private final GradeService gradeService;
    private final SubjectService subjectService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new LocalDateEditor());
    }


    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("authUsers", authUserService.getAllAuthUsers());
        return "dashboard/AuthUser/list"; // The Thymeleaf template name for listing users
    }

    @GetMapping("/new")
    public String showCreateUserForm(Model model) {
        model.addAttribute("authUser", new AuthUserDTO());
        model.addAttribute("grades", gradeService.getAllGrades());
        model.addAttribute("subjects", subjectService.getAllSubjects());
        return "dashboard/AuthUser/create-user"; // The Thymeleaf template name for creating a user
    }


    @PostMapping("/new")
    public String createAuthUser(@ModelAttribute("authUser") AuthUserDTO authUserDTO) {
            // Get the currently authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            AuthUser loggedInUser = (AuthUser) authentication.getPrincipal(); // Assuming AuthUser implements UserDetails
    
            // Set the schoolId based on the logged-in user's schoolId
                authUserDTO.getAppUser().setSchoolId(loggedInUser.getAppUser().getSchool().getId());
         
            authUserService.createAuthUser(authUserDTO);
       return "redirect:/v1/authorised/admin/users";
    }

    @GetMapping("/{id}/edit")
    public String showEditUserForm(@PathVariable Long id, Model model) {
        try {
            AuthUserDTO authUserDTO = authUserService.getAuthUserById(id);
            model.addAttribute("authUser", authUserDTO);
            model.addAttribute("grades", gradeService.getAllGrades());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            return "dashboard/edit-user"; // The Thymeleaf template name for editing a user
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error: User not found.");
            return "redirect:/authorised/admin/users";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateAuthUser(@PathVariable Long id, @ModelAttribute("authUser") AuthUserDTO authUserDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Error: Please correct the errors in the form.");
            model.addAttribute("grades", gradeService.getAllGrades());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            return "dashboard/edit-user";
        }
        try {
            authUserService.updateAuthUser(id, authUserDTO);
            model.addAttribute("successMessage", "User updated successfully!");
            return "redirect:/authorised/admin/users";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error: Unable to update user. Please try again.");
            model.addAttribute("grades", gradeService.getAllGrades());
            model.addAttribute("subjects", subjectService.getAllSubjects());
            return "dashboard/edit-user";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteAuthUser(@PathVariable Long id, Model model) {
        try {
            authUserService.deleteAuthUser(id);
            model.addAttribute("successMessage", "User deleted successfully!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error: Unable to delete user. Please try again.");
        }
        return "redirect:/authorised/admin/users";
    }
}
