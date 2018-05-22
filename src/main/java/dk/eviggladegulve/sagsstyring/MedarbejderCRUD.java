package dk.eviggladegulve.sagsstyring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
@SuppressWarnings("Duplicates")
public class MedarbejderCRUD {
    static MedarbejderCRUD instance = new MedarbejderCRUD();

    /**
     *  This method gets an instance of the MedarbejderCRUD class.
     * @return instance
     */
    public static MedarbejderCRUD getInstance() {
        return instance;
    }

    /**
     * This method finds the last generated employee id
     * @return employee_id
     */
    public int getLastEmployeeId() {
        Connection con = AccessDB.getConnection();
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

    /**
     * This method inserts a new employee into the database and takes an employee as parameter
     * @param currentEmployee Medarbejder
     * @throws NullPointerException If the employee object is empty or not received
     */
    public void insertEmployee(Medarbejder currentEmployee) throws NullPointerException {
        Connection con = AccessDB.getConnection();
        String selectSQL = "INSERT INTO medarbejder(fornavn, efternavn, email, telefonnummer, kodeord, stilling) VALUES(?,?,?,?,AES_ENCRYPT(?,'y93ZhTvmASwz3CfEQt4aLf8HrUuHpqvFCtVjzLuFPycvmbcAHqzyhAPujveajAfVW59UcTmpzQz2YsKHsHe'),?);";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, currentEmployee.getFornavn());
            preparedStatement.setString(2, currentEmployee.getEfternavn());
            preparedStatement.setString(3, currentEmployee.getEmail());
            preparedStatement.setString(4, currentEmployee.getTelefonnummer());
            preparedStatement.setString(5, currentEmployee.getKodeord());
            preparedStatement.setString(6, currentEmployee.getStilling());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method updates an employee in the database. It takes a medarbejder as parameters
     * @param medarbejder Medarbejder
     */
    public void editEmployee(Medarbejder medarbejder) {
        Connection con = AccessDB.getConnection();
        String selectSQL = "UPDATE medarbejder SET fornavn = ?, efternavn = ?, email = ?, telefonnummer = ?, stilling = ?, kodeord = AES_ENCRYPT(?,'y93ZhTvmASwz3CfEQt4aLf8HrUuHpqvFCtVjzLuFPycvmbcAHqzyhAPujveajAfVW59UcTmpzQz2YsKHsHe') WHERE medarbejder_id=?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, medarbejder.getFornavn());
            preparedStatement.setString(2, medarbejder.getEfternavn());
            preparedStatement.setString(3, medarbejder.getEmail());
            preparedStatement.setString(4, medarbejder.getTelefonnummer());
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

    /**
     * This method deletes an employee from the database. It needs the employee_id as a parameter
     * @param medarbejder_id INT
     */
    public void deleteEmployee(int medarbejder_id) {
        Connection con = AccessDB.getConnection();
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

    /**
     * This method finds all employees
     * @return ArrayList<Medarbejder>
     */
    public ArrayList<Medarbejder> executeStamementEmployeeList() {
        Connection con = AccessDB.getConnection();
        String selectSQL = "SELECT medarbejder_id, fornavn, efternavn, email, telefonnummer,CAST(AES_DECRYPT(kodeord,'y93ZhTvmASwz3CfEQt4aLf8HrUuHpqvFCtVjzLuFPycvmbcAHqzyhAPujveajAfVW59UcTmpzQz2YsKHsHe') AS CHAR) AS kodeord, stilling FROM medarbejder";
        ArrayList<Medarbejder> empList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    try {
                        empList.add(new Medarbejder(rs.getInt("medarbejder_id"), rs.getString("fornavn"), rs.getString("efternavn"), rs.getString("email"), rs.getString("telefonnummer"), rs.getString("kodeord"), rs.getString("stilling")));
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

    /**
     * This method finds all attached employees to a given case. The method
     * takes one parameter sags_id
     * @param sags_id INT
     * @return ArrayList<Medarbejder>
     */
    public ArrayList<Medarbejder> showActiveEmployee(int sags_id) {
        Connection con = AccessDB.getConnection();
        String SQL = "SELECT medarbejder.medarbejder_id, medarbejder.fornavn, medarbejder.efternavn, medarbejder.stilling FROM svend_sager JOIN medarbejder ON (medarbejder.medarbejder_id = svend_sager.medarbejder_id) WHERE sags_id=?;";
        ArrayList<Medarbejder> medarbejdere = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, sags_id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    medarbejdere.add(new Medarbejder(rs.getInt("medarbejder_id"),rs.getString("fornavn"),rs.getString("efternavn"),rs.getString("stilling")));
                }
            }
            preparedStatement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medarbejdere;
    }

    /**
     * This method verifies the users credentials. It requires the username and password as parameters
     * @param username String
     * @param password String
     * @return Stilling
     */
    public String checkLogin(String username, String password) {
       Connection con = AccessDB.getConnection();
       // crypto from https://passwordsgenerator.net/
        String selectSQL = "SELECT CAST(AES_DECRYPT(kodeord,'y93ZhTvmASwz3CfEQt4aLf8HrUuHpqvFCtVjzLuFPycvmbcAHqzyhAPujveajAfVW59UcTmpzQz2YsKHsHe') " +
                "AS CHAR) AS kodeord, stilling FROM medarbejder WHERE medarbejder_id = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(selectSQL);
            preparedStatement.setString(1, username);

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
}
