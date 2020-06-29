package com.ardublock.ui;

import com.ardublock.ui.ControllerConfiguration.ControllerButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author User
 */
public class TutorialPane extends JPanel {

    private OpenblocksFrame openblocksFrame;
    private DarkPanel factoryPanel;
    private DarkPanel buttonsPanel;
    private DarkPanel workspacePanel;
    private DarkPanel controllerPanel;
    private DarkPanel logoPanel;
    private DarkPanel rightTopPanel;

    /**
     *
     */
    public TutorialPane tutorialPane;

    /**
     *
     */
    public IllumPanel dummy;

    /**
     *
     */
    public List<List<DarkPanel>> activeAnimPanels = new ArrayList<>();

    /**
     *
     */
    public List<DarkPanel> inactiveTutorPanels = new ArrayList<>();

    /**
     *
     */
    public List<Point> tutorLocations = new ArrayList<>();

    /**
     *
     */
    public List<Dimension> tutorDimensions = new ArrayList<>();

    /**
     *
     */
    public List<String> tutorTexts = new ArrayList<>();

    /**
     *
     */
    public List<Color> tutorColors = new ArrayList<>();

    /**
     *
     */
    public List<Color> tutorTextColors = new ArrayList<>();

    /**
     *
     */
    public List<List<IllumPanel>> tutorIllumPanels = new ArrayList<>();

    /**
     *
     */
    public TPanel tutorTextPanel;

    private final int menuHeight = 22;
    private final int kostilEbani = 1;

    /**
     *
     */
    public int iter = 0;

    /**
     *
     */
    public int lastIter = 1;
    private boolean tutorIsActive = true;

