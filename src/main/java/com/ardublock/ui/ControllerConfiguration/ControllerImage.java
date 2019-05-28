package com.ardublock.ui.ControllerConfiguration;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.ArrayList;
import com.ardublock.ui.ControllerConfiguration.СontrollerСonfiguration.Pin;
import com.ardublock.ui.OpenblocksFrame;
import java.awt.Image;
import java.net.URL;

public class ControllerImage extends JPanel {

    private Image imageRaw;
    private ImageIcon image;
    private ImageIcon usingImage;
    private ArrayList<JLabel> moduleLabels = new ArrayList<JLabel>();
    private ArrayList<ImageIcon> moduleImages = new ArrayList<ImageIcon>();

    public ControllerImage(СontrollerСonfiguration controller) {
        setLayout(null);
        this.setBounds(0,0,150,150);
        URL iconURL = ControllerImage.class.getClassLoader().getResource("com/ardublock/Images/plata.png");
        image = new ImageIcon(iconURL);
        JButton someButt = new JButton();
        someButt.setLayout(null);
        someButt.setBounds(this.getBounds().x,this.getBounds().y,10,10);
        this.add(someButt);

    }

    public void setModuleImages() {
        URL iconURL = ControllerImage.class.getClassLoader().getResource("com/ardublock/Images/Moduli_Dlya_veb/Plata.png");
        moduleImages.add(new ImageIcon(iconURL));
    }

    public void addModule(int numberOfModule, Pin pin) {
        JLabel picLabel;
        switch (pin) {
            case dir04pwm05:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(usingImage);
                picLabel.setBounds(10, 10, 10, 10);
                add(picLabel);
                break;
            case dir07pwm06:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(usingImage);
                picLabel.setBounds(10, 10, 10, 10);
                add(picLabel);
                break;
            case d2:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(usingImage);
                picLabel.setBounds(10, 10, 10, 10);
                add(picLabel);
                break;
            case d3:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(usingImage);
                picLabel.setBounds(10, 10, 10, 10);
                add(picLabel);
                break;
            case d8:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(usingImage);
                picLabel.setBounds(10, 10, 10, 10);
                add(picLabel);
                break;
            case d10:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(usingImage);
                picLabel.setBounds(10, 10, 10, 10);
                add(picLabel);
                break;
            case d9:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(usingImage);
                picLabel.setBounds(10, 10, 10, 10);
                add(picLabel);
                break;
            case d11:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(usingImage);
                picLabel.setBounds(10, 10, 10, 10);
                add(picLabel);
                break;
            case a3:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(usingImage);
                picLabel.setBounds(10, 10, 10, 10);
                add(picLabel);
                break;
            case a2:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(usingImage);
                picLabel.setBounds(this.getBounds().x,this.getBounds().y,10,10);
                picLabel.setBounds(10, 10, 10, 10);
                add(picLabel);
                break;
            case a1:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(usingImage);
                picLabel.setBounds(10, 10, 10, 10);
                add(picLabel);
                break;
            case a0:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(usingImage);
                picLabel.setBounds(10, 10, 10, 10);
                add(picLabel);
                break;
            case i2c:
                usingImage = moduleImages.get(numberOfModule);
                picLabel = new JLabel(usingImage);
                picLabel.setBounds(10, 10, 10, 10);
                add(picLabel);
                break;
        }
    }

    public void addPicture(int numberOfModule, int x, int y, int width, int height) {
        usingImage = moduleImages.get(numberOfModule);
        JLabel picLabel = new JLabel(usingImage);
        picLabel.setBounds(x, y, width, height);
        add(picLabel);
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
