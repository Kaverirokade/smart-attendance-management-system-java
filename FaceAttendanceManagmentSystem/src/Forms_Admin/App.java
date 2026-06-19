package Forms_Admin;

import Forms_Admin.AdminLogin;
import Forms_Student.UserLogin;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import utility.BDUtility;

public class App extends javax.swing.JFrame {

    public App() {
        initComponents();
        this.getContentPane().setBackground(new Color(28, 60, 120));

        ImageIcon icon = new ImageIcon("src/app_images/logo.png");
        this.setIconImage(icon.getImage());

        lblHeader.setOpaque(true);
        lblHeader.setBackground(new Color(25, 118, 210));
        btnAdminLogin.setOpaque(true);
        btnAdminLogin.setBackground(new Color(76, 175, 80));
        btnUserLogin.setOpaque(true);
        btnUserLogin.setBackground(new Color(255, 193, 7));
        lblHeader.setBackground(new Color(25, 118, 210));

        lblBackground.setOpaque(true);
        lblBackground.setBackground(Color.WHITE);
        lblBackground.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(120, 144, 156), 2));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblHeader = new javax.swing.JLabel();
        btnAdminLogin = new javax.swing.JButton();
        btnUserLogin = new javax.swing.JButton();
        lblPanel = new javax.swing.JLabel();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AI Face Attendance ");
        setMinimumSize(new java.awt.Dimension(1230, 650));
        setResizable(false);

        lblHeader.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        lblHeader.setText(" Smart Attendance Managment System");

        btnAdminLogin.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnAdminLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app_images/icons8-admin-86.png"))); // NOI18N
        btnAdminLogin.setText("Admin Login");
        btnAdminLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminLoginActionPerformed(evt);
            }
        });

        btnUserLogin.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnUserLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app_images/user_icon.png"))); // NOI18N
        btnUserLogin.setText("User Login");
        btnUserLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(348, Short.MAX_VALUE)
                .addComponent(lblHeader)
                .addGap(335, 335, 335))
            .addGroup(layout.createSequentialGroup()
                .addGap(439, 439, 439)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdminLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(400, Short.MAX_VALUE)
                    .addComponent(lblPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(400, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(313, Short.MAX_VALUE)
                    .addComponent(lblBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(299, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(lblHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119)
                .addComponent(btnAdminLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUserLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(170, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(213, Short.MAX_VALUE)
                    .addComponent(lblPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(171, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(78, Short.MAX_VALUE)
                    .addComponent(lblBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(88, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdminLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminLoginActionPerformed
        BDUtility.OpenForm(AdminLogin.class.getSimpleName(), new AdminLogin());
    }//GEN-LAST:event_btnAdminLoginActionPerformed

    private void btnUserLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserLoginActionPerformed
        BDUtility.OpenForm(UserLogin.class.getSimpleName(), new UserLogin());
    }//GEN-LAST:event_btnUserLoginActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new App().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdminLogin;
    private javax.swing.JButton btnUserLogin;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblPanel;
    // End of variables declaration//GEN-END:variables
}
