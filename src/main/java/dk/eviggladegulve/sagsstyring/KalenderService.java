package dk.eviggladegulve.sagsstyring;

import java.time.LocalDate;
import java.util.ArrayList;

public class KalenderService {
    /**
     * Fields
     */
    final MedarbejderCRUD medarbejderCRUD = MedarbejderCRUD.getInstance();
    LocalDate date = null;
    ArrayList<Medarbejder> medarbejderListe;

    /**
     * Constructor
     *
     * @param date
     */
    public KalenderService(LocalDate date) {
        this.date = date;
        medarbejderListe = medarbejderCRUD.executeStamementEmployeeList();

    }

    /**
     * Getters and Setters
     *
     * @return
     */
    public ArrayList<Medarbejder> getMedarbejderListe() {
        return medarbejderListe;
    }

    public void setMedarbejderListe(ArrayList<Medarbejder> medarbejderListe) {
        this.medarbejderListe = medarbejderListe;
    }


    public LocalDate firstDay() {
        return date.plusDays(1).minusDays(date.getDayOfWeek().getValue());
    }

    public String getMonth(int month) {

        switch (month) {
            case 1: {
                return "januar";
            }
            case 2: {
                return "februar";
            }
            case 3: {
                return "marts";
            }
            case 4: {
                return "april";
            }
            case 5: {
                return "maj";
            }
            case 6: {
                return "juni";
            }
            case 7: {
                return "juli";
            }
            case 8: {
                return "august";
            }
            case 9: {
                return "september";
            }
            case 10: {
                return "oktober";
            }
            case 11: {
                return "november";
            }
            case 12: {
                return "december";
            }
            default: {
                return "";
            }
        }
    }
}
