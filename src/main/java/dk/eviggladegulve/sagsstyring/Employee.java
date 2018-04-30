package dk.eviggladegulve.sagsstyring;

public class Employee {
    private String fornavn;
    private String efternavn;
    private String email;
    private int telefonnummer;

    // Constructors


    public Employee() {
    }

    public Employee(String fornavn, String efternavn, String email, int telefonnummer) {
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
}
