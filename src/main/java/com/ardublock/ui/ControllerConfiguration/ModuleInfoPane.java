package com.ardublock.ui.ControllerConfiguration;

import com.mit.blocks.codeblockutil.Window2Explorer;
import com.mit.blocks.controller.WorkspaceController;
import com.mit.blocks.renderable.FactoryRenderableBlock;
import com.mit.blocks.workspace.FactoryCanvas;

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
import java.util.Map;

public class ModuleInfoPane extends JPanel {

    private URL imageURL;
    public String moduleName;
    private String moduleInfo;
    private СontrollerСonfiguration controller;

    private JLabel moduleNameLabel = new JLabel();
    private JLabel moduleIcon = new JLabel();
    private JButton blocksButton = new JButton();
    private JButton closeButton = new JButton();
    private JTextArea moduleInfoLabel = new JTextArea();



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
        infoImagePanel.setBackground(Color.LIGHT_GRAY);
        setButtons();
        JPanel nameAndOthers = new JPanel();
        nameAndOthers.setBackground(Color.LIGHT_GRAY);
        nameAndOthers.setLayout(new FlowLayout(FlowLayout.RIGHT));
        nameAndOthers.add(closeButton);
        nameAndOthers.add(moduleNameLabel);

        blocksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = moduleName.toUpperCase();
                Map<String,String[]> suitableDict = WorkspaceController.suitableBlocks;
                Map<String, JComponent> blocksDict = Window2Explorer.dictionary;
                ArrayList<JComponent> blocks = new ArrayList<JComponent>();
                String[] suitableNames = suitableDict.get(name);

                System.out.println("-------------------");
                for(String s:suitableDict.keySet()){
                    System.out.println(s);
                }
                System.out.println("-----------------------");


                FactoryCanvas fcanvas = new FactoryCanvas("searched canvas", Color.WHITE);
                BoxLayout blout = new BoxLayout(fcanvas, BoxLayout.Y_AXIS);

                fcanvas.setLayout(blout);
                fcanvas.setBackground(Color.WHITE);

                System.out.println("button pressed"+name);

                for(String blockName:suitableNames){
                    System.out.println(blockName);
                    FactoryRenderableBlock block = (FactoryRenderableBlock)(blocksDict.get(blockName));
                    block = block.deepClone();
                    block.setZoomLevel(1);
                    fcanvas.add(block);

                }

            }
        });

        nameAndOthers.add(blocksButton);
        nameAndOthers.setPreferredSize(new Dimension(
                50, 100));
        
        infoImagePanel.add(moduleIcon);
        infoImagePanel.add(nameAndOthers);
        
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
                260, 200));
        infoTextPanel.add(moduleInfoLabel);

        this.add(infoImagePanel, BorderLayout.NORTH);
        this.add(infoTextPanel, BorderLayout.CENTER);
    }
    
    private void setButtons(){
        URL iconURL = ControllerButton.class.getClassLoader().getResource("com/ardublock/Images/closeButton.png");
        ImageIcon image = new ImageIcon(iconURL);
        Image imageRaw = image.getImage().getScaledInstance(
                30, 20, java.awt.Image.SCALE_SMOOTH);
        this.closeButton.setIcon(new ImageIcon(imageRaw));
        this.closeButton.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {

            }
            
            public void mouseReleased(MouseEvent e) {
                controller.changeConnectorComponentsPane(null);
                controller.controllerImage.unpressAll();
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {                
            }
        });
        iconURL = ControllerButton.class.getClassLoader().getResource("com/ardublock/Images/blocksButton.png");
        image = new ImageIcon(iconURL);
        imageRaw = image.getImage().getScaledInstance(
                30, 20, java.awt.Image.SCALE_SMOOTH);
        this.blocksButton.setIcon(new ImageIcon(imageRaw));
    }
    
    public void setButtonAction(String modules){//просто как пример
        this.blocksButton.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {

            }
            
            public void mouseReleased(MouseEvent e) {
                //чтото происходит
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {                
            }
        });
    }

    public void setModuleImage(String deviceName) {
        setImageURL(deviceName+"Info");
        ImageIcon image = new ImageIcon(this.imageURL);
        Image imageRaw = image.getImage().getScaledInstance(
                200, 100, java.awt.Image.SCALE_SMOOTH);
        this.moduleIcon.setIcon(new ImageIcon(imageRaw));
    }

    public void setModuleName(String deviceName) {
        this.moduleNameLabel.setText(deviceName);
    }

    public void setModuleInfo(String info) {
        this.moduleInfo = info;
        this.moduleInfoLabel.setText(this.moduleInfo);
    }

    private void setImageURL(String deviceName) {
        //if (!deviceName.equals("start")) {
            this.imageURL = ModuleInfoPane.class.getClassLoader().getResource(
                    "com/ardublock/Images/module/" + deviceName + ".png");
        //}
    }
}
