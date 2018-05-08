package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class Rediger_sagController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping(value = "/rediger_sag/{id}")
    public String redigerSag(@PathVariable("id") int id, Model model) {

        ArrayList<Sag> sager = access.getAllActiveCases();
        model.addAttribute("Sag", Sag.findCaseById(id));

        return "rediger_sag";
    }

    @PostMapping("/rediger_sag")
    public String redigerSagPost(@ModelAttribute("Sag") Sag sag) {
        access.editCase(sag);
        return "redirect:/rediger_sag";

    }


}
