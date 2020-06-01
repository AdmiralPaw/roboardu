package com.ardublock.ui.ControllerConfiguration;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class ControllerButton extends JToggleButton {

    private final ControllerImage controllerImage;
    private final СontrollerСonfiguration controller;
    private final String buttonId;
    private String path;

    /**
     *
     */
    public String moduleName = "start";

    /**
     *
     */
    public String moduleTranslatedName = "modules.start.info";

    /**
     *
     */
    public ArrayList<String> moduleSuitableBlocks = new ArrayList<String>();
    private String mode;
    //private String pathSet;
    private ImageIcon image;
    private ImageIcon imageSet;
    private boolean isItConnector;
    private ControllerButton button;
    private boolean mouseIsOnThis;
    private boolean mouseIsPressedThis;
    private int type_of_plate;
    //private boolean canBePressed;

    /**
     * @param root
     * @param rootImage
     * @param Id
     * @param mode
     */
    public ControllerButton(СontrollerСonfiguration root, ControllerImage rootImage, String Id, String mode, int type_of_plate) {
        moduleSuitableBlocks.add("");

        this.button = this;
        this.controller = root;
        this.buttonId = Id;
        this.controllerImage = rootImage;
        this.mode = mode;
        this.type_of_plate = type_of_plate;
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
                this.setStartPositionAsConnector(Id,this.type_of_plate);
                this.setIconAsConnector(Id,this.type_of_plate);
                break;
            case "module":
            case "Module":
                this.isItConnector = false;
                this.setStartPositionAsModule(Id,this.type_of_plate);
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
                mouseIsPressedThis=true;
            }
            
            public void mouseReleased(MouseEvent e) {
                mouseIsPressedThis=false;
                if(mouseIsOnThis) {
                    if (button.isSelected()) {
                        controllerImage.setSelectedId(Id, isItConnector);
                        if (isItConnector) {
                            controller.changeConnectorComponentsPane(buttonId);
                        } else {
                            controller.changeModuleComponentsPane(button.moduleName);
                        }
                    } else {
                        controllerImage.resetSelectedId(Id, isItConnector);
                        controller.changeConnectorComponentsPane(null);
                    }
                    controllerImage.unpressElse(Id, isItConnector);
                } else if (!isItConnector) {
//                    controllerImage.resetSelectedId(Id, isItConnector);
                    setNewIconAsModule("com/ardublock/Images/module/start.png");
                    setModuleName("start");
                    setTranslatedName("modules.start.info");
                    controllerImage.resetSelectedId(Id, isItConnector);
                    controller.changeConnectorComponentsPane(null);
                }
            }

            public void mouseEntered(MouseEvent e) {
                mouseIsOnThis=true;
//                System.out.println(buttonId+moduleName+mouseIsOnThis);
                setModuleBig(true);
                if(!isItConnector) if(mouseIsPressedThis) {
//                    System.out.println(buttonId + moduleName + mouseIsOnThis + " must be clear!!!");

                    //controllerImage.resetSelectedId(Id, isItConnector);
                    setNewIconAsModule("com/ardublock/Images/module/" + moduleName + ".png");
//                    setModuleName("start");
//                    setTranslatedName("modules.start.info");
                }
                else{
                    
                }
//                if(mouseIsPressedThis)
                //setIconAccordingToPress();
            }

            public void mouseExited(MouseEvent e) {
                mouseIsOnThis=false;
//                System.out.println(buttonId+moduleName+mouseIsOnThis);
                setModuleBig(false);
                if(!isItConnector) if(mouseIsPressedThis) {
//                    System.out.println(buttonId + moduleName + mouseIsOnThis + " must be clear!!!");

                    //controllerImage.resetSelectedId(Id, isItConnector);
                    setNewIconAsModule("com/ardublock/Images/module/trash.png");
//                    setModuleName("start");
//                    setTranslatedName("modules.start.info");
                }
                else{
                    
                }
                //setIconAccordingToPress();
            }
        });
        rootImage.add(this);
        this.setImages();

    }

    /**
     *
     * @param paths
     */
    public void setSuitableBlocks(ArrayList<String> paths){

    }
    
    /**
     *
     * @param isItBig
     */
    public void setModuleBig(boolean isItBig) { //можно лучше, но мозг в такой жаре не работает
        if (isItConnector) {
            if(isItBig) setBounds(getX() - 10, getY() - 10, getWidth() + 20, getHeight() + 20);
            else setBounds(getX() + 10, getY() + 10, getWidth() - 20, getHeight() - 20);
        } 
        else {
            setButtonBig(isItBig);
        }
        button.rePaint();
    }
    
    private void setButtonBig(boolean isItBig) {
        if (isItBig) {
            switch (buttonId) {
                case "dir04pwm05":
                case "dir07pwm06":
                case "d2":
                case "d3":
                    setBounds(getX() - 5, getY() - 10, getWidth() + 20, getHeight() + 20);
                    break;
                case "d8":
                case "d10":
                case "d9":
                case "d11":
                    setBounds(getX() - 10, getY() - 10, getWidth() + 20, getHeight() + 20);
                    break;
                case "a3":
                case "a2":
                case "a1":
                case "a0":
                    setBounds(getX() - 10, getY() - 10, getWidth() + 20, getHeight() + 20);
                    break;
                case "i2c":
                    setBounds(getX() - 15, getY() - 10, getWidth() + 20, getHeight() + 20);
                    break;
            }
        } else {
            switch (buttonId) {
                case "dir04pwm05":
                case "dir07pwm06":
                case "d2":
                case "d3":
                    setBounds(getX() + 5, getY() + 10, getWidth() - 20, getHeight() - 20);
                    break;
                case "d8":
                case "d10":
                case "d9":
                case "d11":
                    setBounds(getX() + 10, getY() + 10, getWidth() - 20, getHeight() - 20);
                    break;
                case "a3":
                case "a2":
                case "a1":
                case "a0":
                    setBounds(getX() + 10, getY() + 10, getWidth() - 20, getHeight() - 20);
                    break;
                case "i2c":
                    setBounds(getX() + 15, getY() + 10, getWidth() - 20, getHeight() - 20);
                    break;
            }
        }
    }

    //-----------------------------------------------Методы коннекторов------------------------------------------
    private void setIconAsConnector(String Id, int type_of_plate) {
        switch (type_of_plate) {
            case 0:
                switch (Id) {
                    case "dir04pwm05":
                    case "dir07pwm06":
                        path = "com/ardublock/Images/connectors/engine1.png";
                        break;
                    case "d2":
                    case "d3":
                        path = "com/ardublock/Images/connectors/connector1.png";
                        break;
                    case "d8":
                    case "d10":
                    case "d9":
                    case "d11":
                        path = "com/ardublock/Images/connectors/connectorEncUp.png";
                        break;
                    case "a3":
                    case "a2":
                    case "a1":
                    case "a0":
                        path = "com/ardublock/Images/connectors/connectorEncDown.png";
                        break;
                    case "i2c":
                        path = "com/ardublock/Images/connectors/i2c.png";
                        break;
                }
                break;
            case 1:
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
                break;
        }
    }

    private void setStartPositionAsConnector(String Id, int type_of_plate) {
        int width_between_simples;
        int height_between_i2c;
        int width_of_simples;
        int height_of_simples;
        int width_of_i2c;
        int height_of_i2c;
        int where_right_width;
        int where_right_height;
        int where_up_width;
        int where_up_height;
        int where_down_width;
        int where_down_height;
        switch (type_of_plate) {
            case 0:
                width_between_simples = 5;
                height_between_i2c = 2;
                width_of_simples = 27;
                height_of_simples = 20;
                width_of_i2c = 26;
                height_of_i2c = 20;
                where_right_width = 220;
                where_right_height = 108;
                where_up_width = 98;
                where_up_height = 86;
                where_down_width = 98;
                where_down_height = 192;
                switch (Id) {
                    case "dir04pwm05":
                        this.setBounds(this.controller.getX() + 54, this.controller.getY() + 88, 16, 27);
                        break;
                    case "dir07pwm06":
                        this.setBounds(this.controller.getX() + 54, this.controller.getY() + 185, 16, 27);
                        break;
                    case "d2":
                        this.setBounds(this.controller.getX() + where_right_width, this.controller.getY() + where_right_height, height_of_simples, width_of_simples);
                        break;
                    case "d3":
                        this.setBounds(this.controller.getX() + where_right_width, 
                                this.controller.getY() + where_right_height + width_of_simples + width_of_i2c + 2*height_between_i2c, height_of_simples, width_of_simples);
                        break;
                    case "d8":
                        this.setBounds(this.controller.getX() + where_up_width, 
                                this.controller.getY() + where_up_height, width_of_simples, height_of_simples);
                        break;
                    case "d10":
                        this.setBounds(this.controller.getX() + where_up_width + width_of_simples + width_between_simples, 
                                this.controller.getY() + where_up_height, width_of_simples, height_of_simples);
                        break;
                    case "d9":
                        this.setBounds(this.controller.getX() + where_up_width + 2*width_of_simples + 2*width_between_simples, this.controller.getY() + where_up_height, width_of_simples, height_of_simples);
                        break;
                    case "d11":
                        this.setBounds(this.controller.getX() + where_up_width + 3*width_of_simples + 3*width_between_simples, this.controller.getY() + where_up_height, width_of_simples, height_of_simples);
                        break;
                    case "a3":
                        this.setBounds(this.controller.getX() + where_down_width, 
                                this.controller.getY() + where_down_height, width_of_simples, height_of_simples);
                        break;
                    case "a2":
                        this.setBounds(this.controller.getX() + where_down_width + width_of_simples + width_between_simples, 
                                this.controller.getY() + where_down_height, width_of_simples, height_of_simples);
                        break;
                    case "a1":
                        this.setBounds(this.controller.getX() + where_down_width + 2*width_of_simples + 2*width_between_simples, 
                                this.controller.getY() + where_down_height, width_of_simples, height_of_simples);
                        break;
                    case "a0":
                        this.setBounds(this.controller.getX() + where_down_width + 3*width_of_simples + 3*width_between_simples, 
                                this.controller.getY() + where_down_height, width_of_simples, height_of_simples);
                        break;
                    case "i2c":
                        this.setBounds(this.controller.getX() + where_right_width, 
                                this.controller.getY() + where_right_height + width_of_simples + height_between_i2c, height_of_i2c, width_of_i2c);
                        break;

                }
                break;
            case 1:
                
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
                break;
        }
    }

    private void setStartPositionAsModule(String Id, int type_of_plate) {
        int where_up_width;
        int where_up_height;
        int where_right_width;
        int where_right_height;
        int where_down_width;
        int where_down_height;
        int where_left_width;
        int where_left_height;
        int side_of_module;
        int between_modules;
        switch (type_of_plate) {
            case 0:
                side_of_module = 35;
                where_right_width = 250;
                where_right_height = 85;
                where_up_width = 50;
                where_up_height = 27;
                where_down_width = 50;
                where_down_height = 240;
                between_modules = 10;
                switch (Id) {
                    case "dir04pwm05":
                        this.setBounds(this.controller.getX() + 5, this.controller.getY() + 60, side_of_module, side_of_module);
                        break;
                    case "dir07pwm06":
                        this.setBounds(this.controller.getX() + 5, this.controller.getY() + 210, side_of_module, side_of_module);
                        break;
                    case "d2":
                        this.setBounds(this.controller.getX() + where_right_width, this.controller.getY() + where_right_height, side_of_module, side_of_module);
                        break;
                    case "d3":
                        this.setBounds(this.controller.getX() + where_right_width, this.controller.getY() + where_right_height + 2*side_of_module + 2*between_modules, side_of_module, side_of_module);
                        break;
                    case "d8":
                        this.setBounds(this.controller.getX() + where_up_width, this.controller.getY() + where_up_height, 35, 35);
                        break;
                    case "d10":
                        this.setBounds(this.controller.getX() + where_up_width + side_of_module + between_modules, this.controller.getY() + where_up_height, side_of_module, side_of_module);
                        break;
                    case "d9":
                        this.setBounds(this.controller.getX() + where_up_width + 2*side_of_module + 2*between_modules, this.controller.getY() + where_up_height, side_of_module, side_of_module);
                        break;
                    case "d11":
                        this.setBounds(this.controller.getX() + where_up_width + 3*side_of_module + 3*between_modules, this.controller.getY() + where_up_height, side_of_module, side_of_module);
                        break;
                    case "a3":
                        this.setBounds(this.controller.getX() + where_down_width, this.controller.getY() + where_down_height, side_of_module, side_of_module);
                        break;
                    case "a2":
                        this.setBounds(this.controller.getX() + where_down_width + side_of_module + between_modules, this.controller.getY() + where_down_height, side_of_module, side_of_module);
                        break;
                    case "a1":
                        this.setBounds(this.controller.getX() + where_down_width + 2*side_of_module + 2*between_modules, this.controller.getY() + where_down_height, side_of_module, side_of_module);
                        break;
                    case "a0":
                        this.setBounds(this.controller.getX() + where_down_width + 3*side_of_module + 3*between_modules, this.controller.getY() + where_down_height, side_of_module, side_of_module);
                        break;
                    case "i2c":
                        this.setBounds(this.controller.getX() + where_right_width, this.controller.getY() + where_right_height + side_of_module + between_modules, side_of_module, side_of_module);
                        break;

                }
                break;
            case 1:
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
                break;
        }
    }
    
    /**
     *
     * @param type_of_plate
     */
    public void refresh_plate(int type_of_plate){
        switch (mode) {
            case "connector":
            case "Connector":
                this.isItConnector = true;
                this.setStartPositionAsConnector(this.buttonId,this.type_of_plate);
                this.setIconAsConnector(this.buttonId,this.type_of_plate);
                break;
            case "module":
            case "Module":
                this.isItConnector = false;
                this.setStartPositionAsModule(this.buttonId,this.type_of_plate);
                this.setStartIconAsModule();
                break;
            default:
                this.path = null;
                //this.pathSet = null;
                break;
        }
    }

    private void setStartIconAsModule() {
        path = "com/ardublock/Images/module/start.png";
    }

    /**
     *
     * @param iconURL
     */
    public void setNewIconAsModule(URL iconURL) {
        path = iconURL.getPath();
    }

    /**
     *
     * @param Path
     */
    public void setNewIconAsModule(String Path) {
        path = Path;
        this.setImages();
    }

    private String getPathAddedName(String base, String adding) {
        String beforePoint = base.substring(0, base.length() - 4);
        String afterPoint = base.substring(base.length() - 4);
        return beforePoint + adding + afterPoint;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return this.buttonId;
    }

    /**
     *
     * @return
     */
    public String getPath() {
        return this.path;
    }

    /**
     *
     * @return
     */
    public boolean isConnector() {
        return this.isItConnector;
    }

    /**
     *
     */
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
        try {
            image = new ImageIcon(iconURL);
        }
        catch (java.lang.NullPointerException e) {
            //System.out.println(iconURL);
        }
        iconURL = ControllerButton.class.getClassLoader().getResource(getPathAddedName(path, "Set"));
        imageSet = new ImageIcon(iconURL);
        this.setIcon(getScaled(image));
        this.setSelectedIcon(getScaled(imageSet));
    }
    
    /**
     *
     * @param moduleName
     */
    public void setModuleName(String moduleName){
        this.moduleName = moduleName;
    }
    
    /**
     *
     * @param moduleTrName
     */
    public void setTranslatedName(String moduleTrName){
        this.moduleTranslatedName = moduleTrName;
    }
}
