package com.mit.blocks.codeblockutil;

import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BoundedRangeModel;
import javax.swing.JLayeredPane;

/**
 * CScrollPane-это совместимый со Swing виджет, который позволяет клиентам этого CScrollPane
 * управлять шириной большого пальца, цветом большого пальца и цветом дорожки.
 * Как и все совместимые со Swing панели прокрутки, CScrollPane оборачивает видовой
 * экран и должен изменять пространство просмотра (также известное как видимый прямоугольник)
 * панели прокрутки, когда пользователи пытаются прокрутить его
 * с помощью мыши, колесика или клавиатуры.
 * @author AdmiralPaw, Ritevi, Aizek
 */
public abstract class CScrollPane extends JLayeredPane implements MouseWheelListener, KeyListener {

    /**
     *
     */
    public enum ScrollPolicy {

        /**
         *
         */
        HORIZONTAL_BAR_ALWAYS,

        /**
         *
         */
        HORIZONTAL_BAR_NEVER,

        /**
         *
         */
        HORIZONTAL_BAR_AS_NEEDED,

        /**
         *
         */
        VERTICAL_BAR_ALWAYS,

        /**
         *
         */
        VERTICAL_BAR_NEVER,

        /**
         *
         */
        VERTICAL_BAR_AS_NEEDED
    };

    CScrollPane() {
        super();
    }

    /**
     * @return vertical scroll bar bounding range model.  May be null
     */
    abstract public BoundedRangeModel getVerticalModel();

    /**
     * @return horizontal scroll bar bounding range model.  May be null
     */
    abstract public BoundedRangeModel getHorizontalModel();

    /**
     * Scrolls the view so that Rectangle  within the view becomes visible.
     * This attempts to validate the view before scrolling if the view is
     * currently not valid - isValid returns false. To avoid excessive
     * validation when the containment hierarchy is being created this
     * will not validate if one of the ancestors does not have a peer,
     * or there is no validate root ancestor, or one of the ancestors
     * is not a Window or Applet.
     *
     * Note that this method will not scroll outside of the valid viewport;
     * for example, if contentRect is larger than the viewport, scrolling
     * will be confined to the viewport's bounds.
     *
     * @param contentRect - the Rectangle to display
     */
    abstract public void scrollRectToVisible(Rectangle contentRect);

    /**
     * Set the amount by which the mouse wheel scrolls
     * @param x
     * @requires INTEGER_MIN<x<INTEGER_MAX
     * @modifies this.SCROLLINGUNIT
     * @effects set this.scrollingunit to x
     */
    abstract public void setScrollingUnit(int x);

    /**
     * MouseWheelListener: Should move the viewport by same amount of wheel scroll
     * @param e
     */
    abstract public void mouseWheelMoved(MouseWheelEvent e);
}
