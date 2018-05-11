package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@SessionAttributes({"BrugerID","Stilling"})

public class LoginController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/log_ind")
    public String login() {
        return "log_ind";
    }

    @PostMapping("/log_ind")
    public String loginPost(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        String stilling = access.checkLogin(username, password);
        model.addAttribute("BrugerID", username);
        switch (stilling) {
            case "Leder":
                model.addAttribute("Stilling", "Leder");
                return "redirect:/menu";
            case "Konduktør":
                model.addAttribute("Stilling", "Konduktør");
                return "redirect:/menu";
            case "Svend":
                model.addAttribute("Stilling", "Svend");
                return "redirect:/igangvaerende_sager";
            default:
                return "redirect:/log_ind";
        }

    }
}
