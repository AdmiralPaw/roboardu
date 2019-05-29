package com.ardublock.ui.ControllerConfiguration;

<<<<<<< HEAD
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
=======
import java.awt.*;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.util.logging.Level;
import java.util.logging.Logger;
>>>>>>> 658a592225fe81c42b181623f2d5ee150384985f
import javax.swing.*;
import java.awt.image.BufferedImage;

public class InvisibleButton extends JButton {
    
    
    private ImageIcon image;

<<<<<<< HEAD
    private final СontrollerСonfiguration controller;
    private final String buttonId;

    /**
     * @param controller
     * @param buttonId отвечает за индексирование кнопок
     */
    public InvisibleButton(СontrollerСonfiguration root, String Id) {
        this.controller = root;
        this.buttonId = Id;
//        this.setOpaque(false);
//        this.setContentAreaFilled(false);
//        this.setBorderPainted(false);
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                controller.changeComponentsPane(buttonId);
            }

            public void mousePressed(MouseEvent e) {
                controller.changeComponentsPane(buttonId);
            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }
        }
        );
=======
  
    
    InvisibleButton(BufferedImage image){
        
        /*this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);*/
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image.getImage(), 0, 0, this); // see javadoc for more info on the parameters
>>>>>>> 658a592225fe81c42b181623f2d5ee150384985f
    }

}
