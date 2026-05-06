package NurseSearch.backendAPI.mvc;

import NurseSearch.backendAPI.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppUiController {

    @GetMapping({"", "/", "/home"})
    public String home() {
        return "home";
    }

    @GetMapping("/signup")
    public String signup() {
        return "redirect:/customer/signup";
    }
}
