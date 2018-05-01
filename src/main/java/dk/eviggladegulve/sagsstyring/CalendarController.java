package dk.eviggladegulve.sagsstyring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

@Controller
public class CalendarController {


    final AccessDB access = AccessDB.getInstance();

    @GetMapping("/")

    public String calendar(Model model) {
        LocalDate date = LocalDate.now();
        CalendarService calendarService = new CalendarService();
        //calendarService.createViewData();
        ArrayList<Employee> empList = calendarService.getEmployeeList();
        access.executeStamementCases(calendarService.firstDay(),empList);

        calendarService.dateMangement();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        model.addAttribute("week1", date.get(weekFields.weekOfWeekBasedYear()));
        model.addAttribute("week2", date.get(weekFields.weekOfWeekBasedYear())+1);
        model.addAttribute("empList", empList);


        return "kalender";
    }
}
