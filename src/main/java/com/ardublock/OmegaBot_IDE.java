package com.ardublock;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;

import processing.app.Editor;
import processing.app.Editor;
import processing.app.EditorStatus;
import processing.app.EditorTab;
import processing.app.SketchFile;
import processing.app.tools.Tool;

import com.ardublock.core.Context;
import com.ardublock.ui.ArduBlockToolFrame;
import com.ardublock.ui.Settings;
import com.ardublock.ui.TutorialPane;
import com.ardublock.ui.listener.OpenblocksFrameListener;

import java.lang.reflect.Method;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author User
 */
public class OmegaBot_IDE implements Tool, OpenblocksFrameListener {

    static Editor editor;
    static ArduBlockToolFrame openblocksFrame;
    private Preferences userPrefs;

    /**
     *
     * @param editor
     */
    public void init(Editor editor) {
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
        
        if (OmegaBot_IDE.editor == null) {
            OmegaBot_IDE.editor = editor;

            //OmegaBot_IDE.editor.setVisible(false);

            OmegaBot_IDE.openblocksFrame = new ArduBlockToolFrame();
            OmegaBot_IDE.openblocksFrame.addListener(this);
            Context context = Context.getContext();
            String arduinoVersion = this.getArduinoVersion();
            context.setInArduino(true);
            context.setArduinoVersionString(arduinoVersion);
            context.setEditor(editor);
            //editor.setVisible(false);
            System.out.println("Arduino Version: " + arduinoVersion);
            userPrefs = Preferences.userRoot().node("OmegaBot_IDE");
            // Don't just "close" Ardublock, see if there's something to save first.
            // Note to self: Code here only affects behaviour when we're an Arduino Tool,
            // not when run directly - See Main.java for that.
            //ArduBlockTool.openblocksFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            Settings settings = openblocksFrame.settings;
            if (settings.isFirstLaunch()) {
                TutorialPane tutorialPane = new TutorialPane(openblocksFrame);
                openblocksFrame.setGlassPane(tutorialPane);
                openblocksFrame.getGlassPane().setVisible(true);
                openblocksFrame.repaint();
            }

            OmegaBot_IDE.openblocksFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    OmegaBot_IDE.openblocksFrame.doCloseArduBlockFile();
                }
            });
            if (userPrefs.getBoolean("ardublock.ui.autostart", false)) {
                OmegaBot_IDE.openblocksFrame.setVisible(true);
//                Runnable task = () -> {
//                    while (!editor.isVisible());
//                    editor.setVisible(false);
//                };
//                task.run();
            }
        }
    }

    public void run() {
        try {
            //OmegaBot_IDE.editor.toFront();
            OmegaBot_IDE.openblocksFrame.setVisible(true);
            OmegaBot_IDE.openblocksFrame.toFront();
            OmegaBot_IDE.openblocksFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            OmegaBot_IDE.editor.setVisible(false);
        } catch (Exception e) {

        }
        //this.hideEditor();
        java.lang.reflect.Method method;
        try {
            Class ed = OmegaBot_IDE.editor.getClass();
            Class[] cArg = new Class[1];
            cArg[0] = String.class;
            method = ed.getMethod("setVisible", boolean.class);
            method.invoke(OmegaBot_IDE.editor, false);
        } catch (NoSuchMethodException | IllegalAccessException | SecurityException | InvocationTargetException e) {
            OmegaBot_IDE.editor.getCurrentTab().setText("");
        }
        OmegaBot_IDE.editor.handleExport(false);
    }

    /**
     *
     * @return
     */
    public String getMenuTitle() {
        return Context.APP_NAME;
    }

    /**
     *
     */
    public void didSave() {

    }

    /**
     *
     */
    public void didLoad() {

    }

    /**
     *
     */
    public void didSaveAs() {

    }

    /**
     *
     */
    public void didNew() {

    }

    /**
     *
     * @param source
     */
    public void didGenerate(String source) {
        java.lang.reflect.Method method;
        try {
            // pre Arduino 1.6.12
            Class ed = OmegaBot_IDE.editor.getClass();
            Class[] cArg = new Class[1];
            cArg[0] = String.class;
            method = ed.getMethod("setText", cArg);
            method.invoke(OmegaBot_IDE.editor, source);
        } catch (NoSuchMethodException e) {
            OmegaBot_IDE.editor.getCurrentTab().setText(source);
        } catch (IllegalAccessException e) {
            OmegaBot_IDE.editor.getCurrentTab().setText(source);
        } catch (SecurityException e) {
            OmegaBot_IDE.editor.getCurrentTab().setText(source);
        } catch (InvocationTargetException e) {
            OmegaBot_IDE.editor.getCurrentTab().setText(source);
        }
        OmegaBot_IDE.editor.handleExport(false);
    }

    /**
     *
     * @param source
     */
    public void didVerify(String source) {
        java.lang.reflect.Method method;
        try {
            // pre Arduino 1.6.12
            Class ed = OmegaBot_IDE.editor.getClass();
            Class[] cArg = new Class[1];
            cArg[0] = String.class;
            method = ed.getMethod("setText", cArg);
            method.invoke(OmegaBot_IDE.editor, source);
        } catch (NoSuchMethodException e) {
            OmegaBot_IDE.editor.getCurrentTab().setText(source);
        } catch (IllegalAccessException e) {
            OmegaBot_IDE.editor.getCurrentTab().setText(source);
        } catch (SecurityException e) {
            OmegaBot_IDE.editor.getCurrentTab().setText(source);
        } catch (InvocationTargetException e) {
            OmegaBot_IDE.editor.getCurrentTab().setText(source);
        }
        try {
            Class ed = OmegaBot_IDE.editor.getClass();
            Method reset = Editor.class.getDeclaredMethod("resetHandlers");
            reset.setAccessible(true);
            reset.invoke(OmegaBot_IDE.editor);
            Field ph = Editor.class.getDeclaredField("presentHandler");
            ph.setAccessible(true);
            Runnable run_ph = (Runnable) ph.get(OmegaBot_IDE.editor);
            Field rh = Editor.class.getDeclaredField("runHandler");
            rh.setAccessible(true);
            Runnable run_rh = (Runnable) rh.get(OmegaBot_IDE.editor);
            OmegaBot_IDE.editor.handleRun(false, run_ph, run_rh);
        } catch (NoSuchFieldException e) {
            System.out.println(e.toString());
        } catch (IllegalAccessException e) {
            System.out.println(e.toString());
        } catch (NoSuchMethodException e) {
            System.out.println(e.toString());
        } catch (InvocationTargetException e) {
            System.out.println(e.toString());
        }
    }

    private String getArduinoVersion() {
        Context context = Context.getContext();
        File versionFile = context.getArduinoFile("lib/version.txt");
        if (versionFile.exists()) {
            try {
                InputStream is = new FileInputStream(versionFile);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line = reader.readLine();
                reader.close();
                if (line == null) {
                    return Context.ARDUINO_VERSION_UNKNOWN;
                }
                line = line.trim();
                if (line.length() == 0) {
                    return Context.ARDUINO_VERSION_UNKNOWN;
                }
                return line;

            } catch (FileNotFoundException e) {
                return Context.ARDUINO_VERSION_UNKNOWN;
            } catch (UnsupportedEncodingException e) {
                return Context.ARDUINO_VERSION_UNKNOWN;
            } catch (IOException e) {
                e.printStackTrace();
                return Context.ARDUINO_VERSION_UNKNOWN;
            }
        } else {
            return Context.ARDUINO_VERSION_UNKNOWN;
        }

    }
}
