package com.ardublock.ui.ControllerConfiguration;

import com.mit.blocks.codeblockutil.CScrollPane;
import com.mit.blocks.codeblockutil.Explorer;
import com.mit.blocks.codeblockutil.RHoverScrollPane;
import com.mit.blocks.codeblockutil.Window2Explorer;
import com.mit.blocks.controller.WorkspaceController;
import com.mit.blocks.renderable.FactoryRenderableBlock;
import com.mit.blocks.workspace.FactoryCanvas;
import com.mit.blocks.workspace.SearchBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Класс, содержащий информацию о панели модулей
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class ModuleInfoPane extends JPanel {

    /**Поле с URL ссылкой на изображение*/
    private URL imageURL;

    /**Поле имени модуля*/
    public String moduleName;

    /**Поле с переведённым именем модуля*/
    public String transModuleName;

    /**Поле информации о модуле*/
    private String moduleInfo;

    /**Поле настроек контроллера*/
    private СontrollerСonfiguration controller;

    /**Поле имени метки модуля*/
    private JTextArea moduleNameLabel = new JTextArea();

    /**Поле иконки модуля*/
    private JLabel moduleIcon = new JLabel();

    /**Поле кнопки блоков*/
    private JButton blocksButton = new JButton();

    /**Поле кнопки закрытия*/
    private JButton closeButton = new JButton();

    /**Поле метки информации о модуле*/
    private JTextArea moduleInfoLabel = new JTextArea();

    /**
     * Метод для настройки внешнего вида информационной панели модулей (цвет, размеры, фон, метод взаимодействия)
     * @param controller Настройки контроллера
     */
    public ModuleInfoPane(СontrollerСonfiguration controller) {
        super();
        this.controller = controller;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        JPanel infoTextPanel = new JPanel();
        JPanel infoImagePanel = new JPanel();
        infoTextPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 35, 0));
        infoImagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        infoTextPanel.setBackground(Color.WHITE);
        infoImagePanel.setBackground(new Color(98,169,171));
        setButtons();
        JPanel twoButtons = new JPanel();
        twoButtons.setBackground(new Color(98,169,171));
        twoButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));

        blocksButton.addActionListener(new ActionListener() {
            /**
             * Метод для отображения информации о модуле (как будет выглядеть всплывающая информация,
             * текст, фон, как будет происходить её прокрутка и т.д.)
             * @param e Событие совершённого действия
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = moduleName;
                String transName = transModuleName;
                Map<String,String[]> suitableDict = WorkspaceController.suitableBlocks;
                Map<String, JComponent> blocksDict = Window2Explorer.dictionary;
                String[] suitableNames = suitableDict.get(transName);

                /*System.out.println("-------------------");
                for(String s:suitableDict.keySet()){
                    //System.out.println(s);
                }
                System.out.println("-----------------------");*/

                FactoryCanvas fcanvas = new FactoryCanvas("searched canvas", Color.WHITE);
                //BoxLayout blout = new BoxLayout(fcanvas, BoxLayout.Y_AXIS);

                //fcanvas.setLayout(blout);
                fcanvas.setBackground(Color.WHITE);

                //System.out.println("button pressed"+name + transModuleName);

                for(String blockName:suitableNames){

                    //System.out.println(blockName);
                    //крч эту дичь можно изменить (подудалить ненужное), главное правильно заполнить мой XML

                        FactoryRenderableBlock block = (FactoryRenderableBlock)(blocksDict.get(blockName.toUpperCase()));
                        block = block.deepClone();
                        block.setZoomLevel(1);
                        fcanvas.add(block);

                }

                fcanvas.layoutBlocks();
                JComponent scroll = new RHoverScrollPane(
                        fcanvas,
                        CScrollPane.ScrollPolicy.VERTICAL_BAR_AS_NEEDED,
                        CScrollPane.ScrollPolicy.HORIZONTAL_BAR_AS_NEEDED,
                        15, Color.BLACK, Color.darkGray);

                List<Explorer> exList = SearchBar.workspace.getFactoryManager().getNavigator().getExplorers();
                for (Explorer ex : exList) {
                    if (ex instanceof Window2Explorer) {
                        ((Window2Explorer) ex).setSearchResult(scroll);
                    }
                }

            }
        });

        twoButtons.add(blocksButton);
        twoButtons.add(closeButton);
        twoButtons.setPreferredSize(new Dimension(80,100));
        twoButtons.setAlignmentX(RIGHT_ALIGNMENT);
        twoButtons.setAlignmentY(TOP_ALIGNMENT);
        
        
        
        moduleNameLabel.setFont(new Font("TimesNewRoman", Font.PLAIN, 16));
        moduleNameLabel.setForeground(Color.BLACK);
        moduleNameLabel.setBackground(Color.WHITE);
        moduleNameLabel.setWrapStyleWord(true);
        moduleNameLabel.setLineWrap(true);
        moduleNameLabel.setEditable(false);
        moduleNameLabel.setPreferredSize(new Dimension(
                260, 30));
        infoTextPanel.add(moduleNameLabel);
        
        infoImagePanel.add(moduleIcon);
        infoImagePanel.add(twoButtons);
        
        /*infoImagePanel.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {

            }
            
            public void mouseReleased(MouseEvent e) {
                controller.changeConnectorComponentsPane(null);
                controller.controllerImage.unpressAll();
            }

            public void mouseEntered(MouseEvent e) {
                infoImagePanel.setBackground(new Color(235, 158, 91));
            }

            public void mouseExited(MouseEvent e) {
                infoImagePanel.setBackground(Color.LIGHT_GRAY);                
            }
        });*/

        moduleInfoLabel.setFont(new Font("TimesNewRoman", Font.PLAIN, 14));
        moduleInfoLabel.setWrapStyleWord(true);
        moduleInfoLabel.setLineWrap(true);
        moduleInfoLabel.setEditable(false);
        moduleInfoLabel.setPreferredSize(new Dimension(
                260, 170));
        infoTextPanel.add(moduleInfoLabel);

        this.add(infoImagePanel, BorderLayout.NORTH);
        this.add(infoTextPanel, BorderLayout.CENTER);
    }

    /**
     * Метод для назначения параметров кнопок, их изображений, их действий
     */
    private void setButtons(){
        closeButton.setPreferredSize(new Dimension(23,23));
        blocksButton.setPreferredSize(new Dimension(23,23));
        URL iconURL = ControllerButton.class.getClassLoader().getResource("com/ardublock/Images/closeButton.png");
        ImageIcon image = new ImageIcon(iconURL);
        Image imageRaw = image.getImage().getScaledInstance(
                23, 23, java.awt.Image.SCALE_SMOOTH);
        this.closeButton.setIcon(new ImageIcon(imageRaw));
        iconURL = ControllerButton.class.getClassLoader().getResource("com/ardublock/Images/closeButtonSet.png");
        image = new ImageIcon(iconURL);
        imageRaw = image.getImage().getScaledInstance(
                23, 23, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(imageRaw);
        //this.closeButton.setSelectedIcon(image);

        this.closeButton.setPressedIcon(image);
        this.closeButton.setSelectedIcon(image);
        this.closeButton.setRolloverIcon(image);
        this.closeButton.addMouseListener(new MouseListener(){

            /**
             * Метод, указывающий на то, что кнопка мыши была нажата
             * @param e Событие, указывающее, что в компоненте произошло действие мыши
             */
            public void mouseClicked(MouseEvent e) {

            }

            /**
             * Метод, указывающий на то, что кнопка мыши была нажата (Тоже самое?)
             * (Возможная разница: Click - быстрое нажатие, Press - зажатая кнопка мыши)
             * @param e Событие, указывающее, что в компоненте произошло действие мыши
             */
            public void mousePressed(MouseEvent e) {

            }

            /**
             * Метод, указывающий на то, что кнопка мыши была отпущена
             * @param e Событие, указывающее, что в компоненте произошло действие мыши
             */
            public void mouseReleased(MouseEvent e) {
                controller.changeConnectorComponentsPane(null);
                controller.controllerImage.unpressAll();
            }

            /**
             * Метод, указывающий на то, что мышь наведена
             * @param e Событие, указывающее, что в компоненте произошло действие мыши
             */
            public void mouseEntered(MouseEvent e) {
                closeButton.setSelected(true);
            }

            /**
             * Метод, указывающий на то, что мышь не наведена (убрана из зоны наведения)
             * @param e Событие, указывающее, что в компоненте произошло действие мыши
             */
            public void mouseExited(MouseEvent e) {    
                closeButton.setSelected(false);            
            }
        });
        iconURL = ControllerButton.class.getClassLoader().getResource("com/ardublock/Images/blocksButton.png");
        image = new ImageIcon(iconURL);
        imageRaw = image.getImage().getScaledInstance(
                23, 23, java.awt.Image.SCALE_SMOOTH);
        this.blocksButton.setIcon(new ImageIcon(imageRaw));
        iconURL = ControllerButton.class.getClassLoader().getResource("com/ardublock/Images/blocksButtonSet.png");
        image = new ImageIcon(iconURL);
        imageRaw = image.getImage().getScaledInstance(
                23, 23, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(imageRaw);
        this.blocksButton.setPressedIcon(image);
        this.blocksButton.setSelectedIcon(image);
        this.blocksButton.setRolloverIcon(image);
        /*this.blocksButton.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {

            }
            
            public void mouseReleased(MouseEvent e) {
                
            }

            public void mouseEntered(MouseEvent e) {
                blocksButton.setSelected(true);
            }

            public void mouseExited(MouseEvent e) {    
                blocksButton.setSelected(false);            
            }
        });*/
    }
    
    /**
     * Метод настройки действий кнопки
     * @param modules Выбранные модули
     */
    public void setButtonAction(String modules){//просто как пример
        this.blocksButton.addMouseListener(new MouseListener(){
            /**
             * Метод, указывающий на то, что кнопка мыши была нажата
             * @param e Событие, указывающее, что в компоненте произошло действие мыши
             */
            public void mouseClicked(MouseEvent e) {

            }

            /**
             * Метод, указывающий на то, что кнопка мыши была нажата (Тоже самое?)
             * (Возможная разница: Click - быстрое нажатие, Press - зажатая кнопка мыши)
             * @param e Событие, указывающее, что в компоненте произошло действие мыши
             */
            public void mousePressed(MouseEvent e) {

            }

            /**
             * Метод, указывающий на то, что кнопка мыши была отпущена
             * @param e Событие, указывающее, что в компоненте произошло действие мыши
             */
            public void mouseReleased(MouseEvent e) {
                //чтото происходит
            }

            /**
             * Метод, указывающий на то, что мышь наведена
             * @param e Событие, указывающее, что в компоненте произошло действие мыши
             */
            public void mouseEntered(MouseEvent e) {
            }

            /**
             * Метод, указывающий на то, что мышь не наведена (убрана из зоны наведения)
             * @param e Событие, указывающее, что в компоненте произошло действие мыши
             */
            public void mouseExited(MouseEvent e) {                
            }
        });
    }

    /**
     * Метод для выбора изображения модуля
     * @param deviceName Имя устройства
     */
    public void setModuleImage(String deviceName) {
        setImageURL(deviceName+"Info");
        ImageIcon image = new ImageIcon(this.imageURL);
        Image imageRaw = image.getImage().getScaledInstance(
                170, 100, java.awt.Image.SCALE_SMOOTH);
        this.moduleIcon.setIcon(new ImageIcon(imageRaw));
    }

    /**
     * Метод для присвоения имени модулю
     * @param deviceName Имя устройства
     */
    public void setModuleName(String deviceName) {
        this.moduleNameLabel.setText(deviceName);
    }

    /**
     * Метод для назначения информации к модулю
     * @param info Информация о выбранном модуле
     */
    public void setModuleInfo(String info) {
        this.moduleInfo = info;
        this.moduleInfoLabel.setText(this.moduleInfo);
    }

    /**
     * Метод для выбора изображения модуля при помощи URL
     * @param deviceName Имя устройства
     */
    private void setImageURL(String deviceName) {
        //if (!deviceName.equals("start")) {
            this.imageURL = ModuleInfoPane.class.getClassLoader().getResource(
                    "com/ardublock/Images/module/" + deviceName + ".png");
        //}
    }
}
