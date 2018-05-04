package dk.eviggladegulve.sagsstyring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class opret_medarbejderController {

    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/opret_medarbejder")
    public String createEmployee(Model model) {
        model.addAttribute("employee",new Medarbejder());
        return "opret_medarbejder";
    }

    @PostMapping(value= "/opret_medarbejder", params = "save")
    public String createEmployeePost(@ModelAttribute Medarbejder nuvaerendeMedarbejder) {
        access.insertEmployee(nuvaerendeMedarbejder);
        return "opret_medarbejder";
    }

}
