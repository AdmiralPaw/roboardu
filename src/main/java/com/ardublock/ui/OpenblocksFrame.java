package com.ardublock.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import com.ardublock.core.Context;
import com.ardublock.ui.listener.ArdublockWorkspaceListener;
import com.ardublock.ui.listener.GenerateCodeButtonListener;
import com.ardublock.ui.listener.NewButtonListener;
import com.ardublock.ui.listener.OpenButtonListener;
import com.ardublock.ui.listener.OpenblocksFrameListener;
import com.ardublock.ui.listener.SaveAsButtonListener;
import com.ardublock.ui.listener.SaveButtonListener;
import com.ardublock.ui.ControllerConfiguration.СontrollerСonfiguration;

import com.mit.blocks.controller.WorkspaceController;
import com.mit.blocks.workspace.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;

public class OpenblocksFrame extends JFrame {

    private static final long serialVersionUID = 2841155965906223806L;

    private Context context;
    private JFileChooser fileChooser;
    private FileFilter ffilter;
    private ErrWindow errWindow;
    private Settings settings;

    private ResourceBundle uiMessageBundle;

    private boolean controllerIsShown = true;

    public void addListener(OpenblocksFrameListener ofl) {
        context.registerOpenblocksFrameListener(ofl);
    }


    public static void deleteAllBlocks(){
        Page.blocksContainer.removeAll();
        Page.blocksContainer.revalidate();
        Page.blocksContainer.repaint();
    }

    public String makeFrameTitle() {
        String title;
        if (!context.getSaveFileName().endsWith(".abp")) {
            title = context.getSaveFileName() + ".abp" + " | ";
        } else {
            title = context.getSaveFileName() + " | ";
        }
        title = title + Context.APP_NAME + " v." + "0.1";
        if (context.isWorkspaceChanged()) {
            title = title + " *";
        }
        return title;

    }

    public OpenblocksFrame() {
        context = Context.getContext();
        settings = new Settings();
        this.setTitle(makeFrameTitle());
        this.setSize(new Dimension(1024, 760));
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setLayout(new BorderLayout());
        //put the frame to the center of screen
        this.setLocationRelativeTo(null);
        uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
        //25.03.2019
        Image icon = new ImageIcon(OpenblocksFrame.class.getClassLoader().getResource(
                "com/ardublock/block/mainIcon.png")).getImage();
        this.setIconImage(icon);
        //25.03.2019

        fileChooser = new JFileChooser();
        ffilter = new FileNameExtensionFilter(uiMessageBundle.getString("ardublock.file.suffix"), "abp");
        fileChooser.setFileFilter(ffilter);
        fileChooser.addChoosableFileFilter(ffilter);

        initOpenBlocks();
    }

    private void initOpenBlocks() {


        final Context context = Context.getContext();
        final Workspace workspace = context.getWorkspace();
        errWindow = workspace.getErrWindow();
        workspace.addWorkspaceListener(new ArdublockWorkspaceListener(this));
        workspace.setMinimumSize(new Dimension(100, 0));

        // <editor-fold defaultstate="collapsed" desc="menu">
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(uiMessageBundle.getString("ardublock.ui.file"));
        JMenuItem newItem = new JMenuItem(uiMessageBundle.getString("ardublock.ui.new"));
        JMenuItem openItem = new JMenuItem(uiMessageBundle.getString("ardublock.ui.open") + "...");
        JMenuItem saveItem = new JMenuItem(uiMessageBundle.getString("ardublock.ui.save"));
        JMenuItem saveAsItem = new JMenuItem(uiMessageBundle.getString("ardublock.ui.saveAs") + "...");
        JMenuItem settingsItem = new JMenuItem(uiMessageBundle.getString("ardublock.ui.settings"));
        JMenuItem exitItem = new JMenuItem(uiMessageBundle.getString("ardublock.ui.exit"));
        JMenu toolsMenu = new JMenu(uiMessageBundle.getString("ardublock.ui.tools"));
        JMenuItem verifyItem = new JMenuItem(uiMessageBundle.getString("ardublock.ui.verify"));
        JMenuItem uploadItem = new JMenuItem(uiMessageBundle.getString("ardublock.ui.upload"));
        JMenuItem serialMonitorItem = new JMenuItem(uiMessageBundle.getString("ardublock.ui.serialMonitor"));
        JMenuItem saveImageItem = new JMenuItem(uiMessageBundle.getString("ardublock.ui.saveImage"));

        //JMenuItem deleteAll = new JMenuItem(uiMessageBundle.getString("ardublock.ui.saveImage"));




        newItem.addActionListener(new NewButtonListener(this));
        KeyStroke newButStr = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
        newItem.setAccelerator(newButStr);

        openItem.addActionListener(new OpenButtonListener(this));
        KeyStroke openButStr = KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK);
        openItem.setAccelerator(openButStr);

