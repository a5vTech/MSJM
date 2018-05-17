package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@SessionAttributes({"BrugerID", "Stilling"})

public class LoginController {
    final MedarbejderCRUD medarbejderCRUD = MedarbejderCRUD.getInstance();

    /**
     * This method loads the login screen and clears the two session attributes
     * "BrugerID" and "Stilling"
     * @param model model
     * @return log_ind
     */

    @GetMapping("/log_ind")
    public String login(Model model) {
        model.addAttribute("BrugerID","");
        model.addAttribute("Stilling","");
        return "log_ind";
    }

    /**
     * This method takes the username and password entered by the user
     * and checks it against the stored data  in the database.
     * It also sets the Session attributes "BrugerID" and "Stilling"
     * @param model Model to add the slessionAttributes
     * @param username
     * @param password
     * @return redirect based on JobPosition
     */
    @PostMapping("/log_ind")
    public String loginPost(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        String stilling = medarbejderCRUD.checkLogin(username, password);
        model.addAttribute("BrugerID", username);
        switch (stilling) {
            case "Leder":
                model.addAttribute("Stilling", "Leder");
                return "redirect:/menu";
            case "Konduktør":
                model.addAttribute("Stilling", "Konduktør");
                return "redirect:/menu";
            case "Svend":
                model.addAttribute("Stilling", "Svend");
                return "redirect:/igangvaerende_sager";
            default:
                return "redirect:/log_ind";
        }

    }


}
