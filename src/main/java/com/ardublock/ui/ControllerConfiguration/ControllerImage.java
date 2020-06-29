package com.ardublock.ui.ControllerConfiguration;

import java.awt.Graphics;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Image;
import java.net.URL;

/**
 * Класс, работающий с внешним видом контроллеров
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class ControllerImage extends JPanel {

    /**Поле с типом платы*/
    private int type_of_plate;

    /**Поле с настройками контроллера*/
    private СontrollerСonfiguration controllerConf;

    /**Поле изображения фона*/
    private Image background;

    /**Поле со списком кнопок модулей*/
    public ArrayList<ControllerButton> moduleButtons;

    /**Поле со списком кнопок коннекторов*/
    public ArrayList<ControllerButton> connectorButtons;

    /**Поле со списком имён кнопок контроллеров*/
    private static final String[] names = {
//        "dir04pwm05",
//        "dir07pwm06",
        "d2", "d3", 
        "d8", "d10", "d9", "d11",
        "a3", "a2", "a1", "a0", 
        "i2c"};

    /**
     * Метод для назначения внешнего вида и изображения для платы
     * @param controller Настройки контроллера
     * @param type_of_plate Тип платы
     */
    public ControllerImage(СontrollerСonfiguration controller, int type_of_plate) {
        this.setLayout(null);
        this.setBackground(Color.WHITE);
        this.controllerConf = controller;
        this.type_of_plate = type_of_plate;
        switch(this.type_of_plate){
            case 0:
                background = getImage("com/ardublock/Images/PlataBackground0.png",
                300/*this.getWidth()*/, 300/*this.getHeight()*/);
                break;
            case 1:
                background = getImage("com/ardublock/Images/PlataBackground1.png",
                300/*this.getWidth()*/, 300/*this.getHeight()*/);
                break;
        }
        
        this.initArrays(type_of_plate);
    }

    /**
     * Метод для инициализици списков модулей и коннекторов
     * @param type_of_plate Тип платы
     */
    private void initArrays(int type_of_plate) {
        this.moduleButtons = new ArrayList<ControllerButton>();
        this.connectorButtons = new ArrayList<ControllerButton>();
        for (String i : names) {
            this.moduleButtons.add(new ControllerButton(controllerConf, this, i, "module",type_of_plate));
            this.connectorButtons.add(new ControllerButton(controllerConf, this, i, "connector",type_of_plate));
        }
    }
    
    /**
     * Модуль для выбора подключенной платы
     * @param plate_in Подключенная плата
     */
    public void setPlate(int plate_in){
        this.type_of_plate = plate_in;
    }

    //-------------------------------------ДЛЯ УСТАНОВКИ ИЗОБРАЖЕНИЯ В МОДУЛЬ----------------------------------------------

    /**
     * Метод для установки изображения модуля при помощи пути
     * @param Id Идентификатор
     * @param Path Путь
     */
    public void setModule(String Id, String Path) {
        this.callModuleButton(Id).setNewIconAsModule(Path);
    }

    /**
     * Метод для установки изображения модуля через URL
     * @param Id Идентификатор
     * @param iconURL URL изображения иконки
     */
    public void setModule(String Id, URL iconURL) {
        this.callModuleButton(Id).setNewIconAsModule(iconURL);
    }
    //---------------------------------------------------------------------------------------------------------------------

    /**
     * Метод для получения изображения
     * @param Path Путь
     * @param Width Ширина
     * @param Height Высота
     * @return Увеличенное изображение
     */
    public Image getImage(String Path, int Width, int Height) {
        URL iconURL = ControllerImage.class.getClassLoader().getResource(Path);
        return new ImageIcon(iconURL).getImage().getScaledInstance(
                Width, Height, java.awt.Image.SCALE_SMOOTH);
    }
//

    /**
     * Метод вызова кнопки выбранного модуля
     * @param Id Идентификатор
     * @return Идентификатор кнопки, если не был найден возвращается null
     */
    public ControllerButton callModuleButton(String Id) {
        for (ControllerButton i : moduleButtons) {
            if (Id.equals(i.getId())) {
                return i;
            }
        }
        return null;
    }

    /**
     * Метод вызова кнопки выбранного коннектора
     * @param Id Идентификатор
     * @return Идентификатор кнопки, если не был найден возвращается null
     */
    public ControllerButton callConnectorButton(String Id) {
        for (ControllerButton i : connectorButtons) {
            if (i.getId() == Id) {
                return i;
            }
        }
        return null;
    }

    /**
     * Метод для отжатия всех кнопок
     */
    public void unpressAll() {
        for (Component butt : this.getComponents()) {
            if (butt instanceof ControllerButton) {
                ((ControllerButton) butt).setSelected(false);
            }
        }
    }

    /**
     * Метод, отжимающий оставшиеся кнопки контроллера
     * @param Id Идентификатор
     * @param isConnector Логическая переменная, которая показывает
     * является ли данный компонент коннектором
     */
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

    /**
     * Метод, который устанавливает кнопки контроллера с выбранными идентификаторами
     * @param Id Идентификатор
     * @param isConnector Логическая переменная, которая показывает является
     * ли данный компонент коннектором
     */
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
    
    /**
     * Метод, который сбрасывает кнопки контроллера с выбранными идентификаторами
     * @param Id Идентификатор
     * @param isConnector Логическая переменная, которая показывает является ли
     * данный компонент коннектором
     */
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


    /**
     * Метод, окрашивающий компонент
     * @param g Параметр графического контекста
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }

}
