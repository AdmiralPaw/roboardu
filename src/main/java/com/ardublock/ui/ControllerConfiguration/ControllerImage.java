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

    private СontrollerСonfiguration controllerConf;
    private Image background;
    public ArrayList<InvisibleButton> moduleButtons;
    public ArrayList<InvisibleButton> connectorButtons;
    private static final String[] names = {"dir04pwm05", "dir07pwm06", "d2", "d3", "d8", "d10", "d9", "d11", "a3", "a2", "a1", "a0", "i2c"};
    private String idOfPressedConnector;
    private String idOfPressedModule;

    ControllerImage(СontrollerСonfiguration controller) {
        setLayout(null);
        this.controllerConf = controller;
        background = getImage("com/ardublock/Images/PlataBackground1.png",300/*this.getWidth()*/,300/*this.getHeight()*/);
        this.initArrays();
        
        /*for(String i:names){
            this.moduleButtons.add(new InvisibleButton(controller, this, i, "module"));
            this.connectorButtons.add(new InvisibleButton(controller, this, i, "connector"));
        }*/
        /*for(int i=0;i<names.length;i++){
            this.moduleButtons.add(new InvisibleButton(controller, this, names[i], "module"));
            this.connectorButtons.add(new InvisibleButton(controller, this, names[i], "connector"));
        }*/
        /*InvisibleButton someButt = new InvisibleButton(controller, this, names[0], "connector");
        InvisibleButton someButt2 = new InvisibleButton(controller, this, names[0], "connector");*/

    }
    
    private void initArrays(){
        this.moduleButtons = new ArrayList<InvisibleButton>();
        this.connectorButtons = new ArrayList<InvisibleButton>();
        for(String i:names){
            this.moduleButtons.add(new InvisibleButton(controllerConf, this, i, "module"));
            this.connectorButtons.add(new InvisibleButton(controllerConf, this, i, "connector"));
        }
    }
    
    //-------------------------------------ДЛЯ УСТАНОВКИ ИЗОБРАЖЕНИЯ В МОДУЛЬ----------------------------------------------
    public void setModule(String Id, String Path){
        this.callModuleButton(Id).setNewIconAsModule(Path);
    }
    
    public void setModule(String Id, URL iconURL){
        this.callModuleButton(Id).setNewIconAsModule(iconURL);
    }
    //---------------------------------------------------------------------------------------------------------------------
    
    public Image getImage(String Path, int Width, int Height){
        URL iconURL = ControllerImage.class.getClassLoader().getResource(Path);
        return new ImageIcon(iconURL).getImage().getScaledInstance(Width, Height, java.awt.Image.SCALE_SMOOTH);
    }
    
    public InvisibleButton callModuleButton(String Id){
        for(InvisibleButton i: moduleButtons){
            if(i.getId() == Id) return i;
        }
        return null;
    }
    
    public InvisibleButton callConnectorButton(String Id){
        for(InvisibleButton i: connectorButtons){
            if(i.getId() == Id) return i;
        }
        return null;
    }
    
    public void someConnectorPressed(InvisibleButton connector){
        if(connector.getId() == this.idOfPressedConnector){
            connector.commandToBeUnpressed();
            this.idOfPressedConnector = null;
            if (this.idOfPressedModule != null) {
                callModuleButton(this.idOfPressedModule).commandToBeUnpressed();
                this.idOfPressedModule = null;
            }
        }
        else{
            if(this.idOfPressedConnector != null ) this.callConnectorButton(this.idOfPressedConnector).commandToBeUnpressed();
            connector.commandToBePressed();
            this.idOfPressedConnector = connector.getId();
            if (this.idOfPressedModule != null) {
                if (this.idOfPressedModule != connector.getId()) {
                    callModuleButton(this.idOfPressedModule).commandToBeUnpressed();
                    callModuleButton(connector.getId()).commandToBePressed();
                    this.idOfPressedModule = connector.getId();
                }
            }
            else{
                callModuleButton(connector.getId()).commandToBePressed();
                this.idOfPressedModule = connector.getId();
            }
            /*if (connector.getId() != this.idOfPressedModule) {
                callModuleButton(this.idOfPressedModule).commandToBeUnpressed();
                callModuleButton(connector.getId()).commandToBePressed();
                this.idOfPressedModule = connector.getId();
            }*/
        }
    }

    public void someModulePressed(InvisibleButton module) {
        if (this.idOfPressedConnector != null) {
            this.callConnectorButton(this.idOfPressedConnector).commandToBeUnpressed();
            this.idOfPressedConnector = null;
            this.callModuleButton(this.idOfPressedModule).commandToBeUnpressed();
            module.commandToBePressed();
            this.idOfPressedModule = module.getId();
        }
        else {
            if (module.getId() == this.idOfPressedModule) {
                module.commandToBeUnpressed();
                this.idOfPressedModule = null;
            } 
            else {
                if(this.idOfPressedModule != null) this.callModuleButton(this.idOfPressedModule).commandToBeUnpressed();
                module.commandToBePressed();
                this.idOfPressedModule = module.getId();
            }
        }
    }

    


    public void deletePicture(int numberOfModule) {
        try {
            remove(numberOfModule);
        } catch (NullPointerException nul) {
            throw new NullPointerException("Такого дерьма тут нет");
        }
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(image.getImage(), 0, 0, this); // see javadoc for more info on the parameters
        g.drawImage(background, 0, 0, this);
    }

}
