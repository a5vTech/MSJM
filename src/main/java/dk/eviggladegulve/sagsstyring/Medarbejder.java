package dk.eviggladegulve.sagsstyring;

import java.util.ArrayList;

public class Medarbejder implements Comparable<Medarbejder> {


    /**
     * Fields
     */

    private int medarbejder_id;
    private String fornavn;
    private String efternavn;
    private String email;
    private String telefonnummer;
    private String kodeord;
    private String stilling;
    private ArrayList<ArrayList<Sag>> sager = new ArrayList<>();


    /**
     * Constructors
     */

    public Medarbejder() {
    }

    public Medarbejder(int medarbejder_id, String fornavn, String efternavn, String stilling) {
        this.medarbejder_id = medarbejder_id;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.stilling = stilling;
    }

    public Medarbejder(String fornavn, String efternavn, String email, String telefonnummer, String kodeord) {
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.email = email;
        this.telefonnummer = telefonnummer;
        this.kodeord = kodeord;
    }

    public Medarbejder(int medarbejder_id, String fornavn, String efternavn, String email, String telefonnummer, String kodeord) {
        this.medarbejder_id = medarbejder_id;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.email = email;
        this.telefonnummer = telefonnummer;
        this.kodeord = kodeord;
    }

    public Medarbejder(int medarbejder_id, String fornavn, String efternavn, String email, String telefonnummer, String kodeord, String stilling) {
        this.medarbejder_id = medarbejder_id;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.email = email;
        this.telefonnummer = telefonnummer;
        this.kodeord = kodeord;
        this.stilling = stilling;
    }

    /**
     * Gets employees first name
     * @return fornavn
     */
    public String getFornavn() {
        return fornavn;
    }
    /**
     * sets employees first name
     */
    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    /**
     * Gets employees last name
     * @return efternavn
     */
    public String getEfternavn() {
        return efternavn;
    }

    public void setEfternavn(String efternavn) {
        this.efternavn = efternavn;
    }

    /**
     * Gets employees email
     * @return email
     */

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets employees phone number
     * @return telefonnummer
     */

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }
    /**
     * Gets employees list of days with cases
     * @return ArrayList<Sag>
     */
    public ArrayList<ArrayList<Sag>> getSager() {
        return sager;
    }

    public void setSager(ArrayList<ArrayList<Sag>> sager) {
        this.sager = sager;
    }
    /**
     * Gets employees id
     * @return id
     */
    public int getMedarbejder_id() {
        return medarbejder_id;
    }

    public void setMedarbejder_id(int medarbejder_id) {
        this.medarbejder_id = medarbejder_id;
    }

    /**
     * Gets employees password
     * @return kodeord
     */
    public String getKodeord() {
        return kodeord;
    }

    public void setKodeord(String kodeord) {
        this.kodeord = kodeord;
    }
    /**
     * Gets employees jobPosition
     * @return stilling
     */
    public String getStilling() {
        return stilling;
    }

    public void setStilling(String stilling) {
        this.stilling = stilling;
    }
    /**
     * Gets employees total case count within the range of days with cases
     * @return fornavn
     */
    public int antalSager() {
        int antalSager = 0;
        for (int i = 0; i < sager.size(); i++) {
            for (int j = 0; j < sager.get(i).size(); j++) {
                antalSager += 1;
            }
        }
        return antalSager;
    }
    /**
     * This method is used forsorting
     *
     */
    @Override
    public int compareTo(Medarbejder o) {
        return antalSager() - o.antalSager();
    }
}
