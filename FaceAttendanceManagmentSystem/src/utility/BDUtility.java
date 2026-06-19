/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BDUtility {

    private static HashMap<String, JFrame> formsMap = new HashMap<>();

    public static void setImage(JFrame frame, String imagePath, int newWidth, int newHeight) {
        try {
            BufferedImage originalImage = ImageIO.read(BDUtility.class.getResourceAsStream(imagePath));
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
            g2d.dispose();  // ✅ Prevents memory leaks

            ImageIcon backgroundImage = new ImageIcon(resizedImage);
            JLabel backgroundLabel = new JLabel(backgroundImage);
            frame.getContentPane().add(backgroundLabel, BorderLayout.CENTER);
            frame.validate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void OpenForm(String formName, JFrame formInstance) {
        JFrame existingForm = formsMap.get(formName);
        if (existingForm == null || !existingForm.isVisible()) {
            formsMap.put(formName, formInstance);
            formInstance.setVisible(true);
        } else {
            existingForm.toFront();
        }
    }

    public static String getPath(String finalPath) {
        String projectPath = System.getProperty("user.dir");
        return projectPath + File.separator + "src" + File.separator + finalPath;
    }

    public static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        return (lastDotIndex != -1) ? fileName.substring(lastDotIndex + 1) : "";
    }

    public static BufferedImage scaleImage(BufferedImage originalImage, BufferedImage selectedImage) {
        int width = selectedImage.getWidth();
        int height = selectedImage.getHeight();
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);  // ✅ Transparency supported

        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose();  // ✅ Prevents memory leaks

        return scaledImage;
    }
}
