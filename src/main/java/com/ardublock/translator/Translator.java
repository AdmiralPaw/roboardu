package com.ardublock.translator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardublock.translator.adaptor.BlockAdaptor;
import com.ardublock.translator.adaptor.OpenBlocksAdaptor;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.TranslatorBlockFactory;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNameDuplicatedException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

import com.mit.blocks.codeblocks.Block;
import com.mit.blocks.renderable.RenderableBlock;
import com.mit.blocks.workspace.Workspace;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.Consumer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author User
 */
public class Translator {

    private static final String variablePrefix = "_";

    private Set<String> headerFileSet;
    private Set<String> headerDefinitionSet;
    private Set<String> definitionSet;
    private List<String> setupCommand;
    private List<String> guinoCommand;
    private Set<String> functionNameSet;
    private Set<TranslatorBlock> bodyTranslatreFinishCallbackSet;
    private BlockAdaptor blockAdaptor;

    private Set<String> inputPinSet;
    private Set<String> outputPinSet;

    private Map<String, String> numberVariableSet;
    private Map<String, String> booleanVariableSet;
    private Map<String, String> stringVariableSet;
    private Map<String, Object> internalData;

    private Workspace workspace;

    private String rootBlockName;

    private int variableCnt;
    private boolean isScoopProgram;
    private boolean isGuinoProgram;
    
    private String ValueMark = "VALUE_";//add after _ number of value

    /**
     *
     * @param ws
     */
    public Translator(Workspace ws) {
        workspace = ws;
        reset();
    }

    /**
     *
     * @return
     */
    public String genreateHeaderCommand() {
        StringBuilder headerCommand = new StringBuilder();

        if (!headerFileSet.isEmpty()) {
            for (String file : headerFileSet) {
                headerCommand.append("#include <" + file + ">\n");
            }
        }

        if (!headerDefinitionSet.isEmpty()) {
            for (String command : headerDefinitionSet) {
                headerCommand.append(command + "");
            }
        }

        if (!definitionSet.isEmpty()) {
            for (String command : definitionSet) {
                headerCommand.append(command + "\n");
            }
        }

        if (!functionNameSet.isEmpty()) {
            for (String functionName : functionNameSet) {
                headerCommand.append("void " + functionName + "();\n");
            }
        }

        return headerCommand.toString() + "\n" + generateSetupFunction()
                + generateGuinoFunction();
    }

    /**
     *
     * @return
     */
    public String generateSetupFunction() {
        StringBuilder setupFunction = new StringBuilder();
        setupFunction.append("void setup()\n{\n");

        if (!inputPinSet.isEmpty()) {
            for (String pinNumber : inputPinSet) {
                setupFunction.append("pinMode( " + pinNumber + " , INPUT);\n");
            }
        }
        if (!outputPinSet.isEmpty()) {
            for (String pinNumber : outputPinSet) {
                setupFunction.append("pinMode( " + pinNumber + " , OUTPUT);\n");
            }
        }

        if (!setupCommand.isEmpty()) {
            for (String command : setupCommand) {
                setupFunction.append(command + "\n");
            }

        }

        setupFunction.append("}\n\n");

        return setupFunction.toString();
    }

    /**
     *
     * @return
     */
    public String generateGuinoFunction() {
        StringBuilder guinoFunction = new StringBuilder();

        if (!guinoCommand.isEmpty()) {
            guinoFunction.append("void GUINO_DEFINIR_INTERFACE()\n{\n");
            for (String command : guinoCommand) {
                guinoFunction.append(command + "\n");
            }
            guinoFunction.append("}\n\n");
        }

        return guinoFunction.toString();
    }

    /**
     *
     * @param blockId
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     * @throws BlockException
     */
    public String translate(Long blockId) throws SocketNullException, SubroutineNotDeclaredException, BlockException {
        TranslatorBlockFactory translatorBlockFactory = new TranslatorBlockFactory();
        Block block = workspace.getEnv().getBlock(blockId);
        TranslatorBlock rootTranslatorBlock = translatorBlockFactory.buildTranslatorBlock(this, blockId, block.getGenusName(), "", "", block.getBlockLabel());
        return rootTranslatorBlock.toCode();
    }

    /**
     *
     * @return
     */
    public BlockAdaptor getBlockAdaptor() {
        return blockAdaptor;
    }

    /**
     *
     */
    public void reset() {
        headerFileSet = new LinkedHashSet<String>();
        headerDefinitionSet = new LinkedHashSet<String>();
        definitionSet = new LinkedHashSet<String>();
        setupCommand = new LinkedList<String>();
        guinoCommand = new LinkedList<String>();
        functionNameSet = new HashSet<String>();
        inputPinSet = new HashSet<String>();
        outputPinSet = new HashSet<String>();
        bodyTranslatreFinishCallbackSet = new HashSet<TranslatorBlock>();

        numberVariableSet = new HashMap<String, String>();
        booleanVariableSet = new HashMap<String, String>();
        stringVariableSet = new HashMap<String, String>();

        internalData = new HashMap<String, Object>();
        blockAdaptor = buildOpenBlocksAdaptor();

        variableCnt = 0;

        rootBlockName = null;
        isScoopProgram = false;
        isGuinoProgram = false;
    }

