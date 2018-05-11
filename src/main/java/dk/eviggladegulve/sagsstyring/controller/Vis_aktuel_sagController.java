package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class Vis_aktuel_sagController {

    final AccessDB access = AccessDB.getInstance();

    @GetMapping(value = "/aktuel_sag/{id}")
    public String aktuelSag(@PathVariable("id") int id, Model model) {
        model.addAttribute("sags_id", id);
        model.addAttribute("Sag", Sag.findCaseById(id));
        model.addAttribute("timer", access.registreredeTimerSag(id));
        System.out.println(access.registreredeTimerSag(id));


        return "vis_aktuel_sag";
    }


}
