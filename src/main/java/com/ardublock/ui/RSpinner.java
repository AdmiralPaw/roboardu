package com.ardublock.ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSpinnerUI;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class RSpinner extends JSpinner {

    public RSpinner(SpinnerNumberModel model)
    {
        super(model);
        this.setUI(new RSpinnerUI());
        this.setBackground(new Color(236,236,236));
        this.setBorder(BorderFactory.createLineBorder(new Color(198,198,198),1));
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int min = e.getComponent().getHeight();
                getComponent(0).setPreferredSize(new Dimension(min, min/2));
                getComponent(1).setPreferredSize(new Dimension(min, min/2));
                getComponent(2).setPreferredSize(new Dimension(e.getComponent().getWidth() - min, e.getComponent().getHeight()/2));
            }
        });

    }

    public int getIntValue()
    {
        return Integer.parseInt(getValue().toString());
    }
}

class RSpinnerUI extends BasicSpinnerUI
{
    public static JSpinner s;
    public static ComponentUI createUI(JComponent c)
    {
        return new RSpinnerUI();

    }

    public Component createNextButton()
    {
        JButton b = new RSpinnerButton(SwingConstants.NORTH);
        b.setName("Spinner.nextButton");
        installNextButtonListeners(b);
        return b;
    }

    public Component createPreviousButton()
    {
        JButton b = new RSpinnerButton(SwingConstants.SOUTH);
        b.setName("Spinner.previousButton");
        installPreviousButtonListeners(b);
        return b;
    }

    public JComponent createEditor()
    {

        return  new SpinEditor(super.spinner);
    }

    public class SpinEditor extends JPanel implements ChangeListener
    {
        JTextField editor;
        public SpinEditor(JSpinner spin)
        {
            super();
            setLayout(new BorderLayout());
            editor = new JTextField();
            editor.setBackground(new Color(236,236,236));
            editor.setText(spinner.getValue().toString());
            editor.setHorizontalAlignment(SwingConstants.CENTER);
            editor.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
            editor.setForeground(new Color(0,152,157));
            editor.setBorder(BorderFactory.createEmptyBorder());
            editor.setEditable(false);
            this.setBackground(new Color(236,236,236));
            add(editor, BorderLayout.CENTER);
            spin.addChangeListener(this);
        }

        public void stateChanged(ChangeEvent e)
        {
            JSpinner spinner = (JSpinner)(e.getSource());
            editor.setText(spinner.getValue().toString());
        }
    }

    public class RSpinnerButton extends JButton
    {
        private int diraction;
        public RSpinnerButton(int dir)
        {
            super();
            diraction = dir;
        }

        public void paint(Graphics g)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRect(0,0,this.getWidth(), getHeight());

            g2.setColor(new Color(198,198,198));
            g2.drawLine(0,0,0,getHeight()-1);

            int xcenter = getWidth() / 2;
            int ypos = diraction == SwingConstants.NORTH ? (int)(1f * getHeight() / 5*4) : (int) (1f*getHeight() / 5);
            g2.setColor(new Color(0,152,157));
            fillTriangle(g2, xcenter,  ypos);
        }

        public void fillTriangle(Graphics2D g, int xpos, int ypos)
        {
            int xoffset = 5;
            int yoffset = 5;
            int[] x = new int[3];
            int[] y = new int[3];

            x[0] =  xpos - xoffset ;
            x[1] =  xpos ;
            x[2] =  xpos + xoffset ;
            if (diraction == SwingConstants.NORTH)
            {
                y[0] =  ypos ;
                y[1] =  ypos - yoffset;
                y[2] =  ypos;
            }
            else
            {
                y[0] =  ypos ;
                y[1] =  ypos + yoffset;
                y[2] =  ypos;
            }

            Polygon p = new Polygon(x,y,3);
            g.fillPolygon(p);

        }
    }


}
