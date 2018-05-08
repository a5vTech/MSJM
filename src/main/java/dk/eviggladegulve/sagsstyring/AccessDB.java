package dk.eviggladegulve.sagsstyring;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

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


    public int getLastCaseId() {
        createConnection();
        Statement s = null;
        int id = 0;
        try {
            s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT sags_id FROM sag ORDER BY sags_id DESC LIMIT 1;");
            if (rs != null) {
                while (rs.next()) {
                    try {
                        id = rs.getInt("sags_id");
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

    public String checkLogin(String username, String password) {
        createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            // crypto from https://passwordsgenerator.net/
            ResultSet rs = s.executeQuery(String.format("SELECT CAST(AES_DECRYPT(kodeord,'y93ZhTvmASwz3CfEQt4aLf8HrUuHpqvFCtVjzLuFPycvmbcAHqzyhAPujveajAfVW59UcTmpzQz2YsKHsHe') AS CHAR) AS kodeord, stilling FROM medarbejder WHERE medarbejder_id = %s", username));
            if (rs != null) {
                while (rs.next()) {
                    try {
                        if (password.equals(rs.getString("kodeord"))) {
                            if ("Leder".equals(rs.getString("stilling")))
                                return "redirect:/menu";
                            else if ("Konduktør".equals(rs.getString("stilling")))
                                return "redirect:/kalender";
                            else if ("Svend".equals(rs.getString("stilling")))
                                return "igangværende_sager";
                        } else
                            return "redirect:/log_ind";
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/log_ind";
    }


    public void insertEmployee(Medarbejder currentEmployee) {
        createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            s.executeUpdate(String.format("INSERT INTO medarbejder(fornavn, efternavn, email, telefonnummer, kodeord, stilling) VALUES('%s','%s','%s',%s,AES_ENCRYPT('%s','y93ZhTvmASwz3CfEQt4aLf8HrUuHpqvFCtVjzLuFPycvmbcAHqzyhAPujveajAfVW59UcTmpzQz2YsKHsHe'),'%s');", currentEmployee.getFornavn(), currentEmployee.getEfternavn(), currentEmployee.getEmail(), currentEmployee.getTelefonnummer(), currentEmployee.getKodeord(), currentEmployee.getStilling()));
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
                        activeCaseList.add(new Sag(rs.getInt("sags_id"), rs.getString("arbejdssted"), rs.getInt("telefonnummer"),rs.getInt("adresse_id"), rs.getString("vejnavn"), rs.getInt("vejnummer"), rs.getString("start_dato"), rs.getString("slut_dato"), rs.getInt("postnummer"), rs.getString("by_navn"), rs.getString("email"), rs.getString("saerlige_aftaler"), rs.getString("kontaktperson_navn"), rs.getInt("kontaktperson_telefonnummer"), rs.getString("kontaktperson_email"), rs.getString("arbejdsbeskrivelse"), rs.getString("ekstra_arbejde")));

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
                    ResultSet rs = s.executeQuery(String.format("SELECT * FROM medarbejder JOIN svend_sager ON (medarbejder.medarbejder_id = svend_sager.medarbejder_id) JOIN sag ON (sag.sags_id = svend_sager.sags_id) WHERE medarbejder.medarbejder_id=%d AND ('%s' BETWEEN start_dato AND slut_dato)", employeeList.get(i).getMedarbejder_id(), startDate.toString()));
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

            ResultSet rs = s.executeQuery("SELECT * FROM medarbejder");
            if (rs != null) {
                while (rs.next()) {
                    try {
                        empList.add(new Medarbejder(rs.getInt("medarbejder_id"), rs.getString("fornavn"), rs.getString("efternavn"), rs.getString("email"), rs.getInt("telefonnummer"), rs.getString("kodeord"), rs.getString("stilling")));
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


    public void end_case(String id) {
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

    public void timer(String svend_id, String sags_id, String timer) {
        createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            s.executeUpdate(String.format("INSERT INTO registrerede_timer(medarbejder_id, sags_id, timer) VALUES(%s, %s, %s)", svend_id, sags_id, timer));
            s.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void add_extra_work(String ekstra_arbejde, String id) {
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

    public void assignToCase(int sags_id, String medarbejder_id) {
        createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            s.executeUpdate(String.format("INSERT INTO svend_sager(medarbejder_id, sags_id) VALUES(%s, %s)", medarbejder_id, sags_id));
            s.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editCase(Sag sag) {
        createConnection();
        Statement s = null;
        try {
            s = con.createStatement();
            System.out.println(sag.getAdresse_id());
            String sql = String.format("UPDATE sag SET arbejdssted = '%s', telefonnummer = %s, start_dato = '%s', slut_dato = '%s', email = '%s', saerlige_aftaler = '%s', kontaktperson_navn = '%s', kontaktperson_telefonnummer = %s, kontaktperson_email = '%s', arbejdsbeskrivelse = '%s', ekstra_arbejde = '%s' WHERE sags_id=%s",sag.getArbejdssted(),sag.getTelefonnummer(),sag.getStart_dato(),sag.getSlut_dato(),sag.getEmail(),sag.getSaerlige_aftaler(),sag.getKontaktperson_navn(), sag.getKontaktperson_telefonnummer(), sag.getKontaktperson_email(), sag.getArbejdsbeskrivelse(),sag.getEkstra_arbejde(), sag.getSags_id());
             s.executeUpdate(sql);
             s.executeUpdate(String.format("UPDATE adresse SET vejnavn = '%s', vejnummer = %s, postnummer = %s, by_navn = '%s' WHERE adresse_id = %s;",sag.getVejnavn(), sag.getVejnummer(), sag.getPostnummer(),sag.getBy(),sag.getAdresse_id()));
            s.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}