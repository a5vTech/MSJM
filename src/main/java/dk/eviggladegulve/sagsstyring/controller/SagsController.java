package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
@SessionAttributes({"BrugerID", "Stilling"})
public class SagsController {
    final SagCRUD sagCRUD = SagCRUD.getInstance();
    final MedarbejderCRUD medarbejderCRUD = MedarbejderCRUD.getInstance();

    //OPRET_SAG

    /**
     * This method loads the create case view and takes model as a parameter
     * The method sends an empty case object to the view as well as the generated
     * id, which the new case, will receive
     *
     * @param model Model
     * @return opret_sag
     */
    @GetMapping("/opret_sag")
    public String createCase(Model model) {
        model.addAttribute("Sag", new Sag());
        model.addAttribute("sags_id", sagCRUD.getLastCaseId() + 1);
        return "opret_sag";
    }

    /**
     * This method takes the current case as a parameter of the case that has been written and saves the address
     * and the rest of the case to the database.
     *
     * @param nuvaerendeSag Sag
     * @return tilknyt_medarbejder_opret_sag+id
     */
    @PostMapping(value = "/opret_sag", params = "save")
    public String createCasePost(@ModelAttribute Sag nuvaerendeSag) {

        sagCRUD.insertAddress(nuvaerendeSag.getVejnavn(), nuvaerendeSag.getVejnummer(), nuvaerendeSag.getPostnummer(), nuvaerendeSag.getBy());
        sagCRUD.createCase(nuvaerendeSag);
        return "redirect:/tilknyt_medarbejder_opret_sag/" + sagCRUD.getLastCaseId();
    }

    //TILKNYT_MEDARBEJDER

    /**
     * This method takes the id and model as a parameter and loads all the employees into the view and removes the
     * employee from the list when he/she is added to the case
     *
     * @param id    INT
     * @param model Model
     * @return tilknyt_medarbejder_opret_sag
     */
    @GetMapping(value = "/tilknyt_medarbejder_opret_sag/{id}")
    public String assignEmployeeCreate(@PathVariable("id") int id, Model model) {
        ArrayList<Medarbejder> emplist = medarbejderCRUD.executeStamementEmployeeList();
        ArrayList<Medarbejder> activeList = medarbejderCRUD.showActiveEmployee(id);
        for (Medarbejder m : activeList) {
            for (int i = 0; i < emplist.size(); i++) {
                if (m.getMedarbejder_id() == emplist.get(i).getMedarbejder_id()) {
                    emplist.remove(i);
                }
            }
        }
        model.addAttribute("medarbejderListe", emplist);
        model.addAttribute("aktiveMedarbejdere", medarbejderCRUD.showActiveEmployee(id));
        Sag sag = sagCRUD.findCaseById(id, 1);
        model.addAttribute("Sag", sag);
        return "tilknyt_medarbejder_opret_sag";
    }

    /**
     * This method takes the employee id, case id and the current case as a parameter, and adds the chosen employees
     * for the case and saves them in the database with the rest of the case info, with the correct ID's
     *
     * @param nuvaerendeSag  Sag
     * @param medarbejder_id INT
     * @param sag_id         INT
     * @return tilknyt_medarbejder_opret_sag+id
     */
    @PostMapping(value = "/tilknyt_medarbejder_opret_sag", params = "save")
    public String assignEmployeeCreatePost(@ModelAttribute Sag nuvaerendeSag, @RequestParam("tilknyt_Medarbejder_id") String medarbejder_id, @RequestParam("sag_id") int sag_id) {
        nuvaerendeSag.setSags_id(sag_id);
        String[] idListe = medarbejder_id.split(",");
        for (int i = 0; i < idListe.length; i++) {
            sagCRUD.assignToCase(sag_id, idListe[i]);
        }
        return "redirect:/tilknyt_medarbejder_opret_sag/" + nuvaerendeSag.getSags_id();
    }


    @PostMapping(value = "/tilknyt_medarbejder_opret_sag", params = "opret")
    public String assignNoEmployeesEditCreatePost() {
        return "redirect:/menu";
    }

    /**
     * This method takes the employee id, case id and the current case as a parameter and removes the chosen employee
     * for the case and removes them from the case. The method uses the ID's to remove the correct employee
     * from the correct case
     *
     * @param nuvaerendeSag  Sag
     * @param medarbejder_id INT
     * @param sag_id         INT
     * @return tilknyt_medarbejder_opret_sag/{sag_id}
     */
    @PostMapping(value = "/tilknyt_medarbejder_opret_sag", params = "remove")
    public String removeEmployeesCreatePost(@ModelAttribute Sag nuvaerendeSag, @RequestParam("fjern_Medarbejder_id") String medarbejder_id, @RequestParam("sag_id") int sag_id) {
        nuvaerendeSag.setSags_id(sag_id);
        String[] idListe = medarbejder_id.split(",");
        for (int i = 0; i < idListe.length; i++) {
            sagCRUD.removeFromCase(sag_id, idListe[i]);
        }
        return "redirect:/tilknyt_medarbejder_opret_sag/" + nuvaerendeSag.getSags_id();
    }

