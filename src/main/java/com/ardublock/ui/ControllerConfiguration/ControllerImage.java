package com.ardublock.ui.ControllerConfiguration;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.net.URL;


public class ControllerImage extends JPanel {

    private ImageIcon image;

    ControllerImage(СontrollerСonfiguration controller) {
        setLayout(null);
        this.setBounds(0,0,150,150);
        URL iconURL = ControllerImage.class.getClassLoader().getResource("com/ardublock/Images/Plata.png");
        BufferedImage img = null;
        BufferedImage resized = null;
        try{
             img = ImageIO.read(iconURL);
             resized = resize(img,150,200);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        image = new ImageIcon(resized);
        JButton someButt = new JButton(image);
        someButt.setLayout(null);
        someButt.setBounds(this.getBounds().x + 50,this.getBounds().y+ 100,200,150);
        this.add(someButt);

    }
    
    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }


}
