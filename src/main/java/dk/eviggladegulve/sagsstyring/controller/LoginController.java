package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;


@Controller
public class LoginController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/log_ind")
    public String login() {
        return "log_ind";
    }

    @PostMapping("/log_ind")
    public String loginPost(@RequestParam("username") String username, @RequestParam("password") String password) {
        if(access.checkLogin(username,password)){
            return "redirect:/kalender";
        }else
            return "redirect:/log_ind";
    }

}
