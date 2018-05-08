package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class Rediger_sagController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping(value = "/rediger_sag/{id}")
    public String redigerSag(@PathVariable("id") int id, Model model) {
        Sag sag = Sag.findCaseById(id);
        model.addAttribute("Sag", sag);

        return "rediger_sag";
    }

    @PostMapping(value = "/rediger_sag", params = "save")
    public String redigerSagPost(@ModelAttribute Sag nuvaerendeSag, @RequestParam("sag_id") int sag_id, @RequestParam("adresse_id") int adresse_id) {
        nuvaerendeSag.setSags_id(sag_id);
        nuvaerendeSag.setAdresse_id(adresse_id);
        System.out.println(nuvaerendeSag.getVejnavn());
        System.out.println(nuvaerendeSag.getVejnummer());
        System.out.println(nuvaerendeSag.getBy());
        System.out.println(nuvaerendeSag.getPostnummer());

        access.editCase(nuvaerendeSag);
        return "redirect:/rediger_sag/"+nuvaerendeSag.getSags_id();

    }


}
