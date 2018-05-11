package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Afsluttede_SagerController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/afsluttede_sager")
    public String afsluttedeSager(Model model) {

        model.addAttribute("sagerListe",access.getAllEndedCases());
        return "afsluttede_sager";
    }


}
