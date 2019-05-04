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
    //private ControllerImage controllerImage = new ControllerImage();
    public ControllerImage controllerImage;
    private List<JComponent> components;    
    private JPanel componentsPane;
    private JPanel controllerPane;
    public enum Pin {
        
        dir04pwm05, dir07pwm06, d2, d3, d8, d10, d9, d11, a3, a2, a1, a0, i2c
    };
    public Pin controllerPin;
    
    public СontrollerСonfiguration()
    {
        super();
        this.setLayout(new BorderLayout());
        this.components = new ArrayList<JComponent>();
        this.controllerImage = new ControllerImage(this);
        this.componentsPane = new JPanel(new VerticalLayout());
        componentsPane.setBackground(Color.white);
        //buttonPane.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, new Color(55, 150, 240)));
        setBackground(Color.black);
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true,
				controllerPane, componentsPane);
        this.add(splitPane);
    }

    public void initControllerConfiguration(){
        everythingVisible.add(controllerImage);
    }
    
    public void changeComponentsPane(Pin index){
        
        CButton button = new RMenuButton(item.getName());
        componentsPane.add(button);
    }
    
    
    /**
    * те самые невидимые кнопки, которые будут над местами пинов
    * на картинке
    */
    public class InvisibleButton extends JButton {
        private СontrollerСonfiguration controller; 
        private Pin index;
        /**
         * @param i отвечает за индексирование кнопок
         */
        public void JButton(Pin i){
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

