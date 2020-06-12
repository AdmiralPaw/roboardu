package com.mit.blocks.workspace;

import processing.app.PreferencesData;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author User
 */
public class ErrWindow extends JPanel {

    private JScrollPane editorConsole;
    private JTextPane consoleTextPane;
    private JPanel editorStatus;
    private JLabel status;

    private Color backgroundColour = new Color(0, 0, 0);
    private Color backgroundColourStatus = new Color(0, 151, 157);
    private Color backgroundColourError = new Color(227, 76, 0);
    private Color foregroundColour = new Color(255,255,255);

    private Font actualFont = new Font("TimesRoman", Font.PLAIN, 12);
    public int mode;
    /**
     *
     */
    public ErrWindow() {
        setLayout(new BorderLayout());

        this.editorConsole = new JScrollPane();
        this.editorConsole.setBackground(backgroundColour);
        this.consoleTextPane = new JTextPane();
        this.consoleTextPane.setEditable(false);
        this.consoleTextPane.setFocusTraversalKeysEnabled(false);
        this.consoleTextPane.setBackground(backgroundColour);
        this.consoleTextPane.setForeground(foregroundColour);
        this.consoleTextPane.setFont(actualFont);
        JPanel noWrapPanel = new JPanel(new BorderLayout());
        noWrapPanel.add(this.consoleTextPane);
        this.editorConsole.setViewportView(noWrapPanel);
        this.editorConsole.getVerticalScrollBar().setUnitIncrement(7);
        FontMetrics metrics = this.getFontMetrics(actualFont);
        this.editorConsole.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        int height = metrics.getAscent() + metrics.getDescent();
        int lines = 3;
        this.editorConsole.setPreferredSize(new Dimension(100, height * lines));
        this.editorConsole.setMinimumSize(new Dimension(100, height * lines));

        this.editorStatus = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        this.editorStatus.setBackground(backgroundColourStatus);
        this.status = new JLabel();
        this.status.setBackground(backgroundColourStatus);
        this.status.setForeground(foregroundColour);
        this.status.setFont(actualFont);
        this.editorStatus.add(this.status);
        this.editorStatus.setPreferredSize(new Dimension(100, height * 2));
        this.editorStatus.setMinimumSize(new Dimension(100, height * 2));

        add(editorStatus, BorderLayout.NORTH);
        add(this.editorConsole, BorderLayout.CENTER);
        mode = 0;
    }

    /**
     *
     * @param text
     */
    public void setErrText(String text) {
        if (!this.consoleTextPane.getText().equals(text))
            this.consoleTextPane.setText(text);
    }

    /**
     *
     * @param text
     */
    public void setErrTitle(String text) {
        if (!this.status.getText().equals(text))
            this.status.setText(text);
    }

    /**
     *
     * @param title
     * @param text
     */
    public void setErr(String title, String text) {
        setErrTitle(title);
        setErrText(text);
        this.editorStatus.setBackground(backgroundColourError);
        mode = 1;
    }

    public void setErr(String title, String text, Color bgcolor) {
        setErrTitle(title);
        setErrText(text);
        this.editorStatus.setBackground(bgcolor);
    }

    /**
     *
     */
    public void reset() {
        setErrTitle("");
        setErrText("");
        this.editorStatus.setBackground(backgroundColourStatus);
        mode = 0;
    }
}
