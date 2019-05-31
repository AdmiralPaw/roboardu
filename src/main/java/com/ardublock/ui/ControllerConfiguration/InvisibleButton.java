package com.ardublock.ui.ControllerConfiguration;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class InvisibleButton extends JButton {
    
    
    private ImageIcon image;



    InvisibleButton(BufferedImage image){

        /*this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);*/
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image.getImage(), 0, 0, this); // see javadoc for more info on the parameters
    }

}
