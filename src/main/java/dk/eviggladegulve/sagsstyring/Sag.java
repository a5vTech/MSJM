package dk.eviggladegulve.sagsstyring;

import java.util.ArrayList;

public class Sag {
    // Fields
    private int sags_id;
    private int adresse_id;
    private String arbejdssted;
    private String telefonnummer;
    private String vejnavn;
    private String vejnummer;
    private String start_dato;
    private String slut_dato;
    private String postnummer;
    private String by;
    private String email;
    private String saerlige_aftaler;
    private String kontaktperson_navn;
    private String kontaktperson_telefonnummer;
    private String kontaktperson_email;
    private String arbejdsbeskrivelse;
    private String ekstra_arbejde;
    private String fast_moedetid;
    private String udfoeres_overtid;
    private String aftalt_med;
    private int by_id;
    private int status;

//Constructors


    public Sag() {
    }

    public Sag(int sags_id, String arbejdssted, String telefonnummer, int adresse_id, String vejnavn, String vejnummer, String start_dato, String slut_dato, String postnummer, String by, String email, String saerlige_aftaler, String kontaktperson_navn, String arbejdsbeskrivelse, String ekstra_arbejde, String aftalt_med, String fast_moedetid, String udfoeres_overtid, int by_id) {
        this.sags_id = sags_id;
        this.arbejdssted = arbejdssted;
        this.telefonnummer = telefonnummer;
        this.vejnavn = vejnavn;
        this.vejnummer = vejnummer;
        this.start_dato = start_dato;
        this.slut_dato = slut_dato;
        this.postnummer = postnummer;
        this.by = by;
        this.email = email;
        this.saerlige_aftaler = saerlige_aftaler;
        this.kontaktperson_navn = kontaktperson_navn;
        this.arbejdsbeskrivelse = arbejdsbeskrivelse;
        this.ekstra_arbejde = ekstra_arbejde;
        this.adresse_id = adresse_id;
        this.aftalt_med = aftalt_med;
        this.fast_moedetid = fast_moedetid;
        this.udfoeres_overtid = udfoeres_overtid;
        this.by_id = by_id;
    }

    public Sag(int sags_id, String arbejdssted, String start_dato, String slut_dato) {
        this.sags_id = sags_id;
        this.arbejdssted = arbejdssted;
        this.start_dato = start_dato;
        this.slut_dato = slut_dato;
    }

    //Getters and setters

    public int getSags_id() {
        return sags_id;
    }

    public void setSags_id(int sags_id) {
        this.sags_id = sags_id;
    }

    public String getArbejdssted() {
        return arbejdssted;
    }

    public void setArbejdssted(String arbejdssted) {
        this.arbejdssted = arbejdssted;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public String getVejnavn() {
        return vejnavn;
    }

    public void setVejnavn(String vejnavn) {
        this.vejnavn = vejnavn;
    }

    public String getVejnummer() {
        return vejnummer;
    }

    public void setVejnummer(String vejnummer) {
        this.vejnummer = vejnummer;
    }

    public String getStart_dato() {
        return start_dato;
    }

    public void setStart_dato(String start_dato) {
        this.start_dato = start_dato;
    }

    public String getSlut_dato() {
        return slut_dato;
    }

    public void setSlut_dato(String slut_dato) {
        this.slut_dato = slut_dato;
    }

    public String getPostnummer() {
        return postnummer;
    }

    public void setPostnummer(String postnummer) {
        this.postnummer = postnummer;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSaerlige_aftaler() {
        return saerlige_aftaler;
    }

    public void setSaerlige_aftaler(String saerlige_aftaler) {
        this.saerlige_aftaler = saerlige_aftaler;
    }

    public String getKontaktperson_navn() {
        return kontaktperson_navn;
    }

    public void setKontaktperson_navn(String kontaktperson_navn) {
        this.kontaktperson_navn = kontaktperson_navn;
    }

    public String getKontaktperson_telefonnummer() {
        return kontaktperson_telefonnummer;
    }

    public void setKontaktperson_telefonnummer(String kontaktperson_telefonnummer) {
        this.kontaktperson_telefonnummer = kontaktperson_telefonnummer;
    }

    public String getKontaktperson_email() {
        return kontaktperson_email;
    }

    public void setKontaktperson_email(String kontaktperson_email) {
        this.kontaktperson_email = kontaktperson_email;
    }

    public String getArbejdsbeskrivelse() {
        return arbejdsbeskrivelse;
    }

    public void setArbejdsbeskrivelse(String arbejdsbeskrivelse) {
        this.arbejdsbeskrivelse = arbejdsbeskrivelse;
    }

    public String getEkstra_arbejde() {
        return ekstra_arbejde;
    }

    public void setEkstra_arbejde(String ekstra_arbejde) {
        this.ekstra_arbejde = ekstra_arbejde;
    }

    public int getAdresse_id() {
        return adresse_id;
    }

    public void setAdresse_id(int adresse_id) {
        this.adresse_id = adresse_id;
    }

    public String getFast_moedetid() {
        return fast_moedetid;
    }

    public void setFast_moedetid(String fast_moedetid) {
        this.fast_moedetid = fast_moedetid;
    }

    public String getUdfoeres_overtid() {
        return udfoeres_overtid;
    }

    public void setUdfoeres_overtid(String udfoeres_overtid) {
        this.udfoeres_overtid = udfoeres_overtid;
    }

    public String getAftalt_med() {
        return aftalt_med;
    }

    public void setAftalt_med(String aftalt_med) {
        this.aftalt_med = aftalt_med;
    }

    public int getBy_id() {
        return by_id;
    }

    public void setBy_id(int by_id) {
        this.by_id = by_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

