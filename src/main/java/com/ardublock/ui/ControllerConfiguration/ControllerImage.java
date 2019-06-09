package com.ardublock.ui.ControllerConfiguration;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import com.ardublock.ui.ControllerConfiguration.СontrollerСonfiguration.Pin;
import com.ardublock.ui.ControllerConfiguration.InvisibleButton;
import com.ardublock.ui.OpenblocksFrame;
import java.awt.Image;
import java.net.URL;


public class ControllerImage extends JPanel {

    public ArrayList<InvisibleButton> moduleButtons;
    public ArrayList<InvisibleButton> connectorButtons;
    private static final String[] names = {"dir04pwm05", "dir07pwm06", "d2", "d3", "d8", "d10", "d9", "d11", "a3", "a2", "a1", "a0", "i2c"};

    ControllerImage(СontrollerСonfiguration controller) {
        setLayout(null);
        setAllLabelsStart();

        InvisibleButton someButt = new InvisibleButton(controller, this, "a0", "connector");
        InvisibleButton someButt2 = new InvisibleButton(controller, this, "a1", "module");

    }

    public void setAllLabelsStart() {
        String[] names = {"com/ardublock/Images/Plata.png", "/com/ardublock/Images/lines/a0.png", "/com/ardublock/Images/lines/a1.png",
            "/com/ardublock/Images/lines/a2.png", "/com/ardublock/Images/lines/a3.png", "/com/ardublock/Images/lines/i2c.png",
            "/com/ardublock/Images/lines/d11.png", "/com/ardublock/Images/lines/d9.png", "/com/ardublock/Images/lines/d10.png",
            "/com/ardublock/Images/lines/d8.png", "/com/ardublock/Images/lines/motorUp.png", "/com/ardublock/Images/lines/d2.png",
            "/com/ardublock/Images/lines/d3.png", "/com/ardublock/Images/lines/motorDown.png"};
        setLabelAsIntended(names[0]);

    }

    public void setLabelAsIntended(String Path) {
        switch(Path){
            case "/com/ardublock/Images/Plata.png":
                this.setLabel(Path, 0, 0, 158, 131);
                break;
        }
    }
    
    public void setLabel(String Path, int x, int y, int width, int height){
        URL iconURL = ControllerImage.class.getClassLoader().getResource(Path);
        Image imageRaw = new ImageIcon(iconURL).getImage().getScaledInstance(
                width, height, java.awt.Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(imageRaw);
        JLabel picLabel = new JLabel(image);
        picLabel.setBounds(this.getX()+x, this.getY()+y, width, height);
        this.add(picLabel);
    }
    

    


    public void deletePicture(int numberOfModule) {
        try {
            remove(numberOfModule);
        } catch (NullPointerException nul) {
            throw new NullPointerException("Такого дерьма тут нет");
        }
    }
    

    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image.getImage(), 0, 0, this); // see javadoc for more info on the parameters
    }*/

}