    //SAGER_TIL_REDIGERING

    /**
     * This method takes model as an parameter and adds all the active cases and loads them into the view
     *
     * @param model Model
     * @return sager_til_redigering
     */
    @GetMapping("/sager_til_redigering")
    public String sager_til_redigering(Model model) {
        ArrayList<Sag> sager = sagCRUD.getAllActiveCases();
        model.addAttribute("caseList", sager);
        model.addAttribute("header", "Rediger sag");
        return "sager_til_redigering";
    }

    /**
     * This method takes the case id chosen from the view as a parameter and deletes the appropriate case
     * from the list and sets it as inactive in the database.
     *
     * @param sags_id
     * @retur sager_til_redigering
     */
    @PostMapping(value = "/sager_til_redigering", params = "slet_sagBtn")
    public String sager_til_redigeringSletPost(@RequestParam("sags_id") int sags_id) {
        sagCRUD.deleteCase(sags_id);
        return "redirect:/sager_til_redigering";
    }

    //REDIGER_SAG

    /**
     * This method takes the chosen case from the cases for editing view and parses it as a parameter
     * the case get shown in the view in the edit case page. The method also takes the model as a parameter to
     * add the case attribute for editing.
     *
     * @param id    INT
     * @param model Model
     * @return rediger_sag
     */
    @GetMapping(value = "/rediger_sag/{id}")
    public String redigerSag(@PathVariable("id") int id, Model model) {
        Sag sag = sagCRUD.findCaseById(id, 1);
        if(sag.getSags_id() == 0){
        sag = sagCRUD.findCaseById(id,0);
        }
        model.addAttribute("Sag", sag);

        return "rediger_sag";
    }

    /**
     * This method takes the current case, the address- and the case id as a parameter. the method then update
     * the database with the newly written values
     *
     * @param nuvaerendeSag Sag
     * @param sag_id        INT
     * @param adresse_id    INT
     * @return /menu
     */
    @PostMapping(value = "/rediger_sag", params = "save")
    public String redigerSagPost(@ModelAttribute Sag nuvaerendeSag, @RequestParam("sag_id") int sag_id, @RequestParam("adresse_id") int adresse_id) {
        nuvaerendeSag.setSags_id(sag_id);
        nuvaerendeSag.setAdresse_id(adresse_id);
        sagCRUD.editCase(nuvaerendeSag);
        sagCRUD.editAddress(nuvaerendeSag);
        return "redirect:/menu";
    }

    //TILKNYT_MEDARBEJDER_REDIGER_SAG

    /**
     * This method takes the id and model as a parameter and loads all the employees into the view whether
     * they are on the case already or not added to the case yet and adds and removes employee's from the case,
     * whatever the user chooses.
     *
     * @param id    INT
     * @param model Model
     * @return tilknyt_medarbejder_rediger_sag
     */
    @GetMapping(value = "/tilknyt_medarbejder_rediger_sag/{id}")
    public String assignEmployee(@PathVariable("id") int id, Model model) {
        ArrayList<Medarbejder> emplist = medarbejderCRUD.executeStamementEmployeeList();
        ArrayList<Medarbejder> activeList = medarbejderCRUD.showActiveEmployee(id);
        for (Medarbejder m : activeList) {
            for (int i = 0; i < emplist.size(); i++) {
                if (m.getMedarbejder_id() == emplist.get(i).getMedarbejder_id()) {
                    emplist.remove(i);
                }
            }
        }
        model.addAttribute("medarbejderListe", emplist);
        model.addAttribute("aktiveMedarbejdere", medarbejderCRUD.showActiveEmployee(id));
        Sag sag = sagCRUD.findCaseById(id, 1);
        model.addAttribute("Sag", sag);
        return "tilknyt_medarbejder_rediger_sag";
    }

