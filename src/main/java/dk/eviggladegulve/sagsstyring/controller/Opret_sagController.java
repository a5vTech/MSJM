package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Opret_sagController {
    final AccessDB access = AccessDB.getInstance();


    @GetMapping("/opret_sag")
    public String createCase(Model model) {
        model.addAttribute("Sag", new Sag());
        model.addAttribute("sags_id", access.getLastCaseId() + 1);
        return "opret_sag";
    }

    @PostMapping(value = "/opret_sag", params = "save")
    public String createCasePost(@ModelAttribute Sag nuvaerendeSag) {
//        String[] adresseSplitter = nuvaerendeSag.getVejnavn().split(" ");
//        String[] bySplitter = nuvaerendeSag.getBy().split(" ");
//        String vejnavn = adresseSplitter[0];
//        for(int i=1;i<adresseSplitter.length-1;i++){
//            vejnavn += " " + adresseSplitter[i];
//        }
//        String bynavn = bySplitter[1];
//        for(int i=2;i<bySplitter.length;i++){
//            bynavn += " " + bySplitter[i];
//        }
//        System.out.println(vejnavn);
//        System.out.println(adresseSplitter[adresseSplitter.length-1]);
//        System.out.println(bynavn);
//        System.out.println(bySplitter[0]);
        access.insertAddress(nuvaerendeSag.getVejnavn(), nuvaerendeSag.getVejnummer(), nuvaerendeSag.getPostnummer(), nuvaerendeSag.getBy());
        access.insertCase(nuvaerendeSag);
//        access.insertAddress(vejnavn, Integer.parseInt(adresseSplitter[adresseSplitter.length-1]), Integer.parseInt(bySplitter[0]), bynavn);
//        access.insertCase(nuvaerendeSag);
        return "redirect:/tilknyt_medarbejder/";
    }
}
