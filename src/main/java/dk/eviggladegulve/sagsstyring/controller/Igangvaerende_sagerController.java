package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
//SVEND SAGER
@Controller
@SessionAttributes({"BrugerID","Stilling"})
public class Igangvaerende_sagerController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/igangvaerende_sager")
    public String igangvaerendeSager(Model model, @ModelAttribute("BrugerID") int medarbejder_id) {
        ArrayList<Sag> sager = access.getAllActiveCasesSvend(medarbejder_id);
        model.addAttribute("Sager", sager);
        return "igangvaerende_sager";
    }

    @PostMapping("/igangvaerende_sager")
    public String igangvaerendeSagerPost() {
        return "igangvaerende_sager";
    }


}
