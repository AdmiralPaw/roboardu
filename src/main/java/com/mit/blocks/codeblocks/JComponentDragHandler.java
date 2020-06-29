package com.mit.blocks.codeblocks;

import com.mit.blocks.codeblockutil.GraphicsManager;
import com.mit.blocks.workspace.Workspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;


/**
 * Этот класс можно использовать для добавления возможности перетаскивания в любой JComponent.
 *  Он содержит методы и элементы данных, необходимые для поддержки автоматического перетаскивания, а также
 *  содержит методы для имплиментации как MouseListener, MouseMotionListener.
 *  В общем случае любой существующий JComponent можно сделать легко перетаскиваемым, создав экземпляр
 *  JComponentDragHandler (передавая ссылку на себя) и зарегистрировав JComponentDragHandler в качестве прослушивателя
 *  для всех событий мыши.
 *
 *  Классы, которые нуждаются в подобном, но не идентичном поведении, или которые должны добавить функциональность к
 *  методам мыши здесь, могут создать внутренний класс, который расширяет этот класс. Таким образом, внутренний класс
 *  может поддерживать функциональность Jcommonentdraghandler, а также иметь доступ к элементам данных и методам своего
 *  заключающего класса для целей расширения.
 *  @author AdmiralPaw, Ritevi, Aizek
 */
public class JComponentDragHandler implements MouseListener, MouseMotionListener {

    /**
     * These data members save the point at which the mouse was pressed
     * relative to the (0,0) corner of the JComponent.
     */
    public int mPressedX; //at mouse pressed

    /**
     *
     */
    public int mPressedY; //at mouse pressed

    /**
     *
     */
    public int mCurrentX; //where the mouse is currently

    /**
     *
     */
    public int mCurrentY; //where the mouse is currently

    /**
     *
     */
    public int dragDX; // amount of last drag in X direction

    /**
     *
     */
    public int dragDY; // amount of last drag in Y direction

    /**
     *
     */
    public int oldLocX; //where the component was before dragging

    /**
     *
     */
    public int oldLocY;
    private static Cursor openHandCursor = null;
    private static Cursor closedHandCursor = null;
    /**
     * Stores location data (typically of this JComponent)
     * as a Point for easy manipulation and to avoid re-creating a new object every time
     * these manipulations are done.
     */
    public Point myLoc = new Point();
    private JComponent myComponent;
    private final Workspace workspace;

    /**
     * Creates a new instance of a JComponentDragHandler with a pointer to the
     * given JComponent.  Remember to register this JComponentDragHandler as the
     * listener for mouse events in the JComponent in order for this class to
     * be allowed to handle those events.
     * @param workspace The workspace in use
     * @param jc the JComponent whose mouse events will be handled by this JComponentDragHandler
     */
    public JComponentDragHandler(Workspace workspace, JComponent jc) {
        this.workspace = workspace;
        // this is the JComponent whose mouse events will be handled in this class
        myComponent = jc;
        if (openHandCursor == null || closedHandCursor == null) {
            initHandCursors();
        }
    }

    private static void initHandCursors() {
        openHandCursor = createHandCursor("/com/ardublock/open_hand.png", "openHandCursor");
        closedHandCursor = createHandCursor("/com/ardublock/closed_hand.png", "closedHandCursor");
    }

    /**
     *
     * @param location
     * @param cursorName
     * @return
     */
    public static Cursor createHandCursor(String location, String cursorName) {
        if (GraphicsEnvironment.isHeadless()) {
            // return default hand cursor if headless
            return Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        }

        java.net.URL handURL = JComponentDragHandler.class.getResource(location);
        assert handURL != null : "Can not find hand cursor image " + cursorName;
        ImageIcon handicon = new ImageIcon(handURL);

        Dimension cursize = Toolkit.getDefaultToolkit().getBestCursorSize(handicon.getIconWidth(), handicon.getIconHeight());
        BufferedImage buffImg = GraphicsManager.gc.createCompatibleImage(
                cursize.width,
                cursize.height,
                Transparency.TRANSLUCENT);
        Graphics2D buffImgG2 = (Graphics2D) buffImg.getGraphics();
        Point cpoint = new Point(cursize.width / 2 - handicon.getIconWidth() / 2, cursize.height / 2 - handicon.getIconHeight() / 2);
        buffImgG2.drawImage(handicon.getImage(), cpoint.x, cpoint.y, null);

        return Toolkit.getDefaultToolkit().createCustomCursor(buffImg, new Point(cpoint.x + 5, cpoint.y), cursorName);

    }

