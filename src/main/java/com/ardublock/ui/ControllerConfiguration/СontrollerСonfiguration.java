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

/**
 * Класс для определения настроек контроллера
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class СontrollerСonfiguration extends JPanel {

    //верхняя панель контроллера

    /**Поле изображения контроллера*/
    public ControllerImage controllerImage;

    /**Поле списка всевозможных компонентов на подключение*/
    private List<Device> components;

    //нижняя панель

    /**Поле панели компонентов*/
    public JPanel componentsPane;

    /**Поле информационной панели модулей*/
    private ModuleInfoPane moduleInfoPane;

    /**Поле сообщений пользовательского интерфейса*/
    private ResourceBundle uiMessageBundle;
<<<<<<< HEAD
   
    //Структура контактов (пинов)
=======

    /**Структура контактов (пинов)*/
>>>>>>> parent of f14dfeb... Revert "Documentation_v1.1"
    public enum Pin {

        //
        dir04pwm05,

        //
        dir07pwm06,

        //
        d2,

        //
        d3,

        // src\main\resources\com\ardublock\Images\lines\d8.png
        d8,

        // src\main\resources\com\ardublock\Images\lines\d10.png
        d10,

        // src\main\resources\com\ardublock\Images\lines\d9.png
        d9,

        // src\main\resources\com\ardublock\Images\lines\d11.png
        d11,

        // src\main\resources\com\ardublock\Images\lines\a3.png
        a3,

        // src\main\resources\com\ardublock\Images\lines\a2.png
        a2,

        // src\main\resources\com\ardublock\Images\lines\a1.png
        a1,

        // src\main\resources\com\ardublock\Images\lines\a0.png
        a0,

        // src\main\resources\com\ardublock\Images\lines\i2c.png
        i2c
    };

    /** Поле контакта контроллера
     *  (ПОХОЖЕ, НИГДЕ НЕ ИСПОЛЬЗУЕТСЯ)
     */
    public Pin controllerPin;

    /**
     * Метод, назначающий настройки контроллера (внешний вид, взаимодействие, расположение, тип платы)
     */
    public СontrollerСonfiguration() {
        super();
        this.setLayout(new BorderLayout());
        this.components = new ArrayList<>();
        this.controllerImage = new ControllerImage(this,0);
        this.componentsPane = new JPanel(new VerticalLayout());
        componentsPane.setBackground(Color.white);
        //buttonPane.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(55, 150, 240)));
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
                controllerImage, componentsPane);
        splitPane.setDividerLocation(300/*750 / 2*/);
        this.add(splitPane);
        moduleInfoPane = new ModuleInfoPane(this);
        uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
        this.resetPane();
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

    /**
     * Метод добавляющий новый компонент
     * @param pin Контакт
     * @param name Имя
     * @param pathToTranslate Путь к переводу
     * @param info Информация
     */
    public void addComponent(String pin, String name, String pathToTranslate, String info) {
        this.components.add(new Device(
                pin,
                name,
                uiMessageBundle.getString(pathToTranslate),
                uiMessageBundle.getString(info)));
    }

    /**
     * Метод, меняющий панель компонентов коннектора
     * @param buttonPin Кнопка контакта
     */
    public void changeConnectorComponentsPane(String buttonPin) {
        componentsPane.removeAll();
        if (buttonPin == null) {
            this.resetPane();
        }
        ControllerMenuButton temp;
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).pin.equals(buttonPin)) {
                temp = new ControllerMenuButton(
                        this,
                        components.get(i).deviceName,
                        components.get(i).deviceNameTranslated,
                        buttonPin);
                componentsPane.add(temp);
            }
        }
        componentsPane.validate();
        componentsPane.repaint();
    }
    
    public void setModuleOnPin(String buttonPin, String moduleName){
        componentsPane.removeAll();
        if (buttonPin == null) {
            this.resetPane();
        }
        ControllerMenuButton temp;
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).pin.equals(buttonPin)) {
                if(components.get(i).deviceName.equalsIgnoreCase(moduleName)){
                    temp = new ControllerMenuButton(
                        this,
                        components.get(i).deviceName,
                        components.get(i).deviceNameTranslated,
                        buttonPin);
                    if(temp!=null){
                        temp.mouseReleased(null);
                    }                   
                }
            }
        }
        componentsPane.validate();
        componentsPane.repaint();
    }
    
    /**
     * Метод, меняющий панель компонентов коннектора
     * @param moduleName Имя модуля
     */
    public void changeModuleComponentsPane(String moduleName) {
        componentsPane.removeAll();
        //if (!moduleName.equals("start")) {
            for (Device device : components) {
                if (device.deviceName.equals(moduleName)) {
                    this.moduleInfoPane.setModuleImage(device.deviceName);
                    this.moduleInfoPane.setModuleName(device.deviceNameTranslated);
                    this.moduleInfoPane.setModuleInfo(device.deviceInfo);
                    this.moduleInfoPane.moduleName = device.deviceName;
                    this.moduleInfoPane.transModuleName = device.deviceNameTranslated;
                    componentsPane.add(this.moduleInfoPane);
                }
            }
        /*} else {
            this.resetPane();
        }*/
        componentsPane.validate();
        componentsPane.repaint();
    }

    /**
     * Метод, сбрасывающий панель компонентов
     */
    public void resetPane() {
        componentsPane.removeAll();
        for (ControllerButton module : this.controllerImage.moduleButtons) {
            if (!module.moduleName.equals("start")) {
                componentsPane.add(new ControllerMenu(
                        this,
                        module.moduleName,
                        module.moduleTranslatedName,
                        module.getId()));
            }
        }
    }

    /**
     * Класс отвечающий за информацию о подключаемом устроистве
     */
    private static class Device {

        /**Поле контакта*/
        final String pin;

        /**Поле имени устройства*/
        final public String deviceName;

        /**Поле переведённого имени устройства*/
        final String deviceNameTranslated;

        /**Поле информации об устройстве*/
        final String deviceInfo;

        /**
         * Метод, для конфигурации информации об устройстве
         * @param pin Контакт
         * @param deviceName Имя устройства
         * @param deviceNameTranslated Переведённое имя устройства
         * @param deviceInfo Информация об устройстве
         */
        public Device(String pin, String deviceName, String deviceNameTranslated, String deviceInfo) {
            this.pin = pin;
            this.deviceName = deviceName;
            this.deviceInfo = deviceInfo;
            this.deviceNameTranslated = deviceNameTranslated;

            //System.out.println("!!!!!!!!!!!!!!!  "+pin+" pin "+deviceName+"  "+deviceNameTranslated);

        }
    }
}
