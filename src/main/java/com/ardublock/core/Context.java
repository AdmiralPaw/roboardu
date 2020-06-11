package com.ardublock.core;

import com.ardublock.ui.listener.OpenblocksFrameListener;
import com.mit.blocks.codeblocks.Block;
import com.mit.blocks.controller.WorkspaceController;
import com.mit.blocks.renderable.FactoryRenderableBlock;
import com.mit.blocks.renderable.RenderableBlock;
import com.mit.blocks.workspace.FactoryManager;
import com.mit.blocks.workspace.Page;
import com.mit.blocks.workspace.Workspace;
import processing.app.Editor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author User
 */
public class Context {

    /**
     *
     */
    public final static String LANG_DTD_PATH = "/com/ardublock/block/lang_def.dtd";

    /**
     *
     */
    public final static String ARDUBLOCK_LANG_PATH = "/com/ardublock/block/ardublock.xml";

    /**
     *
     */
    public final static String DEFAULT_ARDUBLOCK_PROGRAM_PATH = "/com/ardublock/default.abp";

    /**
     *
     */
    public final static String ARDUINO_VERSION_UNKNOWN = "unknown";

    /**
     *
     */
    public final boolean isNeedAutoFormat = true;

    private static Context singletonContext;

    private boolean workspaceChanged;
    private boolean workspaceEmpty;

    private Set<RenderableBlock> highlightBlockSet;
    private Set<OpenblocksFrameListener> ofls;
    private boolean isInArduino = false;
    private String arduinoVersionString = ARDUINO_VERSION_UNKNOWN;
    private OsType osType;
    private String defaultFileName = "untitled";

    /**
     *
     */
    final public static String APP_NAME = "OmegaBot_IDE";

    private Editor editor;

    /**
     *
     */
    public enum OsType {

        /**
         *
         */
        LINUX,

        /**
         *
         */
        MAC,

        /**
         *
         */
        WINDOWS,

        /**
         *
         */
        UNKNOWN,
    };

    private String saveFilePath;
    private String saveFileName;

    /**
     *
     * @return
     */
    public static Context getContext() {
        if (singletonContext == null) {
            synchronized (Context.class) {
                if (singletonContext == null) {
                    singletonContext = new Context();
                }
            }
        }
        return singletonContext;
    }

    private WorkspaceController workspaceController;
    private Workspace workspace;

    private Context() {
        workspaceController = new WorkspaceController();
        resetWorksapce();
        workspace = workspaceController.getWorkspace();
        workspaceChanged = false;
        highlightBlockSet = new HashSet<RenderableBlock>();
        ofls = new HashSet<OpenblocksFrameListener>();
        this.workspace = workspaceController.getWorkspace();

        isInArduino = false;

        osType = determineOsType();
    }

    /**
     *
     */
    public void resetWorksapce() {

        List<String[]> list = new ArrayList<String[]>();
        String[][] styles = {};

        for (String[] style : styles) {
            list.add(style);
        }
        workspaceController.resetWorkspace();
        workspaceController.resetLanguage();
        workspaceController.setLangResourceBundle(ResourceBundle.getBundle("com/ardublock/block/ardublock"));
        workspaceController.setStyleList(list);
        workspaceController.setLangDefDtd(this.getClass().getResourceAsStream(LANG_DTD_PATH));
        workspaceController.setLangDefStream(this.getClass().getResourceAsStream(ARDUBLOCK_LANG_PATH));
        workspaceController.loadFreshWorkspace();

        loadDefaultArdublockProgram();

        saveFilePath = null;
        saveFileName = defaultFileName;
        workspaceEmpty = true;
    }

    /**
     *
     * @param name
     */
    public void setDefaultFileName(String name)
    {
        defaultFileName = name;
        saveFileName = name;
    }

    /**
     *
     */
    public void loadFreshWorkSpace() {
        //workspaceController.loadFreshWorkspace();
        loadDefaultArdublockProgram();

        saveFilePath = null;
        saveFileName = defaultFileName;
        workspaceEmpty = true;
    }

    private void loadDefaultArdublockProgram() {

        Workspace workspace = workspaceController.getWorkspace();
        Page page = workspace.getPageNamed("Main");

        workspace.setWorkspaceZoomToDefault();

        page.newKeeper();

        FactoryManager manager = workspace.getFactoryManager();
        Block newBlock;
        newBlock = new Block(workspace, "loop", false);
        FactoryRenderableBlock factoryRenderableBlock = new FactoryRenderableBlock(workspace, manager, newBlock.getBlockID());
        RenderableBlock renderableBlock = factoryRenderableBlock.createNewInstance();
        renderableBlock.setLocation(100, 100);
        page.addBlock(renderableBlock);
    }

