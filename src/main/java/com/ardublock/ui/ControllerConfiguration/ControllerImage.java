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

    private Image imageRaw;
    private ImageIcon image;
    private ImageIcon usingImage;
    private ArrayList<JLabel> moduleLabels = new ArrayList<JLabel>();
    private ArrayList<ImageIcon> moduleImages = new ArrayList<ImageIcon>();

    ControllerImage(СontrollerСonfiguration controller) {
        setLayout(null);
        this.setBounds(0, 0, 150, 150);
        URL iconURL = ControllerImage.class.getClassLoader().getResource("com/ardublock/Images/Plata.png");
        imageRaw = new ImageIcon(iconURL).getImage().getScaledInstance(
                200, 140, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(imageRaw);
        InvisibleButton someButt = new InvisibleButton(controller, "a0");
        InvisibleButton someButt2 = new InvisibleButton(controller, "a1");
        someButt.setBounds(this.getBounds().x, this.getBounds().y, 10, 10);
        someButt2.setBounds(this.getBounds().x + 10, this.getBounds().y + 10, 10, 10);
        this.add(someButt);
        this.add(someButt2);
        this.setBounds(0,0,150,150);
        iconURL = ControllerImage.class.getClassLoader().getResource("com/ardublock/Images/Plata.png");
        BufferedImage img = null;
        BufferedImage resized = null;
        try{
             img = ImageIO.read(iconURL);
             resized = resize(img,150,200);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        //image = new ImageIcon(iconURL);
        //InvisibleButton mainPlat = new InvisibleButton();
        /*JLabel mainPlat = new JLabel(image);
        mainPlat = */
        image = new ImageIcon(resized);
        someButt.setLayout(null);
        someButt.setBounds(this.getBounds().x + 50,this.getBounds().y+ 100,200,150);
        //someButt.setBorderPainted(false);
        this.add(someButt);
    }

    public void setModuleImages() {
        URL iconURL = ControllerImage.class.getClassLoader().getResource("com/ardublock/Images/Plata.png");
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
    
    
    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image.getImage(), 0, 0, this); // see javadoc for more info on the parameters
    }*/

}
