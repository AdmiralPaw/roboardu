package com.ardublock.ui;

import com.mit.blocks.workspace.Workspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class Settings extends JFrame {

    private ResourceBundle uiMessageBundle;
    private final Preferences userPrefs;
    private Settings thisFrame;
    private String mainFont = "TimesNewRoman";
    boolean beginDrag;
    int mousePressX;
    int mousePressY;
    
    int windowWidth = 400;
    int windowHeight = 300;


    public Settings(OpenblocksFrame openblocksFrame) {
        thisFrame = this;
        this.setTitle("settings");
        this.setSize(new Dimension(windowWidth, windowHeight));
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.white);
        this.setUndecorated(true);
        uiMessageBundle = openblocksFrame.getResource();

        userPrefs = Preferences.userRoot().node("roboscratch");
        if (this.isFirstLaunch()) {
            userPrefs.putBoolean("is_first_launch", false);
            userPrefs.putBoolean("ardublock.ui.autostart", false);
            userPrefs.putInt("ardublock.ui.autosaveInterval", 10);
            userPrefs.putInt("ardublock.ui.ctrlzLength", 10);
        }

        //uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
        final JTabbedPane tabbedPane = new JTabbedPane();
        final JPanel panel = new JPanel();
        //panel.setLayout(new BorderLayout());




        beginDrag = false;
        JPanel windowCapPanel = new JPanel();
        windowCapPanel.setLayout(null);
        windowCapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                beginDrag = true;
                mousePressX = e.getX();
                mousePressY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                beginDrag = false;
                int newX = e.getX() - mousePressX;
                int newY = e.getY() - mousePressY;
                thisFrame.setBounds(thisFrame.getX()+newX, thisFrame.getY()+newY,windowWidth,windowHeight);
            }
        });

        windowCapPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (beginDrag)
                {
                    int newX = e.getX() - mousePressX;
                    int newY = e.getY() - mousePressY;
                    thisFrame.setBounds(thisFrame.getX()+newX, thisFrame.getY()+newY,windowWidth,windowHeight);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });

        add(windowCapPanel);
        windowCapPanel.setBounds(0,0,getWidth(),50);
        windowCapPanel.setBackground(Color.WHITE);
        windowCapPanel.setBorder(BorderFactory.createLineBorder(new Color(215,215,215)));


        int size = 16;
        URL iconURL = Workspace.class.getClassLoader().getResource("com/ardublock/X.png");
        ImageIcon button_icon = new ImageIcon(
                new ImageIcon(iconURL).getImage()
                        .getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH));
        JButton XButton = new JButton(button_icon);
        XButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
            }
        });

        windowCapPanel.add(XButton);
        XButton.setBounds(windowCapPanel.getWidth()- size - (windowCapPanel.getHeight() - size)/2,(windowCapPanel.getHeight() - size) /2 ,size,size);
        XButton.setFocusPainted(false);
        XButton.setBackground(windowCapPanel.getBackground());
        XButton.setContentAreaFilled(false);
        XButton.setFocusable(false);

        int rigthOffset = getWidth() - XButton.getX() - size;
        int leftOffset = rigthOffset;

        JLabel text = new JLabel(uiMessageBundle.getString("ardublock.ui.settings"));
        text.setVerticalAlignment(SwingConstants.CENTER);
        text.setFont(new Font(mainFont, Font.BOLD, 15));
        windowCapPanel.add(text);
        text.setBounds(leftOffset,0,100,windowCapPanel.getHeight());


        JPanel windowBodyPanel = new JPanel();
        windowBodyPanel.setLayout(null);
        windowBodyPanel.setBorder(BorderFactory.createLineBorder(new Color(215,215,215)));
        add(windowBodyPanel);
        windowBodyPanel.setBounds(0,windowCapPanel.getHeight()-1,getWidth(), getHeight()- windowCapPanel.getHeight()+1);

        int position = 5;
        int offset = 40;
        int spinnerHeigth = 30;

        text = new JLabel(uiMessageBundle.getString("ardublock.ui.autostart"));
        text.setVerticalAlignment(SwingConstants.CENTER);
        windowBodyPanel.add(text);
        text.setBounds(leftOffset,position,300,40);
        text.setFont(new Font(mainFont, Font.PLAIN, 15));

        RCheckBox autostart = new RCheckBox();
        autostart.setSelected(userPrefs.getBoolean("ardublock.ui.autostart", false));
        windowBodyPanel.add(autostart);
        autostart.setBounds(windowWidth-44-rigthOffset,position,44,40);

        position += offset;

        text = new JLabel(uiMessageBundle.getString("ardublock.ui.autosaveInterval"));
        text.setVerticalAlignment(SwingConstants.CENTER);
        windowBodyPanel.add(text);
        text.setBounds(leftOffset,position,300,40);
        text.setFont(new Font(mainFont, Font.PLAIN, 15));
        RSpinner autosaveInterval = new RSpinner(new SpinnerNumberModel(userPrefs.getInt("ardublock.ui.autosaveInterval", 10)
                                    ,5,120,5));

        windowBodyPanel.add(autosaveInterval);
        autosaveInterval.setBounds(getWidth()-80-rigthOffset, position + offset/2 - spinnerHeigth/2, 80,spinnerHeigth);
        openblocksFrame.setAutosaveInterval(autosaveInterval.getIntValue());

        position += offset;

        text = new JLabel(uiMessageBundle.getString("ardublock.ui.ctrlzLength"));
        text.setVerticalAlignment(SwingConstants.CENTER);
        windowBodyPanel.add(text);
        text.setBounds(leftOffset,position,300,40);
        text.setFont(new Font(mainFont, Font.PLAIN, 15));

        RSpinner queueSize = new RSpinner(new SpinnerNumberModel(userPrefs.getInt("ardublock.ui.ctrlzLength", 10),5,120,5));
        windowBodyPanel.add(queueSize);
        queueSize.setBounds(getWidth()-80-rigthOffset, position + offset/2 - spinnerHeigth/2, 80,spinnerHeigth);

        JButton saveBtn = new RButton(uiMessageBundle.getString("ardublock.ui.saveAndClose"));
        saveBtn.setFont(new Font(mainFont, Font.PLAIN, 15));
        windowBodyPanel.add(saveBtn);
        saveBtn.setBounds(1,windowBodyPanel.getHeight()-40,getWidth()-1, 39);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
                userPrefs.putBoolean("ardublock.ui.autostart", autostart.isSelected());
                userPrefs.putInt("ardublock.ui.autosaveInterval", autosaveInterval.getIntValue());
                userPrefs.putInt("ardublock.ui.ctrlzLength", queueSize.getIntValue());
                openblocksFrame.setAutosaveInterval(autosaveInterval.getIntValue());
            }
        });

    }

    public boolean isFirstLaunch() {
        return userPrefs.getBoolean("is_first_launch", true);
    }
}