    /**
     *
     * @param openblocksFrame
     */
    public TutorialPane(OpenblocksFrame openblocksFrame) {
        openblocksFrame.validate();
        this.tutorialPane = this;
        this.openblocksFrame = openblocksFrame;
        this.setSize(openblocksFrame.getSize());
        this.setLayout(null);
        this.setOpaque(false);

        this.initPanels();

        this.add(factoryPanel);
        this.add(buttonsPanel);
        this.add(workspacePanel);
        this.add(controllerPanel);
        this.add(logoPanel);
        this.add(rightTopPanel);

        URL iconURL = TutorialPane.class.getClassLoader().getResource("com/ardublock/Images/tutorClose.png");
        ImageIcon image = new ImageIcon(iconURL);
        Image imageRaw = image.getImage().getScaledInstance(
                rightTopPanel.getWidth()-150, rightTopPanel.getHeight()-10, java.awt.Image.SCALE_SMOOTH);
        JButton exitButton = new JButton();
        exitButton.setSize(new Dimension(rightTopPanel.getWidth()-150, rightTopPanel.getHeight()-10));
        exitButton.setIcon(new ImageIcon(imageRaw));
        exitButton.setLocation(0/*this.openblocksFrame.rightPanel.getX()*/, 0/*this.openblocksFrame.rightPanel.getY()-this.openblocksFrame.rightPanel.getHeight()*/);
//        exitButton.set

//        this.openblocksFrame.
        this.rightTopPanel.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tutorialPane.removeAll();
                openblocksFrame.repaint();
            }
        });
        exitButton.setLocation(150, 0);

        JSplitPane workLayer = openblocksFrame.getContext().getWorkspace().workLayer;
        workLayer.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if (workLayer.getDividerLocation() > factoryPanel.getWidth()) {
                    workLayer.setDividerLocation(factoryPanel.getWidth());
                }
                if (workLayer.getDividerLocation() < factoryPanel.getWidth()) {
                    workLayer.setDividerLocation(factoryPanel.getWidth());
                }
            }
        });
        //openblocksFrame.setResizable(false);

        this.repaint();
        this.startTutor();
    }

    private void startTutor() {
        this.nextTutor();
    }

    /**
     *
     */
    public void nextTutor() {
        this.repaintPanels();
        if ((iter == 0) || (iter == this.inactiveTutorPanels.size() - 1)) {
            this.resetPanels();
            this.inactiveTutorPanels.get(iter).add(tutorTextPanel);
            tutorTextPanel.setText(this.tutorTexts.get(iter));
            tutorTextPanel.setTextColor(tutorColors.get(iter));
            tutorTextPanel.changeDimension(this.tutorDimensions.get(iter));
            tutorTextPanel.changeLocation(this.tutorLocations.get(iter));
        } else if (iter == this.inactiveTutorPanels.size()) {
            tutorialPane.removeAll();
            openblocksFrame.repaint();
            return;
        } else {
            this.inactiveTutorPanels.get(iter).add(tutorTextPanel);
            tutorTextPanel.setText(this.tutorTexts.get(iter));
            tutorTextPanel.setTextColor(tutorColors.get(iter));
            tutorTextPanel.changeDimension(this.tutorDimensions.get(iter));
            tutorTextPanel.changeLocation(this.tutorLocations.get(iter));

            if (this.activeAnimPanels.get(iter).get(0) != this.activeAnimPanels.get(lastIter).get(0)
                    || iter == 1) {
                this.resetPanels();
                for (DarkPanel pane : this.activeAnimPanels.get(iter)) {
                    pane.startAnimation();
                }
            }
            if (this.tutorIllumPanels.get(iter).get(0) != dummy) {
                for (IllumPanel pane : this.tutorIllumPanels.get(iter)) {
                    this.add(pane);
                    pane.startAnimation();
                }
            }
        }
        this.inactiveTutorPanels.get(iter).repaint();

    }

    void resetPanels() {
        logoPanel.setColorAlpha(128);
        rightTopPanel.setColorAlpha(128);
        factoryPanel.setColorAlpha(128);
        buttonsPanel.setColorAlpha(128);
        workspacePanel.setColorAlpha(128);
        controllerPanel.setColorAlpha(128);
    }

    void repaintPanels() {
        JComponent component = openblocksFrame.logo;
        logoPanel.repaintPanel(new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22));

        component = openblocksFrame.rightPanel;
        rightTopPanel.repaintPanel(new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22));

        component = openblocksFrame.getContext().
                getWorkspace().getFactoryManager().getNavigator().getJComponent();
        factoryPanel.repaintPanel(new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22 + logoPanel.getHeight()));

        component = openblocksFrame.northPanelCenter;
        buttonsPanel.repaintPanel(new Dimension(component.getWidth(), component.getHeight() - 1),
                new Point(component.getX(), component.getY() + 22));

        component = openblocksFrame.getContext().getWorkspace().centerPane;
        workspacePanel.repaintPanel(new Dimension(component.getWidth() + 6, component.getHeight() + 1),
                new Point(component.getX() - 6, component.getY() + 22 + logoPanel.getHeight() - 1));

        component = openblocksFrame.getContext().getWorkspace().controller;
        controllerPanel.repaintPanel(new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + 22 + logoPanel.getHeight()));

        this.repaint();
    }

    /**
     *
     */
    private void initPanels() {
        tutorTextPanel = new TPanel(this);

        JComponent component = openblocksFrame.logo;
        JComponent component2;
        //----------------------------------------------------------------------
        logoPanel = new DarkPanel(openblocksFrame, this,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + menuHeight));

        component = openblocksFrame.rightPanel;
        rightTopPanel = new DarkPanel(openblocksFrame, this,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + menuHeight));

        component = openblocksFrame.getContext().
                getWorkspace().getFactoryManager().getNavigator().getJComponent();
        factoryPanel = new DarkPanel(openblocksFrame, this,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + menuHeight
                        + logoPanel.getHeight())); //TODO menuHeight

        component = openblocksFrame.northPanelCenter;
        buttonsPanel = new DarkPanel(openblocksFrame, this,
                new Dimension(component.getWidth(), component.getHeight() - 1),
                new Point(component.getX(), component.getY() + menuHeight));

        component = openblocksFrame.getContext().getWorkspace().centerPane;
        workspacePanel = new DarkPanel(openblocksFrame, this,
                new Dimension(component.getWidth() + 6, component.getHeight() + 1),
                new Point(component.getX() - 6, component.getY() + menuHeight
                        + logoPanel.getHeight() - kostilEbani));

        component = openblocksFrame.getContext().getWorkspace().controller;
        controllerPanel = new DarkPanel(openblocksFrame, this,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + menuHeight
                        + logoPanel.getHeight()));

        //----------------------------------------------------------------------        
        component = openblocksFrame.getContext().getWorkspace().getFactoryManager().getNavigator().getCanvasPane();
        IllumPanel blocksChoice = new IllumPanel(openblocksFrame, this,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + menuHeight + logoPanel.getHeight()),
                true);

        component = openblocksFrame.getContext().getWorkspace().getFactoryManager().getNavigator().getButtonsPane();
        IllumPanel cathegoryChoice = new IllumPanel(openblocksFrame, this,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + menuHeight + logoPanel.getHeight()),
                true);

        component = openblocksFrame.getContext().getWorkspace().getFactoryManager().getNavigator().getSearchBar();
        IllumPanel searchBar = new IllumPanel(openblocksFrame, this,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + menuHeight + logoPanel.getHeight()),
                true);

        component = openblocksFrame.verifyButton;
        IllumPanel verifyButton = new IllumPanel(openblocksFrame, this,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX() + logoPanel.getWidth(), component.getY() + menuHeight),
                true);

        component = openblocksFrame.generateButton;
        IllumPanel generateButton = new IllumPanel(openblocksFrame, this,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX() + logoPanel.getWidth(), component.getY() + menuHeight),
                true);
        
        component = openblocksFrame.getContext().getWorkspace().zoomPlus;
        component2 = openblocksFrame.getContext().getWorkspace().level_two;
        IllumPanel ZPlus = new IllumPanel(openblocksFrame, this,
                new Dimension(
                        component.getWidth(),
                        component.getHeight()),
                new Point(
                        component2.getX() + component.getX() + logoPanel.getWidth() + 2,
                        component2.getY() + component.getY() + logoPanel.getHeight() + menuHeight), false);
        
        component = openblocksFrame.getContext().getWorkspace().zoomMinus;
        IllumPanel ZMinus = new IllumPanel(openblocksFrame, this,
                new Dimension(
                        component.getWidth(),
                        component.getHeight()),
                new Point(
                        component2.getX() + component.getX() + logoPanel.getWidth() + 2,
                        component2.getY() + component.getY() + logoPanel.getHeight() + menuHeight),
                false);
        
        component = openblocksFrame.getContext().getWorkspace().zoomNormal;
        IllumPanel ZNormal = new IllumPanel(openblocksFrame, this,
                new Dimension(
                        component.getWidth(),
                        component.getHeight()),
                new Point(
                        component2.getX() + component.getX() + logoPanel.getWidth() + 2,
                        component2.getY() + component.getY() + logoPanel.getHeight() + menuHeight),
                false);
        
        component = openblocksFrame.getContext().getWorkspace().undoAct;
        IllumPanel CtrlZ = new IllumPanel(openblocksFrame, this,
                new Dimension(
                        component.getWidth(),
                        component.getHeight()),
                new Point(
                        component2.getX() + component.getX() + logoPanel.getWidth() + 2,
                        component2.getY() + component.getY() + logoPanel.getHeight() + menuHeight),
                false);
        
        component = openblocksFrame.getContext().getWorkspace().redoAct;
        IllumPanel CtrlY = new IllumPanel(openblocksFrame, this,
                new Dimension(
                        component.getWidth(),
                        component.getHeight()),
                new Point(
                        component2.getX() + component.getX() + logoPanel.getWidth() + 2,
                        component2.getY() + component.getY() + logoPanel.getHeight() + menuHeight),
                false);
        
        dummy = new IllumPanel(openblocksFrame, this,
                new Dimension(0, 0),
                new Point(0, 0), true);

        ArrayList temp = new ArrayList();
        //1
        temp.add(factoryPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(dummy);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Обучение. Для выхода из обучения нажмите на кнопку \"Закрыть обучение\" в правом верхнем углу. Кнопки \"Далее\" и \"Назад\" используются для перехда к следующему шагу или возвращения к предыдущему. Во время обучения можно использовать все элементы интерфейса.");
        tutorLocations.add(new Point(
                (workspacePanel.getWidth() - 300) / 2,
                (workspacePanel.getHeight() - 150) / 3));
        tutorDimensions.add(new Dimension(300, 150));
        tutorColors.add(Color.WHITE);
        //2
        temp = new ArrayList();
        temp.add(factoryPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(dummy);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Панель для выбора блоков.");
        tutorLocations.add(new Point(
                (workspacePanel.getWidth() - 200) / 10,
                (workspacePanel.getHeight() - 100) / 3));
        tutorDimensions.add(new Dimension(200, 100));
        tutorColors.add(Color.WHITE);
        //3
        temp = new ArrayList();
        temp.add(workspacePanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(dummy);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(factoryPanel);
        tutorTexts.add("Холст, где будет писаться программа. ВНИМАНИЕ: выполняются только те блоки, которые подключены в \"ЦИКЛ\" или \"ПРОГРАММА\".");
        tutorLocations.add(new Point(
                (factoryPanel.getWidth() - 300) / 2,
                (factoryPanel.getHeight() - 200) / 3));
        tutorDimensions.add(new Dimension(300, 200));
        tutorColors.add(Color.WHITE);
        //4
        temp = new ArrayList();
        temp.add(controllerPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(dummy);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Подключения к контроллеру. Здесь отражены подсказки при составлении программы.");
        tutorLocations.add(new Point(
                (workspacePanel.getWidth() - 300) / 10 * 9,
                (workspacePanel.getHeight() - 150) / 3));
        tutorDimensions.add(new Dimension(300, 150));
        tutorColors.add(Color.WHITE);
        //5
        temp = new ArrayList();
        temp.add(buttonsPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(dummy);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Кнопки для работы с программой.");
        tutorLocations.add(new Point(
                (workspacePanel.getWidth() - 260) / 5,
                (workspacePanel.getHeight() - 100) / 10));
        tutorDimensions.add(new Dimension(260, 100));
        tutorColors.add(Color.WHITE);
        //6
        temp = new ArrayList();
        temp.add(factoryPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(blocksChoice);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Перейдем к выбору блоков. Блоки разделяются на 3 типа - изначальный блок, управляющий блок, блок-команда и блок-переменная. Блоки-команды нужно подключать с помощью разъемов-выемок друг к другу, блоки-переменные подключаются к блокам-командам справа с помощью разъема. Изначальные блоки - это \"ЦИКЛ\" и \"ПРОГРАММА\". Все блоки подключаются к ним.");
        tutorLocations.add(new Point(
                (workspacePanel.getWidth() - 300) / 10,
                (workspacePanel.getHeight() - 300) / 3));
        tutorDimensions.add(new Dimension(300, 300));
        tutorColors.add(Color.WHITE);
        //7
        temp = new ArrayList();
        temp.add(workspacePanel);
        temp.add(factoryPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(dummy);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(controllerPanel);
        tutorTexts.add("Попробуйте вытащить любой блок-команду и подключить его к циклу. ПОДСКАЗКА: ориентируйтесь по форме блоков.");
        tutorLocations.add(new Point(
                (controllerPanel.getWidth() - 300) / 2,
                (controllerPanel.getHeight() - 150) / 3));
        tutorDimensions.add(new Dimension(300, 150));
        tutorColors.add(Color.WHITE);
        //8
        temp = new ArrayList();
        temp.add(workspacePanel);
        temp.add(factoryPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(cathegoryChoice);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(controllerPanel);
        tutorTexts.add("Выберите категорию \"СЕРВОПРИВОД\" и подключите к своей программе понравившийся блок.");
        tutorLocations.add(new Point(
                (controllerPanel.getWidth() - 300) / 2,
                (controllerPanel.getHeight() - 150) / 3));   
        tutorDimensions.add(new Dimension(300, 150));
        tutorColors.add(Color.WHITE);
        //9
        temp = new ArrayList();
        temp.add(factoryPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(searchBar);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Для поиска нужного блока можно обратиться к \"Поиску\". Например, вы можете написать в строку поиска \"задержка\" и получите блоки-команды, которые выполняют функцию задержки перед следующими командами. Добавьте понравившийся блок в программу.");
        tutorLocations.add(new Point(
                (workspacePanel.getWidth() - 300) / 10,
                (workspacePanel.getHeight() - 300) / 3));
        tutorDimensions.add(new Dimension(300, 300));
        tutorColors.add(Color.WHITE);
        //10
        temp = new ArrayList();
        temp.add(workspacePanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(ZPlus);
        temp.add(ZMinus);
        temp.add(ZNormal);
        temp.add(CtrlZ);
        temp.add(CtrlY);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(factoryPanel);
        tutorTexts.add("Попробуйте отдалить или приблизить камеру. Знак \"=\" - это изначальный масштаб. ");
        tutorLocations.add(new Point(
                (factoryPanel.getWidth() - 300) / 2,
                (factoryPanel.getHeight() - 170) / 3));                
        tutorDimensions.add(new Dimension(300, 170));
        tutorColors.add(Color.WHITE);
        //11
        temp = new ArrayList();
        temp.add(buttonsPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(dummy);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Теперь попробуем сгенерировать код программы. Обратите внимание, что при генерации в всплывающем окне Arduino IDE появляется код, соответствующий составленной вами программе.");
        tutorLocations.add(new Point(
                (workspacePanel.getWidth() - 300) / 10,
                (workspacePanel.getHeight() - 200) / 15));
        tutorDimensions.add(new Dimension(300, 200));
        tutorColors.add(Color.WHITE);
        //12
        temp = new ArrayList();
        temp.add(buttonsPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(verifyButton);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Нажмите на кнопку \"Сгенерировать код\". Произойдет компиляция кода, если блоки подобраны правильно. Это значит, что код будет работать и его можно загрузить на контроллер.");
        tutorLocations.add(new Point(
                (workspacePanel.getWidth() - 300) / 10,
                (workspacePanel.getHeight() - 300) / 15));
        tutorDimensions.add(new Dimension(300, 300));
        tutorColors.add(Color.WHITE);
        //13
        temp = new ArrayList();
        temp.add(controllerPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(dummy);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Попробуем собрать нашего робота. Возьмите плату ОмегаБота и Светодиод.");
        tutorLocations.add(new Point(
                (workspacePanel.getWidth() - 300) / 10 * 9,
                (workspacePanel.getHeight() - 100) / 10 * 2));
        tutorDimensions.add(new Dimension(300, 100));
        tutorColors.add(Color.WHITE);
        //14
        temp = new ArrayList();
        temp.add(controllerPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        for (ControllerButton pane : openblocksFrame.getContext().getWorkspace().controller.controllerImage.connectorButtons) {
            temp.add(new IllumPanel(
                    openblocksFrame, this,
                    new Dimension(pane.getWidth(), pane.getHeight()),
                    new Point(pane.getX() + openblocksFrame.getContext().getWorkspace().controller.getX() + 1,
                            pane.getY() + openblocksFrame.getContext().getWorkspace().controller.getY()
                            + logoPanel.getHeight() + menuHeight + 1), true));
        }
        tutorIllumPanels.add(temp); //TODO
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Нажатием на коннектор (разъем) вы откроете список модулей, которые можно к нему подключить. Вы можете подключать модули, ориентируяс на нее.");
        tutorLocations.add(new Point(
                (workspacePanel.getWidth() - 300) / 10 * 9,
                (workspacePanel.getHeight() - 200) / 10 * 2));
        tutorDimensions.add(new Dimension(300, 200));
        tutorColors.add(Color.WHITE);
        //15
        temp = new ArrayList();
        temp.add(controllerPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        for (ControllerButton pane : openblocksFrame.getContext().getWorkspace().controller.controllerImage.moduleButtons) {
            temp.add(new IllumPanel(
                    openblocksFrame, this,
                    new Dimension(pane.getWidth(), pane.getHeight()),
                    new Point(pane.getX() + openblocksFrame.getContext().getWorkspace().controller.getX() + 1,
                            pane.getY() + openblocksFrame.getContext().getWorkspace().controller.getY()
                            + logoPanel.getHeight() + menuHeight + 1), true));
        }
        tutorIllumPanels.add(temp); //TODO
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Нажмите на сам модуль в изображении или на его название в списке. Вы увидите краткое описание модуля и его изображение, приближенное к реальности. Также в панели вы можете открыть блоки, которые могут взаимодействовать с модулем.");
        tutorLocations.add(new Point(
                (workspacePanel.getWidth() - 300) / 10 * 9,
                (workspacePanel.getHeight() - 200) / 10 * 2));
        tutorDimensions.add(new Dimension(300, 200));
        tutorColors.add(Color.WHITE);
        //16
        temp = new ArrayList();
        temp.add(workspacePanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(dummy);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(controllerPanel);
        tutorTexts.add("Составим новую программу. Оставьте на холсте только блок \"ЦИКЛ\".");
        tutorLocations.add(new Point(
                (controllerPanel.getWidth() - 300) / 2,
                (controllerPanel.getHeight() - 300) / 10 * 2));
        tutorDimensions.add(new Dimension(300, 300));
        tutorColors.add(Color.WHITE);
        //17
        temp = new ArrayList();
        temp.add(controllerPanel); //ВАЖНО: если панель новая, то стоит начинать не с предыдущей панели
        temp.add(workspacePanel);  //поэтому первая тут controllerPanel а не workspacePanel
        temp.add(factoryPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(dummy);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Найдите блок \"СВЕТОДИОД\". Добавьте его в программу, в номере порта укажите тот порт, к которому подключен светодиод на плате, а в состоянии укажите \"ВЫСОКИЙ\". Под высоким подразумевается сигнал - то есть светодиод будет гореть.");
        tutorLocations.add(new Point(
                (workspacePanel.getWidth() - 300) / 15 * 14,
                (workspacePanel.getHeight() - 300) / 10 * 2));
        tutorDimensions.add(new Dimension(300, 300));
        tutorColors.add(Color.BLACK);
        //18
        temp = new ArrayList();
        temp.add(buttonsPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(generateButton);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Теперь подключите к компьютеру вашу плату с подключенным к ней светодиодом. Нажмите на кнопку \"Загрузить в Ардуино\". Если вы все сделали верно, то в окне с кодом под ним вы получите либо сообщение об удачной загрузке, либо ошибку о загрузке в плату. Во втором случае вам нужно будет открыть \"Инструменты\", выбрать \"Порт\" и в выпадающем списке выбрать существующий COM-порт.");
        tutorLocations.add(new Point(
                (workspacePanel.getWidth() - 300) / 10,
                (workspacePanel.getHeight() - 300) / 15));
        tutorDimensions.add(new Dimension(300, 300));
        tutorColors.add(Color.WHITE);
        //19
        temp = new ArrayList();
        temp.add(logoPanel);
        activeAnimPanels.add(temp);
        temp = new ArrayList();
        temp.add(dummy);
        tutorIllumPanels.add(temp);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Если светодиод на вашей плате загорелся, то поздравляем! Вы прошли обучение и готовы составлять собственные программы для своего ОмегаБота!");
        tutorLocations.add(new Point(
                (workspacePanel.getWidth() - 300) / 2,
                (workspacePanel.getHeight() - 150) / 3));
        tutorDimensions.add(new Dimension(300, 150));
        tutorColors.add(Color.WHITE);
    }
}