    //determine OS
    private OsType determineOsType() {
        String osName = System.getProperty("os.name");
        osName = osName.toLowerCase();

        if (osName.contains("win")) {
            return Context.OsType.WINDOWS;
        }
        if (osName.contains("linux")) {
            return Context.OsType.LINUX;
        }
        if (osName.contains("mac")) {
            return Context.OsType.MAC;
        }
        return Context.OsType.UNKNOWN;
    }

    /**
     *
     * @param name
     * @return
     */
    public File getArduinoFile(String name) {
        String path = System.getProperty("user.dir");
        if (osType.equals(OsType.MAC)) {
            String javaroot = System.getProperty("javaroot");
            if (javaroot != null) {
                path = javaroot;
            }
        }
        File workingDir = new File(path);
        return new File(workingDir, name);
    }

    /**
     *
     * @return
     */
    public WorkspaceController getWorkspaceController() {
        return workspaceController;
    }

    /**
     *
     * @return
     */
    public Workspace getWorkspace() {
        return workspace;
    }

    /**
     *
     * @return
     */
    public boolean isWorkspaceChanged() {
        return workspaceChanged;
    }

    /**
     *
     * @param workspaceChanged
     */
    public void setWorkspaceChanged(boolean workspaceChanged) {
        this.workspaceChanged = workspaceChanged;
    }

    /**
     *
     * @param block
     */
    public void highlightBlock(RenderableBlock block) {
        block.updateInSearchResults(true);
        highlightBlockSet.add(block);
    }

    /**
     *
     * @param block
     */
    public void cancelHighlightBlock(RenderableBlock block) {
        block.updateInSearchResults(false);
        highlightBlockSet.remove(block);
    }

    /**
     *
     */
    public void resetHightlightBlock() {
        for (RenderableBlock rb : highlightBlockSet) {
            rb.updateInSearchResults(false);
        }
        highlightBlockSet.clear();
    }

    /**
     *
     * @param saveFile
     * @param saveString
     * @throws IOException
     */
    public void saveArduBlockFile(File saveFile, String saveString) throws IOException {
        if (!saveFile.exists()) {
            saveFile.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(saveFile, false);
        fos.write(saveString.getBytes("UTF8"));
        fos.flush();
        fos.close();
        didSave();
    }

    /**
     *
     * @param savedFile
     * @throws IOException
     */
    public void loadArduBlockFile(File savedFile) throws IOException {
        if (savedFile != null) {
            saveFilePath = savedFile.getAbsolutePath();
            saveFileName = savedFile.getName();
            //workspaceController.resetWorkspace();
            workspaceController.loadProjectFromPath(saveFilePath);
            didLoad();
        }
    }

    /**
     *
     * @param e
     */
    public void setEditor(Editor e) {
        editor = e;
    }

    /**
     *
     * @return
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     *
     * @return
     */
    public boolean isInArduino() {
        return isInArduino;
    }

    /**
     *
     * @param isInArduino
     */
    public void setInArduino(boolean isInArduino) {
        this.isInArduino = isInArduino;
    }

    /**
     *
     * @return
     */
    public String getArduinoVersionString() {
        return arduinoVersionString;
    }

    /**
     *
     * @param arduinoVersionString
     */
    public void setArduinoVersionString(String arduinoVersionString) {
        this.arduinoVersionString = arduinoVersionString;
    }

    /**
     *
     * @return
     */
    public OsType getOsType() {
        return osType;
    }

    /**
     *
     * @param ofl
     */
    public void registerOpenblocksFrameListener(OpenblocksFrameListener ofl) {
        ofls.add(ofl);
    }

    /**
     *
     */
    public void didSave() {
        for (OpenblocksFrameListener ofl : ofls) {
            ofl.didSave();
        }
    }

    /**
     *
     */
    public void didLoad() {
        for (OpenblocksFrameListener ofl : ofls) {
            ofl.didLoad();
        }
    }

    /**
     *
     * @param sourcecode
     */
    public void didGenerate(String sourcecode) {
        for (OpenblocksFrameListener ofl : ofls) {
            ofl.didGenerate(sourcecode);
        }
    }

    /**
     *
     * @param sourcecode
     */
    public void didVerify(String sourcecode) {
        for (OpenblocksFrameListener ofl : ofls) {
            ofl.didVerify(sourcecode);
        }
    }

    public void getInfoText(){
        for (OpenblocksFrameListener ofl : ofls) {
            ofl.getInfoText();
        }
    }

    /**
     *
     * @return
     */
    public String getSaveFileName() {
        return saveFileName;
    }

    /**
     *
     * @param saveFileName
     */
    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    /**
     *
     * @return
     */
    public String getSaveFilePath() {
        return saveFilePath;
    }

    /**
     *
     * @param saveFilePath
     */
    public void setSaveFilePath(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    /**
     *
     * @return
     */
    public boolean isWorkspaceEmpty() {
        return workspaceEmpty;
    }

    /**
     *
     * @param workspaceEmpty
     */
    public void setWorkspaceEmpty(boolean workspaceEmpty) {
        this.workspaceEmpty = workspaceEmpty;
    }
}
