package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
//SVEND SAGER
@Controller
public class Igangvaerende_sagerController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/igangvaerende_sager")
    public String igangvaerendeSager(Model model) {
        ArrayList<Sag> sager = access.getAllActiveCases();
        model.addAttribute("caseList", sager);
        return "igangvaerende_sager";
    }

    @PostMapping("/igangvaerende_sager")
    public String igangvaerendeSagerPost() {
        return "igangvaerende_sager";
    }


}
