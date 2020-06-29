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
 * Класс, который затемняет (неактивное, про которое в данный момент не рассказывается)
 * рабочее пространство оконной процедуры после того как было открыто руководство для новых пользователей
 * (Или затемнённая панель)
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class DarkPanel extends JPanel {

    /**Поле времени*/
    private double time = 0.7;

    /**Поле количества кадров в секунду*/
    private int fps = 30;

    /**Поле фреймов (оконных процедур)*/
    private double frames = time * fps;

    /**Поле итератора оконных процедур*/
    private double iteratorOfFrames = frames;

    /**Поле логической переменной, дающей информацию об окончании показа анимации*/
    boolean animationIsFinished = true;

    /**Поле времени начала анимации*/
    public Timer animationTimerStart = null;

    /**Поле времени конца анимации*/
    public Timer animationTimerBack = null;

    /**Поле цвета*/
    private Color myColor = new Color(0, 0, 0, 128);

    /**Поле оконной процедуры*/
    private final OpenblocksFrame openblocksFrame;

    /**Поле панели обучения*/
    private final TutorialPane tutorialPane;

    /**
     * Метод, задающий парамерты внешнего вида, местоположения и работы затемнённой панели
     * @param openblocksFrame - Оконная процедура
     * @param tutorialPane - Панель руководства
     * @param size - Размер
     * @param point - Координаты точки
     */
    public DarkPanel(OpenblocksFrame openblocksFrame, TutorialPane tutorialPane,
            Dimension size, Point point) {
        this.openblocksFrame = openblocksFrame;
        this.tutorialPane = tutorialPane;
        animationTimerStart = new Timer((int) (time * 1000 / frames), new ActionListener() {
            /**
             * Метод для запуска руководства
             * @param e - Событие совершённого действия
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                animationIsFinished = false;
                animaStart();
            }
        });
        animationTimerBack = new Timer((int) (time * 1000 / frames), new ActionListener() {
            /**
             * Метод для завершения руководства
             * @param e - Событие совершённого действия
             */
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
     * Метод, определяющий цвет
     * @param i - Прозрачность (Альфа компонент в RGBA)
     */
    public void setColorAlpha(int i) {
        this.myColor = new Color(0, 0, 0, i);
        this.updateUI();
    }

    /**
     * Метод для включение таймера анимации
     */
    public void startAnimation() {
        animationTimerStart.start();
    }

    /**
     * Метод для выключения таймера анимации
     */
    public void backAnimation() {
        animationTimerBack.start();
    }

    /**
     * Метод для начала анимации (руководства)
     */
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

    /**
     * Метод для окончания анимации (руководства)
     */
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
     * Метод для того, чтобы закрасить панель заново, т.е. обновить её
     * @param size - Размер
     * @param location - Расположение
     */
    public void repaintPanel(Dimension size, Point location) {
        this.setSize(size);
        this.setPreferredSize(size);
        this.setLocation(location);
        this.repaint();
    }

    /**
     * Метод, затемняющий неактивный в данный момент компонент
     * @param g - параметр графического контекста
     */
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
