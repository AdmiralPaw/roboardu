package com.ardublock.ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSpinnerUI;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * @author AdmiralPaw, Ritevi, Aizek
 * Класс, работающий с однострочным полем ввода, позволяющим пользователю выбрать число или значение
 * объекта из упорядоченной последовательности
 */
public class RSpinner extends JSpinner {

    /**
     * Метод, работающий с выпадающим списком (однострочным полем ввода), а именно с его внешним видом
     * @param model - Несёт параметры модели для выпадающего списка
     */
    public RSpinner(SpinnerNumberModel model)
    {
        super(model);
        this.setUI(new RSpinnerUI());
        this.setBackground(new Color(236,236,236));
        this.setBorder(BorderFactory.createLineBorder(new Color(198,198,198),1));
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                //Поле минимума значения (минимального значения)
                int min = e.getComponent().getHeight();

                getComponent(0).setPreferredSize(new Dimension(min, min/2));
                getComponent(1).setPreferredSize(new Dimension(min, min/2));
                getComponent(2).setPreferredSize(new Dimension(e.getComponent().getWidth() - min, e.getComponent().getHeight()/2));
            }
        });

    }

    /**
     * Метод для получения значения
     * @return Integer.parseInt(getValue().toString())
     */
    public int getIntValue()
    {
        return Integer.parseInt(getValue().toString());
    }
}

/**
 * @author AdmiralPaw, Ritevi, Aizek
 * Класс, работающий конкретно с пользовательским интефрейсом для выпадающего списка
 */
class RSpinnerUI extends BasicSpinnerUI
{
    //Поле с экземпляром выпадающего списка
    public static JSpinner s;

    //Поле с компонентом пользовательского интерфейса для выпадающего списка
    public static ComponentUI createUI(JComponent c)
    {
        return new RSpinnerUI();

    }

    /**
     * Метод, создающий новую кнопку прослушивателя
     * @return JButton b
     */
    public Component createNextButton()
    {
        JButton b = new RSpinnerButton(SwingConstants.NORTH);
        b.setName("Spinner.nextButton");
        installNextButtonListeners(b);
        return b;
    }

    /**
     * Метод, создающий предыдущую кнопку прослушивателя
     * @return JButton b
     */
    public Component createPreviousButton()
    {
        JButton b = new RSpinnerButton(SwingConstants.SOUTH);
        b.setName("Spinner.previousButton");
        installPreviousButtonListeners(b);
        return b;
    }

    /**
     * Метод, создающий редактор выпадающих списков
     * @return new SpinEditor(super.spinner)
     */
    public JComponent createEditor()
    {
        return  new SpinEditor(super.spinner);
    }

    /**
     * @author AdmiralPaw, Ritevi, Aizek
     * Класс, работающий с редактором выпадающих списков (внешний вид, значения)
     */
    public class SpinEditor extends JPanel implements ChangeListener
    {
        JTextField editor;

        /**
         * Метод, описывающий редактируемые свойства выпадающего списка (цвет, фон, шрифт и т.д.)
         * @param spin - экземпляр выпадающего списка
         */
        public SpinEditor(JSpinner spin)
        {
            super();
            setLayout(new BorderLayout());
            editor = new JTextField();
            editor.setBackground(new Color(236,236,236));
            editor.setText(spinner.getValue().toString());
            editor.setHorizontalAlignment(SwingConstants.CENTER);
            editor.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
            editor.setForeground(new Color(0,152,157));
            editor.setBorder(BorderFactory.createEmptyBorder());
            editor.setEditable(false);
            this.setBackground(new Color(236,236,236));
            add(editor, BorderLayout.CENTER);
            spin.addChangeListener(this);
        }

        /**
         * Метод изменения состояния
         * @param e - Уведомление об изменении состояния
         */
        public void stateChanged(ChangeEvent e)
        {
            JSpinner spinner = (JSpinner)(e.getSource());
            String text = spinner.getValue().toString();
            editor.setText(text);
        }
    }

    /**
     * @author AdmiralPaw, Ritevi, Aizek
     * Класс, работающий с кнопкой выпадающего списка (цвет, размеры, направление)
     */
    public class RSpinnerButton extends JButton
    {
        //Поле направления
        private int diraction;

        /**
         * Метод, задающий направление кнопки
         * @param dir - направление
         */
        public RSpinnerButton(int dir)
        {
            super();
            diraction = dir;
        }

        /**
         * Метод, задающий внешний вид кнопки
         * @param g - Параметр графического контекста
         */
        public void paint(Graphics g)
        {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRect(0,0,this.getWidth(), getHeight());

            g2.setColor(new Color(198,198,198));
            g2.drawLine(0,0,0,getHeight()-1);

            //Поле центра по оси Ох
            int xcenter = getWidth() / 2;
            //Поле значения координаты по оси Оу
            int ypos = diraction == SwingConstants.NORTH ? (int)(1f * getHeight() / 5*4) : (int) (1f*getHeight() / 5);
            g2.setColor(new Color(0,152,157));
            fillTriangle(g2, xcenter,  ypos);
        }

        /**
         * Метод для заливки треугольника
         * @param g - Параметр графического контекста
         * @param xpos - Параметр значения по оси Ох
         * @param ypos - Параметр значения по оси Оу
         */
        public void fillTriangle(Graphics2D g, int xpos, int ypos)
        {
            //Поле смещения по оси Ох
            int xoffset = 5;
            //Поле смещения по оси Оу
            int yoffset = 5;
            //Поле массива значений с оси Ох (3 значения для треугольника)
            int[] x = new int[3];
            //Поле массива значений с оси Оу (3 значения для треугольника)
            int[] y = new int[3];

            x[0] =  xpos - xoffset ;
            x[1] =  xpos ;
            x[2] =  xpos + xoffset ;
            if (diraction == SwingConstants.NORTH)
            {
                y[0] =  ypos ;
                y[1] =  ypos - yoffset;
                y[2] =  ypos;
            }
            else
            {
                y[0] =  ypos ;
                y[1] =  ypos + yoffset;
                y[2] =  ypos;
            }

            Polygon p = new Polygon(x,y,3);
            g.fillPolygon(p);

        }
    }


}