    private BlockAdaptor buildOpenBlocksAdaptor() {
        return new OpenBlocksAdaptor();
    }

    /**
     *
     * @param headerFile
     */
    public void addHeaderFile(String headerFile) {
        if (!headerFileSet.contains(headerFile)) {
            headerFileSet.add(headerFile);
        }
    }

    /**
     *
     * @param headerDefinition
     */
    public void addHeaderDefinition(String headerDefinition) {
        if (!headerDefinitionSet.contains(headerDefinition)) {
            headerDefinitionSet.add(headerDefinition);
        }
    }

    /**
     *
     * @param command
     */
    public void addSetupCommand(String command) {
        if (!setupCommand.contains(command)) {
            setupCommand.add(command);
        }
    }

    /**
     *
     * @param block
     */
    public void CheckClassName(TranslatorBlock block) {
        LoadTranslators(block.getClass().getSimpleName(),null);
    }

    /**
     *
     * @param command
     */
    public void addSetupCommandForced(String command) {
        setupCommand.add(command);
    }

    /**
     *
     * @param command
     */
    public void addGuinoCommand(String command) {

        guinoCommand.add(command);

    }

    /**
     *
     * @param command
     */
    public void addDefinitionCommand(String command) {
        definitionSet.add(command);
    }

    /**
     *
     * @param pinNumber
     */
    public void addInputPin(String pinNumber) {
        inputPinSet.add(pinNumber);
    }

    /**
     *
     * @param pinNumber
     */
    public void addOutputPin(String pinNumber) {
        outputPinSet.add(pinNumber);
    }

    /**
     *
     * @param userVarName
     * @return
     */
    public String getNumberVariable(String userVarName) {
        return numberVariableSet.get(userVarName);
    }

    /**
     *
     * @param userVarName
     * @return
     */
    public String getBooleanVariable(String userVarName) {
        return booleanVariableSet.get(userVarName);
    }

    /**
     *
     * @param userVarName
     * @param internalName
     */
    public void addNumberVariable(String userVarName, String internalName) {
        numberVariableSet.put(userVarName, internalName);
    }

    /**
     *
     * @param userVarName
     * @param internalName
     */
    public void addBooleanVariable(String userVarName, String internalName) {
        booleanVariableSet.put(userVarName, internalName);
    }

    /**
     *
     * @param blockId
     * @param functionName
     * @throws SubroutineNameDuplicatedException
     */
    public void addFunctionName(Long blockId, String functionName) throws SubroutineNameDuplicatedException {
        if (functionName.equals("loop") || functionName.equals("setup") || functionNameSet.contains(functionName)) {
            throw new SubroutineNameDuplicatedException(blockId);
        }

        functionNameSet.add(functionName);
    }

    /**
     *
     * @param name
     * @return
     */
    public boolean containFunctionName(String name) {
        return functionNameSet.contains(name.trim());
    }

    /**
     *
     * @return
     */
    public String buildVariableName() {
        return buildVariableName("");
    }

