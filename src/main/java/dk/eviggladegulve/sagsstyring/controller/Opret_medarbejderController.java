package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Opret_medarbejderController {

    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/opret_medarbejder")
    public String opretMedarbejder(Model model) {
        model.addAttribute("medarbejder",new Medarbejder());
        model.addAttribute("medarbejder_id", access.getLastEmployeeId()+1);
        return "opret_medarbejder";
    }

    @PostMapping(value= "/opret_medarbejder", params = "save")
    public String opretMedarbejderPost(@ModelAttribute Medarbejder nuvaerendeMedarbejder) {
        System.out.println(nuvaerendeMedarbejder.getStilling());
        access.insertEmployee(nuvaerendeMedarbejder);
        return "opret_medarbejder";
    }

}
