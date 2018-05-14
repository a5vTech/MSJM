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
        model.addAttribute("medarbejderListe", access.executeStamementEmployeeList());
        model.addAttribute("sags_id", access.getLastCaseId());
        return "tilknyt_medarbejder";
    }

    @PostMapping(value = "/tilknyt_medarbejder", params = "save")
    public String assignEmployeePost(@RequestParam("medarbejder_id") String medarbejder_id) {
        String[] idListe = medarbejder_id.split(",");
        for (int i = 0; i < idListe.length; i++) {
            access.assignToCase(access.getLastCaseId(), idListe[i]);
        }
        return "redirect:/menu";
    }

    @PostMapping(value = "/tilknyt_medarbejder", params = "addNone")
    public String assignNoEmployeesPost() {
        return "redirect:/menu";
    }

}
