package dk.eviggladegulve.sagsstyring;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
@SuppressWarnings("Duplicates")
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
        String selectSQL = "INSERT INTO sag(arbejdssted, telefonnummer, adresse, start_dato, slut_dato, email, saerlige_aftaler, kontaktperson_navn, kontaktperson_telefonnummer, kontaktperson_email, arbejdsbeskrivelse, ekstra_arbejde, aftalt_med, fast_moedetid, udfoeres_overtid) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, currentCase.getArbejdssted());
            preparedStatement.setInt(2, currentCase.getTelefonnummer());
            preparedStatement.setInt(3, getLastAddress());
            preparedStatement.setString(4, currentCase.getStart_dato());
            preparedStatement.setString(5, currentCase.getSlut_dato());
            preparedStatement.setString(6, currentCase.getEmail());
            preparedStatement.setString(7, currentCase.getSaerlige_aftaler());
            preparedStatement.setString(8, currentCase.getKontaktperson_navn());
            preparedStatement.setInt(9, currentCase.getKontaktperson_telefonnummer());
            preparedStatement.setString(10, currentCase.getKontaktperson_email());
            preparedStatement.setString(11, currentCase.getArbejdsbeskrivelse());
            preparedStatement.setString(12, currentCase.getEkstra_arbejde());
            preparedStatement.setString(13, currentCase.getAftalt_med());
            preparedStatement.setString(14, currentCase.getFast_moedetid());
            preparedStatement.setString(15, currentCase.getUdfoeres_overtid());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertAddress(String vejnavn, int vejnummer, int postnummer, String by) {
        createConnection();

        String selectSQL = "INSERT INTO adresse(vejnavn,vejnummer,postnummer,by_navn) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, vejnavn);
            preparedStatement.setInt(2, vejnummer);
            preparedStatement.setInt(3, postnummer);
            preparedStatement.setString(4, by);


            preparedStatement.executeUpdate();

            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getLastEmployeeId() {
        createConnection();
        int id = 0;

        String selectSQL = "SELECT medarbejder_id FROM medarbejder ORDER BY medarbejder_id DESC LIMIT 1;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    try {
                        id = rs.getInt("medarbejder_id");
                        return id;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int getLastAddress() {
        createConnection();

        int id = 0;

        String selectSQL = "SELECT adresse_id FROM adresse ORDER BY adresse_id DESC LIMIT 1;";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();
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
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    public int getLastCaseId() {
        createConnection();
        int id = 0;
        String selectSQL = "SELECT sags_id FROM sag ORDER BY sags_id DESC LIMIT 1;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
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
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public String checkLogin(String username, String password) {
        createConnection();
        String selectSQL = "SELECT CAST(AES_DECRYPT(kodeord,'y93ZhTvmASwz3CfEQt4aLf8HrUuHpqvFCtVjzLuFPycvmbcAHqzyhAPujveajAfVW59UcTmpzQz2YsKHsHe') AS CHAR) AS kodeord, stilling FROM medarbejder WHERE medarbejder_id = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, username);

            // crypto from https://passwordsgenerator.net/
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    try {
                        if (password.equals(rs.getString("kodeord"))) {

                            return rs.getString("stilling");
                        } else {
                            preparedStatement.close();

                            return "ingen";
                        }
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


    public void insertEmployee(Medarbejder currentEmployee) throws NullPointerException {
        createConnection();
        String selectSQL = "INSERT INTO medarbejder(fornavn, efternavn, email, telefonnummer, kodeord, stilling) VALUES(?,?,?,?,AES_ENCRYPT(?,'y93ZhTvmASwz3CfEQt4aLf8HrUuHpqvFCtVjzLuFPycvmbcAHqzyhAPujveajAfVW59UcTmpzQz2YsKHsHe'),?);";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, currentEmployee.getFornavn());
            preparedStatement.setString(2, currentEmployee.getEfternavn());
            preparedStatement.setString(3, currentEmployee.getEmail());
            preparedStatement.setInt(4, currentEmployee.getTelefonnummer());
            preparedStatement.setString(5, currentEmployee.getKodeord());
            preparedStatement.setString(6, currentEmployee.getStilling());

            preparedStatement.executeUpdate();

            preparedStatement.close();
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
        String selectSQL = "SELECT * FROM egg.sag JOIN adresse ON (sag.adresse = adresse.adresse_id) WHERE sag.status = 1";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    try {
                        activeCaseList.add(new Sag(rs.getInt("sags_id"), rs.getString("arbejdssted"), rs.getInt("telefonnummer"), rs.getInt("adresse_id"), rs.getString("vejnavn"), rs.getInt("vejnummer"), rs.getString("start_dato"), rs.getString("slut_dato"), rs.getInt("postnummer"), rs.getString("by_navn"), rs.getString("email"), rs.getString("saerlige_aftaler"), rs.getString("kontaktperson_navn"), rs.getInt("kontaktperson_telefonnummer"), rs.getString("kontaktperson_email"), rs.getString("arbejdsbeskrivelse"), rs.getString("ekstra_arbejde"), rs.getString("aftalt_med"), rs.getString("fast_moedetid"), rs.getString("udfoeres_overtid")));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            preparedStatement.close();
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
        String selectSQL = "SELECT * FROM medarbejder JOIN svend_sager ON (medarbejder.medarbejder_id = svend_sager.medarbejder_id) JOIN sag ON (sag.sags_id = svend_sager.sags_id) WHERE medarbejder.medarbejder_id=? AND (? BETWEEN start_dato AND slut_dato)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            for (int i = 0; i < employeeList.size(); i++) {
                LocalDate startDate = date;

                //System.out.println("START DATE......." + startDate.toString());
                for (int j = 0; j < 14; j++) {
                    ArrayList<Sag> currentSager = new ArrayList<>();
                    preparedStatement.setInt(1, employeeList.get(i).getMedarbejder_id());
                    preparedStatement.setString(2, startDate.toString());
                    ResultSet rs = preparedStatement.executeQuery();
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
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //  return sager;
    }

    public ArrayList<Medarbejder> executeStamementEmployeeList() {
        createConnection();
        String selectSQL = "SELECT medarbejder_id, fornavn, efternavn, email, telefonnummer,CAST(AES_DECRYPT(kodeord,'y93ZhTvmASwz3CfEQt4aLf8HrUuHpqvFCtVjzLuFPycvmbcAHqzyhAPujveajAfVW59UcTmpzQz2YsKHsHe') AS CHAR) AS kodeord, stilling FROM medarbejder";
        ArrayList<Medarbejder> empList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();
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

            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empList;
    }


    public void end_case(int id) {
        createConnection();
        String selectSQL = "UPDATE sag SET status = 0 WHERE sags_id = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void timer(int medarbejder_id, int sags_id, String timer) {
        createConnection();
        String selectSQL = "INSERT INTO registrerede_timer(medarbejder_id, sags_id, timer) VALUES(?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, medarbejder_id);
            preparedStatement.setInt(2, sags_id);
            preparedStatement.setString(3, timer);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void add_extra_work(String ekstra_arbejde, int id) {
        createConnection();
        String selectSQL = "UPDATE sag SET ekstra_arbejde=? WHERE sags_id=?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, ekstra_arbejde);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignToCase(int sags_id, String medarbejder_id) {
        createConnection();
        String SQL = "INSERT INTO svend_sager(medarbejder_id, sags_id) VALUES(?, ?)";
        try {
            PreparedStatement pStatement = con.prepareStatement(SQL);
            pStatement.setString(1, medarbejder_id);
            pStatement.setInt(2, sags_id);
            pStatement.executeUpdate();
            pStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editCase(Sag sag) {
        createConnection();
        String selectSQL = "UPDATE sag SET arbejdssted = ?, telefonnummer = ?, start_dato = ?, slut_dato = ?, email = ?, saerlige_aftaler = ?, kontaktperson_navn = ?, kontaktperson_telefonnummer = ?, kontaktperson_email = ?, arbejdsbeskrivelse = ?, ekstra_arbejde = ?, fast_moedetid = ?, udfoeres_overtid= ?, aftalt_med = ? WHERE sags_id=?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, sag.getArbejdssted());
            preparedStatement.setInt(2, sag.getTelefonnummer());
            preparedStatement.setString(3, sag.getStart_dato());
            preparedStatement.setString(4, sag.getSlut_dato());
            preparedStatement.setString(5, sag.getEmail());
            preparedStatement.setString(6, sag.getSaerlige_aftaler());
            preparedStatement.setString(7, sag.getKontaktperson_navn());
            preparedStatement.setInt(8, sag.getKontaktperson_telefonnummer());
            preparedStatement.setString(9, sag.getKontaktperson_email());
            preparedStatement.setString(10, sag.getArbejdsbeskrivelse());
            preparedStatement.setString(11, sag.getEkstra_arbejde());
            preparedStatement.setString(12, sag.getFast_moedetid());
            preparedStatement.setString(13, sag.getUdfoeres_overtid());
            preparedStatement.setString(14, sag.getAftalt_med());
            preparedStatement.setInt(15, sag.getSags_id());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void editAddress(Sag sag) {
        createConnection();
        String selectSQL = "UPDATE adresse SET vejnavn = ?, vejnummer = ?, postnummer = ?, by_navn = ? WHERE adresse_id = ?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, sag.getVejnavn());
            preparedStatement.setInt(2, sag.getVejnummer());
            preparedStatement.setInt(3, sag.getPostnummer());
            preparedStatement.setString(4, sag.getBy());
            preparedStatement.setInt(5, sag.getAdresse_id());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteCase(int sags_id) {
        createConnection();
        String selectSQL = "DELETE FROM sag WHERE sags_id=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, sags_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void calCases(LocalDate dateFromView, ArrayList<Medarbejder> employeeList) {
        createConnection();
        LocalDate date = dateFromView;
        String selectSQL = "SELECT sags_id FROM medarbejder JOIN svend_sager ON (medarbejder.medarbejder_id = svend_sager.medarbejder_id) JOIN sag ON (sag.sags_id = svend_sager.sags_id) WHERE medarbejder.medarbejder_id=? AND (? BETWEEN start_dato AND slut_dato)";
        // ArrayList<Sag> sager = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            for (int i = 0; i < employeeList.size(); i++) {
                LocalDate startDate = date;
                //System.out.println("START DATE......." + startDate.toString());
                for (int j = 0; j < 14; j++) {
                    ArrayList<Sag> currentSager = new ArrayList<>();
                    preparedStatement.setInt(1, employeeList.get(i).getMedarbejder_id());
                    preparedStatement.setString(2, startDate.toString());
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                // Sag sag = new Sag(rs.getInt("sags_id"), rs.getString("start_dato"), rs.getString("slut_dato"), rs.getString("arbejdssted"));
                                Sag sag = new Sag();
                                sag.setSags_id(rs.getInt("sags_id"));
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
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // return sager;
    }


    public void editEmployee(Medarbejder medarbejder) {
        createConnection();
        String selectSQL = "UPDATE medarbejder SET fornavn = ?, efternavn = ?, email = ?, telefonnummer = ?, stilling = ?, kodeord = AES_ENCRYPT(?,'y93ZhTvmASwz3CfEQt4aLf8HrUuHpqvFCtVjzLuFPycvmbcAHqzyhAPujveajAfVW59UcTmpzQz2YsKHsHe') WHERE medarbejder_id=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, medarbejder.getFornavn());
            preparedStatement.setString(2, medarbejder.getEfternavn());
            preparedStatement.setString(3, medarbejder.getEmail());
            preparedStatement.setInt(4, medarbejder.getTelefonnummer());
            preparedStatement.setString(5, medarbejder.getStilling());
            preparedStatement.setString(6, medarbejder.getKodeord());
            preparedStatement.setInt(7, medarbejder.getMedarbejder_id());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteEmployee(int medarbejder_id) {
        createConnection();
        String selectSQL = "DELETE FROM medarbejder WHERE medarbejder_id=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, medarbejder_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void addHours(int medarbejder_id, int sags_id, int timer) {
        createConnection();
        String selectSQL = "INSERT INTO registrerede_timer (medarbejder_id, sags_id, timer) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, medarbejder_id);
            preparedStatement.setInt(2, sags_id);
            preparedStatement.setInt(3, timer);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int registreredeTimer(int sags_id, int medarbejder_id) {
        createConnection();
        String selectSQL = "SELECT SUM(timer) AS registreredeTimer FROM registrerede_timer  WHERE sags_id = ? AND medarbejder_id = ?;";
        int timer = 0;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, sags_id);
            preparedStatement.setInt(2, medarbejder_id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    timer = rs.getInt("registreredeTimer");
                    return timer;
                }
            }
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timer;
    }

    public int registreredeTimerSag(int sags_id) {
        createConnection();
        String selectSQL = "SELECT SUM(timer) AS registreredeTimer FROM registrerede_timer  WHERE sags_id = ?;";
        int timer = 0;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1, sags_id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    timer = rs.getInt("registreredeTimer");
                    return timer;
                }
            }
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timer;
    }

    public ArrayList<Sag> getAllEndedCases() {
        createConnection();
        ArrayList<Sag> endedCaseList = new ArrayList<>();
        String selectSQL = "SELECT * FROM sag JOIN adresse ON (sag.adresse = adresse.adresse_id) WHERE sag.status = 0";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    try {
                        endedCaseList.add(new Sag(rs.getInt("sags_id"), rs.getString("arbejdssted"), rs.getInt("telefonnummer"), rs.getInt("adresse_id"), rs.getString("vejnavn"), rs.getInt("vejnummer"), rs.getString("start_dato"), rs.getString("slut_dato"), rs.getInt("postnummer"), rs.getString("by_navn"), rs.getString("email"), rs.getString("saerlige_aftaler"), rs.getString("kontaktperson_navn"), rs.getInt("kontaktperson_telefonnummer"), rs.getString("kontaktperson_email"), rs.getString("arbejdsbeskrivelse"), rs.getString("ekstra_arbejde"), rs.getString("aftalt_med"), rs.getString("fast_moedetid"), rs.getString("udfoeres_overtid")));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return endedCaseList;
    }

    public ArrayList<Sag> getAllActiveCasesSvend(int medarbejder_id) {
        createConnection();
        ArrayList<Sag> activeCaseList = new ArrayList<>();
        String selectSQL = "SELECT * FROM egg.sag JOIN adresse ON (sag.adresse = adresse.adresse_id) WHERE sag.status = 1";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    try {
                        activeCaseList.add(new Sag(rs.getInt("sags_id"), rs.getString("arbejdssted"), rs.getInt("telefonnummer"), rs.getInt("adresse_id"), rs.getString("vejnavn"), rs.getInt("vejnummer"), rs.getString("start_dato"), rs.getString("slut_dato"), rs.getInt("postnummer"), rs.getString("by_navn"), rs.getString("email"), rs.getString("saerlige_aftaler"), rs.getString("kontaktperson_navn"), rs.getInt("kontaktperson_telefonnummer"), rs.getString("kontaktperson_email"), rs.getString("arbejdsbeskrivelse"), rs.getString("ekstra_arbejde"), rs.getString("aftalt_med"), rs.getString("fast_moedetid"), rs.getString("udfoeres_overtid")));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeCaseList;
    }
}