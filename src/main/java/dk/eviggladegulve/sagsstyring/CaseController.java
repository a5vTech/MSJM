package dk.eviggladegulve.sagsstyring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class CaseController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/opret_sag")
    public String createCase(Model model) {
        model.addAttribute("case", new Case());
        return "opret_sag";
    }

    @PostMapping(value = "/opret_sag", params = "save")
    public String createCasePost(@ModelAttribute Case currentCase) {
        System.out.println();
        access.insertAddress(currentCase.getVejnavn(),currentCase.getVejnummer(),currentCase.getPostnummer(),currentCase.getBy());
        access.insertCase(currentCase);
        return "opret_sag";
    }
}
