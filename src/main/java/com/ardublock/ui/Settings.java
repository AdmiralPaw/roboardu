package com.ardublock.ui;

//<<<<<<< HEAD

import com.mit.blocks.renderable.RenderableBlock;
import com.mit.blocks.workspace.BlocksKeeper;
import com.mit.blocks.workspace.Workspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

//=======
//>>>>>>> lerofaCtrlZ

/**
 * @author AdmiralPaw, Ritevi, Aizek
 * Класс, работающий с настройками, изменяющий их соответственно с предпочтениями пользователя
 */
public class Settings extends JFrame {

    //Поле, содержащее пакет сообщений пользовательского интерфейса
    private ResourceBundle uiMessageBundle;

    //Поле пользовательских данных конфигурации (Пользовательские предпочтения)
    private final Preferences userPrefs;

    //Поле с настройками оконной процедуры
    private Settings thisFrame;

    //Поле с используемым шрифтом
    private String mainFont = "TimesNewRoman";

    //Поле логической переменной с информацией о перетаскивании (перемещении)
    boolean beginDrag;

    //Поле с координатой по оси Ох нажатии мыши
    int mousePressX;

    //Поле с координатой по оси Оу нажатии мыши
    int mousePressY;

    //
    JLabel eggText;

    //
    RCheckBox egg;

    //Поле ширины окна
    int windowWidth = 500;

    //Поле высоты окна
    int windowHeight = 350;

    //Поле с буффеером нажатий кнопок
    private ArrayList<Integer> keyBuf;


    JFileChooser fileChooser;

    /**
     * Метод с предустановками оконной процедуры "Настройки" (Внешний вид, значения, режим окна и т.д.)
     *
     * @param openblocksFrame
     */
    public Settings(OpenblocksFrame openblocksFrame) {
        thisFrame = this;
        this.setTitle("settings");
        this.setSize(new Dimension(windowWidth, windowHeight));
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.white);
        this.setUndecorated(true);
        uiMessageBundle = openblocksFrame.getResource();

        keyBuf = new ArrayList<Integer>();


        this.addKeyListener(new KeyAdapter() {
            /**
             * Метод, который указывает на то, что была нажата определённая клавиша
             * @param e - Событие нажатия клавиши
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() != KeyEvent.VK_ENTER && e.getKeyCode() != KeyEvent.VK_ESCAPE) {
                    keyBuf.add(e.getKeyCode());
                    if (keyBuf.size() == 11) {
                        keyBuf.remove(0);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (keyBuf.size() == 10) {
                        if (keyBuf.get(0) == KeyEvent.VK_UP &&
                                keyBuf.get(1) == KeyEvent.VK_UP &&
                                keyBuf.get(2) == KeyEvent.VK_DOWN &&
                                keyBuf.get(3) == KeyEvent.VK_DOWN &&
                                keyBuf.get(4) == KeyEvent.VK_LEFT &&
                                keyBuf.get(5) == KeyEvent.VK_RIGHT &&
                                keyBuf.get(6) == KeyEvent.VK_LEFT &&
                                keyBuf.get(7) == KeyEvent.VK_RIGHT &&
                                keyBuf.get(8) == KeyEvent.VK_B &&
                                keyBuf.get(9) == KeyEvent.VK_A) {


                            RenderableBlock.useRandomColor = true;
                            egg.setVisible(true);
                            eggText.setVisible(true);
                            egg.setSelected(true);

                            openblocksFrame.getContext().getWorkspace().fullRandomBlocksRepaint();
                            keyBuf.clear();
                        }
                    }
                } else {
                    setVisible(false);
                }
            }
        });

        userPrefs = Preferences.userRoot().node("OmegaBot_IDE");
        if (this.isFirstLaunch()) {
            userPrefs.putBoolean("is_first_launch", false);
            userPrefs.putBoolean("ardublock.ui.autostart", false);
            userPrefs.putBoolean("ardublock.ui.autohide", false);
            userPrefs.putInt("ardublock.ui.autosaveInterval", 10);
            userPrefs.putInt("ardublock.ui.ctrlzLength", 10);
        }

        //uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
        //Поле панели со вкладками
        final JTabbedPane tabbedPane = new JTabbedPane();
        //Поле панели
        final JPanel panel = new JPanel();
        //panel.setLayout(new BorderLayout());


        beginDrag = false;
        JPanel windowCapPanel = new JPanel();
        windowCapPanel.setLayout(null);
        windowCapPanel.addMouseListener(new MouseAdapter() {
            /**
             * Метод, который указывает на то, что была нажата кнопка мыши
             * @param e - Событие нажатия кнопки мыши
             */
            @Override
            public void mousePressed(MouseEvent e) {
                beginDrag = true;
                mousePressX = e.getX();
                mousePressY = e.getY();
            }

            /**
             * Метод, который указывает на то, что была отпущена кнопка мыши
             * @param e - Событие нажатия кнопки мыши
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                beginDrag = false;

                //Поле с новым местоположением перетаскиваемого объекта по оси Ох
                int newX = e.getX() - mousePressX;

                //Поле с новым местоположением перетаскиваемого объекта по оси Оу
                int newY = e.getY() - mousePressY;

                thisFrame.setBounds(thisFrame.getX() + newX, thisFrame.getY() + newY, windowWidth, windowHeight);
            }
        });

        windowCapPanel.addMouseMotionListener(new MouseMotionListener() {
            /**
             * Метод, который указывает на то, что мышью начали перетаскивать объект
             * @param e - Событие нажатия кнопки мыши
             */
            @Override
            public void mouseDragged(MouseEvent e) {
                if (beginDrag) {

                    //Поле с новым местоположением перетаскиваемого объекта по оси Ох
                    int newX = e.getX() - mousePressX;

                    //Поле с новым местоположением перетаскиваемого объекта по оси Оу
                    int newY = e.getY() - mousePressY;

                    thisFrame.setBounds(thisFrame.getX() + newX, thisFrame.getY() + newY, windowWidth, windowHeight);
                }
            }

            /**
             * Метод, который указывает на то, что мышь была перемещена
             * @param e - Событие нажатия кнопки мыши
             */
            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });

