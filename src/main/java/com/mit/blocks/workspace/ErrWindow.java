package com.mit.blocks.workspace;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author AdmiralPaw, Ritevi, Aizek
 *
 */
public class ErrWindow extends JPanel {

    private final JPanel mainPanel;
    private final JLabel errInfo;
    private final JLabel errLabel;
    private final JPanel errDevider;

    /**
     *
     */
    public ErrWindow() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.black);
        errDevider = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        errLabel = new JLabel();
        errLabel.setForeground(Color.white);
        errDevider.setBackground(
                new Color(0, 151, 157));
        errDevider.add(errLabel);
        errDevider.setPreferredSize(
                new Dimension(0, 24));
        mainPanel.add(errDevider, BorderLayout.NORTH);
        JPanel errWindow = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 3));
        errInfo = new JLabel();
        errInfo.setForeground(Color.white);
        errWindow.add(errInfo);
        errWindow.setBackground(Color.black);
        mainPanel.add(errWindow, BorderLayout.CENTER);
    }

    /**
     *
     * @return
     */
    public JPanel getErrPanel() {
        return this.mainPanel;
    }

    /**
     *
     * @param text
     */
    public void setErrText(String text) {
        this.errInfo.setText(text);
        this.mainPanel.repaint();
    }

    /**
     *
     * @param text
     */
    public void setErrTitle(String text) {
        this.errLabel.setText(text);
        this.mainPanel.repaint();
    }

    /**
     *
     * @param title
     * @param text
     */
    public void setErr(String title, String text) {
        setErrTitle(title);
        setErrText(text);
        errDevider.setBackground(new Color(227, 76, 0));
    }

    /**
     *
     */
    public void reset() {
        errDevider.setBackground(
                new Color(0, 151, 157));
        errLabel.setText("");
        errInfo.setText("");
        this.mainPanel.repaint();
    }
}
