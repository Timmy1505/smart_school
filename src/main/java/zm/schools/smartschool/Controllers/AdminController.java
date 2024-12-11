package zm.schools.smartschool.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;


@Controller
@RequestMapping("/v1/authorised/admin")
@AllArgsConstructor
public class AdminController {
    
    @GetMapping("/index")
    public String showDashboard(Model model) {
      
        return "dashboard/index";
    }

}

