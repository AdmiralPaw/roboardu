package com.mit.blocks.codeblockutil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;

/**
 * Класс CArrowButton - это совместимый с swing виджет, который позволяет клиентам отображать
 * полупрозрачную стрелку в любом из четырех традиционных направлений: север, юг, восток,
 * запад. Клиенты кнопки со стрелкой могут подписывать триггеры мыши на
 * определенные действия, делая следующее: this.addCButtonListener(new CButtonListener());
 * @author AdmiralPaw, Ritevi, Aizek
 */
public abstract class CArrowButton extends CButton implements ActionListener {

    private static final long serialVersionUID = 328149080231L;

    //Структура направлений
    public enum Direction {

        /**
         *
         */
        NORTH,

        /**
         *
         */
        SOUTH,

        /**
         *
         */
        EAST,

        /**
         *
         */
        WEST
    };


    private static final int m = 3;

    //Поле направления
    private final Direction dir;

    //Цвет подсветки
    private static final Color highlight = CGraphite.blue;

    //Цвет стрелки
    private static final Color arrowColor = CGraphite.blue;

    /**
     * @param dir
     * @effects Constructs a new gray arrow button that
     * 			brightens up to white when mouse-over
     */
    public CArrowButton(Direction dir) {
        super(Color.black, CGraphite.blue, null);
        this.setOpaque(false);
        this.dir = dir;
        this.addActionListener(this);
    }

    private Shape getShape(Direction dir) {
        if (dir == Direction.NORTH) {
            return new GeneralPath();
        } else if (dir == Direction.SOUTH) {
            return new GeneralPath();
        } else if (dir == Direction.EAST) {
            GeneralPath arrow = new GeneralPath();
            arrow.moveTo(m, m);
            arrow.lineTo(this.getWidth() - m, this.getHeight() / 2);
            arrow.lineTo(m, this.getHeight() - m);
            arrow.lineTo(m, m);
            arrow.closePath();
            return arrow;
        } else {//if(dir == WEST){
            GeneralPath arrow = new GeneralPath();
            arrow.moveTo(this.getWidth() - m, m);
            arrow.lineTo(m, this.getHeight() / 2);
            arrow.lineTo(this.getWidth() - m, this.getHeight() - m);
            arrow.lineTo(this.getWidth() - m, m);
            arrow.closePath();
            return arrow;
        }
    }

    /**
     * repaints this
     * @param g
     */
    public void paint(Graphics g) {
        //super.paint(g);
        int w = this.getWidth();
        int h = this.getHeight();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Shape arrow = this.getShape(this.dir);
        if (focus) {
            g2.setColor(Color.gray);
            g2.drawRoundRect(0, 0, w - 1, h - 1, 2 * m, 2 * m);
        }
        if (pressed) {
            //g2.setPaint(new GradientPaint(0, 0, fade, 0, this.getHeight()/2,arrowColor, true));
            g2.setColor(highlight);
            g2.fill(arrow);
            g2.setColor(Color.yellow);
            g2.draw(arrow);
        } else {
            g2.setColor(arrowColor);
            g2.fill(arrow);
            g2.setColor(Color.white);
            g2.draw(arrow);
        }
    }

    /**
     * continue to trigger the action of this arrow as user hold down the arrow
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        this.pressed = true;
        this.repaint();
        //timer.start();
    }

    /**
     * stop triggering the action os this arrow as the user holds down the arrow
     * @param e - Событие, указывающее, что в компоненте произошло действие мыши
     */
    public void mouseReleased(MouseEvent e) {
        this.pressed = false;
        this.repaint();
        //timer.stop();
    }

    /**
     * Этот метод неиспользуется
     * @param e - Событие совершённого действия
     */
    public void actionPerformed(ActionEvent e) {
        triggerAction();
    }

    /**
     * Действие вызывается щелчками мыши и нажатием и удержанием стрелок
     */
    abstract public void triggerAction();
}
