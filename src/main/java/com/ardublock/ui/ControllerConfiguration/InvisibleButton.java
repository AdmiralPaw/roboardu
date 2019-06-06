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
    private final ControllerImage controllerImage;
    private final СontrollerСonfiguration controller;
    private final String buttonId;

    /**
     * @param controller
     * @param buttonId отвечает за индексирование кнопок
     */
    
    public InvisibleButton(СontrollerСonfiguration root, ControllerImage rootImage, String Id){
        super();
        this.controller = root;
        this.buttonId = Id;
        this.controllerImage = rootImage;
        setPositionAsConnector(Id);
        setIconAsConnector(Id);
    }
    
    public InvisibleButton(Icon icon, СontrollerСonfiguration root, ControllerImage rootImage, String Id) {
        super(icon);
        this.controller = root;
        this.buttonId = Id;
        this.controllerImage = rootImage;
//        this.setOpaque(false);
//        this.setContentAreaFilled(false);
//        this.setBorderPainted(false);
    }
    
    public void setIconAsConnector(String Id) {
        Image imageRaw;
        ImageIcon image;
        URL iconURL;
        switch (Id) {
            case "dir04pwm05":
                iconURL = ControllerImage.class.getClassLoader().getResource("com/ardublock/Images/connectors/Plata.png");
                imageRaw= new ImageIcon(iconURL).getImage().getScaledInstance(
                        this.getX(), this.getY(), java.awt.Image.SCALE_SMOOTH);
                image = new ImageIcon(imageRaw);
                this.setIcon(image);
                break;
            case "dir07pwm06":
                iconURL = ControllerImage.class.getClassLoader().getResource("com/ardublock/Images/connectors/Plata.png");
                imageRaw= new ImageIcon(iconURL).getImage().getScaledInstance(
                        this.getX(), this.getY(), java.awt.Image.SCALE_SMOOTH);
                image = new ImageIcon(imageRaw);
                this.setIcon(image);
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
    
    public void setPositionAsConnector(String Id){
        switch(Id){
            case "dir04pwm05":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 20, 12);
                break;
            case "dir07pwm06":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 20, 12);
                break;
            case "d2":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 20, 12);
                break;
            case "d3":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 20, 12);
                break;
            case "d8":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 20, 12);
                break;
            case "d10":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 20, 12);
                break;
            case "d9":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 20, 12);
                break;
            case "d11":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 20, 12);
                break;
            case "a3":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 20, 12);
                break;
            case "a2":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 20, 12);
                break;
            case "a1":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 20, 12);
                break;
            case "a0":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 20, 12);
                break;
            case "i2c":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 20, 12);
                break;
                
        }
    }
    
    public String getId(){
        return this.buttonId;
    };
    
    public СontrollerСonfiguration getController(){
        return this.controller;
    }
    
    public ControllerImage getControllerImage(){
        return this.controllerImage;
    }
    
    public void delete(){
        this.controllerImage.remove(this);
    }
    
    public void rePaint(URL iconURL){
        Image imageRaw = new ImageIcon(iconURL).getImage().getScaledInstance(
                this.getX(), this.getY(), java.awt.Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(imageRaw);
        this.setIcon(image);
    }
    
    public void rePaint(ImageIcon image){
        this.setIcon(image);
    }
    
}