    /**
     *
     * @param reference
     * @return
     */
    public String buildVariableName(String reference) {
        variableCnt = variableCnt + 1;
        String varName = variablePrefix + variableCnt + "_";
        int i;
        for (i = 0; i < reference.length(); ++i) {
            char c = reference.charAt(i);
            if (Character.isLetter(c) || Character.isDigit(c) || (c == '_')) {
                varName = varName + c;
            }
        }
        return varName;
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
     * @param blockId
     * @return
     */
    public Block getBlock(Long blockId) {
        return workspace.getEnv().getBlock(blockId);
    }

    /**
     *
     * @param translatorBlock
     */
    public void registerBodyTranslateFinishCallback(TranslatorBlock translatorBlock) {
        bodyTranslatreFinishCallbackSet.add(translatorBlock);
    }

    /**
     *
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    public void beforeGenerateHeader() throws SocketNullException, SubroutineNotDeclaredException {
        for (TranslatorBlock translatorBlock : bodyTranslatreFinishCallbackSet) {
            translatorBlock.onTranslateBodyFinished();
        }
    }

    /**
     *
     * @return
     */
    public String getRootBlockName() {
        return rootBlockName;
    }

    /**
     *
     * @param rootBlockName
     */
    public void setRootBlockName(String rootBlockName) {
        this.rootBlockName = rootBlockName;
    }

    /**
     *
     * @return
     */
    public boolean isScoopProgram() {
        return isScoopProgram;
    }

    /**
     *
     * @param isScoopProgram
     */
    public void setScoopProgram(boolean isScoopProgram) {
        this.isScoopProgram = isScoopProgram;
    }

    /**
     *
     * @return
     */
    public boolean isGuinoProgram() {
        return isGuinoProgram;
    }

    /**
     *
     * @param isGuinoProgram
     */
    public void setGuinoProgram(boolean isGuinoProgram) {
        this.isGuinoProgram = isGuinoProgram;
    }

    /**
     *
     * @param loopBlocks
     * @param subroutineBlocks
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    public String translate(Set<RenderableBlock> loopBlocks, Set<RenderableBlock> subroutineBlocks) throws SocketNullException, SubroutineNotDeclaredException {
        StringBuilder code = new StringBuilder();

        for (RenderableBlock renderableBlock : loopBlocks) {
            Block loopBlock = renderableBlock.getBlock();
            code.append(translate(loopBlock.getBlockID()));
        }

        for (RenderableBlock renderableBlock : subroutineBlocks) {
            Block subroutineBlock = renderableBlock.getBlock();
            code.append(translate(subroutineBlock.getBlockID()));
        }
        beforeGenerateHeader();
        code.insert(0, genreateHeaderCommand());

        return code.toString();
    }

    /**
     *
     * @param name
     * @return
     */
    public Object getInternalData(String name) {
        return internalData.get(name);
    }

    /**
     *
     * @param name
     * @param value
     */
    public void addInternalData(String name, Object value) {
        internalData.put(name, value);
    }
    
    /**
     *
     * @param className
     */
    public void LoadTranslators(String className) {
        LoadTranslators(className,null);
    }
    
    /**
     *
     * @param className
     * @param valuesToChange
     */
    public void LoadTranslators(String className,HashMap<String,ArrayList<String>> valuesToChange) {
        try {

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(this.getClass().getResourceAsStream("/com/ardublock/block/translator.xml"));
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "/root/translator/block[@name=" + "'" + className + "'" + "]";
            Node block = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);

            Translate(xmlDocument,block,"headers/header",this::addHeaderFile,valuesToChange);
            Translate(xmlDocument,block,"headersDefinitions/headerDefinition",this::addHeaderDefinition,valuesToChange);  
            Translate(xmlDocument,block,"commands/command",this::addDefinitionCommand,valuesToChange);

        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    private void Translate(Document xmlDocument,Node foundBlock,String path,Consumer<String> func,HashMap<String,ArrayList<String>> valuesToChange)throws XPathExpressionException {
            LinkedHashMap<String,String> CommandNameAndCode = new LinkedHashMap<String,String>();
            CommandNameAndCode = ParseXml(xmlDocument,foundBlock,path);
            AddValuesToCode(CommandNameAndCode,valuesToChange);
            RefactorCode(CommandNameAndCode);
            AddToTranslator(CommandNameAndCode,func);
    }
    
    
    private LinkedHashMap<String,String> ParseXml(Document xmlDocument,Node foundBlock,String path)throws XPathExpressionException {
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList headers = (NodeList) xPath.compile(path).evaluate(foundBlock, XPathConstants.NODESET);
            String headersCodeExpression = "/root/translatorCode/"+path;
            Node SomeType = null;
            Node headersCode;
            LinkedHashMap<String,String> CommandNameAndCode = new LinkedHashMap<String,String>();
            String nameOfHeader = "";
            for (int i = 0; i < headers.getLength(); i++) {
                SomeType = headers.item(i);
                nameOfHeader = SomeType.getAttributes().getNamedItem("name").getNodeValue();
                if(nameOfHeader!=null){
                    headersCode = (Node) xPath.compile(headersCodeExpression + "[@name=" + "'" + nameOfHeader + "'" + "]").evaluate(xmlDocument, XPathConstants.NODE);
                    CommandNameAndCode.put(nameOfHeader, headersCode.getTextContent());
                }              
            }
            return CommandNameAndCode;
    };
    
    private void AddToTranslator(LinkedHashMap<String,String> collection, Consumer<String> func){
        if(!collection.isEmpty()){
            collection.entrySet().forEach((item) -> {
                func.accept(item.getValue());
            });
        }
    };
    private void AddValuesToCode(LinkedHashMap<String,String> collection, HashMap<String,ArrayList<String>> values){
        if(values!=null&&!collection.isEmpty()){
        
        String lineForChange;
        int countOfValues;
        for(Map.Entry<String, String> item : collection.entrySet()){
            if(values.containsKey(item.getKey())){
                lineForChange = item.getValue();
                countOfValues = Character.getNumericValue(lineForChange.charAt(lineForChange.lastIndexOf(ValueMark)+ValueMark.length()))+1;
                if(values.get(item.getKey()).size()==countOfValues){
                   for(int i=0;i<countOfValues;i++){
                       lineForChange = lineForChange.replace(ValueMark+i, values.get(item.getKey()).get(i));                      
                }
                item.setValue(lineForChange); 
                }                
            }
        }
        }
    };
    
    private void RefactorCode(LinkedHashMap<String,String> collection){
        for(Map.Entry<String, String> item : collection.entrySet()){
            item.setValue(item.getValue().replaceAll("^\n|\n$", ""));
        }
    }
}
