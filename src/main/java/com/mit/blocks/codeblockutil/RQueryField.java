package edu.mit.blocks.codeblockutil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

public class RQueryField extends JPanel implements MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 328149080259L;
    private JTextField field;
    private boolean pressed;
    private boolean mouseover;

    public RQueryField() {
        this((String)null);
    }

    public RQueryField(String text) {
        super(new BorderLayout());
        this.pressed = false;
        this.mouseover = false;
        this.field = new JTextField(text);
        this.field.setBorder((Border)null);
        this.field.setFont(new Font("Ariel", 0, 13));
        this.setBounds(0, 0, 200, 20);
        this.setPreferredSize(new Dimension(200, 20));
        this.setOpaque(false);
        this.add(this.field, BorderLayout.CENTER);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.revalidate();
        this.repaint();
    }

    public Insets getInsets() {
        int h = this.getHeight();
        return new Insets(h / 6, h, h / 6, h);
    }

    private Shape getXCross(int w, int h) {
        GeneralPath shape = new GeneralPath();
        shape.moveTo((float)(w - h * 2 / 3), (float)(h / 3));
        shape.lineTo((float)(w - h / 3), (float)(h * 2 / 3));
        shape.moveTo((float)(w - h / 3), (float)(h / 3));
        shape.lineTo((float)(w - h * 2 / 3), (float)(h * 2 / 3));
        return shape;
    }

    private Shape getXBox(int w, int h) {
        Ellipse2D.Double box = new Ellipse2D.Double((double)(w - 5 * h / 6), (double)(h / 6), (double)(2 * h / 3), (double)(2 * h / 3));
        return box;
    }

    private Shape getMag(int w, int h) {
        Ellipse2D.Double e = new Ellipse2D.Double((double)(h / 2), (double)(h / 6), (double)(h * 1 / 3), (double)(h * 1 / 3));
        GeneralPath shape = new GeneralPath();
        shape.moveTo((float)(h / 3), (float)(h * 2 / 3));
        shape.lineTo((float)(h / 2), (float)(h / 2));
        shape.append(e, false);
        return shape;
    }

    public JTextField getQueryField() {
        return this.field;
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int w = this.getWidth();
        int h = this.getHeight();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);


        g2.setColor(new Color(204,204,204));
        g2.fillRect(0, 0, this.getWidth(),this.getHeight());

        g2.setStroke(new BasicStroke(3));
        g2.setColor(new Color(230,230,230));
        g2.draw(this.getMag(w, h));


        super.paint(g);
    }

    public String getText() {
        return this.field.getText();
    }

    public void setText(String text) {
        this.field.setText(text);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        this.mouseover = false;
        this.repaint();
    }

    public void mousePressed(MouseEvent e) {
        if (e.getX() > this.getWidth() - this.getHeight() * 5 / 6) {
            this.pressed = true;
            this.repaint();
        }

    }

    public void mouseReleased(MouseEvent e) {
        if (e.getX() > this.getWidth() - this.getHeight() * 5 / 6) {
            this.field.setText("");
            this.pressed = false;
            this.repaint();
        }

    }

    public void mouseMoved(MouseEvent e) {
        if (e.getX() > this.getWidth() - this.getHeight() * 5 / 6) {
            if (!this.mouseover) {
                this.mouseover = true;
                this.repaint();
            }
        } else if (this.mouseover) {
            this.mouseover = false;
            this.repaint();
        }

    }

    public void mouseDragged(MouseEvent e) {
    }
}

