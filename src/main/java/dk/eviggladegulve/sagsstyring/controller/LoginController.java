package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@SessionAttributes("Bruger")

public class LoginController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/log_ind")
    public String login() {
        return "log_ind";
    }

    @PostMapping("/log_ind")
    public String loginPost(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        model.addAttribute("Bruger", username);

        return access.checkLogin(username,password);
    }
}