    /**
     * This method takes the employee id, case id and the current case as a parameter, and adds the chosen employees
     * for the case and saves them in the database with the rest of the case info, with the correct ID's
     *
     * @param nuvaerendeSag  Sag
     * @param medarbejder_id INT
     * @param sag_id         INT
     * @return tilknyt_medarbejder_rediger_sag+id
     */
    @PostMapping(value = "/tilknyt_medarbejder_rediger_sag", params = "save")
    public String assignEmployeePost(@ModelAttribute Sag nuvaerendeSag, @RequestParam("tilknyt_Medarbejder_id") String medarbejder_id, @RequestParam("sag_id") int sag_id) {
        nuvaerendeSag.setSags_id(sag_id);
        String[] idListe = medarbejder_id.split(",");
        for (int i = 0; i < idListe.length; i++) {
            sagCRUD.assignToCase(sag_id, idListe[i]);
        }
        return "redirect:/tilknyt_medarbejder_rediger_sag/" + nuvaerendeSag.getSags_id();
    }

    @PostMapping(value = "/tilknyt_medarbejder_rediger_sag", params = "edit")
    public String assignNoEmployeesEditPost() {
        return "redirect:/menu";
    }

    /**
     * This method takes the employee id, case id and the current case as a parameter and removes the chosen employee
     * for the case and removes them from the case. The method uses the ID's to remove the correct employee
     * from the correct case
     *
     * @param nuvaerendeSag  Sag
     * @param medarbejder_id INT
     * @param sag_id         INT
     * @return tilknyt_medarbejder_rediger_sag+id
     */
    @PostMapping(value = "/tilknyt_medarbejder_rediger_sag", params = "remove")
    public String removeEmployeesPost(@ModelAttribute Sag nuvaerendeSag, @RequestParam("fjern_Medarbejder_id") String medarbejder_id, @RequestParam("sag_id") int sag_id) {
        nuvaerendeSag.setSags_id(sag_id);
        String[] idListe = medarbejder_id.split(",");
        for (int i = 0; i < idListe.length; i++) {
            sagCRUD.removeFromCase(sag_id, idListe[i]);
        }
        return "redirect:/tilknyt_medarbejder_rediger_sag/" + nuvaerendeSag.getSags_id();
    }

    //SAGER_TIL_AFSLUTNING

    /**
     * This method takes model as an parameter and adds all the active cases as an attribute and loads
     * them into the view with the case list as an attribute
     *
     * @param model Model
     * @return sager_til_afslutning
     */
    @GetMapping("/sager_til_afslutning")
    public String sagerTilAfslutning(Model model) {
        ArrayList<Sag> sager = sagCRUD.getAllActiveCases();
        model.addAttribute("caseList", sager);
        return "sager_til_afslutning";
    }


    @PostMapping("/sager_til_afslutning")
    public String sagerTilAfslutningPost(@RequestParam int id) {

        return "redirect:/afslut_sag/";
    }

    //AFSLUT_SAG

    /**
     * This method takes id, model, and the user ID as a parameter and gets the chosen case from the active case list.
     * The method also take the registered hours from the employee.
     *
     * @param id       INT
     * @param model    Model
     * @param brugerid INT
     * @return afslut_sag
     */
    @GetMapping("/afslut_sag/{id}")
    public String afslut_sag(@PathVariable("id") int id, Model model, @ModelAttribute("BrugerID") int brugerid) {
        ArrayList<Sag> sager = sagCRUD.getAllActiveCases();
        model.addAttribute("Sag", sagCRUD.findCaseById(id, 1));
        model.addAttribute("sags_id", id);
        model.addAttribute("registreredeTimer", sagCRUD.registreredeTimerMedarbejder(id, brugerid));
        return "afslut_sag";
    }

    /**
     * This method takes the current case, agreed with, case id and ekstra work as a parameter and adds the ekstra work
     * and agreed with fields to the database, and ends the case. This is done from the manager login
     *
     * @param nuvaerendeSag  Sag
     * @param aftalt_med     String
     * @param sags_id        INT
     * @param ekstra_arbejde String
     * @return menu
     */
    @PostMapping(value = "/afslut_sag", params = "afslut_Sag_Leder")
    public String afslut_sagLederPost(Sag nuvaerendeSag, @RequestParam("aftalt_med") String aftalt_med, @RequestParam int sags_id, @RequestParam("ekstra_arbejde") String ekstra_arbejde) {
        sagCRUD.end_case(sags_id);
        sagCRUD.add_extra_work(ekstra_arbejde, aftalt_med, sags_id);
        return "redirect:/menu";
    }

