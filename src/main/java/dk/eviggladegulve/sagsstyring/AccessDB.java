package dk.eviggladegulve.sagsstyring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccessDB {
    static AccessDB instance = new AccessDB();
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://35.195.21.174:3306/egg?useSSL=false";
    static Connection con;

    private AccessDB() {
    }

    /**
     * we want to use JDBC protocol, mysql DBMS , the google cloud platform with
     * the database test
     */


    public void createConnection() {
        con = null;
        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DATABASE_URL, "root", "keapassword");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCase(Case currentCase) {
        createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            s.executeUpdate(String.format("INSERT INTO sag(arbejdssted, telefonnummer, adresse, start_dato, slut_dato, email, saerlige_aftaler, kontaktperson_navn, kontaktperson_telefonnummer, kontaktperson_email, arbejdsbeskrivelse, ekstra_arbejde) VALUES('%s',%d,%d,'%s','%s','%s','%s','%s',%d,'%s','%s','%s');", currentCase.getArbejdssted(), currentCase.getTelefonnummer(), getLastAddress(), currentCase.getStart_dato(), currentCase.getSlut_dato(), currentCase.getEmail(), currentCase.getSaerlige_aftaler(), currentCase.getKontaktperson_navn(), currentCase.getKontaktperson_telefonnummer(), currentCase.getKontaktperson_email(), currentCase.getArbejdsbeskrivelse(), currentCase.getEkstra_arbejde()));
            s.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void insertAddress(String vejnavn, int vejnummer, int postnummer, String by) {
        createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            s.executeUpdate(String.format("INSERT INTO adresse(vejnavn,vejnummer,postnummer,by_navn) VALUES('%s', %s, %s, '%s')", vejnavn, vejnummer, postnummer, by));

            s.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getLastAddress() {
        Statement s = null;
        int id = 0;
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT adresse_id FROM adresse ORDER BY adresse_id DESC LIMIT 1;");
            if (rs != null) {
                while (rs.next()) {
                    try {
                        id = rs.getInt("adresse_id");
                        return id;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
            s.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
    }
        return id;
    }

    public void insertEmployee(Employee currentEmployee) {
        createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            s.executeUpdate(String.format("INSERT INTO svend(fornavn, efternavn, email, telefonnummer) VALUES('%s','%s','%s',%s);", currentEmployee.getFornavn(), currentEmployee.getEfternavn(), currentEmployee.getEmail(), currentEmployee.getTelefonnummer()));
            s.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static AccessDB getInstance() {
        return instance;
    }


    public ArrayList<Case> getAllActiveCases() {
        createConnection();
        ArrayList<Case> activeCaseList = new ArrayList<>();
        Statement s = null;
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM egg.sag JOIN adresse ON (sag.adresse = adresse.adresse_id)");
            if (rs != null) {
                while (rs.next()) {
                    try {
                        activeCaseList.add(new Case(rs.getInt("sags_id"),rs.getString("arbejdssted"),rs.getInt("telefonnummer"),rs.getString("vejnavn"),rs.getInt("vejnummer"),rs.getString("start_dato"),rs.getString("slut_dato"),rs.getInt("postnummer"),rs.getString("by_navn"),rs.getString("email"),rs.getString("saerlige_aftaler"),rs.getString("kontaktperson_navn"),rs.getInt("kontaktperson_telefonnummer"),rs.getString("kontaktperson_email"),rs.getString("arbejdsbeskrivelse"),rs.getString("ekstra_arbejde")));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
            s.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeCaseList;
    }
}