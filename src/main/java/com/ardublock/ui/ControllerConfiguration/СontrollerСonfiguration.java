package com.ardublock.ui.ControllerConfiguration;

import java.awt.*;
import com.ardublock.ui.ControllerConfiguration.ControllerImage;
import com.mit.blocks.codeblockutil.CButton;
import com.mit.blocks.codeblockutil.RMenuButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import org.jfree.ui.tabbedui.VerticalLayout;

public class СontrollerСonfiguration extends JPanel {
    Container everythingVisible = new Container();
    public ControllerImage controllerImage;
    //private ControllerImage controllerImage = new ControllerImage();
    private List<Device> components;
    private JPanel componentsPane;
    public enum Pin {
        
        dir04pwm05, dir07pwm06, d2, d3, d8, d10, d9, d11, a3, a2, a1, a0, i2c
    };
    public Pin controllerPin;

    public СontrollerСonfiguration()
    {
        super();
        this.setLayout(new BorderLayout());
        this.components = new ArrayList<Device>();
        this.controllerImage = new ControllerImage(this);
        this.componentsPane = new JPanel(new VerticalLayout());
        componentsPane.setBackground(Color.white);
        //buttonPane.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(55, 150, 240)));
        setBackground(Color.black);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
				controllerImage, componentsPane);
        this.add(splitPane);
    }
    
    public void addComponent(String name, String pin){
        this.components.add(new Device(pin, name));
    }
    
    public void initControllerConfiguration(){
        everythingVisible.add(controllerImage);
    }
    
    public void changeComponentsPane(int index){
        
        CButton button = new RMenuButton(this.components.get(index).deviceName);
        componentsPane.add(button);
    }
    
    private static class Device {
        final String pin;
        final String deviceName;
        
        public Device(String pin, String deviceName) {
            this.pin = pin;
            this.deviceName = deviceName; 
        }
    }
    /**
    * те самые невидимые кнопки, которые будут над местами пинов
    * на картинке
    */
    public class InvisibleButton extends JButton {
        private СontrollerСonfiguration controller; 
        private int index;
        /**
         * @param i отвечает за индексирование кнопок
         */
        public void JButton(int i){
            index = i;
            this.setOpaque(false);
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
            this.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    controller.changeComponentsPane(index);
                }
            });
        }
    }
}

