package dk.eviggladegulve.sagsstyring;

import java.time.LocalDate;
import java.util.ArrayList;

public class KalenderService {
    final AccessDB access = AccessDB.getInstance();
    final LocalDate date = LocalDate.now();
    ArrayList<Medarbejder> medarbejderListe;

    public KalenderService() {
        access.createConnection();
        medarbejderListe = access.executeStamementEmployeeList("SELECT * FROM svend");
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

 /*   public void createViewData() {

        for (int i = 0; i < medarbejderListe.size(); i++) {
            LocalDate startDate = firstDay();

            //System.out.println("START DATE......." + startDate.toString());
            for (int j = 0; j < 14; j++) {

                access.createConnection();
                ArrayList<Sag> sager = access.executeStamementCases("SELECT *" +
                        " FROM svend" +
                        " JOIN svend_sager ON (svend.svend_id = svend_sager.svend_id)" +
                        " JOIN sag ON (sag.sags_id = svend_sager.sags_id)" +
                        String.format(" WHERE svend.svend_id=%d AND ('%s' BETWEEN start_dato AND slut_dato)", medarbejderListe.get(i).getSvend_id(), startDate.toString()));
                medarbejderListe.get(i).getSager().add(sager);
                startDate = startDate.plusDays(1);
                System.out.println(startDate.toString());

            }
//            }
//            access.createConnection();


        }

    } */



}
