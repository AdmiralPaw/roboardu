package com.mit.blocks.codeblockutil;

import java.awt.*;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.mit.blocks.codeblockutil.CScrollPane.ScrollPolicy;
import com.mit.blocks.workspace.Workspace;

public class CPopupMenu extends JPopupMenu implements ActionListener {

    private static final long serialVersionUID = 328149080311L;
    private int ITEM_HEIGHT = 18;
    private Color background = new Color(236, 236, 236);
    private JComponent scroll;
    private JComponent view;
    private double zoom = 1.0;
    private int items = 0;
    private Workspace workspace;
    private boolean mouseInComponent;
    private boolean mouseInItem;
    //private int MARGIN = 20;

    CPopupMenu() {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(background);
        this.setOpaque(false);
        this.removeAll();
        this.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(255, 133, 8)));
        view = new JPanel(new GridLayout(0, 1));
        view.setBackground(background);
        scroll = new RHoverScrollPane(view,
                ScrollPolicy.VERTICAL_BAR_AS_NEEDED,
                ScrollPolicy.HORIZONTAL_BAR_NEVER,
                12, new Color(255, 133, 8), Color.darkGray);
        this.add(scroll);
        mouseInComponent = false;
        mouseInItem = false;

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                mouseInComponent = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                mouseInComponent = false;
            }
        });
    }

    CPopupMenu(Workspace work)
    {
        this();
        workspace = work;
    }

    @Override
    public Insets getInsets() {
        return new Insets(5, 5, 5, 5);
    }

    public void add(CMenuItem item) {
        items++;
        item.addActionListener(this);
        view.add(item);
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                mouseInItem = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mouseInItem = false;
            }
        });
    }

    public void setZoomLevel(double zoom) {
        this.zoom = zoom;
        // change the size of the panel and the text
        view.setPreferredSize(new Dimension((int) (100 * this.zoom), (int) (items * ITEM_HEIGHT * this.zoom)));
        this.setPopupSize((int) (160 * this.zoom), (int) (Math.min(items * ITEM_HEIGHT + 10, 160) * this.zoom));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.isVisible()) {
            this.superSetVisible(false);
        }
    }

    public void show(Component c, int x, int y)
    {
        deactiveAllItems();
        super.show(c, x, y);
        workspace.setActiveCPopupMenu(this);
        mouseInComponent = false;
        mouseInItem = false;
    }

    public void setVisible(boolean e)
    {
        if (!e)
        {
            if (!mouseInComponent && !mouseInItem)
            {
                superSetVisible(e);
            }
        }
        else
        {
            superSetVisible(e);
        }
    }

    public void superSetVisible(boolean e)
    {
        super.setVisible(e);
    }

    public void deactiveAllItems()
    {
        for (Component item:view.getComponents())
        {
            if (item instanceof CMenuItem)
            {
                ((CMenuItem) item).focus = false;
            }
        }
    }


}
