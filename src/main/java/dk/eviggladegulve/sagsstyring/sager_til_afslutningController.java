package dk.eviggladegulve.sagsstyring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class sager_til_afslutningController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/sager_til_afslutning")
    public String sager(Model model) {
        ArrayList<Sag> sager = access.getAllActiveCases();
        model.addAttribute("caseList", sager);
        return "sager_til_afslutning";
    }


    @PostMapping("/sager_til_afslutning")
    public String sagerPost(@RequestParam int id) {


        return "redirect:/afslut_sag/" + id;
    }


}
