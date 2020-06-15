package com.ardublock;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.ardublock.core.Context;
import com.ardublock.ui.ConsoleFrame;
import com.ardublock.ui.OpenblocksFrame;
import com.ardublock.ui.Settings;
import com.ardublock.ui.TutorialPane;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author AdmiralPaw, Ritevi, Aizek
 * Главный класс проекта
 */
public class Main {

    private OpenblocksFrame openblocksFrame;

    /**
     * Главный метод класса
     * @param args - Аргументы получаемые главным классом
     * @exception ClassNotFoundException - Не был найден необходимый класс
     * @exception InstantiationException - Экземпляр невозможно создать (не был создан)
     * @exception IllegalAccessException - Возможность несанкционированного доступа
     * @exception UnsupportedLookAndFeelException - Неподдерживаемый внешний вид и Руководство для системы пользователя
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        Main me = new Main();
        me.startArdublock();
    }

    /**
     * Метод, вызывающий метод startOpenblocksFrame
     */
    public void startArdublock() {
        startOpenblocksFrame();
    }

    /**
     * Метод создающий оконную процедуру приложения
     */
    private void startOpenblocksFrame() {
        openblocksFrame = new OpenblocksFrame();

        // Don't just "close" Ardublock, see if there's something to save first.
        // Note to self: This only affects behaviour when we're run directly,
        // not when we're an Arduino Tool - See ArduBlockTool.java for that.
        openblocksFrame.addWindowListener(new WindowAdapter() {
            /**
             * Метод закрывающий файл ArduBlock
             * @param e - Оконное событие
             */
            public void windowClosing(WindowEvent e) {
                openblocksFrame.doCloseArduBlockFile();
            }
        });
        
        Context context = Context.getContext();
        context.setInArduino(false);
        openblocksFrame.setMinimumSize(new Dimension(1140, 460));
        openblocksFrame.setVisible(true);
        openblocksFrame.repaint();
        
//        Settings settings = openblocksFrame.settings;
//        if (settings.isFirstLaunch()) {
//            TutorialPane pan = new TutorialPane(openblocksFrame);
//            openblocksFrame.setGlassPane(pan);
//            pan.setOpaque(false);
//            pan.setVisible(true);
//            openblocksFrame.getGlassPane().setVisible(true);
//        }
    }

    /**
     * Метод, закрывающий оконную процедуру
     */
    public void shutdown() {
        openblocksFrame.dispatchEvent(new WindowEvent(openblocksFrame, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Метод, запускающий консольную процедуру
     */
    @SuppressWarnings("unused")
    private void startConsoleFrame() {
        ConsoleFrame consoleFrame = new ConsoleFrame();
        consoleFrame.setVisible(true);
    }
}