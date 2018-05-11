package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class Rediger_medarbejderController {
    final AccessDB access = AccessDB.getInstance();


    @GetMapping(value = "/rediger_medarbejder/{id}")
    public String redigerMedarbejder(@PathVariable("id") int id, Model model) {
        ArrayList<Medarbejder> medarbejderListe = access.executeStamementEmployeeList();
        for (Medarbejder medarbejder : medarbejderListe) {
            if (medarbejder.getMedarbejder_id() == id) {
                model.addAttribute("Medarbejder", medarbejder);
            }
        }
        return "rediger_medarbejder";
    }

    @PostMapping(value = "/rediger_medarbejder", params = "save")
    public String redigerMedarbejderPost(@ModelAttribute Medarbejder nuvaerendeMedarbejder, @RequestParam("medarbejder_id") int medarbejder_id) {
        nuvaerendeMedarbejder.setMedarbejder_id(medarbejder_id);
        access.editEmployee(nuvaerendeMedarbejder);
        return "redirect:/rediger_medarbejder/"+nuvaerendeMedarbejder.getMedarbejder_id();
    }
}
