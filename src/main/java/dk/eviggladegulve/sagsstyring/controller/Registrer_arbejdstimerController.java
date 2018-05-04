package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
public class Registrer_arbejdstimerController {
    final AccessDB access = AccessDB.getInstance();
    @GetMapping("/registrer_arbejdstimer_liste")
    public String registrer_arbejdstimer_liste(Model model) {
        ArrayList<Sag> sager = access.getAllActiveCases();
        model.addAttribute("caseList", sager);
        return "registrer_arbejdstimer_liste";
    }

    @GetMapping(value = "/registrer_arbejdstimer/{id}")
    public String registrer_arbejdstimer(@PathVariable("id") int id, Model model) {
        access.createConnection();
        ArrayList<Sag> sager = access.getAllActiveCases();
        for (Sag c : sager) {
            if (c.getSags_id() == id) {
                model.addAttribute("case", c);
            }
        }

        return "registrer_arbejdstimer";
    }


}
