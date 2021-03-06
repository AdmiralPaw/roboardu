package com.ardublock.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author User
 */
public class IllumPanel extends JPanel {

    private double time = 0.4;
    private int fps = 30;
    private double frames = time * fps;
    private double iteratorOfFrames = 0;
    boolean animationIsFinished = true;

    /**
     *
     */
    public Timer animationTimerStart = null;

    private int pulses = 8;
    private int pulsingCount = pulses * 2;

    /**
     *
     */
    public Color myColor = new Color(254, 254, 254, 0);

    private OpenblocksFrame openblocksFrame;
    private TutorialPane tutorialPane;
    private Boolean rectangle;

    /**
     *
     * @param openblocksFrame
     * @param tutorialPane
     * @param size
     * @param point
     * @param rectangle
     */
    public IllumPanel(OpenblocksFrame openblocksFrame, TutorialPane tutorialPane,
            Dimension size, Point point, Boolean rectangle) {
        this.openblocksFrame = openblocksFrame;
        this.tutorialPane = tutorialPane;
        this.rectangle = rectangle;
        animationTimerStart = new Timer((int) (time * 1000 / frames), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationIsFinished = false;
                animaStart();
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
        this.myColor = new Color(myColor.getRed(), myColor.getGreen(), myColor.getBlue(), i);
        this.updateUI();
    }

    /**
     *
     */
    public void animaStart() {
        if (tutorialPane.activeAnimPanels.get(tutorialPane.iter).get(0).animationIsFinished) {
            if (pulsingCount != 0) {
                if (pulsingCount % 2 == 0) {
                    this.startPulse();
                } else {
                    this.backPulse();
                }
            } else {
                animationTimerStart.stop();
                animationIsFinished = true;
                pulsingCount = pulses * 2;
                tutorialPane.remove(this);
            }
        }
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
    public void stopAnimation() {
        animationTimerStart.stop();
        animationIsFinished = true;
        pulsingCount = pulses * 2;
        tutorialPane.remove(this);
    }

    private void startPulse() {
        iteratorOfFrames = iteratorOfFrames + 1;
        if (iteratorOfFrames > frames) {
            pulsingCount--;
            iteratorOfFrames = frames;
        } else {
            this.setColorAlpha((int) (iteratorOfFrames * 192 / frames));
        }
        this.updateUI();
    }

    private void backPulse() {
        iteratorOfFrames = iteratorOfFrames - 1;
        if (iteratorOfFrames < 0) {
            pulsingCount--;
            iteratorOfFrames = 0;
        } else {
            this.setColorAlpha((int) (iteratorOfFrames * 192 / frames));
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
        //super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g.create();
        if (rectangle) {
            g2d.setColor(myColor);
            g2d.fill(new Area(new Rectangle(super.getSize())));
        } else {
            g2d.setColor(myColor);
            g2d.fill(new Area(new Ellipse2D.Double(0, 0, super.getSize().width + 1, super.getSize().height + 1)));
        }
        g2d.dispose();
    }
}
