package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class Rediger_medarbejerController {
    final AccessDB access = AccessDB.getInstance();


    @GetMapping("/rediger_medarbejdere")
    public String redigerMedarbejder(Model model) throws NullPointerException{
        ArrayList<Medarbejder> medarbejderListe = access.executeStamementEmployeeList();
        model.addAttribute("medarbejderListe", medarbejderListe);
        return "medarbejder_til_redigering";
    }


}
