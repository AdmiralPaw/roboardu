package com.ardublock.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author User
 */
public class myPanel extends JPanel {

    private double time = 1;
    private double fps = time * 24;
    private Thread threadAnimate = null;
    private Color myColor;
    private Timer animationTimer = null;
    private double countOfFrames = fps;
    
    private OpenblocksFrame openblocksFrame;
    
    public myPanel(OpenblocksFrame openblocksFrame, JComponent component,
            Dimension size, Point point) {
        this.openblocksFrame = openblocksFrame;
        this.myColor = new Color(0, 0, 0, 128);
        animationTimer = new Timer((int) (time * 1000 / fps), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anima();
            }
        });
        this.setLayout(null);
        this.setOpaque(false);
        this.setSize(size);
        this.setPreferredSize(size);
        int x = component.getLocation().x;
        int y = component.getLocation().y + 
                openblocksFrame.getJMenuBar().getSize().height;
        this.setLocation(point);
        this.repaint();
    }

    public void setColorAlpha(int i) {
        this.myColor = new Color(0, 0, 0, i);
    }

    public void startAnimation() {
        animationTimer.start();
    }

    public void anima() {
        countOfFrames = countOfFrames - 1;
        if (countOfFrames < 0) {
            animationTimer.stop();
            countOfFrames = fps;
            this.setColorAlpha(0);
        } else {
            this.setColorAlpha((int) (countOfFrames * 128 / fps));
        }
        this.updateUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(myColor);
        g2d.fill(new Area(new Rectangle(super.getSize())));
        //openblocksFrame.getContext().getWorkspace().getFactorySize())));
        g2d.dispose();
    }
}
