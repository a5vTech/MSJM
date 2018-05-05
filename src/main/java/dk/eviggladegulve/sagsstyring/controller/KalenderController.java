package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

@Controller
public class KalenderController {


    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/kalender")

    public String calendar(Model model) {
        LocalDate date = LocalDate.now();
        KalenderService calendarService = new KalenderService();
        //calendarService.createViewData();
        ArrayList<Medarbejder> empList = calendarService.getMedarbejderListe();
        access.executeStamementCases(calendarService.firstDay(), empList);

        calendarService.dateMangement();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        model.addAttribute("week1", date.get(weekFields.weekOfWeekBasedYear()));
        model.addAttribute("week2", date.get(weekFields.weekOfWeekBasedYear()) + 1);
        model.addAttribute("empList", empList);

        model.addAttribute("day1", date.toString());
        model.addAttribute("day2", date.plusDays(1).toString());
        model.addAttribute("day3", date.plusDays(2).toString());
        model.addAttribute("day4", date.plusDays(3).toString());
        model.addAttribute("day5", date.plusDays(4).toString());
        model.addAttribute("day6", date.plusDays(5).toString());
        model.addAttribute("day7", date.plusDays(6).toString());
        model.addAttribute("day8", date.plusDays(7).toString());
        model.addAttribute("day9", date.plusDays(8).toString());
        model.addAttribute("day10", date.plusDays(9).toString());
        model.addAttribute("day11", date.plusDays(10).toString());
        model.addAttribute("day12", date.plusDays(11).toString());
        model.addAttribute("day13", date.plusDays(12).toString());
        model.addAttribute("day14", date.plusDays(13).toString());


        return "kalender";
    }
}
