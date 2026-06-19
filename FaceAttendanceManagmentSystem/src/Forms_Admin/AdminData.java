package Forms_Admin;

import LineChart.ModelChart;
import LineChart.ModelData;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.ConnectionProvider;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javaswingdev.chart.ModelPieChart;
import javaswingdev.chart.PieChart;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class AdminData extends javax.swing.JPanel {

    public int presentstd ;
    public int absentstd ;

 
    public AdminData() {
        initComponents();

        loadStudentStats();

        presentstd = Integer.parseInt(lblPresent4.getText().trim());
        absentstd = Integer.parseInt(lblAbsent.getText().trim());
        
        pieChart1.setChartType(PieChart.PeiChartType.DONUT_CHART);
        pieChart1.addData(new ModelPieChart("Present", presentstd, new Color(0, 204, 51)));
        pieChart1.addData(new ModelPieChart("Absent", absentstd, new Color(255, 51, 0)));

        //Code for Curve chart
        chart.setTitle("Attendance %");
        chart.addLegend("", new Color(66, 135, 245), new Color(0, 204, 51));
        loadAttendanceData();

        Timer timer = new Timer(1, e -> updateTime());
        timer.start();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pieChart1 = new javaswingdev.chart.PieChart();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblPresent = new javax.swing.JLabel();
        panelShadow1 = new LineChart.PanelShadow();
        chart = new LineChart.CurveLineChart();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblTimestamp = new javax.swing.JLabel();
        lblAbsent1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblPresent4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblAbsent = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 51, 51), new java.awt.Color(102, 102, 102)));
        setMaximumSize(new java.awt.Dimension(959, 650));
        setMinimumSize(new java.awt.Dimension(959, 650));
        setPreferredSize(new java.awt.Dimension(959, 650));

        pieChart1.setBackground(new java.awt.Color(255, 255, 204));
        pieChart1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 204));
        jLabel2.setText("Pie-Chart of Attendance");
        pieChart1.add(jLabel2);
        jLabel2.setBounds(100, 0, 270, 40);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("Attendance");
        pieChart1.add(jLabel3);
        jLabel3.setBounds(200, 140, 90, 30);

        lblPresent.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPresent.setForeground(new java.awt.Color(0, 204, 0));

        panelShadow1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelShadow1.setColorGradient(new java.awt.Color(17, 38, 47));
        panelShadow1.setPreferredSize(new java.awt.Dimension(400, 285));

        chart.setForeground(new java.awt.Color(0, 0, 0));
        chart.setFillColor(true);
        chart.setMaximumSize(new java.awt.Dimension(400, 285));
        chart.setMinimumSize(new java.awt.Dimension(400, 285));

        javax.swing.GroupLayout panelShadow1Layout = new javax.swing.GroupLayout(panelShadow1);
        panelShadow1.setLayout(panelShadow1Layout);
        panelShadow1Layout.setHorizontalGroup(
            panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShadow1Layout.createSequentialGroup()
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 944, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelShadow1Layout.setVerticalGroup(
            panelShadow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelShadow1Layout.createSequentialGroup()
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Date               Time");

        lblTimestamp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        lblAbsent1.setFont(new java.awt.Font("Tahoma", 1, 26)); // NOI18N
        lblAbsent1.setForeground(new java.awt.Color(51, 51, 255));
        lblAbsent1.setText("   WELCOME  BACK !");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel1.setText("Total Registered Students :");

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(51, 51, 0));
        lblTotal.setText("--------");

        lblPresent4.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lblPresent4.setForeground(new java.awt.Color(0, 204, 0));
        lblPresent4.setText("--------");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel6.setText("Total Present Students     :");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel11.setText("Total Absent Students     :");

        lblAbsent.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        lblAbsent.setForeground(new java.awt.Color(255, 51, 51));
        lblAbsent.setText("--------");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTimestamp, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAbsent1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(12, 12, 12)
                                .addComponent(lblPresent4, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(lblAbsent, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTimestamp, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAbsent1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPresent4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAbsent, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelShadow1, javax.swing.GroupLayout.PREFERRED_SIZE, 953, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pieChart1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPresent)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pieChart1, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPresent, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelShadow1, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private LineChart.CurveLineChart chart;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JLabel lblAbsent;
    private javax.swing.JLabel lblAbsent1;
    private javax.swing.JLabel lblPresent;
    public javax.swing.JLabel lblPresent4;
    public javax.swing.JLabel lblTimestamp;
    public javax.swing.JLabel lblTotal;
    private LineChart.PanelShadow panelShadow1;
    private javaswingdev.chart.PieChart pieChart1;
    // End of variables declaration//GEN-END:variables

    private void updateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy     HH:mm:ss");
        lblTimestamp.setText(simpleDateFormat.format(new Date()));
    }

    private void loadStudentStats() {
        try (Connection con = ConnectionProvider.getCon()) {

            PreparedStatement pstTotal = con.prepareStatement("SELECT COUNT(*) FROM userregister");
            ResultSet rsTotal = pstTotal.executeQuery();
            int totalStudents = (rsTotal.next()) ? rsTotal.getInt(1) : 0;

            PreparedStatement pstPresent = con.prepareStatement(
                    "SELECT COUNT(*) FROM attendancedata WHERE date = CURDATE() AND status = 'Present'");
            ResultSet rsPresent = pstPresent.executeQuery();
            int presentStudents = (rsPresent.next()) ? rsPresent.getInt(1) : 0;
           
            int absentStudents = totalStudents - presentStudents;

            // Update new labels
            lblTotal.setText("" + totalStudents);
            lblPresent4.setText("" + presentStudents);
            lblAbsent.setText("" + absentStudents);
        } catch (Exception ex) {

            ex.printStackTrace();
            lblTotal.setText("Error fetching");
            lblPresent4.setText("Error fetching");
            lblAbsent.setText("Error fetching");
        }
    }
    
    public void exportExcelFromDashboard(JLabel lblTotalRegistered, JLabel lblTotalPresent, JLabel lblTotalAbsent, JLabel lblDateTime) {
        int registered = Integer.parseInt(lblTotalRegistered.getText().trim());
        int present = Integer.parseInt(lblTotalPresent.getText().trim());
        int absent = Integer.parseInt(lblTotalAbsent.getText().trim());
        String fullTimestamp = lblDateTime.getText();
        String date = "";
        String time = "";

        if (fullTimestamp.contains(" ")) {
            String[] parts = fullTimestamp.split(" ", 2);
            date = parts[0].trim();
            time = parts[1].trim();
        }

        exportToExcel(registered, present, absent, date, time);
    }

    public void exportToExcel(int totalRegistered, int totalPresent, int totalAbsent, String date, String time) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance Summary");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Total Registered Students");
        header.createCell(1).setCellValue("Total Present Students");
        header.createCell(2).setCellValue("Total Absent Students");
        header.createCell(3).setCellValue("Date");
        header.createCell(4).setCellValue("Time");

        Row data = sheet.createRow(1);
        data.createCell(0).setCellValue(totalRegistered);
        data.createCell(1).setCellValue(totalPresent);
        data.createCell(2).setCellValue(totalAbsent);
        data.createCell(3).setCellValue(date);
        data.createCell(4).setCellValue(time);

        for (int i = 0; i <= 4; i++) {
            sheet.autoSizeColumn(i);
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Excel File");
        fileChooser.setSelectedFile(new java.io.File("AttendanceSummary.xlsx"));

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try (FileOutputStream out = new FileOutputStream(fileChooser.getSelectedFile())) {
                workbook.write(out);
                workbook.close();
                JOptionPane.showMessageDialog(null, "Excel saved successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving Excel file.");
            }
        }
    }

    //Code for exporting Data to PDF
    public void exportToPDFFromLabels(JLabel lblTotalRegistered, JLabel lblTotalPresent, JLabel lblTotalAbsent, JLabel lblTimestamp) {
        try {
            // Extract and parse integer values from the labels
            int totalRegistered = parseIntFromLabel(lblTotalRegistered.getText());
            int totalPresent = parseIntFromLabel(lblTotalPresent.getText());
            int totalAbsent = parseIntFromLabel(lblTotalAbsent.getText());

            // Extract date and time from timestamp label
            String fullTimestamp = lblTimestamp.getText();
            String date = "";
            String time = "";
            if (fullTimestamp.contains(" ")) {
                String[] parts = fullTimestamp.split(" ", 2);
                date = parts[0].trim();
                time = parts[1].trim();
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save PDF File");
            fileChooser.setSelectedFile(new java.io.File("AttendanceSummary.pdf"));

            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(fileChooser.getSelectedFile()));
                document.open();

                // Title
                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
                Paragraph title = new Paragraph("Attendance Summary\n\n", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                // Table with 5 columns
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);

                // Header row
                addTableHeader(table, "Total Registered");
                addTableHeader(table, "Total Present");
                addTableHeader(table, "Total Absent");
                addTableHeader(table, "Date");
                addTableHeader(table, "Time");

                // Data row
                table.addCell(String.valueOf(totalRegistered));
                table.addCell(String.valueOf(totalPresent));
                table.addCell(String.valueOf(totalAbsent));
                table.addCell(date);
                table.addCell(time);

                document.add(table);
                document.close();

                JOptionPane.showMessageDialog(null, "PDF saved successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error exporting PDF.");
        }
    }

    private int parseIntFromLabel(String labelText) {
        // Extract digits from label text
        String digits = labelText.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(digits);
    }

    private void addTableHeader(PdfPTable table, String headerTitle) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setPhrase(new Phrase(headerTitle));
        table.addCell(header);
    }

    private void loadAttendanceData() {
        try {
            List<ModelData> lists = new ArrayList<>();
            Connection connection = ConnectionProvider.getCon();

            String sql = "SELECT DATE(a.date) AS day, "
                    + "ROUND(COUNT(a.enrollment_no) * 100.0 / t.total_students, 2) AS attendance_percent "
                    + "FROM attendancedata a "
                    + "JOIN (SELECT COUNT(*) AS total_students FROM userregister) t "
                    + "GROUP BY DATE(a.date), t.total_students "
                    + "ORDER BY DATE(a.date)";

            PreparedStatement p = connection.prepareStatement(sql);
            ResultSet r = p.executeQuery();

            chart.clear();  // clear old chart data

            while (r.next()) {
                String dateStr = r.getString("day");
                double percent = r.getDouble("attendance_percent");

                // Convert to short weekday like "Mon", "Tue"
                String label = java.time.LocalDate.parse(dateStr).getDayOfWeek().toString().substring(0, 3);
                lists.add(new ModelData(label, percent));
            }

            r.close();
            p.close();

            for (ModelData d : lists) {
                chart.addData(new ModelChart(d.getDayLabel(), new double[]{d.getAttendancePercent()}));
            }

            chart.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