        add(windowCapPanel);
        windowCapPanel.setBounds(0, 0, getWidth(), 50);
        windowCapPanel.setBackground(Color.WHITE);
        windowCapPanel.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215)));


        //Поле с размером иконки
        int size = 16;
        URL iconURL = Workspace.class.getClassLoader().getResource("com/ardublock/X.png");
        ImageIcon button_icon = new ImageIcon(
                new ImageIcon(iconURL).getImage()
                        .getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH));
        JButton XButton = new JButton(button_icon);
        XButton.addActionListener(new ActionListener() {
            /**
             * Метод, меняющий видимость оконной процедуры
             * @param e - Событие совершённого действия
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
            }
        });

        windowCapPanel.add(XButton);
        XButton.setBounds(windowCapPanel.getWidth() - size - (windowCapPanel.getHeight() - size) / 2, (windowCapPanel.getHeight() - size) / 2, size, size);
        XButton.setFocusPainted(false);
        XButton.setBackground(windowCapPanel.getBackground());
        XButton.setContentAreaFilled(false);
        XButton.setFocusable(false);

        //Поле смещения вправо
        int rigthOffset = getWidth() - XButton.getX() - size;

        //Поле смещения влево
        int leftOffset = rigthOffset;

        JLabel text = new JLabel(uiMessageBundle.getString("ardublock.ui.settings"));
        text.setVerticalAlignment(SwingConstants.CENTER);
        text.setFont(new Font(mainFont, Font.BOLD, 15));
        windowCapPanel.add(text);
        text.setBounds(leftOffset, 0, 100, windowCapPanel.getHeight());

        text = new JLabel(userPrefs.get("key", "BOT-V2-20-DEVDEV"));
        text.setVerticalAlignment(SwingConstants.CENTER);
        text.setFont(new Font(mainFont, Font.BOLD, 12));
        windowCapPanel.add(text);
        text.setBounds(leftOffset + 100, 0, 100, windowCapPanel.getHeight());

        JPanel windowBodyPanel = new JPanel();
        windowBodyPanel.setLayout(null);
        windowBodyPanel.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215)));
        add(windowBodyPanel);
        windowBodyPanel.setBounds(0, windowCapPanel.getHeight() - 1, getWidth(), getHeight() - windowCapPanel.getHeight() + 1);

        //Поле позиции
        int position = 5;
        //Поле смещения
        int offset = 40;
        //Поле высоты выпадающего списка
        int spinnerHeigth = 30;

        text = new JLabel(uiMessageBundle.getString("ardublock.ui.autostart"));
        text.setVerticalAlignment(SwingConstants.CENTER);
        windowBodyPanel.add(text);
        text.setBounds(leftOffset, position, 300, 40);
        text.setFont(new Font(mainFont, Font.PLAIN, 15));

        RCheckBox autostart = new RCheckBox();
        autostart.setSelected(userPrefs.getBoolean("ardublock.ui.autostart", false));
        windowBodyPanel.add(autostart);
        autostart.setBounds(windowWidth - 44 - rigthOffset, position, 44, 40);

        position += offset;

        text = new JLabel(uiMessageBundle.getString("ardublock.ui.autohide"));
        text.setVerticalAlignment(SwingConstants.CENTER);
        windowBodyPanel.add(text);
        text.setBounds(leftOffset, position, 300, 40);
        text.setFont(new Font(mainFont, Font.PLAIN, 15));

        RCheckBox autohide = new RCheckBox();
        autohide.setSelected(userPrefs.getBoolean("ardublock.ui.autohide", false));
        windowBodyPanel.add(autohide);
        autohide.setBounds(windowWidth - 44 - rigthOffset, position, 44, 40);

        position += offset;

        text = new JLabel(uiMessageBundle.getString("ardublock.ui.autosaveInterval"));
        text.setVerticalAlignment(SwingConstants.CENTER);
        windowBodyPanel.add(text);
        text.setBounds(leftOffset, position, 300, 40);
        text.setFont(new Font(mainFont, Font.PLAIN, 15));
        RSpinner autosaveInterval = new RSpinner(new SpinnerNumberModel(userPrefs.getInt("ardublock.ui.autosaveInterval", 10)
                , 5, 120, 5));

        windowBodyPanel.add(autosaveInterval);
        autosaveInterval.setBounds(getWidth() - 80 - rigthOffset, position + offset / 2 - spinnerHeigth / 2, 80, spinnerHeigth);
        openblocksFrame.setAutosaveInterval(autosaveInterval.getIntValue());

        position += offset;

        text = new JLabel(uiMessageBundle.getString("ardublock.ui.ctrlzLength"));
        text.setVerticalAlignment(SwingConstants.CENTER);
        windowBodyPanel.add(text);
        text.setBounds(leftOffset, position, 300, 40);
        text.setFont(new Font(mainFont, Font.PLAIN, 15));

        RSpinner queueSize = new RSpinner(new SpinnerNumberModel(userPrefs.getInt("ardublock.ui.ctrlzLength", 10), 5, 120, 5));
        windowBodyPanel.add(queueSize);
        queueSize.setBounds(getWidth() - 80 - rigthOffset, position + offset / 2 - spinnerHeigth / 2, 80, spinnerHeigth);
        queueSize.setBounds(getWidth() - 80 - rigthOffset, position + offset / 2 - spinnerHeigth / 2, 80, spinnerHeigth);
        BlocksKeeper.setSize(queueSize.getIntValue());

        position += offset;

        text = new JLabel(uiMessageBundle.getString("ardublock.ui.path_autosave"));
        text.setVerticalAlignment(SwingConstants.CENTER);
        windowBodyPanel.add(text);
        text.setBounds(leftOffset, position, 300, 40);
        text.setFont(new Font(mainFont, Font.PLAIN, 15));

        JButton button = new JButton(uiMessageBundle.getString("ardublock.ui.button_name_path_autosave"));
        windowBodyPanel.add(button);
        button.setBounds(getWidth() - 80 - rigthOffset, position + offset / 2 - spinnerHeigth / 2, 80, spinnerHeigth);
        button.setBounds(getWidth() - 80 - rigthOffset, position + offset / 2 - spinnerHeigth / 2, 80, spinnerHeigth);
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        position += offset;

        JTextField textField = new JTextField(uiMessageBundle.getString("ardublock.ui.path_autosave"), 128);
        windowBodyPanel.add(textField);
        textField.setBounds(leftOffset, position, getWidth() - leftOffset - rigthOffset, 30);
        textField.setFont(new Font(mainFont, Font.PLAIN, 15));

        String user = System.getProperty("user.name");
        String autosavePath = "C:\\Users\\" + user + "\\Documents\\OmegaBot_IDE\\";
        
        String tempAutoSavePath = "C:\\Users\\" + user + "\\Documents\\OmegaBot_IDE\\";
        
        textField.setText(autosavePath);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.showOpenDialog(null);
                File chosenPath = fileChooser.getSelectedFile();
                if (chosenPath==null){
                    textField.setText(tempAutoSavePath);
                } else {
                    textField.setText(chosenPath.getAbsolutePath());
                }
                
            }
        });
        position += offset;

        //DEBAG
//        JButton resetButton = new JButton("Сбросить настройки");
//        windowBodyPanel.add(resetButton);
//        resetButton.setBounds(leftOffset, position, 300, 40);
//        resetButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                userPrefs.putBoolean("is_first_launch", true);
//                userPrefs.putBoolean("is_key_valid", false);
//                userPrefs.putBoolean("ardublock.ui.autostart", false);
//                userPrefs.putBoolean("ardublock.ui.autohide", false);
//                userPrefs.putInt("ardublock.ui.autosaveInterval", 10);
//                userPrefs.putInt("ardublock.ui.ctrlzLength", 10);
//            }
//        });

        position += offset;

        eggText = new JLabel(uiMessageBundle.getString("ardublock.ui.randomColor"));
        eggText.setVerticalAlignment(SwingConstants.CENTER);
        windowBodyPanel.add(eggText);
        eggText.setBounds(leftOffset, position, 300, 40);
        eggText.setFont(new Font(mainFont, Font.PLAIN, 15));
        eggText.setVisible(false);

        egg = new RCheckBox();
        egg.setSelected(false);
        windowBodyPanel.add(egg);
        egg.setBounds(windowWidth - 44 - rigthOffset, position, 44, 40);
        egg.setVisible(false);
        egg.addItemListener(new ItemListener() {
            /**
             * Метод, сообщающий, что состояние элемента было изменено
             * @param e - событие, указывающее, что элемент был выбран или отменен
             */
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (!egg.isSelected()) {
                    egg.setVisible(false);
                    eggText.setVisible(false);
                    RenderableBlock.useRandomColor = false;
                    openblocksFrame.getContext().getWorkspace().fullRandomBlocksRepaint();
                }

            }
        });

        JButton saveBtn = new RButton(uiMessageBundle.getString("ardublock.ui.saveAndClose"));
        saveBtn.setFont(new Font(mainFont, Font.PLAIN, 15));
        windowBodyPanel.add(saveBtn);
        saveBtn.setBounds(1, windowBodyPanel.getHeight() - 40, getWidth() - 1, 39);
        saveBtn.addActionListener(new ActionListener() {
            /**
             * Метод, который меняет настройки согласно предпочтениям пользователя
             * @param e - Событие совершённого действия
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
                userPrefs.putBoolean("ardublock.ui.autostart", autostart.isSelected());
                userPrefs.putBoolean("ardublock.ui.autohide", autohide.isSelected());
                userPrefs.putInt("ardublock.ui.autosaveInterval", autosaveInterval.getIntValue());
                userPrefs.putInt("ardublock.ui.ctrlzLength", queueSize.getIntValue());
                openblocksFrame.setAutosaveInterval(autosaveInterval.getIntValue());
                openblocksFrame.setAutosavePath(textField.getText()+"\\");
                BlocksKeeper.setSize(queueSize.getIntValue());
            }
        });
        this.requestFocus();
    }

    public boolean isProductKeyValid() {
        return userPrefs.getBoolean("is_key_valid", false);
    }

    public boolean productKeyValidator(OpenblocksFrame openblocksFrame) {
        if (this.isProductKeyValid()) {
            return true;
        }
        while (true) {
            String result = JOptionPane.showInputDialog(openblocksFrame,
                    "Введите ключ продукта: ",
                    "Авторизация",
                    JOptionPane.PLAIN_MESSAGE);
            if (result == null) {
                return false;
            }
            try {
                String[] subStr = result.split("-");
                int resultValue = Integer.parseInt(subStr[subStr.length - 1]);
                String clearResult = result.substring(0, result.lastIndexOf("-"));
                if (resultValue >= 50 && resultValue <= 10000 && clearResult.equals("BOT-V2-20")) {
                    userPrefs.putBoolean("is_key_valid", true);
                    userPrefs.put("key", result);
                    return true;
                }

            } catch (NumberFormatException e) {
                //e.printStackTrace();
            }
            JOptionPane.showMessageDialog(openblocksFrame,
                        new String[]{"Ключ не верный!", "Попробуйте снова"},
                        "Авторизация",
                        JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * Метод, определяющий видимость
     *
     * @param e - логическая переменная, которая ответственная за видимость
     */
    public void setVisible(boolean e) {
        super.setVisible(e);
        if (e) {
            this.requestFocus();
        }
    }

    /**
     * Метод, определяющий был ли это первый запуск или нет
     *
     * @return userPrefs.getBoolean(" is_first_launch ", true)
     */
    public boolean isFirstLaunch() {
        return userPrefs.getBoolean("is_first_launch", true);
    }
}
