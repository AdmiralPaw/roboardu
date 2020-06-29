package com.ardublock.ui.ControllerConfiguration;

import com.mit.blocks.codeblockutil.CButton;
import com.mit.blocks.codeblockutil.CGraphite;
import com.mit.blocks.codeblockutil.RMenuButton;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * Класс содержащий настройки (управление ими) кнопки меню контроллера
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class ControllerMenuButton extends CButton {

    /**Данное поле - идентификатор класса в языке Java, используемый при сериализации
    с использованием стадартного алгоритма. Хранится как числовое значение типа long.*/
    private static final long serialVersionUID = 328149080229L;

    /**Поле цвета*/
    Color cat_color;

    /**Поле имени устройства*/
    String deviceName;

    /**Поле переведённого имени устройства*/
    String deviceNameTranslated;

    /**Поле идентификатора*/
    String Id;

    /**Поле кнопки модуля*/
    ControllerButton moduleButton;

    /**Поле настроек контроллера*/
    СontrollerСonfiguration controller;

    /**
     * Метод, устанавливающий кнопку меню контроллера
     * @param controller Настройки контроллера
     * @param text Текст
     * @param tr Видимый текст, он же перевод
     * @param Id Идентификатор порта
     */
    public ControllerMenuButton(СontrollerСonfiguration controller, String text, String tr, String Id) {
        this(controller, text, tr, Id, Color.black);
    }

    /**
     * Метод, управляющий кнопкой меню контроллера и всеми её настройками (Цвет, шрифт, размеры)
     * @param controller Настройки контроллера
     * @param deviceName Имя устройства
     * @param tr Видимый текст, он же перевод
     * @param Id Идентификатор порта
     * @param cat_col - Цвет
     */
    public ControllerMenuButton(СontrollerСonfiguration controller, String deviceName, String tr, String Id, Color cat_col) {
        super(Color.black, CGraphite.blue, tr);
        this.cat_color = cat_col;
        this.deviceName = deviceName;
        this.deviceNameTranslated = tr;
        this.Id = Id;
        this.controller=controller;
        this.moduleButton = controller.controllerImage.callModuleButton(Id);
        this.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        this.setPreferredSize(new Dimension(80, 25));
    }

    /**
     * Метод для рисовки (окрашивания) кнопки менюшки контроллера
     * @param g Параметр графического контекста
     */
    @Override
    public void paint(Graphics g) {
        // Set up graphics and buffer
        //super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set up first layer

        /**Поле высоты кнопки*/
        int buttonHeight = this.getHeight();

        /**Поле ширины кнопки*/
        int buttonWidth = this.getWidth();

        /**Поле размера куба по оси Ох*/
        int cube_x = 38;

        /**Поле размера куба по оси Оу*/
        int cube_y = buttonHeight / 2;

        g2.setPaint(new Color(218, 226, 228));
        g2.drawLine(0, buttonHeight / 2, cube_x, buttonHeight / 2);
        g2.drawLine(cube_x / 3, 0, buttonWidth, 0);
        g2.drawLine(cube_x / 3, buttonHeight, buttonWidth, buttonHeight);
        g2.setPaint(cat_color);
        g2.fillRect(cube_x - 5, cube_y - 5, 10, 10);
        if (this.focus) {
            g2.setPaint(new Color(241, 241, 241));
            /**Поле размера прямоугольника по оси Ох*/
            int rect_x = 50;
            g2.fillRect(rect_x, 1, buttonWidth - rect_x, buttonHeight - 1);
        }

        // Draw the text (if any)
        if (this.getText() != null) {
            if (this.deviceName.equals(this.moduleButton.moduleName)) {
                g2.setColor(new Color(235, 158, 91));
            } 
            else {
                g2.setColor(new Color(19, 144, 148));
            }
            Font font = g2.getFont().deriveFont((float) (((float) buttonHeight) * 0.6));
            g2.setFont(font);
            FontMetrics metrics = g2.getFontMetrics();
            Rectangle2D textBounds = metrics.getStringBounds(this.getText(), g2);
            float x = 60;
            float y = (float) ((1.0 * this.getHeight() - 1.75 * metrics.getDescent())); //2.75
            g2.drawString(this.getText(), x, y);
        }
    }

    /**
     * Метод, указывающий на то, что кнопка мыши была отпущена
     * @param e Событие, указывающее, что в компоненте произошло действие мыши
     */
    public void mouseReleased(MouseEvent e) {
        //System.out.println("mouseReleased");
        //this.moduleButton.setModuleBig(false);
        this.pressed = false;
        repaint();
        this.moduleButton.setNewIconAsModule(
                "com/ardublock/Images/module/" + deviceName + ".png");
        this.moduleButton.setModuleName(deviceName);
        this.moduleButton.setTranslatedName(deviceNameTranslated);
        this.controller.controllerImage.resetSelectedId(Id, true);
        this.controller.changeConnectorComponentsPane(null);
    }

    /**
     * Метод, указывающий на то, что мышь наведена
     * @param e Событие, указывающее, что в компоненте произошло действие мыши
     */
    public void mouseEntered(MouseEvent e){
        //this.moduleButton.setModuleBig(true);
        this.moduleButton.setNewIconAsModule(
                "com/ardublock/Images/module/" + deviceName + ".png");
        this.moduleButton.setModuleName(deviceName);
        this.moduleButton.setTranslatedName(deviceNameTranslated);
    }

    /**
     * Метод, указывающий на то, что мышь не наведена (убрана из зоны наведения)
     * @param e Событие, указывающее, что в компоненте произошло действие мыши
     */
    public void mouseExited(MouseEvent e){
        //this.moduleButton.setModuleBig(false);
    }
}
