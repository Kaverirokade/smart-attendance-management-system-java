package Forms_Admin;

import Forms_Admin.DashboardMain;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            // Set Nimbus Look and Feel for modern UI
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Launch your dashboard
        SwingUtilities.invokeLater(() -> new DashboardMain().setVisible(true));
    }
}
