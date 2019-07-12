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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import jdk.internal.org.jline.terminal.Size;

/**
 *
 * @author User
 */
public class TutorialPane extends JPanel {

    private OpenblocksFrame openblocksFrame;
    private AnimPanel FactoryPanel;
    private AnimPanel MenuBarPanel;
    private AnimPanel WorkspacePanel;
    private AnimPanel ControllerPanel;
    private AnimPanel LogoPanel;
    private AnimPanel RightPanel;
    public TutorialPane tutorialPane;
    public List<AnimPanel> activeAnimPanels = new ArrayList<AnimPanel>();
    public List<AnimPanel> tutorAnimPanels = new ArrayList<AnimPanel>();
    public List<Point> tutorLocations = new ArrayList<Point>();
    public List<Dimension> tutorDimensions = new ArrayList<Dimension>();

    public TPanel tutorPanel;

    private final int menuHeight = 22;

    public int iter = 0;
    private boolean tutorIsActive = true;

    public TutorialPane(OpenblocksFrame openblocksFrame) {
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(TutorialPane.class.getName()).log(Level.SEVERE, null, ex);
//        }
        openblocksFrame.validate();
        this.tutorialPane = this;
        this.openblocksFrame = openblocksFrame;
        this.setSize(openblocksFrame.getSize());
        this.setLayout(null);
        this.setOpaque(false);
        Dimension size = openblocksFrame.getContext().getWorkspace().getFactorySize();
        //leftPanel.setSize(size);
        //System.out.print("Ширина: " + size);

        JComponent component = openblocksFrame.logo;

        LogoPanel = new AnimPanel(openblocksFrame, component,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22));

        component = openblocksFrame.rightPanel;
        RightPanel = new AnimPanel(openblocksFrame, component,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22));

        component = openblocksFrame.getContext().
                getWorkspace().getFactoryManager().getNavigator().getJComponent();
        FactoryPanel = new AnimPanel(openblocksFrame, component,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22 + LogoPanel.getHeight())); //TODO menuHeight

        component = openblocksFrame.northPanelCenter;
        MenuBarPanel = new AnimPanel(openblocksFrame, component,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22));

        component = openblocksFrame.getContext().getWorkspace().centerPane;
        WorkspacePanel = new AnimPanel(openblocksFrame, component,
                new Dimension(component.getWidth() + 6, component.getHeight()),
                new Point(component.getX() - 6, component.getY() + 22 + LogoPanel.getHeight()));

        component = openblocksFrame.getContext().getWorkspace().controller;
        ControllerPanel = new AnimPanel(openblocksFrame, component,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22 + LogoPanel.getHeight()));

        activeAnimPanels.add(FactoryPanel);
        tutorAnimPanels.add(WorkspacePanel);
        tutorLocations.add(null);
        tutorDimensions.add(null);

        activeAnimPanels.add(LogoPanel);
        tutorAnimPanels.add(WorkspacePanel);
        tutorLocations.add(null);
        tutorDimensions.add(null);

        activeAnimPanels.add(RightPanel);
        tutorAnimPanels.add(WorkspacePanel);
        tutorLocations.add(null);
        tutorDimensions.add(null);

        activeAnimPanels.add(MenuBarPanel);
        tutorAnimPanels.add(WorkspacePanel);
        tutorLocations.add(null);
        tutorDimensions.add(null);

        activeAnimPanels.add(WorkspacePanel);
        tutorAnimPanels.add(FactoryPanel);
        tutorLocations.add(null);
        tutorDimensions.add(null);

        activeAnimPanels.add(ControllerPanel);
        tutorAnimPanels.add(WorkspacePanel);
        tutorLocations.add(null);
        tutorDimensions.add(null);

        this.add(FactoryPanel);
        this.add(MenuBarPanel);
        this.add(WorkspacePanel);
        this.add(ControllerPanel);
        this.add(LogoPanel);
        this.add(RightPanel);

        JButton exitButton = new JButton("выйти");
        exitButton.setSize(new Dimension(70, 30));
        exitButton.setLocation(0, 0);
        this.FactoryPanel.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tutorialPane.removeAll();
                openblocksFrame.repaint();
            }
        });
        
        tutorPanel = new TPanel(this);
//        test.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                FactoryPanel.startAnimation();
//            }
//        });
        JSplitPane workLayer = openblocksFrame.getContext().getWorkspace().workLayer;
        workLayer.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (workLayer.getDividerLocation() > 380) {
                    workLayer.setDividerLocation(380);
                }
                if (workLayer.getDividerLocation() < 380) {
                    workLayer.setDividerLocation(380);
                }
            }
        });
        //openblocksFrame.setResizable(false);
        this.repaint();
        openblocksFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                openblocksFrame.validate();
                repaintPanels();
                openblocksFrame.getGlassPane().repaint();
                openblocksFrame.repaint();
            }
        });
        this.startTutor();
        //leftPanel.setSize(openblocksFrame.getContext().getWorkspace().getFactorySize());
    }
    
    private void startTutor(){
        this.nextTutor();
    }
    
    public void nextTutor() {
        this.resetPanels();
        this.tutorAnimPanels.get(iter).add(tutorPanel);

        this.activeAnimPanels.get(iter).startAnimation();
//        if (!this.tutorDimensions.get(iter).equals(null)) {
//            tutorPanel.setSize(this.tutorDimensions.get(iter));
//        }
//        if (!this.tutorLocations.get(iter).equals(null)) {
//            tutorPanel.setLocation(this.tutorLocations.get(iter));
//        }

        this.tutorAnimPanels.get(iter).repaint();
    }
    
    void resetPanels(){
        LogoPanel.setColorAlpha(128);
        RightPanel.setColorAlpha(128);
        FactoryPanel.setColorAlpha(128);
        MenuBarPanel.setColorAlpha(128);
        WorkspacePanel.setColorAlpha(128);
        ControllerPanel.setColorAlpha(128);
    }
    
    void repaintPanels() {
        JComponent component = openblocksFrame.logo;
        LogoPanel.repaintPanel(new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22));

        component = openblocksFrame.rightPanel;
        RightPanel.repaintPanel(new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22));

        component = openblocksFrame.getContext().
                getWorkspace().getFactoryManager().getNavigator().getJComponent();
        FactoryPanel.repaintPanel(new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22 + LogoPanel.getHeight()));

        component = openblocksFrame.northPanelCenter;
        MenuBarPanel.repaintPanel(new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22));

        component = openblocksFrame.getContext().getWorkspace().centerPane;
        WorkspacePanel.repaintPanel(new Dimension(component.getWidth() + 6, component.getHeight()),
                new Point(component.getX() - 6, component.getY() + 22 + LogoPanel.getHeight()));

        component = openblocksFrame.getContext().getWorkspace().controller;
        ControllerPanel.repaintPanel(new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22 + LogoPanel.getHeight()));

        this.repaint();
    }
}
