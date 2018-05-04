package dk.eviggladegulve.sagsstyring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class rediger_sagController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping(value = "/rediger_sag/{id}")
    public String aktuelSag(@PathVariable("id") int id, Model model) {
        access.createConnection();
        model.addAttribute("case", Sag.findCaseById(id));


        return "rediger_sag";
    }







}