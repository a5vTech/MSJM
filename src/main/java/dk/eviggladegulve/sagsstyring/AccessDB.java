package dk.eviggladegulve.sagsstyring;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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

    public void insertCase(Sag currentCase) {
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
        createConnection();
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

    public boolean checkLogin(String username, String password) {
        createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery(String.format("SELECT kodeord FROM svend WHERE svend_id = %s", username));
            if (rs != null) {
                while (rs.next()) {
                    try {
                        if (password.equals(rs.getString("kodeord"))) {
                            return true;
                        } else
                            return false;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




    public void insertEmployee(Medarbejder currentEmployee) {
        createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            s.executeUpdate(String.format("INSERT INTO svend(fornavn, efternavn, email, telefonnummer, kodeord) VALUES('%s','%s','%s',%s,'%s');", currentEmployee.getFornavn(), currentEmployee.getEfternavn(), currentEmployee.getEmail(), currentEmployee.getTelefonnummer(), currentEmployee.getKodeord()));
            s.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static AccessDB getInstance() {
        return instance;
    }


    public ArrayList<Sag> getAllActiveCases() {
        createConnection();
        ArrayList<Sag> activeCaseList = new ArrayList<>();
        Statement s = null;
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM egg.sag JOIN adresse ON (sag.adresse = adresse.adresse_id)");
            if (rs != null) {
                while (rs.next()) {
                    try {
                        activeCaseList.add(new Sag(rs.getInt("sags_id"), rs.getString("arbejdssted"), rs.getInt("telefonnummer"), rs.getString("vejnavn"), rs.getInt("vejnummer"), rs.getString("start_dato"), rs.getString("slut_dato"), rs.getInt("postnummer"), rs.getString("by_navn"), rs.getString("email"), rs.getString("saerlige_aftaler"), rs.getString("kontaktperson_navn"), rs.getInt("kontaktperson_telefonnummer"), rs.getString("kontaktperson_email"), rs.getString("arbejdsbeskrivelse"), rs.getString("ekstra_arbejde")));

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

    public void executeStamementCases(LocalDate dateFromView, ArrayList<Medarbejder> employeeList) {
        createConnection();
        LocalDate date = dateFromView;
       // ArrayList<Sag> sager = new ArrayList<>();
        Statement s = null;
        try {
            s = con.createStatement();
            for (int i = 0; i < employeeList.size(); i++) {
                LocalDate startDate = date;

                //System.out.println("START DATE......." + startDate.toString());
                for (int j = 0; j < 14; j++) {
                    ArrayList<Sag> currentSager = new ArrayList<>();
                    ResultSet rs = s.executeQuery(String.format("SELECT * FROM svend JOIN svend_sager ON (svend.svend_id = svend_sager.svend_id) JOIN sag ON (sag.sags_id = svend_sager.sags_id) WHERE svend.svend_id=%d AND ('%s' BETWEEN start_dato AND slut_dato)", employeeList.get(i).getSvend_id(), startDate.toString()));
                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                Sag sag = new Sag(rs.getInt("sags_id"), rs.getString("start_dato"), rs.getString("slut_dato"), rs.getString("arbejdssted"));
                                currentSager.add(sag);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    startDate = startDate.plusDays(1);
                    employeeList.get(i).getSager().add(currentSager);
                }




            }
            s.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
      //  return sager;

    }

    public ArrayList<Medarbejder> executeStamementEmployeeList() {
        createConnection();
        Statement s = null;
        ArrayList<Medarbejder> empList = new ArrayList<>();
        try {
            s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT * FROM svend");
            if (rs != null) {
                while (rs.next()) {
                    try {
                        empList.add(new Medarbejder(rs.getInt("svend_id"), rs.getString("fornavn"), rs.getString("efternavn"), rs.getString("email"), rs.getInt("telefonnummer"), rs.getString("kodeord"),rs.getString("stilling")));
                        //sager.add(new Sag(rs.getInt("sags_nr"), rs.getDate("start_dato"), rs.getDate("slut_dato"), rs.getString("titel")));
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
        return empList;
    }



    public void end_case(String id){
        createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            s.executeUpdate(String.format("UPDATE sag SET status = 0 WHERE sags_id = %s", id));
            s.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void timer(String svend_id, String sags_id, String timer){
        createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            s.executeUpdate(String.format("INSERT INTO registrerede_timer(svend_id, sags_id, timer) VALUES(%s, %s, %s)", svend_id, sags_id, timer));
            s.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void add_extra_work(String ekstra_arbejde, String id){
        createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            s.executeUpdate(String.format("UPDATE sag SET ekstra_arbejde='%s' WHERE sags_id=%s;", ekstra_arbejde, id));
            s.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}