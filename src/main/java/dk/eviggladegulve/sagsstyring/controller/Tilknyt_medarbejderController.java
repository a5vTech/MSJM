package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class Tilknyt_medarbejderController {
    final AccessDB access = AccessDB.getInstance();


    @GetMapping("/tilknyt_medarbejder")
    public String assignEmployee(Model model) {
        ArrayList<Medarbejder> empList = access.executeStamementEmployeeList();
        model.addAttribute("medarbejderListe", empList);
        model.addAttribute("sags_id", access.getLastCaseId());
        return "tilknyt_medarbejder";
    }

    @PostMapping("/tilknyt_medarbejder")
    public String assignEmployeePost(@RequestParam("medarbejder_id") String medarbejder_id) {
        String[] idListe = medarbejder_id.split(",");

        for (int i = 0; i < idListe.length; i++) {
            access.assignToCase(access.getLastCaseId(),idListe[i]);
      }


        // TILKNYT STATEMENT
        return "redirect:/menu";
    }
}
