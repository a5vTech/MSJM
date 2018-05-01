package dk.eviggladegulve.sagsstyring;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarService {
    final AccessDB access = AccessDB.getInstance();
    final LocalDate date = LocalDate.now();
    ArrayList<Employee> employeeList;

    public CalendarService() {
        access.createConnection();
        employeeList = access.executeStamementEmployeeList("SELECT * FROM svend");
    }

    public void dateMangement() {

        // System.out.println(c.get(Calendar.WEEK_OF_YEAR));

    }

    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(ArrayList<Employee> employeeList) {
        this.employeeList = employeeList;
    }




    public LocalDate firstDay() {
        return date.plusDays(1).minusDays(date.getDayOfWeek().getValue());
    }

 /*   public void createViewData() {

        for (int i = 0; i < employeeList.size(); i++) {
            LocalDate startDate = firstDay();

            //System.out.println("START DATE......." + startDate.toString());
            for (int j = 0; j < 14; j++) {

                access.createConnection();
                ArrayList<Case> sager = access.executeStamementCases("SELECT *" +
                        " FROM svend" +
                        " JOIN svend_sager ON (svend.svend_id = svend_sager.svend_id)" +
                        " JOIN sag ON (sag.sags_id = svend_sager.sags_id)" +
                        String.format(" WHERE svend.svend_id=%d AND ('%s' BETWEEN start_dato AND slut_dato)", employeeList.get(i).getSvend_id(), startDate.toString()));
                employeeList.get(i).getSager().add(sager);
                startDate = startDate.plusDays(1);
                System.out.println(startDate.toString());

            }
//            }
//            access.createConnection();


        }

    } */



}
