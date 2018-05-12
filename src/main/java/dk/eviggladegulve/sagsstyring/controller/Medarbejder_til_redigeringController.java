package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class Medarbejder_til_redigeringController {
    final AccessDB access = AccessDB.getInstance();


    @GetMapping("/medarbejdere_til_redigering")
    public String redigerMedarbejder(Model model) throws NullPointerException {
        ArrayList<Medarbejder> medarbejderListe = access.executeStamementEmployeeList();
        model.addAttribute("medarbejderListe", medarbejderListe);
        return "medarbejdere_til_redigering";
    }

    @PostMapping(value = "/medarbejdere_til_redigering")
    public String medarbejder_til_redigeringSletPost(@RequestParam("medarbejder_id") int medarbejder_id) {
        access.deleteEmployee(medarbejder_id);
        return "redirect:/medarbejdere_til_redigering";
    }



}
