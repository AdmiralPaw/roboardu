package com.ardublock.ui.ControllerConfiguration;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.image.BufferedImage;
import com.ardublock.ui.ControllerConfiguration.СontrollerСonfiguration.Pin;
import java.awt.Image;
import java.net.URL;

public class InvisibleButton extends JButton {

    private ImageIcon image;
    private final СontrollerСonfiguration controller;
    private final String buttonId;

    /**
     * @param controller
     * @param buttonId отвечает за индексирование кнопок
     */
    public InvisibleButton(Icon icon, СontrollerСonfiguration root, String Id) {
        super(icon);
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
    }
    
    public void setPositionAsConnector(String Id){
        switch(Id){
            case "dir04pwm05":
                this.setBounds(this.controller.getX(), this.controller.getY(), 15, 15);
                break;
            case "dir07pwm06":
                break;
            case "d2":
                break;
            case "d3":
                break;
            case "d8":
                break;
            case "d10":
                break;
            case "d9":
                break;
            case "d11":
                break;
            case "a3":
                break;
            case "a2":
                break;
            case "a1":
                break;
            case "a0":
                break;
            case "i2c":
                break;
                
        }
    }
    
    public String getId(){
        return this.buttonId;
    };
    
    public void delete(){
        this.controller.remove(this);
    }
    
    public void rePaint(URL iconURL){
        Image imageRaw = new ImageIcon(iconURL).getImage().getScaledInstance(
                this.getX(), this.getY(), java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(imageRaw);
        this.setIcon(image);
    }
    
    public void rePaint(ImageIcon image){
        this.setIcon(image);
    }
    
}
