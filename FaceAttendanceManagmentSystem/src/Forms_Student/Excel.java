package Forms_Student;

import dao.ConnectionProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

    public void exportToExcel(String enrollment) {
        String sql = """
        SELECT 
            u.enrollment_no, u.name, u.gender, u.contact, u.email, u.address, u.state, u.country,
            a.date, a.checkin, a.status
        FROM 
            attendancedata a
        JOIN 
            userregister u ON a.enrollment_no = u.enrollment_no
        WHERE
            a.enrollment_no = ?
    """;

        try (
                Connection con = ConnectionProvider.getCon(); PreparedStatement ps = con.prepareStatement(sql); XSSFWorkbook workbook = new XSSFWorkbook()) {
            ps.setString(1, enrollment);
            ResultSet rs = ps.executeQuery();

            XSSFSheet sheet = workbook.createSheet("Attendance Report");

            // Headers
            String[] headers = {
                "Enrollment No", "Name", "Gender", "Contact", "Email", "Address", "State", "Country",
                "Date", "Check-in", "Status"
            };

            XSSFRow headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowCount = 1;
            while (rs.next()) {
                XSSFRow row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(rs.getString("enrollment_no"));
                row.createCell(1).setCellValue(rs.getString("name"));
                row.createCell(2).setCellValue(rs.getString("gender"));
                row.createCell(3).setCellValue(rs.getString("contact"));
                row.createCell(4).setCellValue(rs.getString("email"));
                row.createCell(5).setCellValue(rs.getString("address"));
                row.createCell(6).setCellValue(rs.getString("state"));
                row.createCell(7).setCellValue(rs.getString("country"));
                row.createCell(8).setCellValue(rs.getString("date"));
                row.createCell(9).setCellValue(rs.getString("checkin"));
                row.createCell(10).setCellValue(rs.getString("status"));
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Excel File");
            fileChooser.setSelectedFile(new File("attendance_" + enrollment + ".xlsx"));

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                if (!fileToSave.getName().toLowerCase().endsWith(".xlsx")) {
                    fileToSave = new File(fileToSave.getParentFile(), fileToSave.getName() + ".xlsx");
                }

                try (FileOutputStream out = new FileOutputStream(fileToSave)) {
                    workbook.write(out);
                    JOptionPane.showMessageDialog(null, "Excel file exported successfully!");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to save Excel file: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Save command cancelled by user.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to export Excel: " + e.getMessage());
        }
    }
}
