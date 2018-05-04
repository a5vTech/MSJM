package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class Afslut_sagController {
    final AccessDB access = AccessDB.getInstance();

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


    @PostMapping(value = "/afslut_sag", params = "registrer_timer")
    public String registrer_arbejdstimerPost(Sag nuvaerendeSag, @RequestParam("svend_id") String svend_id, @RequestParam("end_case_id") String sags_id, @RequestParam("arbejdstimer") String timer) {

        access.timer(svend_id, sags_id, timer);
        System.out.println("DEBUG det virker2");

        return "redirect:/afslut_sag/" + sags_id;
    }



}
