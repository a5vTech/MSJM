package dk.eviggladegulve.sagsstyring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;


@Controller
public class CaseController {
    final AccessDB access = AccessDB.getInstance();


    //OPRET SAG
    @GetMapping("/opret_sag")
    public String createCase(Model model) {
        model.addAttribute("case", new Case());
        return "opret_sag";
    }

    @PostMapping(value = "/opret_sag", params = "save")
    public String createCasePost(@ModelAttribute Case currentCase) {
        System.out.println();
        access.insertAddress(currentCase.getVejnavn(), currentCase.getVejnummer(), currentCase.getPostnummer(), currentCase.getBy());
        access.insertCase(currentCase);
        return "opret_sag";
    }


    //VIS ALLE AKTIVE SAGER


    @GetMapping("/aktive_sager")
    public String sager(Model model) {
        ArrayList<Case> cases = access.getAllActiveCases();
        model.addAttribute("caseList", cases);
        return "sager";
    }


    @PostMapping("/aktive_sager")
    public String sagerPost(@RequestParam int id) {


        return "vis_aktuel_sag/" + id;
    }

    //AKTUEL SAG


    @GetMapping(value = "/aktuel_sag/{id}")
    public String aktuelSag(@PathVariable("id") int id, Model model) {
        access.createConnection();
        ArrayList<Case> cases = access.getAllActiveCases();
        for(Case c: cases){
            if(c.getSags_id() == id){
                model.addAttribute("case", c);
            }
        }

        return "vis_aktuel_sag";
    }


}
