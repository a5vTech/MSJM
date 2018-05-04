package dk.eviggladegulve.sagsstyring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class opret_sagController {
    final AccessDB access = AccessDB.getInstance();


    @GetMapping("/opret_sag")
    public String createCase(Model model) {
        model.addAttribute("case", new Sag());
        return "opret_sag";
    }

    @PostMapping(value = "/opret_sag", params = "save")
    public String createCasePost(@ModelAttribute Sag nuvaerendeSag) {
        System.out.println();
        access.insertAddress(nuvaerendeSag.getVejnavn(), nuvaerendeSag.getVejnummer(), nuvaerendeSag.getPostnummer(), nuvaerendeSag.getBy());
        access.insertCase(nuvaerendeSag);
        return "redirect:/opret_sag";
    }
}
