package Forms_Admin;

public class AboutApp extends javax.swing.JPanel {

    public AboutApp() {
        initComponents();
    }
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AboutApp().setVisible(true);
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextArea2.setRows(5);
        jTextArea2.setText("This system is a modern attendance management solution powered by AI-based face recognition. It eliminates the need for manual or ID-based\n attendance by using facial features to identify and mark attendance securely and automatically.\n\nKey Features:\n                     Real-time Face Recognition using state-of-the-art InsightFace (ArcFace + RetinaFace)\n                     Secure & Reliable: Only registered faces are recognized\n                     Automatic Cooldown: Prevents multiple entries within 5 seconds\n                     Student & Teacher Mode for role-based access\n                     MySQL Database Integration for storing attendance logs\n                     Java Swing or Flask UI for user interaction\n                     Webcam-based live image processing\n\nTechnology Stack:\n                      Backend: Python \n                      Face Recognition: InsightFace (buffalo_s, ArcFace, RetinaFace)\n                      Frontend: Java Swing\n                      Database: MySQL\n                      Others: OpenCV, Tkinter, threading/multiprocessing\nHow It Works:\n                       Registration: The user provides a face image or captures it via webcam.\n                       Detection: When the system is running, it continuously scans faces using the webcam.\n                       Verification: Matches the detected face with the registered dataset using InsightFace.\n                       UI Panel: User interface shows dashboard, user registration, view logs, etc.\nMotivation:\n                 Traditional attendance methods (manual, RFID, card swipes) are time-consuming, prone to fraud, or require expensive hardware. This\n                 project provides an efficient, low-cost, and AI-driven alternative tailored for schools, colleges, and offices.\nDeveloped By:   Manu Kumar \nGuided By :        Rakesh Kumar Godi Sir");
        jScrollPane2.setViewportView(jTextArea2);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("                                                                 AI Face Attendance Managment System");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 940, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
