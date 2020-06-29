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
public class DarkPanel extends JPanel {

    private double time = 0.7;
    private int fps = 30;
    private double frames = time * fps;
    private double iteratorOfFrames = frames;
    boolean animationIsFinished = true;

    /**
     *
     */
    public Timer animationTimerStart = null;

    /**
     *
     */
    public Timer animationTimerBack = null;

    private Color myColor = new Color(0, 0, 0, 128);

    private final OpenblocksFrame openblocksFrame;
    private final TutorialPane tutorialPane;

    /**
     *
     * @param openblocksFrame
     * @param tutorialPane
     * @param size
     * @param point
     */
    public DarkPanel(OpenblocksFrame openblocksFrame, TutorialPane tutorialPane,
            Dimension size, Point point) {
        this.openblocksFrame = openblocksFrame;
        this.tutorialPane = tutorialPane;
        animationTimerStart = new Timer((int) (time * 1000 / frames), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationIsFinished = false;
                animaStart();
            }
        });
        animationTimerBack = new Timer((int) (time * 1000 / frames), new ActionListener() {
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
    }

    /**
     *
     * @param i
     */
    public void setColorAlpha(int i) {
        this.myColor = new Color(0, 0, 0, i);
        this.updateUI();
    }

    /**
     *
     */
    public void startAnimation() {
        animationTimerStart.start();
    }

    /**
     *
     */
    public void backAnimation() {
        animationTimerBack.start();
    }

    private void animaStart() {
        iteratorOfFrames = iteratorOfFrames - 1;
        if (iteratorOfFrames < 0) {
            animationTimerStart.stop();
            iteratorOfFrames = frames;
            this.setColorAlpha(0);
            animationIsFinished = true;
        } else {
            this.setColorAlpha((int) (iteratorOfFrames * 128 / frames));
        }
        this.updateUI();
    }

    private void animaBack() {
        iteratorOfFrames = iteratorOfFrames - 1;
        if (iteratorOfFrames > frames) {
            animationTimerStart.stop();
            iteratorOfFrames = 0;
            this.setColorAlpha(128);
            animationIsFinished = true;
        } else {
            this.setColorAlpha((int) (iteratorOfFrames * 128 / frames));
        }
        this.updateUI();
    }

    /**
     *
     * @param size
     * @param location
     */
    public void repaintPanel(Dimension size, Point location) {
        this.setSize(size);
        this.setPreferredSize(size);
        this.setLocation(location);
        this.repaint();
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
