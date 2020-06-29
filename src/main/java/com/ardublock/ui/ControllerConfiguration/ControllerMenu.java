package com.ardublock.ui.ControllerConfiguration;

import com.mit.blocks.codeblockutil.CButton;
import com.mit.blocks.codeblockutil.CGraphite;
import com.mit.blocks.codeblockutil.RMenuButton;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.SwingUtilities;

/**
 * Класс содержащий настройки меню контроллера и управления ими
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class ControllerMenu extends CButton {

    /**Данное поле - идентификатор класса в языке Java, используемый при сериализации
    с использованием стадартного алгоритма. Хранится как числовое значение типа long.*/
    private static final long serialVersionUID = 328149080229L;

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

    /**Поле стандартного цвета*/
    final Color standartColor = new Color(19, 144, 148);

    /**Поле цвета при нажатии*/
    final Color pressedColor = new Color(235, 158, 91);

    /**
     * Метод, управляющий меню контроллера и всеми его настройками (Цвет, шрифт, размеры)
     * @param controller Настройки контроллера
     * @param deviceName Имя устройства
     * @param tr Видимый текст, он же перевод
     * @param Id Идентификатор порта
     */
    public ControllerMenu(СontrollerСonfiguration controller, String deviceName, String tr, String Id) {
        super(Color.black, CGraphite.blue, tr);
        this.controller = controller;
        this.deviceName = deviceName;
        this.deviceNameTranslated = tr;
        this.Id = Id;
        this.moduleButton = controller.controllerImage.callModuleButton(Id);
        this.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        this.setPreferredSize(new Dimension(80, 25));
    }

    /**
     * Метод для рисовки (окрашивания) менюшки контроллера
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
        if (this.pressed) {
            g2.setPaint(this.pressedColor);
        } else {
            g2.setPaint(this.standartColor);
        }
        g2.fillRect(cube_x - 5, cube_y - 5, 10, 10);
        if (this.focus) {
            g2.setPaint(new Color(241, 241, 241));

            /**Поле размера прямоугольника по оси Ох*/
            int rect_x = 50;

            g2.fillRect(rect_x, 1, buttonWidth - rect_x, buttonHeight - 1);
        }

        // Draw the text (if any)
        if (this.getText() != null) {
            if (this.pressed) {
                g2.setColor(this.pressedColor);
            } else {
                g2.setColor(this.standartColor);
            }
            Font font = g2.getFont().deriveFont((float) (((float) buttonHeight) * 0.6));
            g2.setFont(font);
            FontMetrics metrics = g2.getFontMetrics();
            Rectangle2D textBounds = metrics.getStringBounds(this.getText(), g2);
            float x = 60;
            float y = (float) ((1.0 * this.getHeight() - 1.75 * metrics.getDescent()));
            g2.drawString("Порт " + this.Id.toUpperCase() + " :", x, y);
            x = 128; //112
            g2.setColor(Color.GRAY);
            g2.drawString(this.deviceNameTranslated, x, y);
        }
    }

    /*public void mousePressed(MouseEvent e) {
        
    }*/

    /**
     * Метод, указывающий на то, что кнопка мыши была отпущена
     * @param e Событие, указывающее, что в компоненте произошло действие мыши
     */
    public void mouseReleased(MouseEvent e) {
        //this.moduleButton.setModuleBig(false);
        controller.changeModuleComponentsPane(this.moduleButton.moduleName);
    }
    
    /*private void lightThoseComponents(){
        if (this.pressed) {
            this.pressed = false;
        } else {
            this.pressed = true;
        }
        //TODO: можно и лучше, но лень
        for (Component i : controller.componentsPane.getComponents()) {
            if (i instanceof ControllerMenu) {
                if (!(((ControllerMenu) i) == this) && ((ControllerMenu) i).pressed) {
                    ((ControllerMenu) i).pressed = false;
                    ((ControllerMenu) i).repaint();
                }
            }
        }
        this.controller.controllerImage.unpressElse(Id, false);
        if (this.moduleButton.isSelected()) {
            this.moduleButton.setSelected(false);
        } else {
            this.moduleButton.setSelected(true);
        }
        repaint();
    }*/

    /**
     * Метод, указывающий на то, что мышь наведена
     * @param e Событие, указывающее, что в компоненте произошло действие мыши
     */
    public void mouseEntered(MouseEvent e){
        this.pressed = true;
        for (Component i : controller.componentsPane.getComponents()) {
            if (i instanceof ControllerMenu) {
                if (!(((ControllerMenu) i) == this) && ((ControllerMenu) i).pressed) {
                    ((ControllerMenu) i).pressed = false;
                    ((ControllerMenu) i).repaint();
                }
            }
        }
        this.controller.controllerImage.unpressElse(Id, false);
        this.moduleButton.setSelected(true);
        //this.moduleButton.setModuleBig(true);
        repaint();
    }

    /**
     * Метод, указывающий на то, что мышь не наведена (убрана из зоны наведения)
     * @param e Событие, указывающее, что в компоненте произошло действие мыши
     */
    public void mouseExited(MouseEvent e) {
        this.pressed = false;
        for (Component i : controller.componentsPane.getComponents()) {
            if (i instanceof ControllerMenu) {
                if (!(((ControllerMenu) i) == this) && ((ControllerMenu) i).pressed) {
                    ((ControllerMenu) i).pressed = false;
                    ((ControllerMenu) i).repaint();
                }
            }
        }
        this.controller.controllerImage.unpressElse(Id, false);
        this.moduleButton.setSelected(false);
        //this.moduleButton.setModuleBig(false);
        repaint();
    }
}
