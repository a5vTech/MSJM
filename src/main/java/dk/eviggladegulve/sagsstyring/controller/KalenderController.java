package dk.eviggladegulve.sagsstyring.controller;

import dk.eviggladegulve.sagsstyring.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;

@Controller

@SessionAttributes({"BrugerID", "Stilling", "Date"})
public class KalenderController {

    final SagCRUD sagCRUD = SagCRUD.getInstance();


    /***
     * This method loads the calendar and takes to parameters a Model and a Date, which it receives from the session attribute "Date"
     * The method  adds all the relevant calendar data to the view
     * @param model Model
     * @param date LocalDate
     * @return kalender
     */
    @GetMapping("/kalender")
    public String calendar(Model model, @ModelAttribute("Date") LocalDate date) {
        KalenderService calendarService = new KalenderService(date);
        ArrayList<Medarbejder> empList = calendarService.getMedarbejderListe();


        sagCRUD.executeStamementCases(calendarService.firstDay(), empList);
        Collections.sort(empList,Collections.reverseOrder());
        //WeekFields is a java class that can find the current week of a localdate
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        model.addAttribute("week1", date.get(weekFields.weekOfWeekBasedYear()));
        model.addAttribute("week2", date.get(weekFields.weekOfWeekBasedYear()) + 1);


        model.addAttribute("empList", empList);
        date = calendarService.firstDay(); //Force date to be the first of the week
        model.addAttribute("startDateMonth", calendarService.getMonth(date.getMonthValue()));
        model.addAttribute("startDateDay", date.toString().substring(8, 10));
        model.addAttribute("startDateYear", date.toString().substring(0, 4));

        model.addAttribute("endDateMonth", calendarService.getMonth(date.plusDays(13).getMonthValue()));
        model.addAttribute("endDateDay", date.plusDays(13).toString().substring(8, 10));
        model.addAttribute("endDateYear", date.plusDays(13).toString().substring(0, 4));
        for(int i = 0; i < 14; i++){
            model.addAttribute("day"+(i+1), date.plusDays(i).toString().substring(8, 10) + date.plusDays(i).toString().substring(4, 7));
    }






        return "kalender";
    }

    /**
     * This method makes the calendar go to the next week
     * And sends the new date to the Calendars getmapping
 @param model Model
     * @param date LocalDate
     * @return redirect:/kalender
     */
    @PostMapping(value = "/kalender", params = "nextWeekBtn")
    public String calendarNextWeek(Model model, @ModelAttribute("Date") LocalDate date) {
        model.addAttribute("Date", date.plusDays(7));
        return "redirect:/kalender";
    }
    /**
     * This method makes the calendar go to the previous week
     * And sends the new date to the Calendars getmapping
     * @param model Model
     * @param date LocalDate
     * @return redirect:/kalender
     */
    @PostMapping(value = "/kalender", params = "previousWeekBtn")
    public String calendarPreviousWeek(Model model, @ModelAttribute("Date") LocalDate date) {
        model.addAttribute("Date", date.minusDays(7));
        return "redirect:/kalender";
    }

    /**
     * This method makes the calendar go to a specific date
     * And sends the new date to the session attribute "Date"
     * @param model Model
     * @param date LocalDate
     * @return redirect:/kalender
     */

    @PostMapping(value = "/kalender", params = "goToDate")
    public String calendarGoToDate(Model model, @ModelAttribute("Date") LocalDate date, @RequestParam("dateFromGoTo") String dateFromGoTo) {
        System.out.println(dateFromGoTo);
        if (dateFromGoTo.equals("")) {
            return "redirect:/kalender";
        }
        model.addAttribute("Date", LocalDate.parse(dateFromGoTo));

        return "redirect:/kalender";
    }

}