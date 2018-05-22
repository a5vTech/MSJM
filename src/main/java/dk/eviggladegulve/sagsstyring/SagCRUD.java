package dk.eviggladegulve.sagsstyring;

import org.omg.PortableInterceptor.ACTIVE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class SagCRUD {
    static SagCRUD instance = new SagCRUD();

    /**
     *  This method gets an instance of the MedarbejderCRUD class.
     * @return instance
     */
    public static SagCRUD getInstance() {
        return instance;
    }

    /**
     * This method adds hours for one employee for a specific case.
     * it takes 3 parameters.The employee id,Case id and the amount of hours worked
     * @param medarbejder_id INT
     * @param sags_id INT
     * @param timer String
     */
    public void timer(int medarbejder_id, int sags_id, String timer) {
        Connection con = AccessDB.getConnection();
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

    /**
     * This method adds extra work to the current case.
     * It takes 3 parameters. The extra work, agreed with and the case id
     * @param ekstra_arbejde String
     * @param aftalt_med String
     * @param id INT
     */
    public void add_extra_work(String ekstra_arbejde, String aftalt_med, int id) {
        Connection con = AccessDB.getConnection();
        String selectSQL = "UPDATE sag SET ekstra_arbejde=?, aftalt_med = ? WHERE sags_id=?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, ekstra_arbejde);
            preparedStatement.setString(2, aftalt_med);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method finds a case by id and it can either be active or ended
     * @param id INT
     * @param active INT active/ended
     * @return new Sag()
     */
    public Sag findCaseById(int id, int active) {
        AccessDB access = AccessDB.getInstance();
        ArrayList<Sag> sager = null;
        if (active == 1) {
            sager = getAllActiveCases();
        } else if (active == 0) {
            sager = getAllEndedCases();
        }

        for (Sag c : sager) {
            if (c.getSags_id() == id) {
                return c;
            }
        }
        return new Sag();
    }

    /**
     * This method find the last generated id of a city
     * @return city_id
     */
    public int getLastCityId() {
        Connection con = AccessDB.getConnection();
        int id = 0;
        String selectSQL = "SELECT by_id FROM byer ORDER BY by_id DESC LIMIT 1;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    try {
                        id = rs.getInt("by_id");
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

    /**
     * This method finds the last generated case id
     * @return INT
     */
    public int getLastCaseId() {
        Connection con = AccessDB.getConnection();
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

    /**
     * This method find the last address id
     * @return adresse_id
     */
    public int getLastAddress() {
        Connection con = AccessDB.getConnection();

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

    /**
     * This method inserts an address to the database and takes 4 parameters.
     * Road,roadNumber,zipCode and city.
     * @param vejnavn String
     * @param vejnummer String
     * @param postnummer String
     * @param by String
     */
    public void insertAddress(String vejnavn, String vejnummer, String postnummer, String by) {
        Connection con = AccessDB.getConnection();
        String selectSQL1 = "INSERT INTO byer(postnummer, by_navn) VALUES(?, ?)";
        String selectSQL = "INSERT INTO adresse(vejnavn,vejnummer, by_id) VALUES(?, ?, ?)";

        try {

            PreparedStatement preparedStatement1 = con.prepareStatement(selectSQL1);
            preparedStatement1.setString(1, postnummer);
            preparedStatement1.setString(2, by);
            preparedStatement1.executeUpdate();

            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, vejnavn);
            preparedStatement.setString(2, vejnummer);
            preparedStatement.setInt(3, getLastCityId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            preparedStatement1.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method inserts a case to the database and takes a case as parameter
     * @param currentCase Sag
     */
    public void createCase(Sag currentCase) {
        Connection con = AccessDB.getConnection();
        String selectSQL = "INSERT INTO sag(arbejdssted, telefonnummer, adresse, start_dato, slut_dato, email, saerlige_aftaler, kontaktperson_navn, arbejdsbeskrivelse, ekstra_arbejde, aftalt_med, fast_moedetid, udfoeres_overtid) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, currentCase.getArbejdssted());
            preparedStatement.setString(2, currentCase.getTelefonnummer());
            preparedStatement.setInt(3, getLastAddress());
            preparedStatement.setString(4, currentCase.getStart_dato());
            preparedStatement.setString(5, currentCase.getSlut_dato());
            preparedStatement.setString(6, currentCase.getEmail());
            preparedStatement.setString(7, currentCase.getSaerlige_aftaler());
            preparedStatement.setString(8, currentCase.getKontaktperson_navn());
            preparedStatement.setString(9, currentCase.getArbejdsbeskrivelse());
            preparedStatement.setString(10, currentCase.getEkstra_arbejde());
            preparedStatement.setString(11, currentCase.getAftalt_med());
            preparedStatement.setString(12, currentCase.getFast_moedetid());
            preparedStatement.setString(13, currentCase.getUdfoeres_overtid());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method assigns an employee to a case.
     * It takes two parameters. Case id and employee id
     * @param sags_id INT
     * @param medarbejder_id INT
     */
    public void assignToCase(int sags_id, String medarbejder_id) {
        Connection con = AccessDB.getConnection();
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

    /**
     * This method removes an assigned employee from the case.
     * It takestwo parameters. Case id and employee id
     * @param sags_id INT
     * @param medarbejder_id INT
     */
    public void removeFromCase(int sags_id, String medarbejder_id) {
        Connection con = AccessDB.getConnection();
        String SQL = "DELETE FROM svend_sager WHERE medarbejder_id=? AND sags_id=?;";
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

    /**
     * This method marks a case as closed. It takes one
     * parameter. The case id
     * @param id INT
     */
    public void end_case(int id) {
        Connection con = AccessDB.getConnection();
        LocalDate endDate = LocalDate.now();
        String selectSQL = "UPDATE sag SET status = 0, slut_dato = ? WHERE sags_id = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, endDate.toString());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates a case.
     * It takes one parameter. A case
     * @param sag Sag
     */
    public void editCase(Sag sag) {
        Connection con = AccessDB.getConnection();
        String selectSQL = "UPDATE sag SET arbejdssted = ?, telefonnummer = ?, start_dato = ?, slut_dato = ?, email = ?, saerlige_aftaler = ?, kontaktperson_navn = ?, arbejdsbeskrivelse = ?, ekstra_arbejde = ?, fast_moedetid = ?, udfoeres_overtid= ?, aftalt_med = ? WHERE sags_id=?;";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, sag.getArbejdssted());
            preparedStatement.setString(2, sag.getTelefonnummer());
            preparedStatement.setString(3, sag.getStart_dato());
            preparedStatement.setString(4, sag.getSlut_dato());
            preparedStatement.setString(5, sag.getEmail());
            preparedStatement.setString(6, sag.getSaerlige_aftaler());
            preparedStatement.setString(7, sag.getKontaktperson_navn());
            preparedStatement.setString(8, sag.getArbejdsbeskrivelse());
            preparedStatement.setString(9, sag.getEkstra_arbejde());
            preparedStatement.setString(10, sag.getFast_moedetid());
            preparedStatement.setString(11, sag.getUdfoeres_overtid());
            preparedStatement.setString(12, sag.getAftalt_med());
            preparedStatement.setInt(13, sag.getSags_id());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method updates an adress for a case.
     * It takes one paramete. A case
     * @param sag Sag
     */
    public void editAddress(Sag sag) {
        Connection con = AccessDB.getConnection();
        String selectSQL = "UPDATE adresse SET vejnavn = ?, vejnummer = ? WHERE adresse_id = ?;";
        String selectSQL1 = "UPDATE byer SET postnummer = ?, by_navn = ? WHERE by_id = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, sag.getVejnavn());
            preparedStatement.setString(2, sag.getVejnummer());
            preparedStatement.setInt(3, sag.getAdresse_id());
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement1 = con.prepareStatement(selectSQL1);
            preparedStatement1.setString(1, sag.getPostnummer());
            preparedStatement1.setString(2, sag.getBy());
            preparedStatement1.setInt(3, sag.getBy_id());
            preparedStatement1.executeUpdate();

            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     *  This method deletes a case from the database.
     *  It takes one paraeter. The case id
     * @param sags_id
     */
    public void deleteCase(int sags_id) {
        Connection con = AccessDB.getConnection();
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

    /**
     * This method shows all registered hours for an employee on a specific case
     * @param sags_id INT
     * @param medarbejder_id INT
     * @return registreredeTimerMedarbejder INT
     */
    public int registreredeTimerMedarbejder(int sags_id, int medarbejder_id) {
        Connection con = AccessDB.getConnection();        String selectSQL = "SELECT SUM(timer) AS registreredeTimer FROM registrerede_timer  WHERE sags_id = ? AND medarbejder_id = ?;";
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

    /**
     *This method sums up all hours for a case added by all employees
     * It takes a case id
     * @param sags_id INT
     * @return registreredeTimer INT
     */
    public int registreredeTimerSag(int sags_id) {
        Connection con = AccessDB.getConnection();
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

    /**
     * This method finds all ended cases
     * @return ArrayList<Sag>
     */
    public ArrayList<Sag> getAllEndedCases() {
        Connection con = AccessDB.getConnection();        ArrayList<Sag> endedCaseList = new ArrayList<>();
        String selectSQL = "SELECT * FROM sag JOIN adresse ON (sag.adresse = adresse.adresse_id) JOIN byer ON (adresse.by_id = byer.by_id) WHERE sag.status = 0 ORDER BY sags_id DESC";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    try {
                        endedCaseList.add(new Sag(rs.getInt("sags_id"), rs.getString("arbejdssted"), rs.getString("telefonnummer"), rs.getInt("adresse_id"), rs.getString("vejnavn"), rs.getString("vejnummer"), rs.getString("start_dato"), rs.getString("slut_dato"), rs.getString("postnummer"), rs.getString("by_navn"), rs.getString("email"), rs.getString("saerlige_aftaler"), rs.getString("kontaktperson_navn"), rs.getString("arbejdsbeskrivelse"), rs.getString("ekstra_arbejde"), rs.getString("aftalt_med"), rs.getString("fast_moedetid"), rs.getString("udfoeres_overtid"), rs.getInt("by_id")));

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

    /**
     * This method return all active cases for a specific employee
     * @param medarbejder_id INT
     * @return ArrayList<Sag>
     */
    public ArrayList<Sag> getAllActiveCasesMedarbejder(int medarbejder_id) {
        Connection con = AccessDB.getConnection();
        ArrayList<Sag> activeCaseList = new ArrayList<>();
        String selectSQL = "SELECT * FROM svend_sager JOIN sag ON (svend_sager.sags_id = sag.sags_id) JOIN adresse ON (sag.adresse = adresse.adresse_id) JOIN byer ON (adresse.by_id = byer.by_id) WHERE medarbejder_id =? AND sag.status = 1";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setInt(1,medarbejder_id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    try {
                        activeCaseList.add(new Sag(rs.getInt("sags_id"), rs.getString("arbejdssted"), rs.getString("telefonnummer"), rs.getInt("adresse_id"), rs.getString("vejnavn"), rs.getString("vejnummer"), rs.getString("start_dato"), rs.getString("slut_dato"), rs.getString("postnummer"), rs.getString("by_navn"), rs.getString("email"), rs.getString("saerlige_aftaler"), rs.getString("kontaktperson_navn"), rs.getString("arbejdsbeskrivelse"), rs.getString("ekstra_arbejde"), rs.getString("aftalt_med"), rs.getString("fast_moedetid"), rs.getString("udfoeres_overtid"), rs.getInt("by_id")));

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

    /**
     * This method finds all ongoing cases
     * @return ArrayList<Sag>
     */
    public ArrayList<Sag> getAllActiveCases() {
        Connection con = AccessDB.getConnection();
        ArrayList<Sag> activeCaseList = new ArrayList<>();
        String selectSQL = "SELECT * FROM sag JOIN adresse ON (sag.adresse = adresse.adresse_id) " +
                "JOIN byer ON (adresse.by_id = byer.by_id) WHERE sag.status = 1 ORDER BY sags_id DESC";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    try {
                        activeCaseList.add(new Sag(rs.getInt("sags_id"), rs.getString("arbejdssted"),
                                rs.getString("telefonnummer"), rs.getInt("adresse_id"),
                                rs.getString("vejnavn"), rs.getString("vejnummer"),
                                rs.getString("start_dato"), rs.getString("slut_dato"),
                                rs.getString("postnummer"), rs.getString("by_navn"),
                                rs.getString("email"), rs.getString("saerlige_aftaler"),
                                rs.getString("kontaktperson_navn"), rs.getString("arbejdsbeskrivelse"),
                                rs.getString("ekstra_arbejde"), rs.getString("aftalt_med"),
                                rs.getString("fast_moedetid"), rs.getString("udfoeres_overtid"),
                                rs.getInt("by_id")));
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

    /**
     * This method maps all active cases to the attached employees.
     * It takes two parameters a start date which is the first day in the week we want to display
     * and then it takes a list of all employees
     * @param dateFromView LocalDate firstDayOfWeek
     * @param employeeList ArrayList<Medarbejder>
     */
    public void executeStamementCases(LocalDate dateFromView, ArrayList<Medarbejder> employeeList) {
        Connection con = AccessDB.getConnection();
        LocalDate date = dateFromView;
        String selectSQL = "SELECT * FROM medarbejder JOIN svend_sager ON (medarbejder.medarbejder_id = svend_sager.medarbejder_id) JOIN sag ON (sag.sags_id = svend_sager.sags_id) WHERE medarbejder.medarbejder_id=? AND (? BETWEEN start_dato AND slut_dato)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            for (int i = 0; i < employeeList.size(); i++) {
                LocalDate startDate = date;

                for (int j = 0; j < 14; j++) {
                    ArrayList<Sag> currentSager = new ArrayList<>();
                    preparedStatement.setInt(1, employeeList.get(i).getMedarbejder_id());
                    preparedStatement.setString(2, startDate.toString());
                    ResultSet rs = preparedStatement.executeQuery();
                    if (rs != null) {
                        while (rs.next()) {
                            try {
                                Sag sag = new Sag(rs.getInt("sags_id"), rs.getString("start_dato"), rs.getString("slut_dato"), rs.getString("arbejdssted"));
                                sag.setStatus(rs.getInt("status"));
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
    }

}
