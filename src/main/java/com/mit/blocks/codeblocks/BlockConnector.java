package com.mit.blocks.codeblocks;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.mit.blocks.workspace.ISupportMemento;
import com.mit.blocks.workspace.Workspace;

/**
 * Класс, который описывает информацию о выходе/входе для каждого выхода
 * или входа конкретного блока. Каждый сокет имеет свой вид (например, число, строку,
 * логическое значение и т. д.), метка и блочный идентификатор блока в этом сокете
 * (не путать с блоком, который содержит информацию о сокете - сокет не имеет ссылки на этот родительский блок).
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class BlockConnector implements ISupportMemento {
    
    //TODO need some sort of indication if there is a block in this socket, i.e. -1 value or boolean flag
    
    //Свойства коннектора

    /**Поле типа*/
    private String kind;

    /**Поле инициализации типа*/
    private String initKind;

    /**Поле типа позиции*/
    private PositionType positionType;

    /**Поле лейбла*/
    private String label;

    /**Поле идентификатора коннектора блока*/
    private Long connBlockID = Block.NULL;

    /**Поле стандартных аргументов*/
    private DefArgument arg = null;

    /**Поле логической перемнной с информацией о наличии стандартных аргументов*/
    private boolean hasDefArg = false;

    /**Поле логической перемнной с информацией о возможности расширения*/
    private boolean isExpandable = false;

    /**Поле логической перемнной с информацией о возможности редактирования лейбла*/
    private boolean isLabelEditable = false;

    /**Поле группы расширения*/
    private String expandGroup = "";

    /**Поле рабочего пространства*/
    private final Workspace workspace;

    /**
     * Данная структура указывает тип позиции соединителя:
     */
    public enum PositionType { 

        /**
         * Single - это соединитель по умолчанию, который отображается
         * только на одной стороне блока (слева/справа).
         */
        SINGLE,

        /**
         * Mirror создает соединители с местоположениями,
         * отраженными как на левой, так и на правой стороне блока.
         */
        MIRROR, 

        /**
         * Bottom - это двустороннее ограждение в нижней
         * части блока или следующая команда.
         */
        BOTTOM, 

        /**
         * Top - это предыдущая команда.
         */
        TOP };
    
    /**
     * Создает новый соединитель блоков
     * @param workspace Рабочая область, в которой создается этот соединитель
     * @param kind Тип этого сокета
     * @param positionType Тип положения соединителя
     * @param label Строковая метка этого сокета
	 * @param isLabelEditable True, если этот BlockConnector может редактировать свои метки
     * @param isExpandable Может ли этот разъем расширяться в другой разъем при подключении блока
     * @param expandGroup Группа расширения сокета этого коннектора
     * @param connBlockID Идентификатор подключенного блока
     */
    public BlockConnector(Workspace workspace, String kind, PositionType positionType, String label, boolean isLabelEditable, boolean isExpandable, String expandGroup, Long connBlockID) {
        this(workspace, kind, positionType, label, isLabelEditable, isExpandable, connBlockID);
        this.expandGroup = expandGroup == null ? "" : expandGroup;
    }

    /**
     * Создает новый соединитель блоков
     * @param workspace Рабочая область, в которой создается этот соединитель
     * @param kind Тип этого сокета
     * @param positionType Тип положения соединителя
     * @param label Строковая метка этого сокета
     * @param isLabelEditable True, если этот BlockConnector может редактировать свои метки
     * @param isExpandable Может ли этот разъем расширяться в другой разъем при подключении блока
     * @param connBlockID Идентификатор подключенного блока
     */
    public BlockConnector(Workspace workspace, String kind, PositionType positionType, String label, boolean isLabelEditable, boolean isExpandable, Long connBlockID) {
        this.workspace = workspace;
        this.kind = kind;
        this.positionType = positionType;
        this.label = label;
        this.isLabelEditable = isLabelEditable;
        this.connBlockID = connBlockID;
        this.isExpandable = isExpandable;
        this.initKind = kind;
    }
    
    /**
     * Создает новый соединитель блоков с соединителем по умолчанию (Single Position)
     * @param workspace Рабочая область, в которой создается этот соединитель
     * @param label Строковая метка этого сокета
     * @param kind Тип этого сокета
     * @param socketBlockID Идентификатор блока подключенного к сокету
     */
    public BlockConnector(Workspace workspace, String kind, String label, Long socketBlockID) {
        this(workspace, kind, PositionType.SINGLE, label, false, false, socketBlockID);
    }
    
    /**
     * Создает новый блочный соединитель с заданной меткой и типом.
     * Новый сокет не имеет подключенного блока.
     * @param workspace Рабочая область, в которой создается этот соединитель
     * @param label Строковая метка этого сокета
	 * @param isLabelEditable True, если этот BlockConnector может редактировать свои метки
     * @param kind Тип этого сокета
     */
    public BlockConnector(Workspace workspace, String label, String kind, boolean isLabelEditable, boolean isExpandable) {
        this(workspace, kind, PositionType.SINGLE, label, isLabelEditable, isExpandable, Block.NULL);
    }
    
    /**
     * Создает новый BlockConnector, копируя информацию о соединителе из
     * указанного con. Копирует метку и вид соединителя con.
     * @param con BlockConnector для копирования из него
     */
    public BlockConnector(BlockConnector con) {
        this(con.workspace, con.kind, con.positionType, con.label, con.isLabelEditable, con.isExpandable, con.connBlockID);
        this.hasDefArg = con.hasDefArg;
        this.arg = con.arg;
        this.isLabelEditable = con.isLabelEditable;
        this.expandGroup = con.expandGroup;
    }
    
    /**
     * Метод возвращает лейбл
     * @return label
     */
    public String getLabel() {
        return label;
    }
    
    /**
     * Метод возвращает тип
     * @return kind
     */
    public String getKind() {
        return kind;
    }
    
    /**
     * Метод возвращает начальный тип
     * @return initKind
     */
    public String initKind() {
        return initKind;
    }
    
    /**
     * Метод возвращает тип позиции
     * @return positionType
     */
    public PositionType getPositionType() {
        return positionType;
    }

    /**
     * Метод возвращает идентификатор блока, подключенного к этому сокету
     * @return connBlockID
     */
    public Long getBlockID() {
        return connBlockID;
    }

    /**
     * Метод возвращает true, если блок присоединен к этому сокету; false в противном случае
     * @return !connBlockID.equals(Block.NULL)
     */
    public boolean hasBlock() {
        return !connBlockID.equals(Block.NULL);
    }

    /**
     * Возвращает true, если этот соединитель расширяется,
     * то есть если к нему подключен блок, то это может привести к появлению
     * другого пустого соединителя, такого же, как этот. Появится ли блок на
     * самом деле, зависит от родительского блока этого соединителя. Технически
     * только розетки могут расширяться. Каждый блок может иметь только один
     * штекер (то есть возвращать одно значение).
     * @return isExpandable
     */
    public boolean isExpandable() {
        return isExpandable;
    }

    /**
     * Возвращает развернутую группу этого соединителя или пустую строку (""),
     * если соединитель не является частью группы
     * @return expandGroup
     */
    public String getExpandGroup() {
        return expandGroup;
    }

    /**
     * Задает лейбл сокета для указанного лейбла
     * @param label Нужная метка
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Возвращает true, если метка этого сокета доступна для редактирования
     * @return isLabelEditable
     */
    public boolean isLabelEditable() {
        return isLabelEditable;
    }

    /**
     * Задает тип сокета на указанный тип
     * @param kind Нужный тип
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * Устанавливает блок сокета, прикрепленный к этому разъему
     * @param id  Идентификатор блока нужного для подключения
     */
    public void setConnectorBlockID(Long id) {
        connBlockID = id;
    }

    /**
     * Задает тип положения этого соединителя
     * @param pos Нужный тип положения
     */
    public void setPositionType(PositionType pos) {
        this.positionType = pos;
    }

    /**
     * Возвращает true, если этот соединитель имеет аргумент по умолчанию;
     * в противном случае false
     * @return hasDefArg
     */
    public boolean hasDefArg() {
        return hasDefArg;
    }

    /**
     * Устанавливает аргумент по умолчанию этого соединителя
     * в указанный genus и начальный лейбл.
     * @param genusName Нужное имя BlockGenus аргумента по умолчанию
     * @param label Начальный лейбл аргумента по умолчанию
     */
    public void setDefaultArgument(String genusName, String label) {
        hasDefArg = true;
        arg = new DefArgument(genusName, label);
    }

    /**
     * Соединяет этот соединитель с его аргументом по умолчанию, если он
     * имеет таковой, и возвращает идентификатор блока подключенного
     * аргумента по умолчанию или Block.NULL, если его нет.
     * @return connBlockID
     */
    public Long linkDefArgument() {
        //checks if connector has a def arg or if connector already has a block
        if (hasDefArg && connBlockID == Block.NULL) {
            Block block = new Block(workspace, arg.getGenusName(), arg.label);
            connBlockID = block.getBlockID();
            return connBlockID;
        }
        return Block.NULL;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Connector label: ");
        out.append(label);
        out.append(", Connector kind: ");
        out.append(kind);
        out.append(", blockID: ");
        out.append(connBlockID);
        out.append(" with pos type: ");
        out.append(getPositionType());
        return out.toString();
    }

    /**
     * DefArgument-это облегченный класс, который хранит информацию об аргументе
     * по умолчанию этого соединителя, если он имеет таковую, в частности имя рода
     * аргумента и его начальный лейбл. Каждый соединитель имеет не более 1
     * аргумента по умолчанию.
     * @author AdmiralPaw, Ritevi, Aizek
     */
    private class DefArgument {

        /**Поле имени рода*/
        private String genusName;

        /**Поле лейбла*/
        private String label;

        /**
         * Метод присвоения стандартных аргументов
         * @param genusName - Нужное имя рода
         * @param label - Нужна метка
         */
        public DefArgument(String genusName, String label) {
            this.genusName = genusName;
            this.label = label;
        }

        /**
         * Метод для получения имени рода
         * @return genusName
         */
        public String getGenusName() {
            return genusName;
        }

        /**
         * Метод для получения нужного лейбла
         * @return label
         */
        public String getLabel() {
            return label;
        }
    }
    
    ////////////////////////
    // SAVING AND LOADING //
    ////////////////////////
    /**
     * Загружает информацию для одного BlockConnector и возвращает экземпляр
     * BlockConnector с загруженной информацией
     * @param workspace Используемое рабочее пространство
     * @param node Узел, содержащий нужную информацию
     * @param idMapping - Сопоставление идентификаторов
     * @return con
     */
    public static BlockConnector loadBlockConnector(Workspace workspace, Node node, HashMap<Long, Long> idMapping) {
        Pattern attrExtractor = Pattern.compile("\"(.*)\"");
        Matcher nameMatcher;

        BlockConnector con = null;

        String initKind = null;
        String kind = null;
        Long idConnected = Block.NULL;
        String label = "";
        boolean isExpandable = false;
        boolean isLabelEditable = false;
        String expandGroup = "";
        String positionType = "single";

        if (node.getNodeName().equals("BlockConnector")) {
            //load attributes
            nameMatcher = attrExtractor.matcher(node.getAttributes().getNamedItem("init-type").toString());
            if (nameMatcher.find()) {
                initKind = nameMatcher.group(1);
            }
            nameMatcher = attrExtractor.matcher(node.getAttributes().getNamedItem("connector-type").toString());
            if (nameMatcher.find()) {
                kind = nameMatcher.group(1);
            }
            nameMatcher = attrExtractor.matcher(node.getAttributes().getNamedItem("label").toString());
            if (nameMatcher.find()) {
                label = nameMatcher.group(1);
            }
            //load optional items
            Node opt_item = node.getAttributes().getNamedItem("con-block-id");
            if (opt_item != null) {
                nameMatcher = attrExtractor.matcher(opt_item.toString());
                if (nameMatcher.find()) {
                    idConnected = Block.translateLong(workspace, Long.parseLong(nameMatcher.group(1)), idMapping);
                }
            }
            opt_item = node.getAttributes().getNamedItem("label-editable");
            if (opt_item != null) {
                nameMatcher = attrExtractor.matcher(opt_item.toString());
                if (nameMatcher.find()) {
                    isLabelEditable = nameMatcher.group(1).equals("true");
                }
            }
            opt_item = node.getAttributes().getNamedItem("is-expandable");
            if (opt_item != null) {
                nameMatcher = attrExtractor.matcher(opt_item.toString());
                if (nameMatcher.find()) {
                    isExpandable = nameMatcher.group(1).equals("yes") ? true : false;
                }
            }
            opt_item = node.getAttributes().getNamedItem("expand-group");
            if (opt_item != null) {
                nameMatcher = attrExtractor.matcher(opt_item.toString());
                if (nameMatcher.find()) {
                    expandGroup = nameMatcher.group(1);
                }
            }
            opt_item = node.getAttributes().getNamedItem("position-type");
            if (opt_item != null) {
                nameMatcher = attrExtractor.matcher(opt_item.toString());
                if (nameMatcher.find()) {
                    positionType = nameMatcher.group(1);
                }
            }

            assert initKind != null : "BlockConnector was not specified a initial connection kind";

            if (positionType.equals("single")) {
                con = new BlockConnector(workspace, initKind, PositionType.SINGLE, label, isLabelEditable, isExpandable, idConnected);
            } else if (positionType.equals("bottom")) {
                con = new BlockConnector(workspace, initKind, PositionType.BOTTOM, label, isLabelEditable, isExpandable, idConnected);
            } else if (positionType.equals("mirror")) {
                con = new BlockConnector(workspace, initKind, PositionType.MIRROR, label, isLabelEditable, isExpandable, idConnected);
            } else if (positionType.endsWith("top")) {
                con = new BlockConnector(workspace, initKind, PositionType.TOP, label, isLabelEditable, isExpandable, idConnected);
            }

            con.expandGroup = expandGroup;
            if (!initKind.equals(kind)) {
                con.setKind(kind);
            }
        }

        assert con != null : "BlockConnector was not loaded " + node;

        return con;
    }
    
    /**
     * Возвращает узел this.Node, содержащий только ту информацию,
     * которая была изменена и модифицирована
     * @param document
     * @param conKind Строка, содержащая, тип: сокет или разъем
     * @return connectorElement
     */
    public Node getSaveNode(Document document, String conKind) {
        Element connectorElement = document.createElement("BlockConnector");
        connectorElement.setAttribute("connector-kind", conKind);
        connectorElement.setAttribute("connector-type", kind);
        connectorElement.setAttribute("init-type", initKind);
        connectorElement.setAttribute("label", label);
        if (expandGroup.length() > 0) {
            connectorElement.setAttribute("expand-group", expandGroup);
        }
        if (isExpandable) {
            connectorElement.setAttribute("is-expandable", "yes");
        }
        if (this.positionType.equals(PositionType.SINGLE)) {
            connectorElement.setAttribute("position-type", "single");
        } else if (this.positionType.equals(PositionType.MIRROR)) {
            connectorElement.setAttribute("position-type", "mirror");
        } else if (this.positionType.equals(PositionType.BOTTOM)) {
            connectorElement.setAttribute("position-type", "bottom");
        } else if (this.positionType.equals(PositionType.TOP)) {
            connectorElement.setAttribute("position-type", "top");
        }

        if (this.isLabelEditable) {
            connectorElement.setAttribute("label-editable", "true");
        }

        if (!this.connBlockID.equals(Block.NULL)) {
            connectorElement.setAttribute("con-block-id", Long.toString(connBlockID));
        }

        return connectorElement;
    }
    
    /***********************************
    * State Saving Stuff for Undo/Redo *
    ***********************************/

    /**
     * Класс, информирующий о состоянии BlockConnector
     * @author AdmiralPaw, Ritevi, Aizek
     */
    private class BlockConnectorState {

        /**Поле типа*/
        public String kind;

        /**Поле инициализации типа*/
        public String initKind;

        /**Поле типа позиции*/
        public PositionType positionType;

        /**Поле лейбла*/
        public String label;

        /**Поле идентификатора коннектора блока*/
        public Long connBlockID = Block.NULL;

        //DefaultArg Stuff

        /**Поле логической перемнной с информацией о наличии стандартных аргументов*/
        public boolean hasDefArg;

        /**Поле стандартных аргументов имени рода*/
        public String defArgGenusName;

        /**Поле стандартных аргументов лейбла*/
        public String defArgLabel;

        /**Поле логической перемнной с информацией о возможности расширения*/
        public boolean isExpandable;

        /**Поле группы расширения*/
        public String expandGroup;

        /**Поле логической перемнной с информацией о возможности редактирования лейбла*/
        public boolean isLabelEditable;
    }

    /**
     * Метод для получения состояния
     * @return state
     */
    @Override
    public Object getState() {
        BlockConnectorState state = new BlockConnectorState();

        state.kind = this.getKind();
        state.initKind = this.initKind;
        state.positionType = this.getPositionType();
        state.label = this.getLabel();
        state.connBlockID = this.getBlockID();
        //Default Args stuff
        if (this.hasDefArg()) {
            state.defArgGenusName = this.arg.getGenusName();
            state.defArgLabel = this.arg.getLabel();
            state.hasDefArg = true;
        } else {
            state.defArgGenusName = null;
            state.defArgLabel = null;
            state.hasDefArg = false;
        }
        state.isExpandable = this.isExpandable();
        state.isLabelEditable = this.isLabelEditable;
        state.expandGroup = this.expandGroup;

        return state;
    }

    /**
     * Состояние загрузки
     * @param memento
     */
    @Override
    public void loadState(Object memento) {
        if (memento instanceof BlockConnectorState) {
            BlockConnectorState state = (BlockConnectorState) memento;

            this.setKind(state.kind);
            this.setPositionType(state.positionType);
            this.setLabel(state.label);
            this.setConnectorBlockID(state.connBlockID);

            if (state.hasDefArg) {
                this.setDefaultArgument(state.defArgGenusName, state.defArgLabel);
            } else {
                this.arg = null;
            }

            this.isExpandable = state.isExpandable;
            this.isLabelEditable = state.isLabelEditable;
            this.expandGroup = state.expandGroup;
        }
    }

    /**
     * Это способ создания BlockConnector из memento. Это немного странно,
     * так как другие объекты не имеют этого метода, однако здесь это имеет лучший смысл,
     * поскольку BlockConnector по сути является структурой.
     * @param workspace Используемое рабочее пространство
     * @param memento Состояние агрузки
     * @return instance
     */
    public static BlockConnector instantiateFromState(Workspace workspace, Object memento) {
        if (memento instanceof BlockConnectorState) {
            BlockConnectorState state = (BlockConnectorState) memento;

            BlockConnector instance = new BlockConnector(workspace, state.kind, state.positionType, state.label, state.isLabelEditable, state.isExpandable, state.connBlockID);
            instance.isLabelEditable = state.isLabelEditable;

            if (state.hasDefArg) {
                instance.setDefaultArgument(state.defArgGenusName, state.defArgLabel);
            }
            return instance;
        }
        return null;
    }
}
