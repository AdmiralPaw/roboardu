package com.ardublock;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.ardublock.core.Context;
import com.ardublock.ui.ConsoleFrame;
import com.ardublock.ui.OpenblocksFrame;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    private OpenblocksFrame openblocksFrame;

    public static void main(String args[]) throws SAXException, IOException, ParserConfigurationException {
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

    public void startArdublock() throws SAXException, IOException, ParserConfigurationException {
        startOpenblocksFrame();
        //startConsoleFrame();
    }

    private void startOpenblocksFrame() throws SAXException, IOException, ParserConfigurationException {
        openblocksFrame = new OpenblocksFrame();

        // Don't just "close" Ardublock, see if there's something to save first.
        // Note to self: This only affects behaviour when we're run directly,
        // not when we're an Arduino Tool - See ArduBlockTool.java for that.
        //openblocksFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openblocksFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                openblocksFrame.doCloseArduBlockFile();
            }
        });

        Context context = Context.getContext();
        context.setInArduino(false);
        openblocksFrame.setMinimumSize(new Dimension(1140, 460));
        openblocksFrame.setVisible(true);
    }

    public void shutdown() {
        openblocksFrame.dispatchEvent(new WindowEvent(openblocksFrame, WindowEvent.WINDOW_CLOSING));
    }

    @SuppressWarnings("unused")
    private void startConsoleFrame() {
        ConsoleFrame consoleFrame = new ConsoleFrame();
        consoleFrame.setVisible(true);
    }
}
