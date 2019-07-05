package com.ardublock.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Settings extends JFrame {

    private ResourceBundle uiMessageBundle;
    private final Preferences userPrefs;

    public Settings() {
        this.setTitle("settings");
        this.setSize(new Dimension(500, 300));
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        
        userPrefs = Preferences.userRoot().node("roboscratch");
        if (this.isFirstLaunch()) {
            userPrefs.putBoolean("is_first_launch", false);
            userPrefs.putBoolean("ardublock.ui.autostart", false);
        }
        
        //uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
        final JTabbedPane tabbedPane = new JTabbedPane();
        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JCheckBox autostart = new JCheckBox("автозапуск");
        panel.add(autostart, BorderLayout.NORTH);
        autostart.setSelected(userPrefs.getBoolean("ardublock.ui.autostart", false));
        autostart.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (autostart.isSelected()) {
                    userPrefs.putBoolean("ardublock.ui.autostart", true);
                }
                else {
                    userPrefs.putBoolean("ardublock.ui.autostart", false);
                }
            }
        });
        this.add(panel, BorderLayout.NORTH);
    }
    
    public boolean isFirstLaunch(){
        return userPrefs.getBoolean("is_first_launch", true);
    }
}