    /**
     * This method takes the current case, agreed with, case id and ekstra work as a parameter and adds the ekstra work
     * and agreed with fields to the database, and ends the case. This is done from the employee login
     *
     * @param nuvaerendeSag  Sag
     * @param aftalt_med     String
     * @param sags_id        INT
     * @param ekstra_arbejde String
     * @return igangvaerende_sager
     */
    @PostMapping(value = "/afslut_sag", params = "afslut_Sag_Svend")
    public String afslut_sagSvendPost(Sag nuvaerendeSag, @RequestParam("aftalt_med") String aftalt_med, @RequestParam int sags_id, @RequestParam("ekstra_arbejde") String ekstra_arbejde) {
        sagCRUD.end_case(sags_id);
        sagCRUD.add_extra_work(ekstra_arbejde, aftalt_med, sags_id);
        return "redirect:/igangvaerende_sager";
    }

    /**
     * This method takes the current case, agreed with, case id, extra work, employee id and the worked
     * hours as a parameter and adds all of these to the database now that the case has been ended
     *
     * @param nuvaerendeSag  Sag
     * @param aftalt_med     String
     * @param sags_id        INT
     * @param ekstra_arbejde String
     * @param medarbejder_id INT
     * @param timer          INT
     * @return afslut_sag/{id}
     */
    @PostMapping(value = "/afslut_sag", params = "registrer_timer_Btn")
    public String registrer_arbejdstimerPost(Sag nuvaerendeSag, @RequestParam("aftalt_med") String aftalt_med, @RequestParam("sags_id") int sags_id, @RequestParam("ekstra_arbejde") String ekstra_arbejde, @RequestParam("medarbejder_id") int medarbejder_id, @RequestParam("timer") String timer) {
        sagCRUD.timer(medarbejder_id, sags_id, timer);
        sagCRUD.add_extra_work(ekstra_arbejde, aftalt_med, sags_id);
        return "redirect:/afslut_sag/" + sags_id;
    }

    //IGANGVAERENDE_SAGER

    /**
     * This method takes model and employee id as a parameter and shows all the cases of the employee who
     * has logged in to the page
     *
     * @param model          Model
     * @param medarbejder_id INT
     * @return igangvaerende_sager
     */
    @GetMapping("/igangvaerende_sager")
    public String igangvaerendeSager(Model model, @ModelAttribute("BrugerID") int medarbejder_id) {
        ArrayList<Sag> sager = sagCRUD.getAllActiveCasesMedarbejder(medarbejder_id);
        model.addAttribute("Sager", sager);
        return "igangvaerende_sager";
    }

    @PostMapping("/igangvaerende_sager")
    public String igangvaerendeSagerPost() {
        return "igangvaerende_sager";
    }

    //VIS_AKTUEL_SAG

    /**
     * This method takes the selected case from the logged in employee by using the id and model parameter,
     * and allows the employee to view the selected case
     *
     * @param id    INT
     * @param model Model
     * @return vis_aktuel_sag
     */
    @GetMapping(value = "/aktuel_sag/{id}")
    public String aktuelSag(@PathVariable("id") int id, Model model) {
        model.addAttribute("sags_id", id);
        model.addAttribute("Sag", sagCRUD.findCaseById(id, 0));
        model.addAttribute("timer", sagCRUD.registreredeTimerSag(id));
        return "vis_aktuel_sag";
    }

    //AFSLUTTEDE_SAGER

    /**
     * This method adds all the ended case as an attribute, with model as a parameter, and sends the cases to the view
     *
     * @param model Model
     * @return afslutted_sager
     */
    @GetMapping("/afsluttede_sager")
    public String afsluttedeSager(Model model) {
        model.addAttribute("sagerListe", sagCRUD.getAllEndedCases());
        return "afsluttede_sager";
    }

    //IKKE_AFSLUTTEDE_SAGER

    /**
     * This method adds all the active cases as an attribute, with model as a parameter, and sends the cases to the view
     *
     * @param model Model
     * @return ikke_afsluttede_sager
     */
    @GetMapping("/ikke_afsluttede_sager")
    public String sager(Model model) {
        ArrayList<Sag> sager = sagCRUD.getAllActiveCases();
        model.addAttribute("caseList", sager);
        return "ikke_afsluttede_sager";
    }

    @PostMapping("/ikke_afsluttede_sager")
    public String sagerPost(@RequestParam int id) {
        return "vis_aktuel_sag/" + id;
    }

    //VIS AKTIVE SAGER

    /**
     * This method adds all the active cases as an attribute, with model as a parameter, and sends the cases to the view
     *
     * @param model Model
     * @return sager_til_redigering
     */
    @GetMapping("/aktive_sager")
    public String aktiveSager(Model model) {
        ArrayList<Sag> sager = sagCRUD.getAllActiveCases();
        model.addAttribute("caseList", sager);
        model.addAttribute("header", "Aktive sager");
        return "sager_til_redigering";
    }
}