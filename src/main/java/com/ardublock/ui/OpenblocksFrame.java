package com.ardublock.ui;

import com.ardublock.core.Context;
import com.ardublock.ui.listener.*;
import com.mit.blocks.controller.WorkspaceController;
import com.mit.blocks.workspace.ErrWindow;
import com.mit.blocks.workspace.Page;
import com.mit.blocks.workspace.Workspace;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;

/**
 * @author AdmiralPaw, Ritevi, Aizek
 * Класс, генерирующий оконную процедуру, графику, кнопки, функции и т.д.
 */
public class OpenblocksFrame extends JFrame {

    /*Данное поле - идентификатор класса в языке Java, используемый при сериализации с использованием стадартного
    алгоритма. Хранится как числовое значение типа long.*/
    private static final long serialVersionUID = 2841155965906223806L;

    //Поле меню
    JMenu recentMenu;

    //Поле окна
    private OpenblocksFrame thisFrame;

    //Поле времени для функции вызова автосохранения
    public int timeDelay = 5;

    //Поле файла, который будет сохранён
    private File fileToSave;

    //Поле рабочего пространства (Контекст, Фон)
    private Context context;

    //Поле механизма для выбора пользователем файла
    private JFileChooser fileChooser;

    //Поле фильтра файлов
    private FileFilter ffilter;

    //Поле консоли
    private ErrWindow errWindow;

    //Поле настроек
    public Settings settings;

    /*Поле добавляет глубину к контейнеру JFC/Swing, позволяя компонентам перекрывать друг друга, когда это необходимо.
    Целочисленный объект определяет глубину каждого компонента в контейнере, где компоненты с более высоким номером
    располагаются "сверху" других компонентов.*/
    public JLayeredPane GlobalLayaredPane;

    //Поле таймера
    private Timer timer = null;

    //Поле центра верхней панели
    public JPanel northPanelCenter = null;

    //Поле логотипа
    public JPanel logo;

    //Поле правой панели
    public JPanel rightPanel;

    //Поле создания кнопки
    public ImageButton generateButton;

    //Поле проверки кнопки
    public ImageButton verifyButton;

    //Поле сообщений пользовательского интерфейса
    private ResourceBundle uiMessageBundle;

    //Поле директории для автосохранения
    private String autosavePath = "";

    //Поле видимости контроллера
    private boolean controllerIsShown = true;
    public boolean hideArduinoToogle = true;

    /**
     * Метод, создающий прослушиватель
     *
     * @param ofl - Прослушиватель открытых оконных процедур
     */
    public void addListener(OpenblocksFrameListener ofl) {
        context.registerOpenblocksFrameListener(ofl);
    }


//<<<<<<< HEAD
//    public static void deleteAllBlocks() {
//=======

    /**
     * Метод, удаляющий все блоки (очистка)
     */
    public static void deleteAllBlocks() {
        Page.currentpage.saveScreen();
//>>>>>>> lerofaCtrlZ
        Page.blocksContainer.removeAll();
        Page.blocksContainer.revalidate();
        Page.blocksContainer.repaint();
    }

    /**
     * Метод, создающий название оконной процедуры
     *
     * @return title - Название оконной процедуры
     */
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


    //TODO: доделать список последних файлов
    String user;

    //Поле последних файлов
    public static String recentFile;

    List<String> recentFiles = new ArrayList<>();

