package com.ardublock.ui.ControllerConfiguration;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class InvisibleButton extends JButton {

    private ImageIcon image;
    private final СontrollerСonfiguration controller;
    private final String buttonId;

    /**
     * @param controller
     * @param buttonId отвечает за индексирование кнопок
     */
    public InvisibleButton(СontrollerСonfiguration root, String Id) {
        this.controller = root;
        this.buttonId = Id;
//        this.setOpaque(false);
//        this.setContentAreaFilled(false);
//        this.setBorderPainted(false);
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                controller.changeComponentsPane(buttonId);
            }

            public void mousePressed(MouseEvent e) {
                controller.changeComponentsPane(buttonId);
            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }
        }
        );
    }
}
