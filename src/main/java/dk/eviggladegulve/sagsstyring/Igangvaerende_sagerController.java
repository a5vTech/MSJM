package dk.eviggladegulve.sagsstyring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
//SVEND SAGER
@Controller
public class Igangvaerende_sagerController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/igangværende_sager")
    public String sager_under_afslutning(Model model) {
        ArrayList<Sag> sager = access.getAllActiveCases();
        model.addAttribute("caseList", sager);
        return "igangværende_sager";
    }

    @PostMapping("/igangværende_sager")
    public String sager_under_afslutningPost() {
        return "igangværende_sager";
    }


}
