/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 *
 * @author Manu Kumar
 */
public class tables {

    public static void main(String[] args) {
        Connection con = null;
        Statement st = null;
        try {
            con = ConnectionProvider.getCon();
            st = con.createStatement();

            if (!tableExists(st, "userregister")) {
                st.executeUpdate("CREATE TABLE userregister (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100) not null, gender ENUM('Male', 'Female') NOT NULL, email VARCHAR(255) not null, enrollment_no VARCHAR(255) not null, contact VARCHAR(20) not null, course VARCHAR(20) not null, department VARCHAR(20) not null, address VARCHAR(500), state VARCHAR(100),country VARCHAR(100), imagename VARCHAR(100));");

            }
            if (!tableExists(st, "attendancedata")) {
             st.executeUpdate("CREATE TABLE attendancedata (enrollment_no VARCHAR(255) NOT NULL PRIMARY KEY, status ENUM('Present', 'Absent') NOT NULL, checkin TIMESTAMP, date DATE NOT NULL);");

            }
            JOptionPane.showMessageDialog(null, "Tables Checked/Created Successfully");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static boolean tableExists(Statement st, String tableName) throws Exception {
        ResultSet resultSet = st.executeQuery("SHOW TABLES LIKE '" + tableName + "'");
        return resultSet.next();
    }
}
