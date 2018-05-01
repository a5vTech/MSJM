package dk.eviggladegulve.sagsstyring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmployeeController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/opret_svend")
    public String createEmployee(Model model) {
        model.addAttribute("employee",new Employee());
        return "opret_svend";
    }

    @PostMapping(value= "/opret_svend", params = "save")
    public String createEmployeePost(@ModelAttribute Employee currentEmployee) {
        access.insertEmployee(currentEmployee);
        return "opret_svend";
    }
}
