package com.mit.blocks.codeblockutil;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.mit.blocks.codeblockutil.CScrollPane.ScrollPolicy;
import java.awt.FlowLayout;

public class CPopupMenu extends JPopupMenu implements ActionListener {

    private static final long serialVersionUID = 328149080311L;
    private int ITEM_HEIGHT = 18;
    private Color background = new Color(236, 236, 236);
    private JComponent scroll;
    private JComponent view;
    private double zoom = 1.0;
    private int items = 0;
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
    }

    @Override
    public Insets getInsets() {
        return new Insets(5, 5, 5, 5);
    }

    public void add(CMenuItem item) {
        items++;
        item.addActionListener(this);
        view.add(item);
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
            this.setVisible(false);
        }
    }




}
