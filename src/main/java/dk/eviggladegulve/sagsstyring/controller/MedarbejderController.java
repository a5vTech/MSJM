package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.AccessDB;
import dk.eviggladegulve.sagsstyring.Medarbejder;
import dk.eviggladegulve.sagsstyring.MedarbejderCRUD;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class MedarbejderController {

    final MedarbejderCRUD medarbejderCRUD = MedarbejderCRUD.getInstance();

    /**
     * This method loads the create employee view and takes a model as parameter.
     * The method sends an empty employee object to the view as well as the generated
     * id, which the employee new employee will receive
     * @param model Model
     * @return opret_medarbejder
     */
    @GetMapping("/opret_medarbejder")
    public String opretMedarbejder(Model model) {
        model.addAttribute("medarbejder", new Medarbejder());
        model.addAttribute("medarbejder_id", medarbejderCRUD.getLastEmployeeId() + 1);
        return "opret_medarbejder";
    }

    /**
     * This method saves the employee from the view
     * @param nuvaerendeMedarbejder Medarbejder
     * @return menu
     */
    @PostMapping(value = "/opret_medarbejder", params = "save")
    public String opretMedarbejderPost(@ModelAttribute Medarbejder nuvaerendeMedarbejder) {
        medarbejderCRUD.insertEmployee(nuvaerendeMedarbejder);
        return "redirect:/menu";
    }


    /**
     * This method loads the edit employee view and takes two parameters an id and a model.
     * The method finds the employee from the id and adds it to the view for editing
     * @param id INT
     * @param model Model
     * @return rediger_medarbejder
     */

    @GetMapping(value = "/rediger_medarbejder/{id}")
    public String redigerMedarbejder(@PathVariable("id") int id, Model model) {
        ArrayList<Medarbejder> medarbejderListe = medarbejderCRUD.executeStamementEmployeeList();
        for (Medarbejder medarbejder : medarbejderListe) {
            if (medarbejder.getMedarbejder_id() == id) {
                model.addAttribute("Medarbejder", medarbejder);
            }
        }
        return "rediger_medarbejder";
    }

    /** This method saves the edited employee object. It takes two parameters.
     *  The edited employee object and the employee id.
     * @param nuvaerendeMedarbejder Medarbejder
     * @param medarbejder_id INT
     * @return menu
     */

    @PostMapping(value = "/rediger_medarbejder", params = "save")
    public String redigerMedarbejderPost(@ModelAttribute Medarbejder nuvaerendeMedarbejder, @RequestParam("medarbejder_id") int medarbejder_id) {
        nuvaerendeMedarbejder.setMedarbejder_id(medarbejder_id);
        medarbejderCRUD.editEmployee(nuvaerendeMedarbejder);
        return "redirect:/menu";
    }

    /**
     * This method loads the employees for editing  view. It takes a model as parameter
     * and it adds a list of all employees to the view
     * @param model Model
     * @return medarbejdere_til_redigering
     * @throws NullPointerException if list is null
     */

    @GetMapping("/medarbejdere_til_redigering")
    public String redigerMedarbejder(Model model) throws NullPointerException {
        ArrayList<Medarbejder> medarbejderListe = medarbejderCRUD.executeStamementEmployeeList();
        model.addAttribute("medarbejderListe", medarbejderListe);
        return "medarbejdere_til_redigering";
    }

    /**
     * This method deletes an employee and takes one parameter medarbejder_id
     * @param medarbejder_id INT
     * @return menu
     */
    @PostMapping(value = "/medarbejdere_til_redigering")
    public String medarbejder_til_redigeringSletPost(@RequestParam("medarbejder_id") int medarbejder_id) {
        medarbejderCRUD.deleteEmployee(medarbejder_id);
        return "redirect:/menu";
    }


}
