package Application;

import Forms_Admin.App;
import javax.swing.*;

public class AppLauncher {

    public static void main(String[] args) {
        try {
            // Apply Modern Nimbus Look & Feel
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Start application at Login screen
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}
