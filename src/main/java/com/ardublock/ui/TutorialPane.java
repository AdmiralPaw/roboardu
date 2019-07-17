package com.ardublock.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Area;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
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
    public TutorialPane tutorialPane;
    public IllumPanel dummy;

    public List<List<DarkPanel>> activeAnimPanels = new ArrayList<List<DarkPanel>>();
    public List<DarkPanel> inactiveTutorPanels = new ArrayList<DarkPanel>();
    public List<Point> tutorLocations = new ArrayList<Point>();
    public List<Dimension> tutorDimensions = new ArrayList<Dimension>();
    public List<String> tutorTexts = new ArrayList<String>();
    public List<Color> tutorColor = new ArrayList<Color>();
    public List<IllumPanel> tutorIllumPanels = new ArrayList<IllumPanel>();

    public TPanel tutorTextPanel;

    private final int menuHeight = 22;
    private final int kostilEbani = 1;

    public int iter = 0;
    public int lastIter = 1;
    private boolean tutorIsActive = true;

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

        JButton exitButton = new JButton("выйти");
        exitButton.setSize(new Dimension(70, 30));
        exitButton.setLocation(0, 0);
        this.factoryPanel.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tutorialPane.removeAll();
                openblocksFrame.repaint();
            }
        });

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

    public void nextTutor() {
        this.repaintPanels();
        if (iter == 0) {
            this.resetPanels();
            this.inactiveTutorPanels.get(iter).add(tutorTextPanel);
            tutorTextPanel.setText(this.tutorTexts.get(iter));
        } else if (iter == this.inactiveTutorPanels.size()) {
            tutorialPane.removeAll();
            openblocksFrame.repaint();
            return;
        } else if (iter == this.inactiveTutorPanels.size() - 1) {
            this.resetPanels();
            this.inactiveTutorPanels.get(iter).add(tutorTextPanel);
            tutorTextPanel.setText(this.tutorTexts.get(iter));
        } else {
            this.inactiveTutorPanels.get(iter).add(tutorTextPanel);
            tutorTextPanel.setText(this.tutorTexts.get(iter));

            if (this.activeAnimPanels.get(iter).get(0) != this.activeAnimPanels.get(lastIter).get(0)
                    || iter == 1) {
                this.resetPanels();
                for (DarkPanel pane : this.activeAnimPanels.get(iter)) {
                    pane.startAnimation();
                }
            }
            if (this.tutorIllumPanels.get(iter) != dummy) {
                this.add(tutorIllumPanels.get(iter));
                this.tutorIllumPanels.get(iter).startAnimation();
            }
//        if (!this.tutorDimensions.get(iter).equals(null)) {
//            tutorTextPanel.setSize(this.tutorDimensions.get(iter));
//        }
//        if (!this.tutorLocations.get(iter).equals(null)) {
//            tutorTextPanel.setLocation(this.tutorLocations.get(iter));
//        }
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

    private void initPanels() {
        tutorTextPanel = new TPanel(this);

        JComponent component = openblocksFrame.logo;
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
                new Point(component.getX(), component.getY() + menuHeight + logoPanel.getHeight()));

        component = openblocksFrame.getContext().getWorkspace().getFactoryManager().getNavigator().getButtonsPane();
        IllumPanel cathegoryChoice = new IllumPanel(openblocksFrame, this,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + menuHeight + logoPanel.getHeight()));

        component = openblocksFrame.getContext().getWorkspace().getFactoryManager().getNavigator().getSearchBar();
        IllumPanel searchBar = new IllumPanel(openblocksFrame, this,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX(), component.getY() + menuHeight + logoPanel.getHeight()));

        component = openblocksFrame.verifyButton;
        IllumPanel verifyButton = new IllumPanel(openblocksFrame, this,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX() + logoPanel.getWidth(), component.getY() + menuHeight));

        component = openblocksFrame.generateButton;
        IllumPanel generateButton = new IllumPanel(openblocksFrame, this,
                new Dimension(component.getWidth(), component.getHeight()),
                new Point(component.getX() + logoPanel.getWidth(), component.getY() + menuHeight));

        dummy = new IllumPanel(openblocksFrame, this,
                new Dimension(0, 0),
                new Point(0, 0));

        ArrayList temp = new ArrayList();
        //1
        temp.add(factoryPanel);
        activeAnimPanels.add(temp);
        inactiveTutorPanels.add(workspacePanel);
        tutorIllumPanels.add(dummy);
        tutorTexts.add("Обучение. Нажмите на \"крестик\" для выхода из обучения. Кнопки \"Далее\" и \"Назад\" используются для перехда к следующему шагу или возвращения к предыдущему. Во время обучения можно использовать все элементы интерфейса.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //2
        temp = new ArrayList();
        temp.add(factoryPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(dummy);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Панель для выбора блоков.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //3
        temp = new ArrayList();
        temp.add(workspacePanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(dummy);
        inactiveTutorPanels.add(factoryPanel);
        tutorTexts.add("Холст, где будет писаться программа. ВНИМАНИЕ: выполняются только те блоки, которые подключены в \"ЦИКЛ\" или \"ПРОГРАММА\". Дополнительные детали работы с блоками описаны в методичке.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //4
        temp = new ArrayList();
        temp.add(controllerPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(dummy);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Подключения к контроллеру. Здесь указаны/отражены/обозначены подсказки при составлении программы.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //5
        temp = new ArrayList();
        temp.add(buttonsPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(dummy);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Кнопки для работы с программой.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //6
        temp = new ArrayList();
        temp.add(factoryPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(blocksChoice);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Перейдем к выбору блоков. Любой блок разделяется на 3 типа - изначальный блок, управляющий блок, блок-команда и блок-переменная. Блоки-команды нужно подключать с помощью разъемов-выемок друг к другу, блоки-переменные подключаются к блокам-командам справа с помощью разъема. Изначальные блоки - это \"ЦИКЛ\" и \"ПРОГРАММА\". Они никуда не подключаются, к ним подключаются все остальные блоки.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //7
        temp = new ArrayList();
        temp.add(workspacePanel);
        temp.add(factoryPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(dummy);
        inactiveTutorPanels.add(controllerPanel);
        tutorTexts.add("Попробуйте вытащить любой блок-команду и подключите его к циклу. ПОДСКАЗКА: ориентируйтесь по форме блоков.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //8
        temp = new ArrayList();
        temp.add(workspacePanel);
        temp.add(factoryPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(cathegoryChoice);
        inactiveTutorPanels.add(controllerPanel);
        tutorTexts.add("Выберите категорию \"СЕРВОПРИВОД\" и подключите к своей программе понравившийся блок.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //9
        temp = new ArrayList();
        temp.add(factoryPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(searchBar);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Для поиска нужного блока можно обратиться к \"Поиску\" - без забыли и точно без лень искать. Например, вы можете написать в строку поиска \"задержка\" и получите блоки-команды, которые выполняют функцию задержки перед следующими командами. Добавьте понравившийся блок в программу. - еще на счет обращений не уверена, возможно все нужно будет писать \"обезличенно\", но это нужно будет подумать и уточнить как лучше");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //10
        temp = new ArrayList();
        temp.add(workspacePanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(dummy);
        inactiveTutorPanels.add(factoryPanel);
        tutorTexts.add("Да у вас уже столько блоков в программе! Попробуйте отдалить или приблизить камеру. ПОДСКАЗКА: Знак \"=\" - это изначальный масштаб.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //11
        temp = new ArrayList();
        temp.add(buttonsPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(dummy);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Теперь попробуем сгенерировать код программы. Обратите внимание, что при генерации в всплывающем окне Arduino IDE появляется код, соответствующий составленной вами программе.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //12
        temp = new ArrayList();
        temp.add(buttonsPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(verifyButton);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Нажмите на кнопку \"Сгенерировать код\" - тут надо будет картинку кнопки или она будет подсвечиватся тоже?. Если вы все сделали правильно (тут сразу возникает вопрос а что все сделали правильно? все правильно слишком общее мне кажется. Может написать как-то: \"Произойдет компиляция кода, если блоки подобраны правильно/по правилам (ведь писали до этого что там надо чтобы они сответствовали друг другу), то код просто скомпилируется. Это значит, что код будет работать и его можно загрузить на контроллер.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //13
        temp = new ArrayList();
        temp.add(controllerPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(dummy);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Попробуем собрать нашего робота. Возьмите плату ОмегаБота и Светодиод.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //14
        temp = new ArrayList();
        temp.add(controllerPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(dummy); //TODO
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Нажатием на коннектор (разъем) вы откроете список модулей, которые можно к нему подключить. Схема в программе соответствует реальной плате, поэтому подключать модули можно ориентируясь на нее.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //15
        temp = new ArrayList();
        temp.add(controllerPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(dummy); //TODO
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Нажмите на сам модуль в изображении или на его название в списке. Вы получите краткое описание модуля и его изображение о модуле и как он примерно выглядит в реальности. Так же в панели вы можете получить блоки, которые могут взаимодействовать с модулем.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //16
        temp = new ArrayList();
        temp.add(workspacePanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(dummy);
        inactiveTutorPanels.add(controllerPanel);
        tutorTexts.add("Теперь удалите ранее написанную вами программу, но не блок \"ЦИКЛ\". Хотя, если вы и его удалили, то можете его достать в категории \"УПРАВЛЕНИЕ\". - не понимаю к чему это..про удалите это, не удаляйте то\n"
                + "Если здесь про возможность удалять блоки, то можно просто написать вот так то и так то можно удалять блоки.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //17
        temp = new ArrayList();
        temp.add(controllerPanel); //ВАЖНО: если панель новая, то стоит начинать не с предыдущей панели
        temp.add(workspacePanel);  //поэтому первая тут controllerPanel а не workspacePanel
        temp.add(factoryPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(dummy);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Перейдем к небольшой тренировке. Найдите блок \"СВЕТОДИОД\". Добавьте его в программу, в номере порта укажите тот порт, к которому подключен светодиод на плате, а в состоянии укажите \"ВЫСОКИЙ\". Под высоким подразумевается сигнал - то есть светодиод будет гореть.");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //18
        temp = new ArrayList();
        temp.add(buttonsPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(generateButton);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Теперь подключите к компьютеру вашу плату с подключенным к ней светодиодом. Нажмите на кнопку \"Отправить в Ардуино\" - картинка кнопки или подсветка. Если вы все сделали верно, то в окне с кодом в выводе вы получите либо сообщение о удачной загрузке, либо ошибку о загрузке в плату. Во втором случае вам нужно будет открыть \"Инструменты\", выбрать \"Порт\" и в выпадающем списке выбрать существующий COM-порт.  - но нужно продумать что реально делать если это так и случится...как вариант сделать на сайте раздел \"вопросы и ошибки\" чтобы если что понимат что может идти не так, если у нас не предусмотрены сообщения об ошибках которые отпраляются");
        tutorLocations.add(null);
        tutorDimensions.add(null);
        //19
        temp = new ArrayList();
        temp.add(logoPanel);
        activeAnimPanels.add(temp);
        tutorIllumPanels.add(dummy);
        inactiveTutorPanels.add(workspacePanel);
        tutorTexts.add("Если светодиод на вашей плате загорелся, то поздравляем! Вы прошли обучение и готовы писать собственные программы для своего ОмегаБота!");
        tutorLocations.add(null);
        tutorDimensions.add(null);
    }
}
