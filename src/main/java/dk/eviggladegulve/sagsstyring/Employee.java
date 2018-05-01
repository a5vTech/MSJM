package dk.eviggladegulve.sagsstyring;

import java.util.ArrayList;

public class Employee {
    private int svend_id;
    private String fornavn;
    private String efternavn;
    private String email;
    private int telefonnummer;
    private ArrayList<ArrayList<Case>> sager = new ArrayList<>();

    // Constructors


    public Employee() {
    }

    public Employee(String fornavn, String efternavn, String email, int telefonnummer) {
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.email = email;
        this.telefonnummer = telefonnummer;
    }

    public Employee(int svend_id, String fornavn, String efternavn, String email, int telefonnummer) {
        this.svend_id = svend_id;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.email = email;
        this.telefonnummer = telefonnummer;
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

    public ArrayList<ArrayList<Case>> getSager() {
        return sager;
    }

    public void setSager(ArrayList<ArrayList<Case>> sager) {
        this.sager = sager;
    }

    public int getSvend_id() {
        return svend_id;
    }

    public void setSvend_id(int svend_id) {
        this.svend_id = svend_id;
    }
}
