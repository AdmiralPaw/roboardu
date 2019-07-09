package com.ardublock.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Area;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import jdk.internal.org.jline.terminal.Size;

/**
 *
 * @author User
 */
public class TutorialPane extends JPanel {

    private OpenblocksFrame openblocksFrame;
    private myPanel FactoryPanel;
    private myPanel MenuBarPanel;
    private myPanel WorkspacePanel;
    private myPanel ControllerPanel;
    private myPanel LogoPanel;
    private myPanel RightPanel;

    private final int menuHeight = 22;

    public TutorialPane(OpenblocksFrame openblocksFrame) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TutorialPane.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.openblocksFrame = openblocksFrame;
        this.setSize(openblocksFrame.getSize());
        this.setLayout(null);
        this.setOpaque(false);
        Dimension size = openblocksFrame.getContext().getWorkspace().getFactorySize();
        //leftPanel.setSize(size);
        //System.out.print("Ширина: " + size);
        
        JComponent component = openblocksFrame.logo;
        
        LogoPanel = new myPanel(openblocksFrame, component,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22));

        component = openblocksFrame.rightPanel;
        RightPanel = new myPanel(openblocksFrame, component,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22));

        component = openblocksFrame.getContext().
                getWorkspace().getFactoryManager().getNavigator().getJComponent();
        FactoryPanel = new myPanel(openblocksFrame, component,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22 + LogoPanel.getHeight())); //TODO menuHeight

        component = openblocksFrame.northPanelCenter;
        MenuBarPanel = new myPanel(openblocksFrame, component,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22));

        component = openblocksFrame.getContext().getWorkspace().centerPane;
        WorkspacePanel = new myPanel(openblocksFrame, component,
                new Dimension(component.getWidth() + 6, component.getHeight()),
                new Point(component.getX() - 6, component.getY() + 22 + LogoPanel.getHeight()));

        component = openblocksFrame.getContext().getWorkspace().controller;
        ControllerPanel = new myPanel(openblocksFrame, component,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22 + LogoPanel.getHeight()));


        this.add(FactoryPanel);
        this.add(MenuBarPanel);
        this.add(WorkspacePanel);
        this.add(ControllerPanel);
        this.add(LogoPanel);
        this.add(RightPanel);

//        JPanel northPanel = new JPanel(new BorderLayout());
//        northPanel.setOpaque(false);
//        JPanel menuBar = new JPanel();
//        menuBar.setOpaque(false);
//        //menuBar.setSize(0, 22);
//        menuBar.setPreferredSize(new Dimension(22, 0));
//        myPanel logo = new myPanel(this.openblocksFrame);
//        myPanel topConfig = new myPanel(this.openblocksFrame);
//        myPanel buttons = new myPanel(this.openblocksFrame);
//        
//        northPanel.add(menuBar, BorderLayout.NORTH);
//        northPanel.add(logo, BorderLayout.WEST);
//        northPanel.add(topConfig, BorderLayout.EAST);
//        northPanel.add(buttons, BorderLayout.CENTER);
//        
//        
//        JPanel leftPanel  = new JPanel(new BorderLayout());
//        leftPanel.setOpaque(false);
//        myPanel categoryButtons = new myPanel(this.openblocksFrame);
//        myPanel search  = new myPanel(this.openblocksFrame);
//        myPanel blocks = new myPanel(this.openblocksFrame);
//        
//        leftPanel.add(categoryButtons, BorderLayout.NORTH);
//        leftPanel.add(search, BorderLayout.CENTER);
//        leftPanel.add(blocks, BorderLayout.SOUTH);
//        
//        
//        JPanel rightPanel = new JPanel(new BorderLayout());
//        rightPanel.setOpaque(false);
//        myPanel configImage = new myPanel(this.openblocksFrame);
//        myPanel configInfo = new myPanel(this.openblocksFrame);
//        
//        rightPanel.add(configImage, BorderLayout.NORTH);
//        rightPanel.add(configInfo, BorderLayout.CENTER);
//        
//        
//        JPanel centralPanel  = new JPanel(new BorderLayout());
//        centralPanel.setOpaque(false);
//        myPanel workspace = new myPanel(this.openblocksFrame);
//        centralPanel.add(workspace, BorderLayout.CENTER);
//        
//        this.add(northPanel, BorderLayout.NORTH);
//        this.add(centralPanel, BorderLayout.CENTER);
//        this.add(leftPanel, BorderLayout.WEST);
//        this.add(rightPanel, BorderLayout.EAST);
        JButton test = new JButton("123");

        test.setForeground(Color.red);

        this.WorkspacePanel.add(test);
        test.setBounds(new Rectangle(500, 200, 100, 100));
        test.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FactoryPanel.startAnimation();
            }
        });

        this.updateUI();
        //leftPanel.setSize(openblocksFrame.getContext().getWorkspace().getFactorySize());
    }

}
