package com.ardublock.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Area;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import jdk.internal.org.jline.terminal.Size;

/**
 *
 * @author User
 */
public class TutorialPane extends JPanel {
    
    private OpenblocksFrame openblocksFrame;
    private JPanel leftPanel; 
    
    public TutorialPane(OpenblocksFrame openblocksFrame){
        this.openblocksFrame = openblocksFrame;
        Dimension size = openblocksFrame.getContext().getWorkspace().blockCanvasLayer.getSize();
        //System.out.print("Ширина: " + size);
        leftPanel =  new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                final Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 128));
                g2d.fill(new Area(new Rectangle(
                        openblocksFrame.getContext().getWorkspace().getFactorySize())));
                g2d.dispose();
            }
        };;
        leftPanel.setBounds(new Rectangle(
                openblocksFrame.getContext().getWorkspace().getFactorySize()));
        this.add(leftPanel, BorderLayout.CENTER);
    }
    
    
}
