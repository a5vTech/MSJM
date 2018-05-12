package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@SessionAttributes({"BrugerID","Stilling"})
public class Afslut_sagController {
    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/afslut_sag/{id}")
    public String afslut_sag(@PathVariable("id") int id, Model model, @ModelAttribute("BrugerID") int brugerid) {
        ArrayList<Sag> sager = access.getAllActiveCases();
        model.addAttribute("Sag", Sag.findCaseById(id));
        model.addAttribute("sags_id", id);
        model.addAttribute("registreredeTimer", access.registreredeTimer(id, brugerid));
        return "afslut_sag";
    }

    @PostMapping(value = "/afslut_sag", params = "afslut_Sag_Leder")
    public String afslut_sagLederPost(Sag nuvaerendeSag, @RequestParam int sags_id, @RequestParam("ekstra_arbejde") String ekstra_arbejde) {
        System.out.println(sags_id);
        access.end_case(sags_id);
        access.add_extra_work(ekstra_arbejde, sags_id);
        return "redirect:/sager_til_afslutning";
    }


    @PostMapping(value = "/afslut_sag", params = "afslut_Sag_Svend")
    public String afslut_sagSvendPost(Sag nuvaerendeSag, @RequestParam int sags_id, @RequestParam("ekstra_arbejde") String ekstra_arbejde) {
        access.end_case(sags_id);
        access.add_extra_work(ekstra_arbejde, sags_id);
        return "redirect:/igangvaerende_sager";
    }


    @PostMapping(value = "/afslut_sag", params = "registrer_timer_Btn")
    public String registrer_arbejdstimerPost(Sag nuvaerendeSag, @RequestParam("sags_id") int sags_id, @RequestParam("medarbejder_id") int medarbejder_id, @RequestParam("timer") String timer) {

        access.timer(medarbejder_id, sags_id, timer);

        return "redirect:/afslut_sag/" + sags_id;
    }



}
