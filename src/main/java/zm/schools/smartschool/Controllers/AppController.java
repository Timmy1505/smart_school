package zm.schools.smartschool.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class AppController {
    
    @GetMapping("/v1/login")
    public String loginPage(Model model, @RequestParam(required = false, name = "error") String error) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }
        return "authentication/login";
    }
}
