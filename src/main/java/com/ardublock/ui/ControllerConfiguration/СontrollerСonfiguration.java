package com.ardublock.ui.ControllerConfiguration;

import java.awt.*;
import com.mit.blocks.codeblockutil.RMenuButton;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import org.jfree.ui.tabbedui.VerticalLayout;

public class СontrollerСonfiguration extends JPanel {

    //верхняя панель контроллера
    public ControllerImage controllerImage;

    //лист всевозможных компонентов на подключение
    private List<Device> components;
    //нижняя панель
    public JPanel componentsPane;
    private ModuleInfoPane moduleInfoPane;
    private ResourceBundle uiMessageBundle;

    public enum Pin {

        dir04pwm05, dir07pwm06, d2, d3, d8, d10, d9, d11, a3, a2, a1, a0, i2c
    };
    public Pin controllerPin;

    public СontrollerСonfiguration() {
        super();
        this.setLayout(new BorderLayout());
        this.components = new ArrayList<>();
        this.controllerImage = new ControllerImage(this);
        this.componentsPane = new JPanel(new VerticalLayout());
        componentsPane.setBackground(Color.white);
        //buttonPane.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(55, 150, 240)));
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
                controllerImage, componentsPane);
        splitPane.setDividerLocation(300/*750 / 2*/);
        this.add(splitPane);
        moduleInfoPane = new ModuleInfoPane(this);
        uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
        //this.controllerImage.add(justButt);
//        this.addButtons();
    }

//    public void addButtons() {
//        this.controllerImage.add(callButtons());
//    }
//
//    public Container callButtons() {
//        Container allButtons = new Container();
//        JButton testVisibleButton = new JButton();
//        testVisibleButton.setLayout(null);
//        testVisibleButton.setBounds(10, 10, 100, 100);
//        allButtons.add(testVisibleButton);
//        return allButtons;
//    }
    
    public void addComponent(String pin, String name, String pathToTranslate, String info) {
        this.components.add(new Device(
                pin,
                name,
                uiMessageBundle.getString(pathToTranslate),
                uiMessageBundle.getString(info)));
    }

    public void changeConnectorComponentsPane(String buttonPin) {
        componentsPane.removeAll();
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).pin.equals(buttonPin)) {
                componentsPane.add(new ControllerMenuButton(
                        this,
                        components.get(i).deviceName,
                        components.get(i).deviceNameTranslated,
                        buttonPin));
            }
        }
        componentsPane.validate();
        componentsPane.repaint();
    }

    public void changeModuleComponentsPane(String moduleName) {
        componentsPane.removeAll();
        for (Device device : components) {
            if (device.deviceName.equals(moduleName)
                    && !moduleName.equals("first")) {
                this.moduleInfoPane.setModuleImage(device.deviceName);
                this.moduleInfoPane.setModuleName(device.deviceNameTranslated);
                this.moduleInfoPane.setModuleInfo(device.deviceInfo);
                componentsPane.add(this.moduleInfoPane);
            }
        }
        componentsPane.validate();
        componentsPane.repaint();
    }

    /**
     * Класс отвечающий за информацию о подключаемом устроистве
     */
    private static class Device {

        final String pin;
        final String deviceName;
        final String deviceNameTranslated;
        final String deviceInfo;

        public Device(String pin, String deviceName, String deviceNameTranslated, String deviceInfo) {
            this.pin = pin;
            this.deviceName = deviceName;
            this.deviceInfo = deviceInfo;
            this.deviceNameTranslated = deviceNameTranslated;
        }
    }
}
