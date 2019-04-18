package edu.mit.blocks.codeblockutil;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RQueryField extends CQueryField {

    public RQueryField() {
        this(null);
    }

    public RQueryField(String text) {
        super(text);
    }

    @Override
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
        g2.setColor(Color.darkGray.brighter());
        g2.draw(this.getMag(w, h));

        g2.setPaint(Color.white);
        g2.fillRect(this.getWidth()/6,this.getHeight()/5, this.getWidth()*4/6, this.getHeight()*3/5);


        //field.paint(g);
    }
}
