package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class tilknyt_medarbejder_rediger_sagController {
    final AccessDB access = AccessDB.getInstance();


    @GetMapping(value = "/tilknyt_medarbejder_rediger_sag/{id}")
    public String assignEmployee(@PathVariable("id") int id, Model model) {
        ArrayList<Medarbejder> emplist = access.executeStamementEmployeeList();
        ArrayList<Medarbejder> nonActive = new ArrayList<>();
        for (Medarbejder m : emplist) {

        }
        model.addAttribute("medarbejderListe", nonActive);
        model.addAttribute("aktiveMedarbejdere", access.showActiveEmployee(id));
        Sag sag = Sag.findCaseById(id, 1);
        model.addAttribute("Sag", sag);
        return "tilknyt_medarbejder_rediger_sag";
    }

    @PostMapping(value = "/tilknyt_medarbejder_rediger_sag", params = "save")
    public String assignEmployeePost(@RequestParam("tilknyt_Medarbejder_id") String medarbejder_id, @RequestParam("sag_id") int sag_id) {
        String[] idListe = medarbejder_id.split(",");
        for (int i = 0; i < idListe.length; i++) {
            access.assignToCase(sag_id, idListe[i]);
        }
        return "redirect:/menu";
    }

    @PostMapping(value = "/tilknyt_medarbejder_rediger_sag", params = "addNone")
    public String assignNoEmployeesPost() {
        return "redirect:/menu";
    }

    @PostMapping(value = "/tilknyt_medarbejder_rediger_sag", params = "remove")
    public String removeEmployeesPost(@RequestParam("fjern_Medarbejder_id") String medarbejder_id, @RequestParam("sag_id") int sag_id) {
        System.out.println("POSTMAPPING - TILKNYT SLET");
        String[] idListe = medarbejder_id.split(",");
        for (int i = 0; i < idListe.length; i++) {
            access.removeFromCase(sag_id, idListe[i]);
            System.out.println(idListe[i]);
        }
        return "redirect:/menu";
    }

}
