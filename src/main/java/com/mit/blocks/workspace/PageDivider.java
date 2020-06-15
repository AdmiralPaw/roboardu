package com.mit.blocks.workspace;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

/**
 * @author AdmiralPaw, Ritevi, Aizek
 * PageDivider-это JComponent, графически отмечающий границу между двумя страницами.
 * Он содержит ссылку на левую страницу и вызывает мутации на этой странице при взаимодействии
 * пользователя с PageDivider. В частности, когда пользователь нажимает PageDivider,
 * он заставляет левую страницу пересчитать минимальную ширину, вызывая Page.reformMinimumPixelWidth().
 * Когда пользователь перетаскивает PageDivider, он изменяет абстрактную ширину левой страницы,
 * вызывая Page.addPixelWidth(). Обратите внимание, что это только изменяет абстрактную ширину страницы,
 * но не перерисовывает левую часть, чтобы отразить изменение абстрактной ширины.
 * Это делается различными прослушивателями страниц, обрабатываемыми, когда PageDivider сообщает
 * PageChangedEventListener, чтобы уведомить различные PageChangeListeners.
 *
 * Мы наблюдаем следующие случаи использования:
 *      1) пользователь наводит мышь на разделитель страниц
 *      Разделитель страниц растет в ширину
 *
 *      2) пользователь перемещает мышь за пределы разделителя страниц
 *      Разделитель страниц сжимается по ширине
 *
 *      3) пользователь нажимает на разделитель страниц
 *      Страница.минимальная реформы ширина в пикселях() вызывается
 *
 *      4) пользователь перетаскивает разделитель страниц
 *      Страница.добавить ширину в пикселях() вызывается
 *      Вызываются прослушиватели изменений страниц
 * 
 * @specfield leftPage : Page //Cтраница в левой части этого PageDivider
 * @specfield color : Color //Wвет этого PageDivider
 * @specfield mouseIn : boolean flag //true тогда и только тогда, когда мышь находится над этим PageDivider
 */
public class PageDivider extends JComponent implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 328149080272L;
    /** The color of all PageDividers */
    private static final Color DIVIDER_COLOR = Color.ORANGE;
    /** A pointer to the left page of this PageDivider */
    private final Page leftPage;
    /** mouseIn Flag: true if and only if mouse is over this PageDivider */
    private boolean mouseIn = false;
    /** Drag Flag: true if and only if mosue is dragging this PageDivider */
    private boolean dragDone = false;
    /** The x corrdinate in pixel of the last mousePressed on this PageDivider */
    private int mPressedX;
    /** The workspace in use */
    private final Workspace workspace;

    /**
     * @param workspace The workspace in use
     * @param left - the left page belonging to the this PageDivider
     *
     * @requires left != null
     * @effects Constructs a new PageDivider that point to
     * 			"left" as the this PageDivier's left page.
     * 			Any user-generated triggers will mutate the
     * 			"left" page.  This.color is set to Page.DIVIDER_COLOR.
     */
    public PageDivider(Workspace workspace, Page left) {
        this.workspace = workspace;
        leftPage = left;
        setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * @return this.leftPage.  May NOT return null.
     */
    public Page getLeftPage() {
        return leftPage;
    }

    /**
     * renders this PageDivider to nornally be a line,
     * or a thick line with a width of 3 if mouseIn flag
     * is true.
     */
    public void paintComponent(Graphics g) {
        g.setColor(DIVIDER_COLOR);
        g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
        if (mouseIn) {
            g.fillRect(getWidth() / 2 - 1, 0, 3, getHeight());
        }
    }

    /**
     * @param e
     * @modifies this.leftPage
     * @effects reforms the minimum width of this.leftPage
     */
    public void mousePressed(MouseEvent e) {
        mPressedX = e.getX();
        leftPage.reformMinimumPixelWidth();
    }

    /**
     * @param e
     * @effects all WorkspaceListeners
     * @modifies fires and notifies all WorkspaceListener of a Page_Resize event
     */
    public void mouseReleased(MouseEvent e) {
        if (dragDone) {
            workspace.notifyListeners(new WorkspaceEvent(workspace, leftPage, WorkspaceEvent.PAGE_RESIZED, true));
            dragDone = false;
        }
    }

    /**
     * @param e
     * @modifies mouseIn flag
     * @effects sets mouseIn boolean flag to true
     */
    public void mouseEntered(MouseEvent e) {
        mouseIn = true;
        repaint();
    }

    /**
     * @param e
     * @modifies mouseIn flag
     * @effects sets mouseIn boolean flag to false
     */
    public void mouseExited(MouseEvent e) {
        mouseIn = false;
        repaint();
    }

    /**
     * @param e
     * @modifies this.leftPage and all PageChangeListeners
     * @effects adds the delta x change to the elft page's abstract width
     * 			and informs all PageChangeListeners of this change
     */
    public void mouseDragged(MouseEvent e) {
        leftPage.addPixelWidth(e.getX() - mPressedX);
        dragDone = true;
        PageChangeEventManager.notifyListeners();
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }
}
