package dk.eviggladegulve.sagsstyring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class ikke_afsluttede_sagerController {
    final AccessDB access = AccessDB.getInstance();


    @GetMapping("/ikke_afsluttede_sager")
    public String sager(Model model) {
        ArrayList<Sag> sager = access.getAllActiveCases();
        model.addAttribute("caseList", sager);
        return "ikke_afsluttede_sager";
    }


    @PostMapping("/ikke_afsluttede_sager")
    public String sagerPost(@RequestParam int id) {


        return "vis_aktuel_sag/" + id;
    }
}
