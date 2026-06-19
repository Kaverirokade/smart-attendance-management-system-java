package Forms_Admin;

import java.awt.CardLayout;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

public class DashboardMain extends javax.swing.JFrame {

    private Process pythonProcess;

    public DashboardMain() {
        initComponents();
        mainPanel.setLayout(new CardLayout());

        AdminData data = new AdminData();
        Notifications notice = new Notifications();
        UserRegistrationForm register = new UserRegistrationForm();
        ViewUserForm view = new ViewUserForm();
        UpdateUserForm update = new UpdateUserForm();
        DeleteUserForm delete = new DeleteUserForm();
        MarkAttendance markattn = new MarkAttendance();
        ViewAttendanceForm attendance = new ViewAttendanceForm();
        AboutApp aboutApp = new AboutApp();

        mainPanel.add(data, "Summary");
        mainPanel.add(notice,"Notice");
        mainPanel.add(register, "Register User");
        mainPanel.add(view, "View User");
        mainPanel.add(update, "Update User");
        mainPanel.add(delete, "Delete User");
        mainPanel.add(attendance, "View Attendance");
        mainPanel.add(markattn, "Mark Attendance");
        mainPanel.add(aboutApp, "About App");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainContentPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        btnTree = new javax.swing.JTree();
        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1230, 651));
        setResizable(false);

        btnTree.setBackground(new java.awt.Color(25, 55, 109));
        btnTree.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnTree.setForeground(new java.awt.Color(255, 255, 255));
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Dashboard");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Admin");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Summary");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Notice");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Register");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Register User");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("View");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Veiw User");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Delete");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Delete User");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Attendance");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Mark Attendance");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("View Attendance");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Downloads");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Attendance PDF");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Attendance Excel");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("About");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("About App");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        btnTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        btnTree.setVerifyInputWhenFocusTarget(false);
        btnTree.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                fgcbx(evt);
            }
        });
        btnTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTreeMouseClicked(evt);
            }
        });
        btnTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                btnTreeValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(btnTree);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setMaximumSize(new java.awt.Dimension(1005, 650));
        mainPanel.setPreferredSize(new java.awt.Dimension(959, 650));
        mainPanel.setRequestFocusEnabled(false);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1019, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout mainContentPanelLayout = new javax.swing.GroupLayout(mainContentPanel);
        mainContentPanel.setLayout(mainContentPanelLayout);
        mainContentPanelLayout.setHorizontalGroup(
            mainContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContentPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1019, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainContentPanelLayout.setVerticalGroup(
            mainContentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainContentPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainContentPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTreeMouseClicked

    }//GEN-LAST:event_btnTreeMouseClicked

    private void fgcbx(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_fgcbx

    }//GEN-LAST:event_fgcbx

    private void btnTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_btnTreeValueChanged
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) btnTree.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }

        if (selectedNode.isLeaf()) {
            String selected = selectedNode.toString();

            CardLayout cl = (CardLayout) mainPanel.getLayout();
            switch (selected) {
                case "Summary":
                    cl.show(mainPanel, "Summary");
                    break;
                case "Notice":
                    cl.show(mainPanel,"Notice");
                    break;
            
                case "Register User":
                    cl.show(mainPanel, "Register User");
                    break;
                case "Veiw User":
                    cl.show(mainPanel, "View User");
                    break;
                case "Delete User":
                    cl.show(mainPanel, "Delete User");
                    break;
                case "Mark Attendance":
                    cl.show(mainPanel, "Mark Attendance");

                    // Get the MarkAttendance panel instance already added in constructor
                    for (Component comp : mainPanel.getComponents()) {
                        if (comp instanceof MarkAttendance) {
                            MarkAttendance markPanel = (MarkAttendance) comp;

                            new Thread(() -> {
                                try {
                                    String batFile = "run_marking_attendance.bat";
                                    ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", batFile);
                                    builder.directory(new File("C:\\Users\\Manu Kumar\\Desktop\\SmartAttendance"));
                                    builder.redirectErrorStream(true); // Merge stdout + stderr

                                    pythonProcess = builder.start();

                                    // Read output
                                    BufferedReader reader = new BufferedReader(
                                            new InputStreamReader(pythonProcess.getInputStream())
                                    );

                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        String finalLine = line;
                                        SwingUtilities.invokeLater(() -> markPanel.appendOutput(finalLine));
                                    }

                                } catch (IOException ex) {
                                    SwingUtilities.invokeLater(() -> markPanel.appendOutput("Error: " + ex.getMessage()));
                                }
                            }).start();
                            break;
                        }
                    }
                    break;

                case "View Attendance":
                    cl.show(mainPanel, "View Attendance");
                    break;
                    
                case "Attendance PDF":
                    AdminData data = new AdminData();
                    data.exportToPDFFromLabels(data.lblTotal, data.lblPresent4, data.lblAbsent, data.lblTimestamp);
                    break;
                    
                case "Attendance Excel":
                   AdminData data1 = new AdminData();
                   data1.exportExcelFromDashboard(data1.lblTotal, data1.lblPresent4, data1.lblAbsent, data1.lblTimestamp);
                    break;
                    
                case "About App":
                    cl.show(mainPanel, "About App");
                    break;
                default:
                    break;
            }
        }

    }//GEN-LAST:event_btnTreeValueChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree btnTree;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainContentPanel;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
private void resetMainPanel(JPanel Panel) {
        mainContentPanel.removeAll();
        mainContentPanel.setLayout(new java.awt.BorderLayout());
        mainContentPanel.add(Panel, java.awt.BorderLayout.CENTER);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }
}
