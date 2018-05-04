package dk.eviggladegulve.sagsstyring;

import java.time.LocalDate;
import java.util.ArrayList;

public class KalenderService {
    final AccessDB access = AccessDB.getInstance();
    final LocalDate date = LocalDate.now();
    ArrayList<Medarbejder> medarbejderListe;

    public KalenderService() {
        medarbejderListe = access.executeStamementEmployeeList();
    }

    public void dateMangement() {

        // System.out.println(c.get(Calendar.WEEK_OF_YEAR));

    }

    public ArrayList<Medarbejder> getMedarbejderListe() {
        return medarbejderListe;
    }

    public void setMedarbejderListe(ArrayList<Medarbejder> medarbejderListe) {
        this.medarbejderListe = medarbejderListe;
    }




    public LocalDate firstDay() {
        return date.plusDays(1).minusDays(date.getDayOfWeek().getValue());
    }





}
