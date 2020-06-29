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
 * Класс, который подсвечивает (активное, про которое в данный момент рассказывается) рабочее
 * пространство оконной процедуры после того как было открыто руководство для новых пользователей
 * (Или подсвеченная панель)
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class IllumPanel extends JPanel {

    /**Поле времени*/
    private double time = 0.4;

    /**Поле количества кадров в секунду*/
    private int fps = 30;

    /**Поле фреймов (оконных процедур)*/
    private double frames = time * fps;

    /**Поле итератора оконных процедур*/
    private double iteratorOfFrames = 0;

    /**Поле логической переменной, дающей информацию об окончании показа анимации*/
    boolean animationIsFinished = true;

    /**Поле времени начала анимации*/
    public Timer animationTimerStart = null;

    /**Поле количества пульсаций*/
    private int pulses = 8;

    /**Поле счётчика пульсаций*/
    private int pulsingCount = pulses * 2;

    /**Поле цвета RGBA*/
    public Color myColor = new Color(254, 254, 254, 0);

    /**Поле оконной процедуры*/
    private OpenblocksFrame openblocksFrame;

    /**Поле панели обучения*/
    private TutorialPane tutorialPane;

    /**Поле логической переменной, содержащей информацию о прямоугольной части рабочего пространства, которая будет подсвечена*/
    private Boolean rectangle;

    /**
     * Метод, задающий парамерты внешнего вида, местоположения и работы подсвеченной панели
     * @param openblocksFrame Оконная процедура
     * @param tutorialPane Панель руководства
     * @param size Размер
     * @param point Координаты точки
     * @param rectangle Прямоугольная часть рабочего пространства, которая будет подсвечена
     */
    public IllumPanel(OpenblocksFrame openblocksFrame, TutorialPane tutorialPane,
            Dimension size, Point point, Boolean rectangle) {
        this.openblocksFrame = openblocksFrame;
        this.tutorialPane = tutorialPane;
        this.rectangle = rectangle;
        animationTimerStart = new Timer((int) (time * 1000 / frames), new ActionListener() {
            /**
             * Метод для запуска руководства
             * @param e Событие совершённого действия
             */
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
     * Метод, определяющий цвет
     * @param i Прозрачность (Альфа компонент в RGBA)
     */
    public void setColorAlpha(int i) {
        this.myColor = new Color(myColor.getRed(), myColor.getGreen(), myColor.getBlue(), i);
        this.updateUI();
    }

    /**
     * Метод для начала анимации (руководства)
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
     * Метод для включения анимации и её таймера
     */
    public void startAnimation() {
        animationTimerStart.start();
    }

    /**
     * Метод для остановки анимации и её таймера
     */
    public void stopAnimation() {
        animationTimerStart.stop();
        animationIsFinished = true;
        pulsingCount = pulses * 2;
        tutorialPane.remove(this);
    }

    /**
     * Метод для запуска пульсации
     */
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

    /**
     * Метод для остановки пульсации
     */
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
     * Метод для того, чтобы закрасить панель заново, т.е. обновить её
     * @param size Размер
     * @param location Расположение
     */
    public void repaintPanel(Dimension size, Point location) {
        this.setSize(size);
        this.setPreferredSize(size);
        this.setLocation(location);
        this.repaint();
    }

    /**
     * Метод, затемняющий неактивный в данный момент компонент
     * @param g параметр графического контекста
     */
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
