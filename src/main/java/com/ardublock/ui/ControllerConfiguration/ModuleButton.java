/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import com.ardublock.ui.ControllerConfiguration.InvisibleButton;

/**
 *
 * @author Jesus
 */
public class ModuleButton extends InvisibleButton {
    
    private final String path;
    
    public ModuleButton(Icon icon, СontrollerСonfiguration root, ControllerImage rootImage, String Id, String pathToModule) {
        super(icon, root, rootImage, Id);
        this.path = pathToModule;
                
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                //this.controller.changeComponentsPane(buttonId);
            }

            public void mousePressed(MouseEvent e) {
                
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
    
    public String getPath(){
        return this.path;
    }
    
    
    
    public String getAssignedId(){
        return this.getId();
    }
}
