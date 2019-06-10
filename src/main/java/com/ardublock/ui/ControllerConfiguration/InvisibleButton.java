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
    private String path;
    private String pathSet;
    private boolean isPressed;
    private boolean isItConnector;
    //private boolean canBePressed;

    /**
     * @param controller
     * @param buttonId отвечает за индексирование кнопок
     */
    
    public InvisibleButton(СontrollerСonfiguration root, ControllerImage rootImage, String Id, String mode){
        super();
        this.controller = root;
        this.buttonId = Id;
        this.controllerImage = rootImage;
        switch (mode) {
            case "connector":
            case "Connector":
                this.isItConnector=true;
                setStartPositionAsConnector(Id);
                setIconAsConnector(Id);
                break;
            case "module":
            case "Module":
                this.isItConnector=false;
                setStartPositionAsModule(Id);
                setStartIconAsModule();
                break;
            default:
                this.path = null;
                this.pathSet = null;
                break;
        }
        setListenersAsConnector();
        rootImage.add(this);
    }

    //-----------------------------------------------Методы коннекторов------------------------------------------
    public void setListenersAsConnector() {
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                /*if (canBePressed) {
                    if (isPressed) {
                        isPressed = false;
                        setIconAccordingToPress();
                    } 
                    else {
                        isPressed = true;
                        setIconAccordingToPress();
                    }
                }*/
                sayPressed(); //хз как правильноп передать в аргументах тут ссылку на обьект класса InvisibleButton, поэтому сделал приватную функцию
            }

            public void mousePressed(MouseEvent e) {
                
            }

            public void mouseReleased(MouseEvent e) {
    
            }

            public void mouseEntered(MouseEvent e) {
                setBounds(getX() - 10, getY() - 10, getWidth() + 20, getHeight() + 20);
                setIconAccordingToPress();
            }

            public void mouseExited(MouseEvent e) {
                setBounds(getX() + 10, getY() + 10, getWidth() - 20, getHeight() - 20);
                setIconAccordingToPress();
            }
        }
        );
    }
    private void sayPressed() {
        if (isItConnector) {
            controllerImage.someConnectorPressed(this);
        } 
        else {
            controllerImage.someModulePressed(this);
        }
    }

    public void commandToBePressed() {
        isPressed = true;
        setIconAccordingToPress();
    }

    public void commandToBeUnpressed(){
        isPressed = false;
        setIconAccordingToPress();
    }
    
    public void setIconAsConnector(String Id) {
        switch (Id) {
            case "dir04pwm05":
            case "dir07pwm06":
                setPath(rePaint("com/ardublock/Images/connectors/engine1.png"));
                break;
            case "d2":
                setPath(rePaint("com/ardublock/Images/connectors/connectorEncUp.png"));
                break;
            case "d3":
                setPath(rePaint("com/ardublock/Images/connectors/connectorEncDown.png"));
                break;
            case "d8":
            case "d10":
            case "d9":
            case "d11":
            case "a3":
            case "a2":
            case "a1":
            case "a0":
                setPath(rePaint("com/ardublock/Images/connectors/connector1.png"));
                break;
            case "i2c":
                setPath(rePaint("com/ardublock/Images/connectors/i2c.png"));
                break;
                
        }
    }
    
    public void setStartPositionAsConnector(String Id){
        switch(Id){
            case "dir04pwm05":
                this.setBounds(this.controller.getX()+0, this.controller.getY()+50, 20, 12);
                break;
            case "dir07pwm06":
                this.setBounds(this.controller.getX()+50, this.controller.getY()+150, 20, 12);
                break;
            case "d2":
                this.setBounds(this.controller.getX()+50, this.controller.getY()+80, 12, 20);
                break;
            case "d3":
                this.setBounds(this.controller.getX()+50, this.controller.getY()+120, 12, 20);
                break;
            case "d8":
                this.setBounds(this.controller.getX()+70, this.controller.getY()+50, 20, 12);
                break;
            case "d10":
                this.setBounds(this.controller.getX()+85, this.controller.getY()+50, 20, 12);
                break;
            case "d9":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+50, 20, 12);
                break;
            case "d11":
                this.setBounds(this.controller.getX()+115, this.controller.getY()+50, 20, 12);
                break;
            case "a3":
                this.setBounds(this.controller.getX()+70, this.controller.getY()+170, 20, 12);
                break;
            case "a2":
                this.setBounds(this.controller.getX()+85, this.controller.getY()+170, 20, 12);
                break;
            case "a1":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+170, 12, 20);
                break;
            case "a0":
                this.setBounds(this.controller.getX()+115, this.controller.getY()+170, 12, 20);
                break;
            case "i2c":
                this.setBounds(this.controller.getX()+200, this.controller.getY()+70, 20, 15);
                break;
                
        }
    }
    
    public void setStartPositionAsModule(String Id){
        switch(Id){
            case "dir04pwm05":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 35, 35);
                break;
            case "dir07pwm06":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 35, 35);
                break;
            case "d2":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 35, 35);
                break;
            case "d3":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 35, 35);
                break;
            case "d8":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 35, 35);
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
                this.setBounds(this.controller.getX()+120, this.controller.getY()+120, 35, 35);
                break;
            case "a0":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+120, 35, 35);
                break;
            case "i2c":
                this.setBounds(this.controller.getX()+100, this.controller.getY()+20, 20, 12);
                break;
                
        }
    }
    
    public void setStartIconAsModule(){
        setPath(rePaint("com/ardublock/Images/module/start.png"));
    }
    
    
    public void setIsPressed(boolean Bool){
        this.isPressed = Bool;
    }
    
    public boolean getIsPressed(){
        return this.isPressed;
    }
    
    
    
    public void setNewIconAsModule(URL iconURL){
        setPath(rePaint(iconURL));
    }
    public void setNewIconAsModule(String Path){
        setPath(rePaint(Path));
    }

    public void setIconAccordingToPress() {
        if (this.isPressed) {
            rePaint(this.pathSet);
        } 
        else {
            rePaint(this.path);
        }
    }
    
    public void setPath(String Path){
        this.path = Path;
        this.pathSet = getPathAddedName(Path, "Set");
    }
    
    public String getPathAddedName(String base, String adding){
        String beforePoint = base.substring(0, base.length()-4);
        String afterPoint = base.substring(base.length()-4);
        return beforePoint + adding + afterPoint;
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
    
    public String getPath(){
        return this.path;
    }
    
    public void delete(){
        this.controllerImage.remove(this);
    }
    
    public String rePaint(URL iconURL){
        Image imageRaw = new ImageIcon(iconURL).getImage().getScaledInstance(
                this.getWidth(), this.getHeight(), java.awt.Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(imageRaw);
        this.setIcon(image);
        return iconURL.getPath();
    }
    
    public String rePaint(String Path){
        URL iconURL = ControllerImage.class.getClassLoader().getResource(Path);
        Image imageRaw = new ImageIcon(iconURL).getImage().getScaledInstance(
                this.getWidth(), this.getHeight(), java.awt.Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(imageRaw);
        this.setIcon(image);
        return Path;
    }
    
    public void rePaint(ImageIcon image){
        this.setIcon(image);
    }
    
}