        saveItem.addActionListener(new SaveButtonListener(this));
        KeyStroke saveButStr = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
        saveItem.setAccelerator(saveButStr);

        saveAsItem.addActionListener(new SaveAsButtonListener(this));
        KeyStroke saveAsButStr = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
        saveAsItem.setAccelerator(saveAsButStr);



        settingsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                settings.setVisible(true);
            }
        });
        KeyStroke settingStr = KeyStroke.getKeyStroke(KeyEvent.VK_WINDOWS, InputEvent.CTRL_DOWN_MASK);
        settingsItem.setAccelerator(settingStr);

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        KeyStroke exitStr = KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK);
        exitItem.setAccelerator(exitStr);

        verifyItem.addActionListener(new GenerateCodeButtonListener(this, context));
        verifyItem.setActionCommand("VERIFY_CODE");
        KeyStroke verifyButStr = KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK);
        verifyItem.setAccelerator(verifyButStr);

        uploadItem.addActionListener(new GenerateCodeButtonListener(this, context));
        uploadItem.setActionCommand("UPLOAD_CODE");
        KeyStroke generateButStr = KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK);
        uploadItem.setAccelerator(generateButStr);

        serialMonitorItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                context.getEditor().handleSerial();
            }
        });
        KeyStroke serialMonitorButStr = KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
        serialMonitorItem.setAccelerator(serialMonitorButStr);

        saveImageItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Dimension size = workspace.getCanvasSize();
                //System.out.println("size: " + size);
                BufferedImage bi = new BufferedImage(2560, 2560, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = (Graphics2D) bi.createGraphics();
                double theScaleFactor = (300d / 72d);
                g.scale(theScaleFactor, theScaleFactor);

                workspace.getBlockCanvas().getPageAt(0).getJComponent().paint(g);
                try {
                    final JFileChooser fc = new JFileChooser();
                    fc.setSelectedFile(new File("ardublock.png"));
                    int returnVal = fc.showSaveDialog(workspace.getBlockCanvas().getJComponent());
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        ImageIO.write(bi, "png", file);
                    }
                } catch (Exception e1) {

                } finally {
                    g.dispose();
                }
            }
        });

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(settingsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        toolsMenu.add(verifyItem);
        toolsMenu.add(uploadItem);
        toolsMenu.add(serialMonitorItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);

        // </editor-fold>

        //Panels------------------------------------------------------//
        final int standartNorthPanelSize = 24;  //TODO: make like constant

        final JPanel northPanel = new JPanel();
        final JPanel logo = new JPanel();
        final JPanel northPanelCenter = new JPanel();
        final JPanel buttons = new JPanel();
        final JPanel panelWithConfigButton = new JPanel();
        final JPanel rightPanel = new JPanel();
        final JLabel dividerFirst = new JLabel();
        final JLabel dividerSecond = new JLabel();

        //Main logo---------------------------------------------------//
        final JLabel mainLogo = new JLabel();
        ImageIcon mLogo = new ImageIcon(OpenblocksFrame.class.getClassLoader().getResource(
                "com/ardublock/block/mainLogo2.png")
        );
        Image image = mLogo.getImage().getScaledInstance(180, standartNorthPanelSize,
                java.awt.Image.SCALE_SMOOTH);
        mLogo = new ImageIcon(image);
        mainLogo.setIcon(mLogo);
        logo.add(mainLogo);
        logo.setBackground(new Color(0, 151, 157));
        logo.setPreferredSize(workspace.getFactorySize());
        logo.setBounds(38, 0, workspace.getFactorySize().width, workspace.getFactorySize().height);

        panelWithConfigButton.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttons.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        logo.setLayout(new FlowLayout(FlowLayout.LEFT, 38, 5));
        northPanel.setLayout(new BorderLayout());
        northPanelCenter.setLayout(new BorderLayout());

        rightPanel.setBackground(new Color(0, 151, 157));
        buttons.setBackground(new Color(0, 100, 104));
        panelWithConfigButton.setBackground(new Color(0, 100, 104));
        northPanel.setBackground(new Color(0, 100, 104));
        northPanelCenter.setBackground(new Color(0, 100, 104));

        northPanelCenter.setMinimumSize(new Dimension(600, standartNorthPanelSize));
        rightPanel.setPreferredSize(new Dimension(310, standartNorthPanelSize));
        dividerFirst.setPreferredSize(new Dimension(2, 2));
        dividerSecond.setPreferredSize(new Dimension(2, 2));

        northPanel.add(logo, BorderLayout.WEST);

        northPanelCenter.add(buttons, BorderLayout.WEST);
        northPanelCenter.add(panelWithConfigButton, BorderLayout.EAST);

        northPanel.add(northPanelCenter, BorderLayout.CENTER);
        northPanel.add(rightPanel, BorderLayout.EAST);

        JLabel infoLabel = new JLabel(); //displays text
        infoLabel.setPreferredSize(new Dimension(150, 20));
        infoLabel.setForeground(Color.white);

        //<editor-fold defaultstate="collapsed" desc="Buttons images and listners">


        ImageButton deleteAll = new ImageButton(
                "deleteAllBlocks",
                "com/ardublock/block/buttons/newA.jpg",
                "com/ardublock/block/buttons/newB.jpg",
                new JLabel()
        );

        deleteAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAllBlocks();
            }
        });


        ImageButton newButton = new ImageButton(
                "new",
                "com/ardublock/block/buttons/newA.jpg",
                "com/ardublock/block/buttons/newB.jpg",
                infoLabel
        );
        newButton.addActionListener(new NewButtonListener(this));

        ImageButton saveButton = new ImageButton(
                "save",
                "com/ardublock/block/buttons/saveA.jpg",
                "com/ardublock/block/buttons/saveB.jpg",
                infoLabel
        );
        saveButton.addActionListener(new SaveButtonListener(this));

        ImageButton saveAsButton = new ImageButton(
                "saveAs",
                "com/ardublock/block/buttons/saveAsA.jpg",
                "com/ardublock/block/buttons/saveAsB.jpg",
                infoLabel
        );
        saveAsButton.addActionListener(new SaveAsButtonListener(this));


        ImageButton openButton = new ImageButton(
                "open",
                "com/ardublock/block/buttons/openA.jpg",
                "com/ardublock/block/buttons/openB.jpg",
                infoLabel
        );
        openButton.addActionListener(new OpenButtonListener(this));
        

        ImageButton verifyButton = new ImageButton("verify program",
                "com/ardublock/block/buttons/verifyA.jpg",
                "com/ardublock/block/buttons/verifyB.jpg",
                infoLabel
        );
        verifyButton.setActionCommand("VERIFY_CODE");
        verifyButton.addActionListener(new GenerateCodeButtonListener(this, context));

        ImageButton generateButton = new ImageButton(
                "upload to Arduino",
                "com/ardublock/block/buttons/uploadA.jpg",
                "com/ardublock/block/buttons/uploadB.jpg",
                infoLabel
        );
        generateButton.setActionCommand("UPLOAD_CODE");
        generateButton.addActionListener(new GenerateCodeButtonListener(this, context));

        ImageButton serialMonitorButton = new ImageButton(
                "serialMonitor",
                "com/ardublock/block/buttons/monitorA.jpg",
                "com/ardublock/block/buttons/monitorB.jpg",
                infoLabel
        );
        serialMonitorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                context.getEditor().handleSerial();
            }
        });
        ImageButton saveImageButton = new ImageButton(
                "save image",
                "com/ardublock/block/buttons/saveAsImageA.jpg",
                "com/ardublock/block/buttons/saveAsImageB.jpg",
                infoLabel
        );
        saveImageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Dimension size = workspace.getCanvasSize();
                //System.out.println("size: " + size);
                BufferedImage bi = new BufferedImage(2560, 2560, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = (Graphics2D) bi.createGraphics();
                double theScaleFactor = (300d / 72d);
                g.scale(theScaleFactor, theScaleFactor);

                workspace.getBlockCanvas().getPageAt(0).getJComponent().paint(g);
                try {
                    final JFileChooser fc = new JFileChooser();
                    fc.setSelectedFile(new File("ardublock.png"));
                    int returnVal = fc.showSaveDialog(workspace.getBlockCanvas().getJComponent());
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        ImageIO.write(bi, "png", file);
                    }
                } catch (Exception e1) {

                } finally {
                    g.dispose();
                }
            }
        });

        ImageButton websiteButton = new ImageButton(
                "website",
                "com/ardublock/block/buttons/websiteA.jpg",
                "com/ardublock/block/buttons/websiteB.jpg",
                infoLabel
        );
        websiteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                URL url;
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        url = new URL("http://ardublock.com");
                        desktop.browse(url.toURI());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        ImageButton configButton = new ImageButton(
                "controller",
                "com/ardublock/block/buttons/showPanelsA.jpg",
                "com/ardublock/block/buttons/showPanelsB.jpg",
                null
        );
        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controllerIsShown) {
                    workspace.blockCanvasLayer.remove(workspace.controller);
                    northPanel.remove(rightPanel);
                    controllerIsShown = false;
                } else {
                    workspace.blockCanvasLayer.add(workspace.controller, BorderLayout.EAST);
                    northPanel.add(rightPanel, BorderLayout.EAST);
                    controllerIsShown = true;
                }
                workspace.updateUI();
                workspace.controller.updateUI();
                northPanel.updateUI();
            }
        });


        InputMap imap = configButton.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        KeyStroke configStr = KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_DOWN_MASK);
        imap.put(configStr, "showPanel");
        configButton.getActionMap().put("showPanel", new ClickAction(configButton));
        // </editor-fold>

        buttons.add(newButton);
        buttons.add(saveButton);
        buttons.add(saveAsButton);
        buttons.add(openButton);
        buttons.add(dividerFirst);
        buttons.add(verifyButton);
        buttons.add(generateButton);
        buttons.add(serialMonitorButton);
        buttons.add(dividerSecond);
        buttons.add(saveImageButton);
        buttons.add(websiteButton);
        buttons.add(infoLabel);

        //TODO: нужна иконка для этой кнопки
        buttons.add(deleteAll);

        panelWithConfigButton.add(configButton);

        workspace.workLayer.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                Dimension s = workspace.getFactorySize();
                Dimension q = workspace.getCanvasSize();
                logo.setPreferredSize(new Dimension(s.width, standartNorthPanelSize));