    /**
     * Returns the Cursor instance that is used when a mouse is over a draggable object
     * @return the Cursor instance that is used when a mouse is over a draggable object
     */
    public Cursor getDragHintCursor() {
        return openHandCursor;
    }

    /**
     * Returns the Cursor instance that is used on mouse drags
     * @return the Cursor instance that is used on mouse drags
     */
    public Cursor getDraggingCursor() {
        return closedHandCursor;
    }

    /**
     * @return the Point where the mouse is, in the JComponent's coordinate frame
     */
    public Point getMousePoint() {
        return new Point(mCurrentX, mCurrentY);
    }

    ///////////////////
    //MOUSE EVENTS
    ///////////////////
    /**
     * Called when the mouse is pressed over the JComponent.Saves the point (which is measured relative to the JComponent's corner)
 over which the mouse was pressed.
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        myComponent.setCursor(closedHandCursor);
        mPressedX = e.getX();
        mPressedY = e.getY();
        oldLocX = myComponent.getX();
        oldLocY = myComponent.getY();
    }

    /**
     * This method is called when the mouse is dragged over the JComponent.Moves the JComponent by the amount of the drag such that the point
 under which the mouse the pressed remains under the mouse cursor.
     * In
 other words, "drags" the JComponent.
     * @param e
     */
    public void mouseDragged(MouseEvent e) {
        //System.out.println("mouse dragged: "+this.getLocation());
        myComponent.setCursor(closedHandCursor);
        mCurrentX = e.getX();
        mCurrentY = e.getY();
        int dx = mCurrentX - mPressedX;
        int dy = mCurrentY - mPressedY;
        int curX = myComponent.getX();
        int curY = myComponent.getY();

        // shift new location by amount of drag
        int newX = dx + curX;
        int newY = dy + curY;

        /*
         * Prevent dragging outside of the canvas (keep the mouse-down point inside the canvas)
         */
        workspace.scrollToComponent(myComponent);
        Point p = SwingUtilities.convertPoint(myComponent, newX + mPressedX, newY + mPressedY, workspace);
        if (workspace.getWidgetAt(p) == null && !workspace.contains(p)) {
            // how is this not working?  if it's in the window, shouldn't it be dragging?
            // I guess the drawer cards aren't widgets, so it's getting confused...
            //...should add them as widgets but pass calls to the drawer.
            //return; TODO djwendel - is the above way the best to do it?  Figure it out then do it.
        }
        // save how much this drag amount is
        dragDX = newX - myComponent.getX();
        dragDY = newY - myComponent.getY();

        // move to the new location
        myComponent.setLocation(newX, newY);
    }

    /**
     * update the current location of the mouse
     * @param e
     */
    public void mouseMoved(MouseEvent e) {
        mCurrentX = e.getX();
        mCurrentY = e.getY();
    }

    /*
     *  The following methods can be extended by children of this
     *  class, and are provided here to fill out the implementations
     *  of MouseListener and MouseMotionListener.
     */
    public void mouseReleased(MouseEvent e) {
        myComponent.setCursor(openHandCursor);
    }

    public void mouseClicked(MouseEvent arg0) {
        myComponent.setCursor(openHandCursor);
    }

    public void mouseEntered(MouseEvent arg0) {
        myComponent.setCursor(openHandCursor);

    }

    public void mouseExited(MouseEvent arg0) {
        myComponent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

}
