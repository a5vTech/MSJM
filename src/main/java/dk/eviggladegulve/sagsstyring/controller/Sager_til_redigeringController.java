package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class Sager_til_redigeringController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/sager_til_redigering")
    public String sager_til_redigering(Model model) {
        ArrayList<Sag> sager = access.getAllActiveCases();
        model.addAttribute("caseList", sager);
        return "sager_til_redigering";
    }

    @PostMapping(value = "/sager_til_redigering", params = "slet_sagBtn")
    public String sager_til_redigeringSletPost(@RequestParam("sags_id") int sags_id) {
        access.deleteCase(sags_id);
        return "redirect:/sager_til_redigering";
    }
}
