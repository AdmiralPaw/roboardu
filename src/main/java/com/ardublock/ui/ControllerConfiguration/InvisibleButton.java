package com.ardublock.ui.ControllerConfiguration;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;

public class InvisibleButton extends JToggleButton {

    private final ControllerImage controllerImage;
    private final СontrollerСonfiguration controller;
    private final String buttonId;
    private String path;
    private String pathSet;
    private ImageIcon image;
    private ImageIcon imageSet;
    private boolean isPressed;
    private boolean isItConnector;
    private InvisibleButton button;
    //private boolean canBePressed;

    /**
     * @param root
     * @param rootImage
     * @param Id
     * @param mode
     */
    public InvisibleButton(СontrollerСonfiguration root, ControllerImage rootImage, String Id, String mode) {
        this.button = this;
        this.controller = root;
        this.buttonId = Id;
        this.controllerImage = rootImage;
        setMargin(new Insets(0, 0, 0, 0));
        setIconTextGap(0);
        setBorderPainted(false);
        setBorder(null);
        setText(null);
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
                this.pathSet = null;
                break;
        }
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (button.isSelected()) {
                    controllerImage.setSelectedId(Id);
                } else {
                    controllerImage.resetSelectedId(Id);
                }
                controllerImage.unpressElse(Id);
            }

            public void mousePressed(MouseEvent e) {
                if (button.isSelected()) {
                    controllerImage.setSelectedId(Id);
                } else {
                    controllerImage.resetSelectedId(Id);
                }
                controllerImage.unpressElse(Id);
            }
            
            public void mouseReleased(MouseEvent e) {
                if (button.isSelected()) {
                    controllerImage.setSelectedId(Id);
                } else {
                    controllerImage.resetSelectedId(Id);
                }
                controllerImage.unpressElse(Id);
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
        pathSet = getPathAddedName(path, "Set");
        URL iconURL = InvisibleButton.class.getClassLoader().getResource(path);
        image = new ImageIcon(iconURL);
        iconURL = InvisibleButton.class.getClassLoader().getResource(pathSet);
        imageSet = new ImageIcon(iconURL);
        this.setIcon(getScaled(image));
        this.setSelectedIcon(getScaled(imageSet));

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
                this.setBounds(this.controller.getX() + 50, this.controller.getY() + 50, 12, 20);
                break;
            case "dir07pwm06":
                this.setBounds(this.controller.getX() + 50, this.controller.getY() + 150, 12, 20);
                break;
            case "d2":
                this.setBounds(this.controller.getX() + 50, this.controller.getY() + 80, 12, 20);
                break;
            case "d3":
                this.setBounds(this.controller.getX() + 50, this.controller.getY() + 120, 12, 20);
                break;
            case "d8":
                this.setBounds(this.controller.getX() + 70, this.controller.getY() + 50, 12, 20);
                break;
            case "d10":
                this.setBounds(this.controller.getX() + 85, this.controller.getY() + 50, 12, 20);
                break;
            case "d9":
                this.setBounds(this.controller.getX() + 100, this.controller.getY() + 50, 12, 20);
                break;
            case "d11":
                this.setBounds(this.controller.getX() + 115, this.controller.getY() + 50, 12, 20);
                break;
            case "a3":
                this.setBounds(this.controller.getX() + 70, this.controller.getY() + 170, 12, 20);
                break;
            case "a2":
                this.setBounds(this.controller.getX() + 85, this.controller.getY() + 170, 12, 20);
                break;
            case "a1":
                this.setBounds(this.controller.getX() + 100, this.controller.getY() + 170, 12, 20);
                break;
            case "a0":
                this.setBounds(this.controller.getX() + 115, this.controller.getY() + 170, 12, 20);
                break;
            case "i2c":
                this.setBounds(this.controller.getX() + 200, this.controller.getY() + 70, 12, 24);
                break;

        }
    }

    private void setStartPositionAsModule(String Id) {
        switch (Id) {
            case "dir04pwm05":
                this.setBounds(this.controller.getX() + 10, this.controller.getY() + 50, 35, 35);
                break;
            case "dir07pwm06":
                this.setBounds(this.controller.getX() + 10, this.controller.getY() + 90, 35, 35);
                break;
            case "d2":
                this.setBounds(this.controller.getX() + 10, this.controller.getY() + 130, 35, 35);
                break;
            case "d3":
                this.setBounds(this.controller.getX() + 10, this.controller.getY() + 170, 35, 35);
                break;
            case "d8":
                this.setBounds(this.controller.getX() + 50, this.controller.getY() + 10, 35, 35);
                break;
            case "d10":
                this.setBounds(this.controller.getX() + 90, this.controller.getY() + 10, 35, 35);
                break;
            case "d9":
                this.setBounds(this.controller.getX() + 130, this.controller.getY() + 10, 35, 35);
                break;
            case "d11":
                this.setBounds(this.controller.getX() + 170, this.controller.getY() + 10, 35, 35);
                break;
            case "a3":
                this.setBounds(this.controller.getX() + 50, this.controller.getY() + 210, 35, 35);
                break;
            case "a2":
                this.setBounds(this.controller.getX() + 90, this.controller.getY() + 210, 35, 35);
                break;
            case "a1":
                this.setBounds(this.controller.getX() + 130, this.controller.getY() + 210, 35, 35);
                break;
            case "a0":
                this.setBounds(this.controller.getX() + 170, this.controller.getY() + 210, 35, 35);
                break;
            case "i2c":
                this.setBounds(this.controller.getX() + 250, this.controller.getY() + 100, 35, 35);
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
}
