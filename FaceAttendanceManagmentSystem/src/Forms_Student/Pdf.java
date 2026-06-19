package Forms_Student;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.ConnectionProvider;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Pdf  {
  public void exportToPDF(String enrollment) {
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

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF File");
        fileChooser.setSelectedFile(new java.io.File("Attendance_Report_" + enrollment + ".pdf"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try (
                    Connection con = ConnectionProvider.getCon(); PreparedStatement ps = con.prepareStatement(sql); FileOutputStream fos = new FileOutputStream(fileChooser.getSelectedFile())) {
                ps.setString(1, enrollment);
                ResultSet rs = ps.executeQuery();

                Document document = new Document(PageSize.A4.rotate(), 36, 36, 54, 36);
                PdfWriter.getInstance(document, fos);
                document.open();

                // Title
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
                Paragraph title = new Paragraph("Attendance Report for " + enrollment + "\n\n", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                // Table headers
                String[] headers = {
                    "Enrollment No", "Name", "Gender", "Contact", "Email", "Address", "State", "Country",
                    "Date", "Check-in", "Status"
                };

                PdfPTable table = new PdfPTable(headers.length);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setHeaderRows(1);
                table.setWidths(new float[]{3f, 4f, 2f, 3f, 5f, 5f, 3f, 3f, 3f, 3f, 2f});

                // Header cells
                for (String header : headers) {
                    PdfPCell headerCell = new PdfPCell(new Phrase(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
                    headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(headerCell);
                }

                // Data rows
                while (rs.next()) {
                    table.addCell(rs.getString("enrollment_no"));
                    table.addCell(rs.getString("name"));
                    table.addCell(rs.getString("gender"));
                    table.addCell(rs.getString("contact"));
                    table.addCell(rs.getString("email"));
                    table.addCell(rs.getString("address"));
                    table.addCell(rs.getString("state"));
                    table.addCell(rs.getString("country"));
                    table.addCell(rs.getString("date"));
                    table.addCell(rs.getString("checkin"));
                    table.addCell(rs.getString("status"));
                }

                document.add(table);
                document.close();

                JOptionPane.showMessageDialog(null, "PDF exported successfully!");

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to export PDF: " + e.getMessage());
            }
        }
    }  
}
