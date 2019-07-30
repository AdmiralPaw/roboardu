package com.ardublock.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RCheckBox extends JPanel
{

    public JCheckBox box;
    public RCheckBox ()
    {
        super();
        box = new JCheckBox("");
        box.setVisible(false);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.getX() >= getWidth()/2-22 && e.getX() <= getWidth()/2+22 && e.getY() >= getHeight()/2-12 && e.getY() <= getHeight()/2+12)
                {
                    setSelected(box.isSelected() ? false : true);
                    revalidate();
                    repaint();
                }
            }
        });
    }

    public void setSelected(boolean e)
    {
        box.setSelected(e);
    }

    public boolean isSelected()
    {
        return box.isSelected();
    }

    public void addItemListener(ItemListener e)
    {
        box.addItemListener(e);
    }



    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRect(0,0,getWidth(), getHeight());
        if (isSelected())
        {
            g2.setColor(new Color(0,152,157));

        }
        else
        {
            g2.setColor(new Color(193,193,193));
        }
        g2.fillRoundRect(getWidth()/2-20,getHeight()/2-10,40,20,20,20);

        int x;
        if (isSelected())
        {
            g2.setColor(new Color(0,145,150));
            g2.fillOval(getWidth()/2-2,getHeight()/2-10-2,24,24);
            g2.setColor(new Color(236,236,236));
            g2.fillOval(getWidth()/2,getHeight()/2-10,20,20);
        }
        else
        {
            g2.fillOval(getWidth()/2-20-2,getHeight()/2-10-2,24,24);
            g2.setColor(new Color(236,236,236));
            g2.fillOval(getWidth()/2-20,getHeight()/2-10,20,20);
        }


    }
}

