package dk.eviggladegulve.sagsstyring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
public class SagController {
    final AccessDB access = AccessDB.getInstance();


    //OPRET SAG
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


    //VIS ALLE AKTIVE SAGER


    @GetMapping("/aktive_sager")
    public String sager(Model model) {
        ArrayList<Sag> sager = access.getAllActiveCases();
        model.addAttribute("caseList", sager);
        return "sager";
    }


    @PostMapping("/aktive_sager")
    public String sagerPost(@RequestParam int id) {


        return "vis_aktuel_sag/" + id;
    }

    //AKTUEL SAG


    @GetMapping(value = "/aktuel_sag/{id}")
    public String aktuelSag(@PathVariable("id") int id, Model model) {
        access.createConnection();
        model.addAttribute("case", Sag.findCaseById(id));


        return "vis_aktuel_sag";
    }


// AFSLUT SAG

    @GetMapping("/afslut_sag/{id}")
    public String afslut_sag(@PathVariable("id") int id, Model model) {
        ArrayList<Sag> sager = access.getAllActiveCases();
        model.addAttribute("case", Sag.findCaseById(id));

        return "afslut_sag";
    }

    @PostMapping(value = "/afslut_sag", params = "end_case")
    public String afslut_sagPost(Sag nuvaerendeSag, @RequestParam String end_case_id, @RequestParam("ekstra_arbejde") String ekstra_arbejde, @RequestParam("arbejdstimer") String timer, @RequestParam("svend_id") String svend_id) {
        access.end_case(end_case_id);
        access.add_extra_work(ekstra_arbejde, end_case_id);
        access.timer(svend_id, end_case_id, timer);
        return "redirect:/kalender";
    }

    @GetMapping("/igangværende_sager")
    public String sager_under_afslutning(Model model) {
        ArrayList<Sag> sager = access.getAllActiveCases();
        model.addAttribute("caseList", sager);
        return "igangværende_sager";
    }

    @PostMapping("/igangværende_sager")
    public String sager_under_afslutningPost() {
        return "/sager_under_afslutning";
    }


    // REGISTRER ARBEJDSTIMER
    @GetMapping("/registrer_arbejdstimer_liste")
    public String registrer_arbejdstimer_liste(Model model) {
        ArrayList<Sag> sager = access.getAllActiveCases();
        model.addAttribute("caseList", sager);
        return "registrer_arbejdstimer_liste";
    }

    @GetMapping(value = "/registrer_arbejdstimer/{id}")
    public String registrer_arbejdstimer(@PathVariable("id") int id, Model model) {
        access.createConnection();
        ArrayList<Sag> sager = access.getAllActiveCases();
        for (Sag c : sager) {
            if (c.getSags_id() == id) {
                model.addAttribute("case", c);
            }
        }

        return "registrer_arbejdstimer";
    }

    @PostMapping(value = "/afslut_sag", params = "registrer_timer")
    public String registrer_arbejdstimerPost(Sag nuvaerendeSag, @RequestParam("svend_id") String svend_id, @RequestParam("end_case_id") String sags_id, @RequestParam("arbejdstimer") String timer) {

        access.timer(svend_id, sags_id, timer);
        System.out.println("DEBUG det virker2");

        return "redirect:/afslut_sag/" + sags_id;
    }

}
