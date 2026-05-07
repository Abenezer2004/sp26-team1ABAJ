package NurseSearch.backendAPI.mvc;

import org.springframework.stereotype.Controller;
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

    @GetMapping("/login")
    public String login() {
        return "redirect:/customer/login";
    }
}