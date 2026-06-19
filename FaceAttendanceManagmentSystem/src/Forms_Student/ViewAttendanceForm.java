package Forms_Student;

import com.toedter.calendar.JDateChooser;
import dao.ConnectionProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ViewAttendanceForm extends javax.swing.JPanel {

    private String enrollment ;

    public ViewAttendanceForm(String enrollment) {
         this.enrollment = enrollment;
        
        initComponents();
        lblHeader.setOpaque(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        btnResetFilters = new javax.swing.JButton();
        absentLBL = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        presentLBL = new javax.swing.JLabel();
        lblPresent = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        lblAbsent = new javax.swing.JLabel();
        dateChooserTo = new com.toedter.calendar.JDateChooser();
        dateChooserFrom = new com.toedter.calendar.JDateChooser();
        lblHeader = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 51, 51), new java.awt.Color(102, 102, 102)));
        setMaximumSize(new java.awt.Dimension(969, 640));
        setMinimumSize(new java.awt.Dimension(969, 640));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("To");

        btnResetFilters.setBackground(new java.awt.Color(102, 102, 255));
        btnResetFilters.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnResetFilters.setText("Reset Filters");
        btnResetFilters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetFiltersActionPerformed(evt);
            }
        });

        absentLBL.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        absentLBL.setText("Absent :");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("On / From");

        presentLBL.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        presentLBL.setText("Present :");

        lblPresent.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblPresent.setForeground(new java.awt.Color(0, 255, 0));
        lblPresent.setText("------");

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(userTable);

        lblAbsent.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblAbsent.setForeground(new java.awt.Color(255, 51, 51));
        lblAbsent.setText("------");

        dateChooserTo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        dateChooserFrom.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblHeader.setBackground(new java.awt.Color(0, 51, 153));
        lblHeader.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        lblHeader.setForeground(new java.awt.Color(255, 255, 255));
        lblHeader.setText("                                         My  Attendance Track");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnResetFilters)
                        .addGap(160, 160, 160)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(absentLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblAbsent, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(presentLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPresent, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(93, 93, 93)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateChooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateChooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(102, 102, 102))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 916, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)
                            .addComponent(dateChooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)
                            .addComponent(dateChooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(presentLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPresent, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(absentLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAbsent, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnResetFilters, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetFiltersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetFiltersActionPerformed
        dateChooserFrom.setDate(null);
        dateChooserTo.setDate(null);

        loadDataInTable();
    }//GEN-LAST:event_btnResetFiltersActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel absentLBL;
    private javax.swing.JButton btnResetFilters;
    private com.toedter.calendar.JDateChooser dateChooserFrom;
    private com.toedter.calendar.JDateChooser dateChooserTo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAbsent;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblPresent;
    private javax.swing.JLabel presentLBL;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables

    private void loadDataInTable() {
        var columns = new ArrayList<>(Arrays.asList("Name", "Gender", "Email", "Date", "CheckIn", "Status"));

        String searchText = enrollment;
        LocalDate fromDate = getLocalDate(dateChooserFrom);
        LocalDate toDate = getLocalDate(dateChooserTo);

        Long daysBetween = (fromDate != null && toDate != null) ? countWeekdays(fromDate, toDate) : null;

        Boolean enrollmentIncluded = true;
        Boolean courseIncluded = true;
        Boolean departmentIncluded = true;
        Boolean stateIncluded = true;
        Boolean countryIncluded = true;

        //  Base Query: Shows all attendance data when no filters are applied
        String sqlQuery = "SELECT ud.name, ud.gender, ud.email, ua.date, ua.checkin, ua.status";

        if (enrollmentIncluded) {
            columns.add("Enrollment");
            sqlQuery += ", ud.enrollment_no";
        }
        if (courseIncluded) {
            columns.add("Course");
            sqlQuery += ", ud.course";
        }
        if (departmentIncluded) {
            columns.add("Department");
            sqlQuery += ", ud.department";
        }
        if (stateIncluded) {
            columns.add("State");
            sqlQuery += ", ud.state";
        }
        if (countryIncluded) {
            columns.add("Country");
            sqlQuery += ", ud.country";
        }

        sqlQuery += " FROM userregister AS ud LEFT JOIN attendancedata AS ua ON ud.enrollment_no = ua.enrollment_no";

        //  Apply Filters Only If a Search or Date is Selected
        if (!searchText.isEmpty() || fromDate != null || toDate != null) {
            sqlQuery += " WHERE 1=1"; // Ensures conditions chain correctly

            if (!searchText.isEmpty()) {
                sqlQuery += " AND (ud.name LIKE '%" + searchText + "%' OR ud.enrollment_no LIKE '%" + searchText + "%')";
            }

            if (fromDate != null && toDate != null) {
                sqlQuery += " AND ua.date BETWEEN '" + fromDate + "' AND '" + toDate + "'AND ua.enrollment_no='" + searchText + "'";
            } else if (fromDate != null) {
                sqlQuery += " AND ua.date = '" + fromDate + "' AND ua.enrollment_no = '" + searchText + "'";
            }
        }

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns.toArray());
        userTable.setModel(model);

        try (Connection con = ConnectionProvider.getCon(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sqlQuery)) {

            Long presentCount = 0L;
            Long absentCount = 0L;
            Set<String> enrollmentList = new HashSet<>();

            while (rs.next()) {
                var row = new ArrayList<>();
                row.add(rs.getString("name"));
                row.add(rs.getString("gender"));
                row.add(rs.getString("email"));
                row.add(rs.getString("date"));
                row.add(rs.getString("checkin"));
                row.add(rs.getString("status"));

                if (enrollmentIncluded) {
                    row.add(rs.getString("enrollment_no"));
                    enrollmentList.add(rs.getString("enrollment_no"));
                }
                if (courseIncluded) {
                    row.add(rs.getString("course"));
                }
                if (departmentIncluded) {
                    row.add(rs.getString("department"));
                }
                if (stateIncluded) {
                    row.add(rs.getString("state"));
                }
                if (countryIncluded) {
                    row.add(rs.getString("country"));
                }

                if ("Absent".equals(rs.getString("status"))) {
                    absentCount++;
                } else {
                    presentCount++;
                }

                model.addRow(row.toArray());
            }

            updateAttendanceLabels(enrollmentList, presentCount, absentCount, daysBetween);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Something went wrong: " + ex.getMessage());
        }
    }

    private LocalDate getLocalDate(JDateChooser chooser) {
        java.util.Date utilDate = chooser.getDate(); // Explicitly define type
        return (utilDate != null) ? utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

    private void updateAttendanceLabels(Set<String> enrollmentList, Long presentCount, Long absentCount, Long daysBetween) {
        if (enrollmentList.size() == 1) {
            lblPresent.setVisible(true);
            lblAbsent.setVisible(true);
            presentLBL.setVisible(true);
            absentLBL.setVisible(true);
            lblPresent.setText(presentCount.toString());
            lblAbsent.setText((daysBetween != null && daysBetween > presentCount) ? (daysBetween - presentCount) + "" : absentCount.toString());
        } else {
            lblPresent.setVisible(false);
            lblAbsent.setVisible(false);
            presentLBL.setVisible(false);
            absentLBL.setVisible(false);
        }
    }

    private Long countWeekdays(LocalDate start, LocalDate end) {
        long count = 0;
        LocalDate date = start;
        while (!date.isAfter(end)) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                count++;
            }
            date = date.plusDays(1);
        }
        return count;
    }

    private boolean isValidDateRange(String enrollmentNo, LocalDate fromDate, LocalDate toDate) {
        try (Connection con = ConnectionProvider.getCon(); PreparedStatement stmt = con.prepareStatement("SELECT registered_at FROM userregister WHERE enrollment_no = ?")) {

            stmt.setString(1, enrollmentNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                LocalDate registeredDate = rs.getDate("registered_at").toLocalDate();

                // Prevent selecting dates before registration
                return !(fromDate != null && fromDate.isBefore(registeredDate))
                        && !(toDate != null && toDate.isBefore(registeredDate))
                        && !(toDate != null && toDate.isAfter(LocalDate.now()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
