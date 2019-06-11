package com.ardublock.ui.ControllerConfiguration;

import com.mit.blocks.codeblockutil.CButton;
import com.mit.blocks.codeblockutil.CGraphite;
import com.mit.blocks.codeblockutil.RMenuButton;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class ControllerMenuButton extends CButton {

    private static final long serialVersionUID = 328149080229L;

    Color cat_color;
    String deviceName;
    String Id;
    ControllerButton moduleButton;

    public ControllerMenuButton(СontrollerСonfiguration controller, String text, String Id) {
        this(controller, text, Id, Color.black);
    }

    public ControllerMenuButton(СontrollerСonfiguration controller, String text, String Id, Color cat_col) {
        super(Color.black, CGraphite.blue, text);
        this.cat_color = cat_col;
        this.deviceName = text;
        this.Id = Id;
        this.moduleButton = controller.controllerImage.callModuleButton(Id);
        this.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        this.setPreferredSize(new Dimension(80, 25));
    }

    /**
     * re paints this
     */
    @Override
    public void paint(Graphics g) {
        // Set up graphics and buffer
        //super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set up first layer
        int buttonHeight = this.getHeight();
        int buttonWidth = this.getWidth();
        int cube_x = 38;
        int cube_y = buttonHeight / 2;
        g2.setPaint(new Color(218, 226, 228));
        g2.drawLine(0, buttonHeight / 2, cube_x, buttonHeight / 2);
        g2.drawLine(cube_x / 3, 0, buttonWidth, 0);
        g2.drawLine(cube_x / 3, buttonHeight, buttonWidth, buttonHeight);
        g2.setPaint(cat_color);
        g2.fillRect(cube_x - 5, cube_y - 5, 10, 10);
        if (this.focus) {
            g2.setPaint(new Color(241, 241, 241));
            int rect_x = 50;
            g2.fillRect(rect_x, 1, buttonWidth - rect_x, buttonHeight - 1);
        }

        // Draw the text (if any)
        if (this.getText() != null) {
            g2.setColor(new Color(19, 144, 148));
            Font font = g2.getFont().deriveFont((float) (((float) buttonHeight) * 0.4));
            g2.setFont(font);
            FontMetrics metrics = g2.getFontMetrics();
            Rectangle2D textBounds = metrics.getStringBounds(this.getText(), g2);
            float x = 60;
            float y = (float) ((1.0 * this.getHeight() - 2.75 * metrics.getDescent()));
            g2.drawString(this.getText(), x, y);
        }
    }

    public void mouseReleased(MouseEvent e) {
        this.pressed = false;
        repaint();
        this.moduleButton.setNewIconAsModule(
                "com/ardublock/Images/module/" + deviceName.toLowerCase() + ".png");
    }

}