    /**
     * Главный метод класса
     *
     * @throws IOException           - Исключение связанное с ошибками во время
     *                               выполнения операций потоков входа/выхода
     * @throws IOException           e - Исключение, связанное с невозможностью закрыть поток входа,
     *                               после загрузки и работы с классами
     * @throws IOException           ex - Проверка закрытия потока входа
     * @throws FileNotFoundException e - Сигнализирует о том, что попытка открыть файл,
     *                               обозначенный указанным именем пути, не удалась.
     */
    public OpenblocksFrame() {
        thisFrame = this;
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("recentFiles.txt");
        Scanner scanner = new Scanner(inputStream);

        try {
            if (scanner.hasNextLine()) {
                String recentFileName = scanner.nextLine();
                recentFiles.add(recentFileName);
                recentFile = recentFileName;
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                inputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        context = Context.getContext();
        uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
        context.setDefaultFileName(uiMessageBundle.getString("ardublock.ui.untitled"));

        settings = new Settings(this);

//        if (settings.productKeyValidator(this)) {
//            this.keyValidated = true;
//        }

        this.setTitle(makeFrameTitle());
        this.setSize(new Dimension(1024, 760));
        // Определяем разрешение экрана монитора
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Задаем размер
        this.setSize(sSize);
        this.setExtendedState(MAXIMIZED_BOTH);
        //this.setResizable(false);
        //this.setUndecorated(true);
        this.setLayout(new BorderLayout());
        //put the frame to the center of screen
        this.setLocationRelativeTo(null);

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

        user = System.getProperty("user.name");
        autosavePath = "C:\\Users\\" + user + "\\Documents\\OmegaBot_IDE\\";

        File directory = new File(autosavePath);
        if (!directory.exists()) {
            directory.mkdir();
        }
        //System.out.println(user);

        try {
            File file = new File(autosavePath + "saver.abp");

            if (file.exists()) {

            } else {
                Formatter fileCreator = new Formatter(autosavePath + "saver.abp");
                fileCreator.close();
                Formatter fileCreator2 = new Formatter(autosavePath + "beforeDelete.abp");
                fileCreator2.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //TODO: ok all, funcs i need to save and load in this class , see in docs TimerTask is asynchronous or not?
        //make public static var to set time to timer
        //see screenshots on my phone
//        Thread timeToSave = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Timer timer = new Timer();
//                timer.scheduleAtFixedRate(new TimerTask() {
//                    @Override
//                    public void run() {
//                        String text = getContext().getSaveFileName();
//                        writeFileAndUpdateFrame(getArduBlockString(), new File(text + "_autosave.abp"));
//                        System.out.println(text);
//                        setTitle(makeFrameTitle());
//                        System.out.println("delayed and worked successfully");
//                    }
//                },1000*60*4,1000*60*timeDelay);
//            }
//        });

        timer = new Timer(1000 * 60 * timeDelay, new ActionListener() {
            /**
             * Метод, работающий с сохранениями (и автосохранениями) файлов
             * @param e - Событие совершённого действия
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.gc();
                String text = getContext().getSaveFileName();
                String newText = text.replace(".abp", "");
                String oldPath = context.getSaveFilePath();
                boolean isWorkspaceChanged = context.isWorkspaceChanged();
                writeFileAndUpdateFrame(getArduBlockString(), new File(autosavePath + newText + "_autosave.abp"));
                context.setSaveFileName(newText);
                context.setWorkspaceChanged(isWorkspaceChanged);
                context.setSaveFilePath(oldPath);
                setTitle(makeFrameTitle());
                Date dateNow = new Date();
                SimpleDateFormat formatForDateNow = new SimpleDateFormat("HH:mm:ss");
                errWindow.setErrText("[" + formatForDateNow.format(dateNow) + "] " +
                        uiMessageBundle.getString("ardublock.ui.compledAutosave") + autosavePath + newText + "_autosave.abp");
            }
        });
        timer.start();

    }

    /**
     * Метод, который устанавливает, через сколько произойдёт автосохранение файла
     *
     * @param interval - Интервал времени, после которого будет сделано автосохранение
     */
    public void setAutosaveInterval(int interval) {
        timeDelay = interval;
        if (timer != null) {
            timer.setDelay(1000 * 60 * timeDelay);
            timer.restart();
        }
    }

    public void setAutosavePath(String path) {
        autosavePath = path;
    }

    public String getAutosavePath() {
        return autosavePath;
    }

    /**
     * Метод, инициализирующий рабочее пространство, цвета, розмеры, расположение всех панелей, элементов и т.д.
     *
     * @throws Exception e - Не были сохранены недавние файлы
     */
    private void initOpenBlocks() {

        //Финальное (неизменяемое, без наследования) поле контекста
        final Context context = Context.getContext();

        //Финальное (неизменяемое, без наследования) поле рабочего пространства
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
        JMenuItem tutorialItem = new JMenuItem(uiMessageBundle.getString("ardublock.ui.tutorial"));
        JMenu recentItems = new JMenu(uiMessageBundle.getString("ardublock.ui.recent"));

        //JMenuItem recentFiles = new JMenuItem("open recent");
        this.recentMenu = recentItems;

        try {
            File recentFiles = new File("C:\\Users\\Public\\recentFiles.txt");
            List<String> files = new ArrayList<>();
            Scanner scanner = new Scanner(recentFiles);
            while (scanner.hasNextLine()) {
                files.add(scanner.nextLine());
            }

            remakeRecentItems(files);

        } catch (Exception e) {

        }


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
            /**
             * Метод, делающий выбранные настройки видимыми
             * @param e - Событие совершённого действия
             */
            public void actionPerformed(ActionEvent e) {
                settings.setVisible(true);
            }
        });
        KeyStroke settingStr = KeyStroke.getKeyStroke(KeyEvent.VK_WINDOWS, InputEvent.CTRL_DOWN_MASK);
        settingsItem.setAccelerator(settingStr);

        exitItem.addActionListener(new ActionListener() {
            /**
             * Метод для выхода из программы
             * @param e - Событие совершённого действия
             */
            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
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
            /**
             * Метод для определения серийного номера
             * @param e - Событие совершённого действия
             */
            public void actionPerformed(ActionEvent e) {
                context.getEditor().handleSerial();
            }
        });
        KeyStroke serialMonitorButStr = KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK);
        serialMonitorItem.setAccelerator(serialMonitorButStr);

        saveImageItem.addActionListener(new ActionListener() {
            /**
             * Метод для сохранения элемента в качесте png изображения
             * @param e - Событие совершённого действия
             * @exception Exception e1 - Не было сохранено изображение
             */
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

        tutorialItem.addActionListener(new ActionListener() {
            /**
             * Метод для вывода обучающего гайда (туториала) в новой оконной процедуре
             * @param e - Событие совершённого действия
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                TutorialPane pan = new TutorialPane(thisFrame);
                setGlassPane(pan);
                getGlassPane().setVisible(true);
                repaint();
                settings.setVisible(false);
            }
        });

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(recentItems);
        fileMenu.addSeparator();
        fileMenu.add(settingsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);


        toolsMenu.add(verifyItem);
        toolsMenu.add(uploadItem);
        toolsMenu.add(serialMonitorItem);
        toolsMenu.add(tutorialItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);

        // </editor-fold>
        //Panels------------------------------------------------------//

        //Финальное (неизменяемое, без наследования) поле размера верхней панели
        final int standartNorthPanelSize = 24;  //TODO: make like constant
        //Финальное (неизменяемое, без наследования) поле верхней панели
        final JPanel northPanel = new JPanel();

        logo = new JPanel();
        northPanelCenter = new JPanel();

        //Финальное (неизменяемое, без наследования) поле кнопок
        final JPanel buttons = new JPanel();

        //Финальное (неизменяемое, без наследования) поле панели с кнопкой настроек
        final JPanel panelWithConfigButton = new JPanel();

        rightPanel = new JPanel();

        //Финальное (неизменяемое, без наследования) поле с первым разделителем
        final JLabel dividerFirst = new JLabel();

        //Финальное (неизменяемое, без наследования) поле со вторым разделителем
        final JLabel dividerSecond = new JLabel();

        //Main logo---------------------------------------------------//

        //Финальное (неизменяемое, без наследования) поле логотипа
        final JLabel mainLogo = new JLabel();

        ImageIcon mLogo = new ImageIcon(OpenblocksFrame.class.getClassLoader().getResource(
                "com/ardublock/block/mainLogo2.png")
        );
        Image image = mLogo.getImage().getScaledInstance(81, standartNorthPanelSize,
                java.awt.Image.SCALE_SMOOTH);
        mLogo = new ImageIcon(image);
        mainLogo.setIcon(mLogo);
        logo.add(mainLogo);
        logo.setBackground(new Color(0, 151, 157));
        Dimension size = workspace.getFactorySize();
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
        //TODO может сделать авто размер лейбла от текста в нем?
        //+ нужно изменить минимальные размеры проги, а то кнопки друг на друга залезут
        //либо как идея: сделать "прячущиеся" кнопки
        infoLabel.setPreferredSize(new Dimension(230, 20));
        infoLabel.setForeground(Color.white);

        //<editor-fold defaultstate="collapsed" desc="Buttons images and listners">
        ImageButton reloadDeleted = new ImageButton(
                "reloadDeleted",
                "com/ardublock/block/buttons/newA.jpg",
                "com/ardublock/block/buttons/newB.jpg",
                infoLabel
        );

        reloadDeleted.addActionListener(new ActionListener() {
            /**
             * Метод, загружающий файл
             * @param e - Событие совершённого действия
             */
            @Override
            public void actionPerformed(ActionEvent e) {
//                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    context.loadArduBlockFile(new File("C:\\Users\\" + user + "\\Documents\\beforeDelete.abp"));
                    context.setWorkspaceChanged(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

//        ImageButton deleteAll = new ImageButton(
//                "deleteAllBlocks",
//                "com/ardublock/block/buttons/newA.jpg",
//                "com/ardublock/block/buttons/newB.jpg",
//                infoLabel
//        );

//        deleteAll.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                writeFileAndUpdateFrame(getArduBlockString(), new File("C:\\Users\\" + user + "\\Documents\\beforeDelete.abp"));
//                deleteAllBlocks();
//            }
//        });
        ImageButton newButton = new ImageButton(
                uiMessageBundle.getString("ardublock.ui.new"),
                "com/ardublock/block/buttons/newA.jpg",
                "com/ardublock/block/buttons/newB.jpg",
                infoLabel
        );
        newButton.addActionListener(new NewButtonListener(this));

        ImageButton saveButton = new ImageButton(
                uiMessageBundle.getString("ardublock.ui.save"),
                "com/ardublock/block/buttons/saveA.jpg",
                "com/ardublock/block/buttons/saveB.jpg",
                infoLabel
        );
        saveButton.addActionListener(new SaveButtonListener(this));

        ImageButton saveAsButton = new ImageButton(
                uiMessageBundle.getString("ardublock.ui.saveAs"),
                "com/ardublock/block/buttons/saveAsA.jpg",
                "com/ardublock/block/buttons/saveAsB.jpg",
                infoLabel
        );
        saveAsButton.addActionListener(new SaveAsButtonListener(this));

        ImageButton openButton = new ImageButton(
                uiMessageBundle.getString("ardublock.ui.open"),
                "com/ardublock/block/buttons/openA.jpg",
                "com/ardublock/block/buttons/openB.jpg",
                infoLabel
        );
        openButton.addActionListener(new OpenButtonListener(this));

        verifyButton = new ImageButton(
                uiMessageBundle.getString("ardublock.ui.verify"),
                "com/ardublock/block/buttons/verifyA.jpg",
                "com/ardublock/block/buttons/verifyB.jpg",
                infoLabel
        );
        verifyButton.setActionCommand("VERIFY_CODE");
        verifyButton.addActionListener(new GenerateCodeButtonListener(this, context));

        generateButton = new ImageButton(
                uiMessageBundle.getString("ardublock.ui.upload"),
                "com/ardublock/block/buttons/uploadA.jpg",
                "com/ardublock/block/buttons/uploadB.jpg",
                infoLabel
        );
        generateButton.setActionCommand("UPLOAD_CODE");
        generateButton.addActionListener(new GenerateCodeButtonListener(this, context));

        ImageButton serialMonitorButton = new ImageButton(
                uiMessageBundle.getString("ardublock.ui.serialMonitor"),
                "com/ardublock/block/buttons/monitorA.jpg",
                "com/ardublock/block/buttons/monitorB.jpg",
                infoLabel
        );
        serialMonitorButton.addActionListener(new ActionListener() {
            /**
             * Метод для получения серийного номера
             * @param e - Событие совершённого действия
             */
            public void actionPerformed(ActionEvent e) {
                context.getEditor().handleSerial();
            }
        });
        ImageButton saveImageButton = new ImageButton(
                uiMessageBundle.getString("ardublock.ui.saveImage"),
                "com/ardublock/block/buttons/saveAsImageA.jpg",
                "com/ardublock/block/buttons/saveAsImageB.jpg",
                infoLabel
        );
        saveImageButton.addActionListener(new ActionListener() {
            /**
             * Метод для сохранения кнопки в качесте png изображения
             * @param e - Событие совершённого действия
             * @exception Exception e1 - Не было сохранено изображение
             */
            public void actionPerformed(ActionEvent e) {
                Dimension size = workspace.getCanvasSize();
                //System.out.println("size: " + size);
                BufferedImage bi = new BufferedImage(2560, 2560, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = (Graphics2D) bi.createGraphics();

                //Поле множителя масштаба
                double theScaleFactor = (300d / 72d);

                g.scale(theScaleFactor, theScaleFactor);

                workspace.getBlockCanvas().getPageAt(0).getJComponent().paint(g);
                try {
                    //Финальное (неизменяемое, без наследования) поле выбираемого файла
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
            /**
             * Метод, осуществляющий переход на сайт омегабота
             * @param e - Событие совершённого действия
             * @exception Exception e1 - Невозмонжость перехода на веб-сайт омегабота из-за несовместимости
             */
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                URL url;
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        url = new URL("https://omegabot.ru/");
                        desktop.browse(url.toURI());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });


        ImageButton hideArduinoButton = new ImageButton(
                "hideArduino",
                "com/ardublock/block/buttons/websiteA.jpg",
                "com/ardublock/block/buttons/websiteB.jpg",
                infoLabel
        );
        hideArduinoButton.addActionListener(new ActionListener() {
            /**
             *
             */
            public void actionPerformed(ActionEvent e) {
                //TODO: код ниже позволяет скрывать окно Arduino IDE, необходимо автоматически закрывать его, если
                //  включен параметр @autostart@
                hideArduinoToogle = false;
                if (context.getEditor() != null) {
                    context.getEditor().setVisible(!context.getEditor().isVisible());
                }
                else {
                    System.out.println("[DEBUG] editor == null");
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
            /**
             * Метод, обновляющий интерфейс рабочего стола
             * @param e - Событие совершённого действия
             */
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
        buttons.add(hideArduinoButton);
        buttons.add(infoLabel);

        panelWithConfigButton.add(configButton);

        workspace.workLayer.addPropertyChangeListener(new PropertyChangeListener() {
            /**
             * Метод, обновляющий интерфейс рабочего стола
             * @param e - Событие изменённого свойства
             */
            public void propertyChange(PropertyChangeEvent e) {
                Dimension s = workspace.getFactorySize();
                logo.setPreferredSize(new Dimension(s.width, standartNorthPanelSize));
                northPanel.updateUI();
                workspace.updateUI();
                workspace.validate();
            }
        });

        northPanel.updateUI();
        workspace.updateUI();

        JPanel GlobalPanel = new JPanel(new BorderLayout());
        //GlobalLayaredPane = new JLayeredPane();
        //GlobalLayaredPane.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                GlobalPanel.setSize(e.getComponent().getSize());
//                //pan.setSize(e.getComponent().getSize());
//            }
//        });
        //this.add(GlobalLayaredPane, BorderLayout.CENTER);
        this.setJMenuBar(menuBar);
        //GlobalPanel.add(northPanel, BorderLayout.NORTH);
        //GlobalPanel.add(workspace, BorderLayout.CENTER);
        this.add(northPanel, BorderLayout.NORTH);
        this.add(workspace, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
            /**
             * Метод, изменяющий статус окна на "Включено"
             * @param e - Событие, указывающее статус окна
             */
            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
            }

            /**
             * Метод, изменяющий статус окна на "Выключено"
             * @param e - Событие, указывающее статус окна
             */
            @Override
            public void windowDeactivated(WindowEvent e) {
                super.windowDeactivated(e);
                workspace.deactiveCPopupMenu();
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            /**
             * Метод, меняющий размер компонента
             * @param e - Событие, которое указывает, что компонент изменил размер
             */
            @Override
            public void componentResized(ComponentEvent e) {
                workspace.deactiveCPopupMenu();
            }

            /**
             * Метод, меняющий местоположение компонента
             * @param e - Событие, которое указывает, что компонент переместился
             */
            @Override
            public void componentMoved(ComponentEvent e) {
                workspace.deactiveCPopupMenu();
            }

            /**
             * Метод, указываюший статус компонента - "Виден"
             * @param e - Событие, которое указывает, что компонент виден
             */
            @Override
            public void componentShown(ComponentEvent e) {
                workspace.deactiveCPopupMenu();
            }

            /**
             * Метод, указываюший статус компонента - "Не виден"
             * @param e - Событие, которое указывает, что компонент не виден
             */
            @Override
            public void componentHidden(ComponentEvent e) {
                workspace.deactiveCPopupMenu();
            }
        });
    }


    // <editor-fold defaultstate="collapsed" desc="Buttons listners">

    /**
     *
     */
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

    /**
     * @return
     */
    public boolean doSaveArduBlockFile() {
        if (!context.isWorkspaceChanged()) {
            return true;
        }

        String saveString = getArduBlockString();

        if (context.getSaveFilePath() == null) {
            return chooseFileAndSave(saveString);
        } else {
            File saveFile = new File(context.getSaveFilePath());
            fileToSave = saveFile;
            writeFileAndUpdateFrame(saveString, saveFile);
            return true;
        }
    }

    /**
     *
     */
    public void doSaveAsArduBlockFile() {
        if (context.isWorkspaceEmpty()) {
            return;
        }

        String saveString = getArduBlockString();

        chooseFileAndSave(saveString);

    }

    /**
     * @return
     */
    public ResourceBundle getResource() {
        return uiMessageBundle;
    }

    private void remakeRecentItems(List<String> recentFiles) {
        File recentfiles = new File("C:\\Users\\Public\\recentFiles.txt");
        List<String> files = new ArrayList<>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(recentfiles);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        while (scanner.hasNextLine()) {
            files.add(scanner.nextLine());
        }


        for (String s : files) {
            JMenuItem item = new JMenuItem(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        context.loadArduBlockFile(new File(s));
                        remakeRecentFiles(s);
                        //посоветоваться и узнать изменять ли сразу или только при загрузке

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            String name = s.substring(s.lastIndexOf("\\"), s.length() - 1);
            String name2 = "";
            char[] chars = name.toCharArray();
            for (int i = 1; i < name.length(); i++) {
                name2 += chars[i];
            }

            item.setText(name2);
            recentMenu.add(item);
            //System.out.println(s);
        }
        recentMenu.revalidate();
        recentMenu.repaint();
    }

    private void remakeRecentFiles(String path) {

        //файл для хранения всех недавних файлов
        File recentFilesData = new File("C:\\Users\\Public\\recentFiles.txt");
        if (recentFilesData.exists()) {
            try {
                Scanner scanner = new Scanner(recentFilesData);
                recentFiles = new ArrayList<>();
                while (scanner.hasNextLine()) {
                    recentFiles.add(scanner.nextLine());
                }

                if (!recentFiles.contains(path)) {

                    if (recentFiles.size() >= 5) {
                        List<String> files = new ArrayList<>();
                        files.add(path);
                        for (int i = 0; i < 4; i++) {
                            files.add(recentFiles.get(i));
                        }
                        Formatter writer = new Formatter("C:\\Users\\Public\\recentFiles.txt");
                        for (String s : files) {
                            writer.format("%s", s + "\r\n");
                        }
                        writer.close();
                    } else {
                        List<String> files = new ArrayList<>();
                        files.add(path);
                        for (int i = 0; i < recentFiles.size(); i++) {
                            files.add(recentFiles.get(i));
                        }
                        Formatter writer = new Formatter("C:\\Users\\Public\\recentFiles.txt");
                        for (String s : files) {
                            writer.format("%s", s + "\r\n");
                        }
                        writer.close();
                    }

                    recentMenu.removeAll();
                    remakeRecentItems(recentFiles);
                } else {
                    int index = recentFiles.indexOf(path);
                    List<String> rlist = new ArrayList<>();
                    rlist.add(path);
                    for (int i = 0; i < recentFiles.size(); i++) {
                        if (i != index) {
                            rlist.add(recentFiles.get(i));
                        }
                    }

                    Formatter writer = new Formatter("C:\\Users\\Public\\recentFiles.txt");
                    for (String s : rlist) {
                        writer.format("%s", s + "\r\n");
                    }
                    writer.close();
                    recentFiles = rlist;
                    recentMenu.removeAll();
                    remakeRecentItems(rlist);

                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            try {
                Formatter writer = new Formatter("C:\\Users\\Public\\recentFiles.txt");
                writer.format("%s", path + "\r\n");
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean chooseFileAndSave(String ardublockString) {
        File saveFile = letUserChooseSaveFile();
        fileToSave = saveFile;
        if (saveFile == null) {
            return false;
        }

        if (saveFile.exists() && !askUserOverwriteExistedFile()) {
            return false;
        }
        //System.out.println(saveFile.getAbsolutePath());
        //файл для хранения всех недавних файлов
        saveFile = checkFileSuffix(saveFile);
        remakeRecentFiles(saveFile.getAbsolutePath());


        //System.out.println(ardublockString+saveFile.getPath().toString());

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
        int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.content.overwrite"),
                uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, null, JOptionPane.YES_OPTION);
        return (optionValue == JOptionPane.YES_OPTION);
    }

    private void saveArduBlockToFile(String ardublockString, File saveFile) throws IOException {
        context.saveArduBlockFile(saveFile, ardublockString);
        context.setSaveFileName(saveFile.getName());
        context.setSaveFilePath(saveFile.getAbsolutePath());
    }

    /**
     *
     */
    public void doNewArduBlockFile() {
        if (context.isWorkspaceChanged()) {
            int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.question.newfile_on_workspace_changed"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);

            switch (optionValue) {
                case JOptionPane.YES_OPTION:
                    doSaveArduBlockFile();
                    //break;
                case JOptionPane.NO_OPTION:
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    //context.resetWorksapce();
                    deleteAllBlocks();
                    context.loadFreshWorkSpace();
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
            //context.resetWorksapce();
            deleteAllBlocks();
            context.loadFreshWorkSpace();
            context.setWorkspaceChanged(false);
            this.setTitle(this.makeFrameTitle());
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }

    }

    /**
     *
     */
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
                        if (!context.getEditor().isVisible())
                            System.exit(0);
                    } else {
                        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                    break;
                case JOptionPane.NO_OPTION:
                    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    if (!context.getEditor().isVisible())
                        System.exit(0);
                    break;
                case JOptionPane.CANCEL_OPTION:
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    break;
            }
        } else {

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            if (!context.getEditor().isVisible())
                System.exit(0);

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

    /**
     * Метод для получения контекста (фона, рабочего пространства)
     *
     * @return context
     */

    public Context getContext() {
        return this.context;
    }


    /**
     * @author AdmiralPaw, Ritevi, Aizek
     * Класс, работающий с внешним видом кнопок
     */
    class ImageButton extends JButton {

        //Поле наименования
        private String name;

        //Поле метки
        private JLabel label;

        //Поле размера
        private int size = 23;

        /**
         * Метод, работающий с внешним видом кнопки
         *
         * @param name  - название кнопки
         * @param iconA - первое изображение
         * @param iconB - второе изображение
         * @param label - метка
         */
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
            addActionListener(new ActionListener() {
                /**
                 * Метод фокусировки ввода на кнопке
                 * @param e - Событие совершённого действия
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    ImageButton.this.grabFocus();
                }
            });

        }

        /**
         * @author AdmiralPaw, Ritevi, Aizek
         * Класс обработки событий мыши
         */
        public class CustomMouseListener implements MouseListener {

            /**
             * Метод, указывающий на то, что мышь наведена
             *
             * @param e - Событие, указывающее, что в компоненте произошло действие мыши
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                if (label != null) {
                    label.setText(name);
                }
            }

            /**
             * Метод, указывающий на то, что мышь не наведена (убрана из зоны наведения)
             *
             * @param e - Событие, указывающее, что в компоненте произошло действие мыши
             */
            @Override
            public void mouseExited(MouseEvent e) {
                if (label != null) {
                    label.setText("");
                }
            }

            /**
             * Метод, указывающий на то, что кнопка мыши была нажата
             *
             * @param e - Событие, указывающее, что в компоненте произошло действие мыши
             */
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            /**
             * Метод, указывающий на то, что кнопка мыши была нажата (Тоже самое?)
             * (Возможная разница: Click - быстрое нажатие, Press - зажатая кнопка мыши)
             *
             * @param e - Событие, указывающее, что в компоненте произошло действие мыши
             */
            @Override
            public void mousePressed(MouseEvent e) {
            }

            /**
             * Метод, указывающий на то, что кнопка мыши была отпущена
             *
             * @param e - Событие, указывающее, что в компоненте произошло действие мыши
             */
            @Override
            public void mouseReleased(MouseEvent e) {
            }
        }
    }

    /**
     * @author AdmiralPaw, Ritevi, Aizek
     * Класс обработки событий нажатий кнопки
     */
    class ClickAction extends AbstractAction {

        //Поле кнопки
        private JButton button;

        /**
         * Метод, показывающий что нажатие было совершено
         *
         * @param but - кнопка
         */
        public ClickAction(JButton but) {
            button = but;
        }

        /**
         * Метод, показывающий, что действие было совершено
         *
         * @param e - Событие совершённого действия
         */
        public void actionPerformed(ActionEvent e) {
            button.doClick();
        }
    }

    Thread timerSave = new Thread(new Runnable() {
        /**
         * Метод, сохраняющий файл (Начало работы?)
         */
        @Override
        public void run() {


            chooseFileAndSave("backupSave.abp");
        }
    });


}
