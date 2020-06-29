package com.ardublock;

import com.ardublock.core.Context;
import com.ardublock.ui.ArduBlockToolFrame;
import com.ardublock.ui.Settings;
import com.ardublock.ui.TutorialPane;
import com.ardublock.ui.listener.OpenblocksFrameListener;
import processing.app.Editor;
import processing.app.EditorConsole;
import processing.app.EditorStatus;
import processing.app.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 * @author User
 */
public class OmegaBot_IDE implements Tool, OpenblocksFrameListener {

    static Editor editor;
    static ArduBlockToolFrame openblocksFrame;
    private Preferences userPrefs;
    private Context context;
    private boolean kostil = false;
    public Timer autohideTimer;
    private boolean firstAutohide;
    private boolean autostart;

    /**
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
            OmegaBot_IDE.openblocksFrame = new ArduBlockToolFrame();
            OmegaBot_IDE.openblocksFrame.addListener(this);
            Context context = Context.getContext();
            String arduinoVersion = this.getArduinoVersion();
            context.setInArduino(true);
            context.setArduinoVersionString(arduinoVersion);
            context.setEditor(editor);
            this.context = context;
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

            this.firstAutohide = userPrefs.getBoolean("ardublock.ui.autohide", false);
            this.autostart = userPrefs.getBoolean("ardublock.ui.autostart", false);
            if (autostart) {
                OmegaBot_IDE.openblocksFrame.setVisible(true);
//                if (userPrefs.getBoolean("ardublock.ui.autohide", true)) {
//                    autohideTimer = new Timer(300, new ActionListener() {
//                        /**
//                         *
//                         */
//                        public void actionPerformed(ActionEvent e) {
//                            //TODO: код ниже позволяет скрывать окно Arduino IDE, необходимо автоматически закрывать его, если
//                            //  включен параметр @autostart@
//                            if (context.getEditor() != null) {
//                                context.getEditor().setVisible(false);
//                            }
//                        }
//                    });
//                    autohideTimer.start();
//                }
            }
            Timer timer = new Timer(300, (ActionListener) e -> getInfoText());
            timer.start();
        }
    }

    //TODO сделать как-то почеловечески
    private void hideArduinoEditor(boolean b) {
        if (context.getEditor() != null && (context.getEditor().isVisible() ^ !b)) {
            context.getEditor().setVisible(!b);
        }
    }

    public void run() {
        try {
            if (OmegaBot_IDE.openblocksFrame.settings.isProductKeyValid()) {
                OmegaBot_IDE.openblocksFrame.setVisible(true);
                OmegaBot_IDE.openblocksFrame.toFront();
                OmegaBot_IDE.openblocksFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            } else {
                if (OmegaBot_IDE.openblocksFrame.settings.productKeyValidator(OmegaBot_IDE.openblocksFrame))
                    run();
            }
        } catch (Exception ignored) {
        }
    }


    /**
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

            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                return Context.ARDUINO_VERSION_UNKNOWN;
            } catch (IOException e) {
                e.printStackTrace();
                return Context.ARDUINO_VERSION_UNKNOWN;
            }
        } else {
            return Context.ARDUINO_VERSION_UNKNOWN;
        }

    }

    public void getInfoText() {
        if (this.context.getWorkspace().getErrWindow().mode == 0) {
            try {
                Field f1 = Editor.class.getDeclaredField("console");
                f1.setAccessible(true);
                EditorConsole console = (EditorConsole) f1.get(OmegaBot_IDE.editor);
                Field f2 = Editor.class.getDeclaredField("status");
                f2.setAccessible(true);
                EditorStatus status = (EditorStatus) f2.get(OmegaBot_IDE.editor);
                Field f3 = EditorStatus.class.getDeclaredField("message");
                f3.setAccessible(true);
                String message = (String) f3.get(status);
                Field f4 = EditorStatus.class.getDeclaredField("mode");
                f4.setAccessible(true);
                int mode = (int) f4.get(status);
                Field f5 = EditorStatus.class.getDeclaredField("BGCOLOR");
                f5.setAccessible(true);
                Color[] BGCOLOR = (Color[]) f5.get(status);
                this.context.getWorkspace().getErrWindow().setErr(message, console.getText(), BGCOLOR[mode]);
            } catch (NoSuchFieldException | IllegalAccessException | NullPointerException e) {
                //System.out.println(e.toString());
            }
        }
        if (openblocksFrame.hideArduinoToogle && firstAutohide && autostart) {
            hideArduinoEditor(true);
        }
    }
}
