/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app_images;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageUtil {

    /**
     * Loads and resizes an image from src/app_images/imageName.png
     *
     * @param imageName name of the image file (e.g., "icon.png")
     * @param width target width in pixels
     * @param height target height in pixels
     * @return resized ImageIcon or null if failed
     */
    public static ImageIcon getResizedIcon(String imageName, int width, int height) {
        try {
            BufferedImage img = ImageIO.read(
                    ImageUtil.class.getResourceAsStream("/app_images/" + imageName)
            );
            if (img == null) {
                System.err.println("Image not found: " + imageName);
                return null;
            }
            Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Applies a background image to the given JPanel.
     *
     * @param panel JPanel to draw image on
     * @param imageName name of image in /app_images/
     */
    public static void setImageToPanel(JPanel panel, String imageName) {
        try {
            BufferedImage img = ImageIO.read(
                    ImageUtil.class.getResourceAsStream("/app_images/" + imageName)
            );
            if (img == null) {
                System.err.println("Image not found: " + imageName);
                return;
            }

            // Create custom panel with overridden paintComponent
            JPanel imagePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(
                            img,
                            0,
                            0,
                            getWidth(),
                            getHeight(),
                            this
                    );
                }
            };

            imagePanel.setOpaque(false);
            imagePanel.setLayout(panel.getLayout());

            // Move children if any
            for (Component comp : panel.getComponents()) {
                imagePanel.add(comp);
            }

            // Replace old panel content with new one
            Container parent = panel.getParent();
            if (parent != null) {
                int index = -1;
                for (int i = 0; i < parent.getComponentCount(); i++) {
                    if (parent.getComponent(i) == panel) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    parent.remove(index);
                    imagePanel.setBounds(panel.getBounds());
                    parent.add(imagePanel, index);
                    parent.revalidate();
                    parent.repaint();
                }
            }

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}

//import app_images.ImageUtil;
//
//...
//
//// In constructor or wherever needed
//myLabel.setIcon(ImageUtil.getResizedIcon("icon.png", 100, 100));

//ImageUtil.setImageToPanel(jPanel1, "avatar.png");
