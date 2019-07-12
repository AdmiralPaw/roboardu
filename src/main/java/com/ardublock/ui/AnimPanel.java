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
public class AnimPanel extends JPanel {

    private double time = 0.7;
    private double fps = time * 24;
    private Thread threadAnimate = null;
    private Color myColor;
    private Timer animationTimerStart = null;
    private Timer animationTimerBack = null;
    private double countOfFrames = fps;
    boolean animationIsFinished = false;
    //public TPanel tutorPanel;
    
    private OpenblocksFrame openblocksFrame;
    
    public AnimPanel(OpenblocksFrame openblocksFrame, JComponent component,
            Dimension size, Point point) {
        this.openblocksFrame = openblocksFrame;
        this.myColor = new Color(0, 0, 0, 128);
        animationTimerStart = new Timer((int) (time * 1000 / fps), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationIsFinished = false;
                animaStart();
            }
        });
        animationTimerBack = new Timer((int) (time * 1000 / fps), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationIsFinished = false;
                animaBack();
            }
        });
        this.setLayout(null);
        this.setOpaque(false);
        this.setSize(size);
        this.setPreferredSize(size);
        this.setLocation(point);
        this.repaint();
        //--------------
        //this.tutorPanel = new TPanel();
        //tutorPanel.setLocation(0, 0);
        //tutorPanel.setVisible(false);
        //this.add(tutorPanel);
    }

    public void setColorAlpha(int i) {
        this.myColor = new Color(0, 0, 0, i);
        this.updateUI();
    }

    public void startAnimation() {
        animationTimerStart.start();
    }
    
    public void backAnimation() {
        animationTimerBack.start();
    }

    public void animaStart() {
        countOfFrames = countOfFrames - 1;
        if (countOfFrames < 0) {
            animationTimerStart.stop();
            countOfFrames = fps;
            this.setColorAlpha(0);
            animationIsFinished = true;
        } else {
            this.setColorAlpha((int) (countOfFrames * 128 / fps));
        }
        this.updateUI();
    }
    
    public void animaBack() {
        countOfFrames = countOfFrames - 1;
        if (countOfFrames > fps) {
            animationTimerStart.stop();
            countOfFrames = 0;
            this.setColorAlpha(128);
            animationIsFinished = true;
        } else {
            this.setColorAlpha((int) (countOfFrames * 128 / fps));
        }
        this.updateUI();
    }
    
    
    public void repaintPanel(Dimension size, Point location){
        this.setSize(size);
        this.setPreferredSize(size);
        this.setLocation(location);
        this.repaint();
    }
    
    public void activateTutor(){
        //this.tutorPanel.setVisible(true);
    }
    
    public void deactivateTutor(){
        //this.tutorPanel.setVisible(false);
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
