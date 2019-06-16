package com.ardublock.ui.ControllerConfiguration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ModuleInfoPane extends JPanel {

    private URL imageURL;
    private String moduleName;
    private String moduleInfo;
    private СontrollerСonfiguration controller;

    private JLabel moduleNameLabel = new JLabel();
    private JLabel moduleIcon = new JLabel();
    private JTextArea moduleInfoLabel = new JTextArea();

    ;

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

        infoImagePanel.add(moduleIcon);
        infoImagePanel.add(moduleNameLabel);
        infoImagePanel.addMouseListener(new MouseListener(){
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

    public void setModuleImage(String deviceName) {
        setImageURL(deviceName);
        this.moduleIcon.setIcon(new ImageIcon(this.imageURL));
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
