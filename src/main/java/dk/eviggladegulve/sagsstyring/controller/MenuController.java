package dk.eviggladegulve.sagsstyring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import dk.eviggladegulve.sagsstyring.*;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"BrugerID","Stilling"})
public class MenuController {

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @PostMapping("/menu")
    public String menuPost() {
        return "redirect:/menu";
    }


    //Opretsag
    @PostMapping(value = "/menu", params = "opret_sag")
    public String menuOpretSagPost() {

        return "redirect:/opret_sag";

    }

    @PostMapping(value = "/menu", params = "rediger_sag")
    public String menuRedigerSagPost() {

        return "redirect:/sager_til_redigering";
    }
    @PostMapping(value = "/menu", params = "afslut_sag")
    public String menuAfslutSagPost() {

        return "redirect:/sager_til_afslutning";
    }

    @PostMapping(value = "/menu", params = "opret_medarbejder")
    public String menuOpretMedarbejder() {

        return "redirect:/opret_medarbejder";
    }

    @PostMapping(value = "/menu", params = "rediger_medarbejder")
    public String menuRedigerMedarbejder() {

        return "redirect:/rediger_medarbejdere";
    }
    @PostMapping(value = "/menu", params = "kalender")
    public String menuKalenderPost() {

        return "redirect:/kalender";
    }


}
