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
 * Класс, ответственный за контекстуальные данные: настройки рабочего
 * пространства, язык, данные о среде (Напрмер версия ардуино) и т.д.
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class Context {

    /**Поле, которое определяет путь к файлу с настройками языка
    и начальной настройкой рабочей области*/
    public final static String LANG_DTD_PATH = "/com/ardublock/block/lang_def.dtd";

    /**Поле, которое определяет путь к файлу с описанием
    каждого блока, который генерирует код для Arduino*/
    public final static String ARDUBLOCK_LANG_PATH = "/com/ardublock/block/ardublock.xml";

    /**Поле со стандартным путём к программе Ardublock*/
    public final static String DEFAULT_ARDUBLOCK_PROGRAM_PATH = "/com/ardublock/default.abp";

    /**Поле со строкой версии Arduino*/
    public final static String ARDUINO_VERSION_UNKNOWN = "unknown";

    /**Поле с логической переменной, несущей в себе информацию об автоформатировании*/
    public final boolean isNeedAutoFormat = true;

    /**Поле с синглтоном контекста (Т.е. экземпляр такого класса создаётся только один раз)*/
    private static Context singletonContext;

    /**Поле с логической переменной, несущей в себе информацию о том,
    что рабочее пространство было изменено*/
    private boolean workspaceChanged;

    /**Поле с логической переменной, несущей в себе информацию о том,
    что рабочее пространство пусто*/
    private boolean workspaceEmpty;

    /**Поле подсветки (выделения) набора блоков*/
    private Set<RenderableBlock> highlightBlockSet;

    /**Поле с набором прослушивателей оконных процедур*/
    private Set<OpenblocksFrameListener> ofls;

    /**Поле с логической переменной, несущей в себе информацию есть ли данный объект в Arduino*/
    private boolean isInArduino = false;

    /**Поле с версией Arduino*/
    private String arduinoVersionString = ARDUINO_VERSION_UNKNOWN;

    /**Поле с информацией о текущей операционной системе*/
    private OsType osType;

    /**Поле со стандартным именем файла*/
    private String defaultFileName = "untitled";

    /**Поле с названием программы*/
    final public static String APP_NAME = "OmegaBot_IDE";

    /**Поле редактора*/
    private Editor editor;

    /**Структура возможных используемых операционных систем*/
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

    /**Поле, содержащее путь для сохранения файла*/
    private String saveFilePath;

    /**Поле, содержащее имя сохраняемого файла*/
    private String saveFileName;

    /**
     * Метод для получения контекста данных
     * @return singletonContext
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

    /**Поле с контроллера рабочего пространства*/
    private WorkspaceController workspaceController;

    /**Поле рабочего пространства*/
    private Workspace workspace;

    /**
     * Метод, который устанавливает контекстуальные данные (для рабочего пространства,
     * для контроллера рабочего пространства), выбирает какой набор блоков должен быть
     * подсвечен, выбирает тип ОС
     */
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
     * Метод для сброса рабочего пространства
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
     * Метод для определения стандартного имени фйла
     * @param name Имя
     */
    public void setDefaultFileName(String name)
    {
        defaultFileName = name;
        saveFileName = name;
    }

    /**
     * Метод для очистки рабочего пространства (Сброса значений к стандартным)
     */
    public void loadFreshWorkSpace() {
        //workspaceController.loadFreshWorkspace();
        loadDefaultArdublockProgram();

        saveFilePath = null;
        saveFileName = defaultFileName;
        workspaceEmpty = true;
    }

    /**
     * Метод для загрузки стандартной программы Ardublock
     */
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

    /**
     * Метод, определяющий используемую операционную систему
     * @return Context.OsType
     */
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
     * Метод для получения файла Arduino
     * @param name Имя
     * @return File(workingDir, name)
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
     * Метод для получения данных о контроллере рабочего пространства
     * @return workspaceController
     */
    public WorkspaceController getWorkspaceController() {
        return workspaceController;
    }

    /**
     * Метод для получения данных о рабочем пространстве
     * @return workspace
     */
    public Workspace getWorkspace() {
        return workspace;
    }

    /**
     * Метод, содержащий информацию было ли изменено рабочее пространство
     * @return workspaceChanged
     */
    public boolean isWorkspaceChanged() {
        return workspaceChanged;
    }

    /**
     * Метод, устанавливающий измененное рабочее пространство
     * @param workspaceChanged Логическая переменная
     * с информацией было ли изменено рабочее пространство
     */
    public void setWorkspaceChanged(boolean workspaceChanged) {
        this.workspaceChanged = workspaceChanged;
    }

    /**
     * Метод, подсвечивающий (выделяющий) блок
     * @param block Блок, который будет подсвечен
     */
    public void highlightBlock(RenderableBlock block) {
        block.updateInSearchResults(true);
        highlightBlockSet.add(block);
    }

    /**
     * Метод, прекращающий выделение блока
     * (ПОХОЖЕ, НЕ ИСПОЛЬЗУЕТСЯ НИГДЕ)
     * @param block Блок, который больше не будет подсвечен
     */
    public void cancelHighlightBlock(RenderableBlock block) {
        block.updateInSearchResults(false);
        highlightBlockSet.remove(block);
    }

    /**
     * Метод, сбрасывающий подсветку (выделение) блока
     */
    public void resetHightlightBlock() {
        for (RenderableBlock rb : highlightBlockSet) {
            rb.updateInSearchResults(false);
        }
        highlightBlockSet.clear();
    }

    /**
     * Метод, сохраняющий файл ArduBlock
     * @param saveFile Сохраняемый файл
     * @param saveString Сохраняемая строка
     */
     //@throws IOException - Исключение связанное с ошибками
     //во время выполнения операций потоков входа/выхода
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
     * Метод для загрузки файла ArduBlock
     * @param savedFile Сохранённый файл
     */
     //@throws IOException - Исключение связанное с ошибками
     //во время выполнения операций потоков входа/выхода
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
     * Метод, выбирающий редактор
     * @param e Редактор
     */
    public void setEditor(Editor e) {
        editor = e;
    }

    /**
     * Метод, загружающий редактор
     * @return editor
     */
    public Editor getEditor() {
        return editor;
    }

    /**
     * Метод, который даёт информацию, находится ли объект в Arduino
     * @return isInArduino
     */
    public boolean isInArduino() {
        return isInArduino;
    }

    /**
     * Метод, меняющий переменную о возможности нахождения в Arduino
     * @param isInArduino
     */
    public void setInArduino(boolean isInArduino) {
        this.isInArduino = isInArduino;
    }

    /**
     * Метод для получения информации о версии Arduino
     * @return arduinoVersionString
     */
    public String getArduinoVersionString() {
        return arduinoVersionString;
    }

    /**
     * Метод для изменения версии Arduino
     * @param arduinoVersionString - Версия Arduino
     */
    public void setArduinoVersionString(String arduinoVersionString) {
        this.arduinoVersionString = arduinoVersionString;
    }

    /**
     * Метод для получения типа используемой операционной системы
     * @return osType
     */
    public OsType getOsType() {
        return osType;
    }

    /**
     * Метод для создания прослушивателя оконной процедуры
     * @param ofl Прослушиватель оконной процедуры
     */
    public void registerOpenblocksFrameListener(OpenblocksFrameListener ofl) {
        ofls.add(ofl);
    }

    /**
     * Метод, показывающий была ли сохранена оконная процедура
     */
    public void didSave() {
        for (OpenblocksFrameListener ofl : ofls) {
            ofl.didSave();
        }
    }

    /**
     * Метод, показывающий была ли загружена оконная процедура
     */
    public void didLoad() {
        for (OpenblocksFrameListener ofl : ofls) {
            ofl.didLoad();
        }
    }

    /**
     * Метод, показывающий был ли сгенерирован код
     * @param sourcecode Исходный код
     */
    public void didGenerate(String sourcecode) {
        for (OpenblocksFrameListener ofl : ofls) {
            ofl.didGenerate(sourcecode);
        }
    }

    /**
     * Метод, показывающий были ли пройдена верификация
     * @param sourcecode Исходный код
     */
    public void didVerify(String sourcecode) {
        for (OpenblocksFrameListener ofl : ofls) {
            ofl.didVerify(sourcecode);
        }
    }

    /**НИГДЕ НЕ ИСПОЛЬЗУЕТСЯ (Кроме интерфейса OpenblocksFrameListener)*/
    public void getInfoText(){
        for (OpenblocksFrameListener ofl : ofls) {
            ofl.getInfoText();
        }
    }

    /**
     * Метод для загрузки имени сохраняемого файла
     * @return saveFileName
     */
    public String getSaveFileName() {
        return saveFileName;
    }

    /**
     * Метод для сохранения имени файла
     * @param saveFileName Имя сохраняемого файла
     */
    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    /**
     * Метод для получения пути сохраняемого файла
     * @return saveFilePath
     */
    public String getSaveFilePath() {
        return saveFilePath;
    }

    /**
     * Метод для установки пути сохраняемого файла
     * @param saveFilePath Путь сохраняемого файла
     */
    public void setSaveFilePath(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    /**
     * Метод, содержащий информацию является ли рабочее пространство пустым
     * @return workspaceEmpty
     */
    public boolean isWorkspaceEmpty() {
        return workspaceEmpty;
    }

    /**
     * Метод, делающий рабочее пространство пустым
     * @param workspaceEmpty - Логическая переменная, содержащая
     * информацию свободно ли рабочее пространство
     */
    public void setWorkspaceEmpty(boolean workspaceEmpty) {
        this.workspaceEmpty = workspaceEmpty;
    }
}
