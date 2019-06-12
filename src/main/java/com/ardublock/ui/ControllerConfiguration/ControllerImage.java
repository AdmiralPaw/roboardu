package com.ardublock.ui.ControllerConfiguration;

import java.awt.Graphics;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Image;
import java.net.URL;

public class ControllerImage extends JPanel {

    private СontrollerСonfiguration controllerConf;
    private Image background;
    public ArrayList<ControllerButton> moduleButtons;
    public ArrayList<ControllerButton> connectorButtons;
    private static final String[] names = {
        "dir04pwm05",
        "dir07pwm06",
        "d2", "d3", 
        "d8", "d10", "d9", "d11",
        "a3", "a2", "a1", "a0", 
        "i2c"};
    private String idOfPressedConnector;
    private String idOfPressedModule;

    public ControllerImage(СontrollerСonfiguration controller) {
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.controllerConf = controller;
        background = getImage("com/ardublock/Images/PlataBackground1.png",
                300/*this.getWidth()*/, 300/*this.getHeight()*/);
        this.initArrays();
    }

    private void initArrays() {
        this.moduleButtons = new ArrayList<ControllerButton>();
        this.connectorButtons = new ArrayList<ControllerButton>();
        for (String i : names) {
            this.moduleButtons.add(new ControllerButton(controllerConf, this, i, "module"));
            this.connectorButtons.add(new ControllerButton(controllerConf, this, i, "connector"));
        }
    }

    //-------------------------------------ДЛЯ УСТАНОВКИ ИЗОБРАЖЕНИЯ В МОДУЛЬ----------------------------------------------
    public void setModule(String Id, String Path) {
        this.callModuleButton(Id).setNewIconAsModule(Path);
    }

    public void setModule(String Id, URL iconURL) {
        this.callModuleButton(Id).setNewIconAsModule(iconURL);
    }
    //---------------------------------------------------------------------------------------------------------------------

    public Image getImage(String Path, int Width, int Height) {
        URL iconURL = ControllerImage.class.getClassLoader().getResource(Path);
        return new ImageIcon(iconURL).getImage().getScaledInstance(
                Width, Height, java.awt.Image.SCALE_SMOOTH);
    }
//

    public ControllerButton callModuleButton(String Id) {
        for (ControllerButton i : moduleButtons) {
            if (i.getId() == Id) {
                return i;
            }
        }
        return null;
    }

    public ControllerButton callConnectorButton(String Id) {
        for (ControllerButton i : connectorButtons) {
            if (i.getId() == Id) {
                return i;
            }
        }
        return null;
    }

    public void unpressAll() {
        for (Component butt : this.getComponents()) {
            if (butt instanceof ControllerButton) {
                ((ControllerButton) butt).setSelected(false);
            }
        }
    }

    public void unpressElse(String Id, boolean isConnector) {
        if (isConnector) {
            for (Component i : this.getComponents()) {
                if (i instanceof ControllerButton) {
                    if (!((ControllerButton) i).getId().equals(Id)) {
                        ((ControllerButton) i).setSelected(false);
                    }
                }
            }
        } else {
            for (Component i : this.getComponents()) {
                if (this.moduleButtons.contains(i)) {
                    if (!((ControllerButton) i).getId().equals(Id)) {
                        ((ControllerButton) i).setSelected(false);

                    }
                }
                if (this.connectorButtons.contains(i)) {
                    ((ControllerButton) i).setSelected(false);
                }
            }
        }
    }

    public void setSelectedId(String Id, boolean isConnector) {
        if (isConnector) {
            for (Component i : this.getComponents()) {
                if (i instanceof ControllerButton) {
                    if (((ControllerButton) i).getId().equals(Id)) {
                        ((ControllerButton) i).setSelected(true);

                    }
                }
            }
        } else {
            for (Component i : this.getComponents()) {
                if (this.moduleButtons.contains(i)) {
                    if (((ControllerButton) i).getId().equals(Id)) {
                        ((ControllerButton) i).setSelected(true);

                    }
                }
            }
        }
    }
    
    public void resetSelectedId(String Id, boolean isConnector) {
        if (isConnector) {
            for (Component i : this.getComponents()) {
                if (i instanceof ControllerButton) {
                    if (((ControllerButton) i).getId().equals(Id)) {
                        ((ControllerButton) i).setSelected(false);

                    }
                }
            }
        } else {
            for (Component i : this.getComponents()) {
                if (this.moduleButtons.contains(i)) {
                    if (((ControllerButton) i).getId().equals(Id)) {
                        ((ControllerButton) i).setSelected(false);

                    }
                }
                if(this.connectorButtons.contains(i)){
                    ((ControllerButton) i).setSelected(false);
                }
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }

}
