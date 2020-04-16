package com.mit.blocks.codeblockutil;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;

/**
 *
 * @author User
 */
public class CMenuItem extends JButton implements MouseListener {

    private static final long serialVersionUID = 328149080429L;

    /**
     *
     */
    public enum Position {

        /**
         *
         */
        CENTER,

        /**
         *
         */
        LEFT
    };
    private Color background = new Color(255, 133, 8);//new Color(193,193,193);
    private Color highlight = new Color(236, 236, 236);

    /**
     *
     */
    public boolean focus = false;
    Position textPosition;

    /**
     *
     * @param text
     */
    public CMenuItem(String text) {
        this(text, Position.LEFT);
    }

    /**
     *
     * @param text
     * @param position
     */
    public CMenuItem(String text, Position position) {
        super();
        super.setText(text);
        super.setOpaque(false);
        this.addMouseListener(this);
        textPosition = position;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (focus) {
            g2.setColor(background);
        } else {
            g.setColor(highlight);
        }
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        String text = this.getText();
        if (text != null) {
            Font font = g2.getFont().deriveFont((float) (((float) this.getHeight()) * .8));
            g2.setFont(font);

            FontMetrics metrics = g2.getFontMetrics();
            Rectangle2D textBounds = metrics.getStringBounds(this.getText(), g2);
            double textHeight = textBounds.getHeight();
            double textWidth = textBounds.getWidth() > this.getWidth() ? this.getWidth() / 2 : textBounds.getWidth();
            float y = (float) ((this.getHeight() / 2) + (textHeight / 2)) - metrics.getDescent();
            float x;
            if (textPosition == Position.LEFT) {
                x = 10;
            } else {
                x = (float) ((this.getWidth() / 2) - (textWidth / 2));
            }

            g2.setColor(Color.black);
            g2.drawString(text, x, y);
        }
    }

    public void mouseEntered(MouseEvent e) {
        this.focus = true;
        this.repaint();
    }

    public void mouseExited(MouseEvent e) {
        this.focus = false;
        this.repaint();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }
    
}
