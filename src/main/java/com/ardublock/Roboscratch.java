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
import com.ardublock.ui.listener.OpenblocksFrameListener;

import java.lang.reflect.Method;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class Roboscratch implements Tool, OpenblocksFrameListener {
    
    static Editor editor;
    static ArduBlockToolFrame openblocksFrame;
    private Preferences userPrefs;
    
    public void init(Editor editor) {
        if (Roboscratch.editor == null) {
            Roboscratch.editor = editor;
            Roboscratch.openblocksFrame = new ArduBlockToolFrame();
            Roboscratch.openblocksFrame.addListener(this);
            Context context = Context.getContext();
            String arduinoVersion = this.getArduinoVersion();
            context.setInArduino(true);
            context.setArduinoVersionString(arduinoVersion);
            context.setEditor(editor);
            System.out.println("Arduino Version: " + arduinoVersion);
            userPrefs = Preferences.userRoot().node("roboscratch");
            // Don't just "close" Ardublock, see if there's something to save first.
            // Note to self: Code here only affects behaviour when we're an Arduino Tool,
            // not when run directly - See Main.java for that.
            //ArduBlockTool.openblocksFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            Roboscratch.openblocksFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    Roboscratch.openblocksFrame.doCloseArduBlockFile();
                }
            });
            if (userPrefs.getBoolean("ardublock.ui.autostart", false)) {
                Roboscratch.openblocksFrame.setVisible(true);
            }
        }
    }

    public void run() {
        try {
            Roboscratch.editor.toFront();
            Roboscratch.openblocksFrame.setVisible(true);
            Roboscratch.openblocksFrame.toFront();
            Roboscratch.openblocksFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        } catch (Exception e) {

        }
    }

    public String getMenuTitle() {
        return Context.APP_NAME;
    }

    public void didSave() {

    }

    public void didLoad() {

    }

    public void didSaveAs() {

    }

    public void didNew() {

    }

    public void didGenerate(String source) {
        java.lang.reflect.Method method;
        try {
            // pre Arduino 1.6.12
            Class ed = Roboscratch.editor.getClass();
            Class[] cArg = new Class[1];
            cArg[0] = String.class;
            method = ed.getMethod("setText", cArg);
            method.invoke(Roboscratch.editor, source);
        } catch (NoSuchMethodException e) {
            Roboscratch.editor.getCurrentTab().setText(source);
        } catch (IllegalAccessException e) {
            Roboscratch.editor.getCurrentTab().setText(source);
        } catch (SecurityException e) {
            Roboscratch.editor.getCurrentTab().setText(source);
        } catch (InvocationTargetException e) {
            Roboscratch.editor.getCurrentTab().setText(source);
        }
        Roboscratch.editor.handleExport(false);
    }

    public void didVerify(String source)
    {
        java.lang.reflect.Method method;
        try {
            // pre Arduino 1.6.12
            Class ed = Roboscratch.editor.getClass();
            Class[] cArg = new Class[1];
            cArg[0] = String.class;
            method = ed.getMethod("setText", cArg);
            method.invoke(Roboscratch.editor, source);
        } catch (NoSuchMethodException e) {
            Roboscratch.editor.getCurrentTab().setText(source);
        } catch (IllegalAccessException e) {
            Roboscratch.editor.getCurrentTab().setText(source);
        } catch (SecurityException e) {
            Roboscratch.editor.getCurrentTab().setText(source);
        } catch (InvocationTargetException e) {
            Roboscratch.editor.getCurrentTab().setText(source);
        }
        try
        {
            Class ed = Roboscratch.editor.getClass();
            Method reset = Editor.class.getDeclaredMethod("resetHandlers");
            reset.setAccessible(true);
            reset.invoke(Roboscratch.editor);
            Field ph = Editor.class.getDeclaredField("presentHandler");
            ph.setAccessible(true);
            Runnable run_ph = (Runnable) ph.get(Roboscratch.editor);
            Field rh = Editor.class.getDeclaredField("runHandler");
            rh.setAccessible(true);
            Runnable run_rh = (Runnable) rh.get(Roboscratch.editor);
            Roboscratch.editor.handleRun(false, run_ph, run_rh);
        }
        catch (NoSuchFieldException e)
        {
            System.out.println(e.toString());
        }
        catch (IllegalAccessException e)
        {
            System.out.println(e.toString());
        }
        catch (NoSuchMethodException e)
        {
            System.out.println(e.toString());
        }
        catch (InvocationTargetException e)
        {
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
