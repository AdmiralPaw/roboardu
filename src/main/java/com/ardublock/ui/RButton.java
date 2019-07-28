package com.ardublock.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RButton extends JButton {
    public RButton(String text)
    {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setFocusable(false);
        setBorder(BorderFactory.createEmptyBorder());
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(new Color(0,152,157));
        g2.fillRect(0,0,getWidth(),getHeight());

        if (this.getText() != null) {
            g2.setColor(Color.white);
            Font font = new Font(Font.DIALOG_INPUT, Font.PLAIN, 15);
            g2.setFont(font);
            FontMetrics metrics = g2.getFontMetrics();
            Rectangle2D textBounds = metrics.getStringBounds(this.getText(), g2);
            float x = (float)(getWidth() / 2 - textBounds.getWidth()/2);
            float y = (float) ((1.0*this.getHeight() - 2.75 * metrics.getDescent()));
            g2.drawString(this.getText(), x, y);
        }
    }
}
