package dk.eviggladegulve.sagsstyring;

import java.util.ArrayList;

public class Medarbejder {
    private int medarbejder_id;
    private String fornavn;
    private String efternavn;
    private String email;
    private int telefonnummer;
    private String kodeord;
    private String stilling;
    private ArrayList<ArrayList<Sag>> sager = new ArrayList<>();

    // Constructors


    public Medarbejder() {
    }

    public Medarbejder(String fornavn, String efternavn, String email, int telefonnummer, String kodeord) {
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.email = email;
        this.telefonnummer = telefonnummer;
        this.kodeord = kodeord;
    }

    public Medarbejder(int medarbejder_id, String fornavn, String efternavn, String email, int telefonnummer, String kodeord) {
        this.medarbejder_id = medarbejder_id;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.email = email;
        this.telefonnummer = telefonnummer;
        this.kodeord = kodeord;
    }

    public Medarbejder(int medarbejder_id, String fornavn, String efternavn, String email, int telefonnummer, String kodeord, String stilling) {
        this.medarbejder_id = medarbejder_id;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.email = email;
        this.telefonnummer = telefonnummer;
        this.kodeord = kodeord;
        this.stilling = stilling;
    }

    // Getters and setters
    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEfternavn() {
        return efternavn;
    }

    public void setEfternavn(String efternavn) {
        this.efternavn = efternavn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(int telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public ArrayList<ArrayList<Sag>> getSager() {
        return sager;
    }

    public void setSager(ArrayList<ArrayList<Sag>> sager) {
        this.sager = sager;
    }

    public int getMedarbejder_id() {
        return medarbejder_id;
    }

    public void setMedarbejder_id(int medarbejder_id) {
        this.medarbejder_id = medarbejder_id;
    }

    public String getKodeord() {
        return kodeord;
    }

    public void setKodeord(String kodeord) {
        this.kodeord = kodeord;
    }

    public String getStilling() {
        return stilling;
    }

    public void setStilling(String stilling) {
        this.stilling = stilling;
    }
}
