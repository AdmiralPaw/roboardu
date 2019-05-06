package com.ardublock.ui.ControllerConfiguration;

import javax.swing.*;

public class InvisibleButton extends JButton {

    public void JButton(){
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
    }

}
