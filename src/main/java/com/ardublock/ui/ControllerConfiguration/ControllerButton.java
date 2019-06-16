package com.ardublock.ui.ControllerConfiguration;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;

public class ControllerButton extends JToggleButton {

    private final ControllerImage controllerImage;
    private final СontrollerСonfiguration controller;
    private final String buttonId;
    private String path;
    public String moduleName = "start";
    public String moduleTranslatedName = "modules.start.info";
    //private String pathSet;
    private ImageIcon image;
    private ImageIcon imageSet;
    private boolean isItConnector;
    private ControllerButton button;
    //private boolean canBePressed;

    /**
     * @param root
     * @param rootImage
     * @param Id
     * @param mode
     */
    public ControllerButton(СontrollerСonfiguration root, ControllerImage rootImage, String Id, String mode) {
        this.button = this;
        this.controller = root;
        this.buttonId = Id;
        this.controllerImage = rootImage;
        setMargin(new Insets(0, 0, 0, 0));
        setIconTextGap(0);
        setBorderPainted(false);
        setBorder(null);
        setText(null);
        setFocusable(false);
        this.setBackground(null);
        switch (mode) {
            case "connector":
            case "Connector":
                this.isItConnector = true;
                this.setStartPositionAsConnector(Id);
                this.setIconAsConnector(Id);
                break;
            case "module":
            case "Module":
                this.isItConnector = false;
                this.setStartPositionAsModule(Id);
                this.setStartIconAsModule();
                break;
            default:
                this.path = null;
                //this.pathSet = null;
                break;
        }
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {

            }
            
            public void mouseReleased(MouseEvent e) {
                if (button.isSelected()) {
                    controllerImage.setSelectedId(Id,isItConnector);
                    if (isItConnector) controller.changeConnectorComponentsPane(buttonId);
                    else controller.changeModuleComponentsPane(button.moduleName);
                } else {
                    controllerImage.resetSelectedId(Id,isItConnector);
                    controller.changeConnectorComponentsPane(null);
                }
                controllerImage.unpressElse(Id,isItConnector);
            }

            public void mouseEntered(MouseEvent e) {
                setBounds(getX() - 10, getY() - 10, getWidth() + 20, getHeight() + 20);
                button.rePaint();
                //setIconAccordingToPress();
            }

            public void mouseExited(MouseEvent e) {
                setBounds(getX() + 10, getY() + 10, getWidth() - 20, getHeight() - 20);
                button.rePaint();
                //setIconAccordingToPress();
            }
        });
        rootImage.add(this);
        this.setImages();

    }

    //-----------------------------------------------Методы коннекторов------------------------------------------
    private void setIconAsConnector(String Id) {
        switch (Id) {
            case "dir04pwm05":
            case "dir07pwm06":
                path = "com/ardublock/Images/connectors/engine1.png";
                break;
            case "d2":
                path = "com/ardublock/Images/connectors/connectorEncUp.png";
                break;
            case "d3":
                path = "com/ardublock/Images/connectors/connectorEncDown.png";
                break;
            case "d8":
            case "d10":
            case "d9":
            case "d11":
            case "a3":
            case "a2":
            case "a1":
            case "a0":
                path = "com/ardublock/Images/connectors/connector1.png";
                break;
            case "i2c":
                path = "com/ardublock/Images/connectors/i2c.png";
                break;
        }
    }

    private void setStartPositionAsConnector(String Id) {
        switch (Id) {
            case "dir04pwm05":
                this.setBounds(this.controller.getX() + 54, this.controller.getY() + 88, 16, 27);
                break;
            case "dir07pwm06":
                this.setBounds(this.controller.getX() + 54, this.controller.getY() + 185, 16, 27);
                break;
            case "d2":
                this.setBounds(this.controller.getX() + 54, this.controller.getY() + 120, 25, 16);
                break;
            case "d3":
                this.setBounds(this.controller.getX() + 54, this.controller.getY() + 165, 25, 16);
                break;
            case "d8":
                this.setBounds(this.controller.getX() + 86, this.controller.getY() + 65, 16, 25);
                break;
            case "d10":
                this.setBounds(this.controller.getX() + 106, this.controller.getY() + 65, 16, 25);
                break;
            case "d9":
                this.setBounds(this.controller.getX() + 126, this.controller.getY() + 65, 16, 25);
                break;
            case "d11":
                this.setBounds(this.controller.getX() + 146, this.controller.getY() + 65, 16, 25);
                break;
            case "a3":
                this.setBounds(this.controller.getX() + 86, this.controller.getY() + 210, 16, 25);
                break;
            case "a2":
                this.setBounds(this.controller.getX() + 106, this.controller.getY() + 210, 16, 25);
                break;
            case "a1":
                this.setBounds(this.controller.getX() + 126, this.controller.getY() + 210, 16, 25);
                break;
            case "a0":
                this.setBounds(this.controller.getX() + 146, this.controller.getY() + 210, 16, 25);
                break;
            case "i2c":
                this.setBounds(this.controller.getX() + 228, this.controller.getY() + 90, 16, 30);
                break;

        }
    }

    private void setStartPositionAsModule(String Id) {
        switch (Id) {
            case "dir04pwm05":
                this.setBounds(this.controller.getX() + 5, this.controller.getY() + 60, 35, 35);
                break;
            case "dir07pwm06":
                this.setBounds(this.controller.getX() + 5, this.controller.getY() + 210, 35, 35);
                break;
            case "d2":
                this.setBounds(this.controller.getX() + 5, this.controller.getY() + 110, 35, 35);
                break;
            case "d3":
                this.setBounds(this.controller.getX() + 5, this.controller.getY() + 160, 35, 35);
                break;
            case "d8":
                this.setBounds(this.controller.getX() + 40, this.controller.getY() + 10, 35, 35);
                break;
            case "d10":
                this.setBounds(this.controller.getX() + 90, this.controller.getY() + 10, 35, 35);
                break;
            case "d9":
                this.setBounds(this.controller.getX() + 140, this.controller.getY() + 10, 35, 35);
                break;
            case "d11":
                this.setBounds(this.controller.getX() + 190, this.controller.getY() + 10, 35, 35);
                break;
            case "a3":
                this.setBounds(this.controller.getX() + 40, this.controller.getY() + 255, 35, 35);
                break;
            case "a2":
                this.setBounds(this.controller.getX() + 90, this.controller.getY() + 255, 35, 35);
                break;
            case "a1":
                this.setBounds(this.controller.getX() + 140, this.controller.getY() + 255, 35, 35);
                break;
            case "a0":
                this.setBounds(this.controller.getX() + 190, this.controller.getY() + 255, 35, 35);
                break;
            case "i2c":
                this.setBounds(this.controller.getX() + 260, this.controller.getY() + 100, 35, 35);
                break;

        }
    }

    private void setStartIconAsModule() {
        path = "com/ardublock/Images/module/start.png";
    }

    public void setNewIconAsModule(URL iconURL) {
        path = iconURL.getPath();
    }

    public void setNewIconAsModule(String Path) {
        path = Path;
        this.setImages();
    }

    private String getPathAddedName(String base, String adding) {
        String beforePoint = base.substring(0, base.length() - 4);
        String afterPoint = base.substring(base.length() - 4);
        return beforePoint + adding + afterPoint;
    }

    public String getId() {
        return this.buttonId;
    }

    public String getPath() {
        return this.path;
    }

    public boolean isConnector() {
        return this.isItConnector;
    }

    public void rePaint() {
        Image imageRaw = image.getImage().getScaledInstance(
                this.getWidth(), this.getHeight(), java.awt.Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(imageRaw));
        imageRaw = imageSet.getImage().getScaledInstance(
                this.getWidth(), this.getHeight(), java.awt.Image.SCALE_SMOOTH);
        this.setSelectedIcon(new ImageIcon(imageRaw));
    }

    private ImageIcon getScaled(ImageIcon icon) {
        Image imageRaw = icon.getImage().getScaledInstance(
                this.getWidth(), this.getHeight(), java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(imageRaw);
    }
    
    private void setImages(){
        //pathSet = getPathAddedName(path, "Set");
        URL iconURL = ControllerButton.class.getClassLoader().getResource(path);
        image = new ImageIcon(iconURL);
        iconURL = ControllerButton.class.getClassLoader().getResource(getPathAddedName(path, "Set"));
        imageSet = new ImageIcon(iconURL);
        this.setIcon(getScaled(image));
        this.setSelectedIcon(getScaled(imageSet));
    }
    
    public void setModuleName(String moduleName){
        this.moduleName = moduleName;
    }
    
    public void setTranslatedName(String moduleTrName){
        this.moduleTranslatedName = moduleTrName;
    }
}