//                        if (workspace.workLayer.getDividerLocation() > 380)
//                            workspace.workLayer.setDividerLocation(380);
//                        else if (workspace.workLayer.getDividerLocation() < 120)
//                            workspace.workLayer.setDividerLocation(120);
                northPanel.updateUI();
                workspace.updateUI();
                workspace.validate();
            }
        });

        northPanel.updateUI();
        workspace.updateUI();
        this.setJMenuBar(menuBar);
        this.add(northPanel, BorderLayout.NORTH);
        this.add(workspace, BorderLayout.CENTER);
    }

    // <editor-fold defaultstate="collapsed" desc="Buttons listners">
    public void doOpenArduBlockFile() {
        if (context.isWorkspaceChanged()) {
            int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.content.open_unsaved"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
            if (optionValue == JOptionPane.YES_OPTION) {
                doSaveArduBlockFile();
                this.loadFile();
            } else {
                if (optionValue == JOptionPane.NO_OPTION) {
                    this.loadFile();
                }
            }
        } else {
            this.loadFile();
        }
        this.setTitle(makeFrameTitle());
    }

    private void loadFile() {
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File savedFile = fileChooser.getSelectedFile();
            if (!savedFile.exists()) {
                //JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.file_not_found"), uiMessageBundle.getString("message.title.error"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
                errWindow.setErr(uiMessageBundle.getString("message.title.error"),
                        uiMessageBundle.getString("message.file_not_found"));
                return;
            }

            try {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                context.loadArduBlockFile(savedFile);
                context.setWorkspaceChanged(false);
            } catch (IOException e) {
                //JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.file_not_found"), uiMessageBundle.getString("message.title.error"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
                errWindow.setErr(uiMessageBundle.getString("message.title.error"),
                        uiMessageBundle.getString("message.file_not_found"));
                e.printStackTrace();
            } finally {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        }
        errWindow.reset();
    }

    public boolean doSaveArduBlockFile() {
        if (!context.isWorkspaceChanged()) {
            return true;
        }

        String saveString = getArduBlockString();

        if (context.getSaveFilePath() == null) {
            return chooseFileAndSave(saveString);
        } else {
            File saveFile = new File(context.getSaveFilePath());
            writeFileAndUpdateFrame(saveString, saveFile);
            return true;
        }
    }

    public void doSaveAsArduBlockFile() {
        if (context.isWorkspaceEmpty()) {
            return;
        }

        String saveString = getArduBlockString();

        chooseFileAndSave(saveString);

    }

    private boolean chooseFileAndSave(String ardublockString) {
        File saveFile = letUserChooseSaveFile();
        saveFile = checkFileSuffix(saveFile);
        if (saveFile == null) {
            return false;
        }

        if (saveFile.exists() && !askUserOverwriteExistedFile()) {
            return false;
        }

        writeFileAndUpdateFrame(ardublockString, saveFile);
        return true;
    }

    private String getArduBlockString() {
        WorkspaceController workspaceController = context.getWorkspaceController();
        return workspaceController.getSaveString();
    }

    private void writeFileAndUpdateFrame(String ardublockString, File saveFile) {
        try {
            saveArduBlockToFile(ardublockString, saveFile);
            context.setWorkspaceChanged(false);
            this.setTitle(this.makeFrameTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private File letUserChooseSaveFile() {
        int chooseResult;
        chooseResult = fileChooser.showSaveDialog(this);
        if (chooseResult == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    private boolean askUserOverwriteExistedFile() {
        int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.content.overwrite"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
        return (optionValue == JOptionPane.YES_OPTION);
    }

    private void saveArduBlockToFile(String ardublockString, File saveFile) throws IOException {
        context.saveArduBlockFile(saveFile, ardublockString);
        context.setSaveFileName(saveFile.getName());
        context.setSaveFilePath(saveFile.getAbsolutePath());
    }

    public void doNewArduBlockFile() {
        if (context.isWorkspaceChanged()) {
            int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.question.newfile_on_workspace_changed"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);

            switch (optionValue) {
                case JOptionPane.YES_OPTION:
                    doSaveArduBlockFile();
                //break;
                case JOptionPane.NO_OPTION:
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    context.resetWorksapce();
                    context.setWorkspaceChanged(false);
                    this.setTitle(this.makeFrameTitle());
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    break;
                case JOptionPane.CANCEL_OPTION:
                    break;
            }
        } else {
            // If workspace unchanged just start a new Ardublock
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            context.resetWorksapce();
            context.setWorkspaceChanged(false);
            this.setTitle(this.makeFrameTitle());
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }

    public void doCloseArduBlockFile() {
        if (context.isWorkspaceChanged()) {
            int optionValue = JOptionPane.showOptionDialog(this,
                    uiMessageBundle.getString("message.question.close_on_workspace_changed"),
                    uiMessageBundle.getString("message.title.question"),
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, null, JOptionPane.YES_OPTION);
            switch (optionValue) {
                case JOptionPane.YES_OPTION:
                    if (doSaveArduBlockFile()) {
                        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    } else {
                        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                    break;
                case JOptionPane.NO_OPTION:
                    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    break;
                case JOptionPane.CANCEL_OPTION:
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    break;
            }
        } else {
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        settings.dispose();

    }

    private File checkFileSuffix(File saveFile) {
        String filePath = saveFile.getAbsolutePath();
        if (filePath.endsWith(".abp")) {
            return saveFile;
        } else {
            return new File(filePath + ".abp");
        }
    }
    // </editor-fold>

    class ImageButton extends JButton {

        private String name;
        private JLabel label;
        private int size = 23;

        public ImageButton(String name, String iconA, String iconB, JLabel label) {
            this.name = name;
            this.label = label;
            URL iconURL = OpenblocksFrame.class.getClassLoader().getResource(iconA);
            Image image = new ImageIcon(iconURL).getImage().getScaledInstance(
                    size, size, java.awt.Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(image));

            iconURL = OpenblocksFrame.class.getClassLoader().getResource(iconB);
            image = new ImageIcon(iconURL).getImage().getScaledInstance(
                    size, size, java.awt.Image.SCALE_SMOOTH);
            setPressedIcon(new ImageIcon(image));
            setSelectedIcon(new ImageIcon(image));
            setRolloverIcon(new ImageIcon(image));

            setMargin(new Insets(0, 0, 0, 0));
            setIconTextGap(0);
            setBorderPainted(false);
            setBorder(null);
            setText(null);
            setFocusable(false);
            setSize(image.getWidth(null) - 1, image.getHeight(null) - 1);
            MouseListener mouseListener = new CustomMouseListener();
            addMouseListener(mouseListener);
        }

        public class CustomMouseListener implements MouseListener {

            @Override
            public void mouseEntered(MouseEvent e) {
                if (label != null) {
                    label.setText(name);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (label != null) {
                    label.setText("");
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        }
    }
    class ClickAction extends AbstractAction
    {
        private JButton button;

        public ClickAction(JButton but)
        {
            button = but;
        }

        public void actionPerformed(ActionEvent e)
        {
            button.doClick();
        }
    }
}
