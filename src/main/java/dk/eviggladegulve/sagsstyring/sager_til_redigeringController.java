package dk.eviggladegulve.sagsstyring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class sager_til_redigeringController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/sager_til_redigering")
    public String sager_under_afslutning(Model model) {
        ArrayList<Sag> sager = access.getAllActiveCases();
        model.addAttribute("caseList", sager);
        return "sager_til_redigering";
    }
}
