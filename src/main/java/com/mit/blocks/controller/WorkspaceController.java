package com.mit.blocks.controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.EntityResolver;

import com.mit.blocks.codeblocks.ProcedureOutputManager;	//*****
import com.mit.blocks.codeblocks.BlockConnectorShape;
import com.mit.blocks.codeblocks.BlockGenus;
import com.mit.blocks.codeblocks.BlockLinkChecker;
import com.mit.blocks.codeblocks.CommandRule;
import com.mit.blocks.codeblocks.Constants;
import com.mit.blocks.codeblocks.SocketRule;
import com.mit.blocks.codeblocks.ParamRule;
import com.mit.blocks.codeblocks.PolyRule;
import com.mit.blocks.codeblocks.StackRule;
import com.mit.blocks.workspace.SearchBar;
import com.mit.blocks.workspace.SearchableContainer;
import com.mit.blocks.workspace.Workspace;

/**
 * Класс, который управляет взаимодействиями/настройками с blocks.workspace
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class WorkspaceController {

    /**Поле словаря, содержащего подходящие блоки*/
    public static Map<String,String[]> suitableBlocks;

    /**Поле языка по умолчанию*/
    public static Map<String,String> translateRightPanel;

    private Element langDefRoot;

    /**Поле логической переменной, содержащей информацию была
     * ли инициализирована панель рабочего пространства*/
    private boolean isWorkspacePanelInitialized = false;

    /**Поле панели рабочего пространства*/
    protected JPanel workspacePanel;

    /** Финальное (неизменяемое, без наследования) защищенное (Доступ protected дает подклассу
     * возможность использовать вспомогательный метод или переменную, предотвращая неродственный
     * класс от попыток использовать их). Поле рабочего пространства*/
    protected final Workspace workspace;

    /**Защищенное (Доступ protected дает подкласс возможность использовать вспомогательный
     * метод или переменную, предотвращая неродственный класс от попыток использовать их).
     * Поле поисковой строки*/
    protected SearchBar searchBar;

    /**
     * Метод для получения рабочего пространства
     * @return this.workspace
     */
    public Workspace getWorkspace() {
        return this.workspace;
    }

    /**Поле, указывающее, был ли установлен новый файл определения языка*/
    private boolean langDefDirty = true;

    /**Поле, которое обрабатывает случай загрузки DTD из файла jar*/
    private InputStream langDefDtd;

    /**Поле, указывающее, была ли рабочая область загружена / инициализирована*/
    private boolean workspaceLoaded = false;

    /**Поле, последнего каталога, выбранного с помощью действия открыть или сохранить*/
    private File lastDirectory;

    /**Поле, файла, загруженного в данный момент в рабочую область*/
    private File selectedFile;

    /**Ссылка сохранена, чтобы иметь возможность обновить
     * заголовок кадра с текущим загруженным файлом*/
    private JFrame frame;
    
    /**Поле пакета ресурсов I18N*/
    private ResourceBundle langResourceBundle;

	/**Поле со списком стилей*/
    private List<String[]> styleList;

    /**Поле процедур вывода отображений типов
     * (Взаимодействие/Синхронизация с выходными блочными сокетами)*/
    private static ProcedureOutputManager pom;	//*****
    

    /**
     * Метод, создающий экземпляр WorkspaceController,
     * который управляет взаимодействием с codeblocks.Workspace
     */
    public WorkspaceController() {
        this.workspace = new Workspace();
        pom = new ProcedureOutputManager(workspace);	//*****
    }
    
    /**
     * Метод, который определяет язык работы
     * @param is Поток входа
     */
    public void setLangDefDtd(InputStream is) {
    	langDefDtd = is;
    }
    
    /**
     * Метод, который устанавливает языковой пакет ресурсов
     * @param bundle Пакет реесурсов
     */
    public void setLangResourceBundle(ResourceBundle bundle) {
    	langResourceBundle = bundle;
    }
    
    /**
     * Метод, устанавливающий список стилей
     * @param list Список
     */
    public void setStyleList(List<String[]> list) {
    	styleList = list;
    }

    /**
     * Метод, который задает путь к файлу определения языка,
     * если файл определения языка присутствует (т.е. данный язык доступен)
     * @param filePath Путь к файлу
     */
     // @exception IOException e - Исключение связанное с ошибками во время
     // выполнения операций потоков входа/выхода
     // (Неправильно задан путь до файла с определенями языков)
     // @throws RuntimeException
     // @exception IOException e - Не происходит закрытие потока
     // @throws RuntimeException
    public void setLangDefFilePath(final String filePath) {
        InputStream in = null;
        try {
            in = new FileInputStream(filePath);
            setLangDefStream(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Метод, который задает файл определения языка из заданного входного потока
     * @param in Входной поток для чтения
     */
     // @exception ParserConfigurationException e - Указывает на серьезную ошибку конфигурации
     // @throws RuntimeException
     // @exception SAXException e - Encapsulate a general SAX error or warning
     // @throws RuntimeException
     // @exception IOException e - Исключение связанное с ошибками во время
     // выполнения операций потоков входа/выхода
     // @throws RuntimeException
    public void setLangDefStream(InputStream in) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder;
        final Document doc;
        try {
            builder = factory.newDocumentBuilder();
            if (langDefDtd != null) {
            	builder.setEntityResolver(new EntityResolver () {
            		public InputSource resolveEntity( String publicId, String systemId) throws SAXException, IOException {
            			return new InputSource(langDefDtd);
            		}
            	});
            }
            doc = builder.parse(in);
            // TODO modify the L10N text and style here
            ardublockLocalize(doc);
            ardublockStyling(doc);
            getAllSuitableBlocks(doc);
            getRightPanelTranslate(doc);
            langDefRoot = doc.getDocumentElement();
            langDefDirty = true;
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод, который показывает все подходящие блоки
     * @param doc Документ для парсинга
     */
     // @exception Exception e - При отсутствии необходимого ключа блока
    private void getAllSuitableBlocks(Document doc){

        Map<String,String[]> allsuitableBlocks = new HashMap<String,String[]>(){};
        Map<String,String[]> parsedSuitableBlocks = new HashMap<String,String[]>(){};

        ResourceBundle resourses = ResourceBundle.getBundle("com/ardublock/block/ardublock");

        NodeList elements = doc.getElementsByTagName("SuitableBlocks");
        Element el = (Element) elements.item(0);

        NodeList keyElements = el.getElementsByTagName("KeyBlock");
        //System.out.println("keys from xml ***************8");
        for(int i=0;i<keyElements.getLength();i++){
            String key = ((Element)keyElements.item(i)).getAttribute("key");

            try {
                key = resourses.getString(key);

            }catch (Exception e){
                key = "not found";
            }


            NodeList blocks = ((Element)keyElements.item(i)).getElementsByTagName("Block");
            String[] values = new String[blocks.getLength()];
            String[] parsedValues = new String[blocks.getLength()];
               
            for(int q=0;q<blocks.getLength();q++){
                values[q] = ((Element)blocks.item(q)).getAttribute("value");   
                parsedValues[q] = resourses.getString(values[q]);

            }
            parsedSuitableBlocks.put(key,parsedValues);
            allsuitableBlocks.put(key.toUpperCase(), values);
        }

        this.suitableBlocks=parsedSuitableBlocks;

        //разкомментить чтоб посмотреть что дает нам xml
//        for(String key:suitableBlocks.keySet()){
//            System.out.println(key);
//
//            String[] s = suitableBlocks.get(key);
//            for(String val:s){
//                System.out.println(val);
//            }
//
//        }


    }

    private void getRightPanelTranslate(Document doc){

        Map<String,String> translateRightPanel = new HashMap<String,String>(){};

        ResourceBundle resourses = ResourceBundle.getBundle("com/ardublock/block/ardublock");

        NodeList elements = doc.getElementsByTagName("SuitableBlocks");
        Element el = (Element) elements.item(0);

        NodeList keyElements = el.getElementsByTagName("KeyBlock");
        //System.out.println("keys from xml ***************8");
        for(int i=0;i<keyElements.getLength();i++){
            String key = ((Element)keyElements.item(i)).getAttribute("key");

            try {
                //key = resourses.getString(key);

            }catch (Exception e){
                key = "not found";
            }


            NodeList blocks = ((Element)keyElements.item(i)).getElementsByTagName("Block");
            String[] values = new String[blocks.getLength()];
            String[] parsedValues = new String[blocks.getLength()];
               
            for(int q=0;q<blocks.getLength();q++){
                values[q] = ((Element)blocks.item(q)).getAttribute("value");                
                parsedValues[q] = resourses.getString(values[q]);
                translateRightPanel.put(values[q].replace("bg.", ""),key.replace("modules.","").replace(".name", ""));
            }
            
        }

        this.translateRightPanel=translateRightPanel;
    }
    
    /**
     * Метод, который стилизирует BlockGenus и другие элементы с помощью цвета
     * @param doc Выбранынй документ
     */
     // @exception XPathExpressionException e - Ошибка в выражении XPath
	private void ardublockStyling(Document doc) {
		if (styleList != null) {
			XPathFactory factory = XPathFactory.newInstance();
			for (String[] style : styleList) {
				XPath xpath = factory.newXPath();
				try {
					// XPathExpression expr = xpath.compile("//BlockGenus[@name[starts-with(.,\"Tinker\")]]/@color");
					XPathExpression expr = xpath.compile(style[0]);
					NodeList bgs = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
					for (int i = 0; i < bgs.getLength(); i++) {
						Node bg = bgs.item(i);
						bg.setNodeValue(style[1]);
						// bg.setAttribute("color", "128 0 0");
					}
				} catch (XPathExpressionException e) {
					e.printStackTrace();
				}
			}
		}
	}

    /**
     * Процесс l10n для ArduBlock
     * @param doc Парсируемый документ
     */
     // @exception java.util.MissingResourceException mre - Сигнализирует о том,
     // что ресурс отсутствует
    private void ardublockLocalize(Document doc) {
        if (langResourceBundle != null) {
        	NodeList nodes = doc.getElementsByTagName("BlockGenus");
        	for (int i = 0 ; i < nodes.getLength(); i++) {
        		Element elm = (Element)nodes.item(i);
        		String name = elm.getAttribute("name");
				
        		// System.out.println("Translating BlockGenu:" + name);
				
        		String altName = langResourceBundle.getString("bg." + name);
        		if (altName != null) {
        			elm.setAttribute("initlabel", altName);
        		}
				NodeList descriptions = elm.getElementsByTagName("description");
				Element description = (Element)descriptions.item(0);
				if (description != null) {
					NodeList texts = description.getElementsByTagName("text");
					Element text = (Element)texts.item(0);
					if (text != null) {
						String pname = "bg." + name + ".description";
						try {
							altName = langResourceBundle.getString(pname);
							if (altName != null) {
								text.setTextContent(altName);
							}
						} catch (java.util.MissingResourceException mre) {
							System.err.println("ardublock.xml: missing " + pname);
						}
					}
				}
				NodeList arg_descs = elm.getElementsByTagName("arg-description");
				for (int j = 0 ; j < arg_descs.getLength(); j++) {
					Element arg_desc = (Element)arg_descs.item(j);
					String arg_name = arg_desc.getAttribute("name");
//					 System.out.println("bg." + name + ".arg_desc." + arg_name);
				}
			}
        	nodes = doc.getElementsByTagName("BlockDrawer");
        	for (int i = 0 ; i < nodes.getLength(); i++) {
        		Element elm = (Element)nodes.item(i);
        		String name = elm.getAttribute("name");
        		String altName = langResourceBundle.getString(name);
        		if (altName != null) {
        			elm.setAttribute("name", altName);
        		}
        	}
        	nodes = doc.getElementsByTagName("BlockConnector");
        	for (int i = 0 ; i < nodes.getLength(); i++) {
        		Element elm = (Element)nodes.item(i);
        		String name = elm.getAttribute("label");
        		if (name.startsWith("bc.")) {
					String altName = langResourceBundle.getString(name);
					if (altName != null) {
						elm.setAttribute("label", altName);
					}
				}
        	}
        }
    }

    /**
     * Загружает все типы блоков, свойства и правила ссылок языка,
     * заданные в предопределенном файле langDef.
     * @param root Загружает язык, указанный в корне элемента
     */
    public void loadBlockLanguage(final Element root) {
        /* MUST load shapes before genuses in order to initialize
         connectors within each block correctly */
        BlockConnectorShape.loadBlockConnectorShapes(root);

        //load genuses
        BlockGenus.loadBlockGenera(workspace, root);

        //load rules
        BlockLinkChecker.addRule(workspace, new CommandRule(workspace));
        BlockLinkChecker.addRule(workspace, new SocketRule());
        BlockLinkChecker.addRule(workspace, new PolyRule(workspace));
        BlockLinkChecker.addRule(workspace, new StackRule(workspace));
        BlockLinkChecker.addRule(workspace, new ParamRule());

        //set the dirty flag for the language definition file
        //to false now that the lang file has been loaded
        langDefDirty = false;
    }

    /**
     * Метод для броса текущего языка в активной рабочей области
     */
    public void resetLanguage() {
        BlockConnectorShape.resetConnectorShapeMappings();
        getWorkspace().getEnv().resetAllGenuses();
        BlockLinkChecker.reset();
    }

    /**
     * Метод, который возвращает сохраненную строку для всей рабочей области.
     * Включает в себя рабочую область блока, любые пользовательские фабрики,
     * состояние и положение представления холста, массив страниц
     * @return writer.toString()
     */
     // @exception TransformerConfigurationException e - Указывает на серьезную ошибку конфигурации
     // @throws RuntimeException
     // @exception TransformerException e - Ошибка, возникшая в процессе преобразования
     // @throws RuntimeException
    public String getSaveString() {
        try {
            Node node = getSaveNode();

            StringWriter writer = new StringWriter();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(node), new StreamResult(writer));
            return writer.toString();
        }
        catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
        catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает узел DOM для всей рабочей области. Включает в себя рабочую
     * область блока, любые пользовательские фабрики, состояние и положение
     * представления холста, страницы
     * @return getSaveNode(true).
     */
    public Node getSaveNode() {
        return getSaveNode(true);
    }

    /**
     * Возвращает узел DOM для всей рабочей области. Включает в себя рабочую
     * область блока, любые пользовательские фабрики, состояние и положение
     * представления холста, страницы
     * @param validate Если {@code true}, производит проверку выходных
     * данных по схеме блоков кода
     * @return document.
     */
     // @exception ParserConfigurationException e - Указывает на серьезную ошибку конфигурации
     // @throws RuntimeException
    public Node getSaveNode(final boolean validate) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element documentElement = document.createElementNS(Constants.XML_CODEBLOCKS_NS, "cb:CODEBLOCKS");
            // schema reference
            documentElement.setAttributeNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "xsi:schemaLocation", Constants.XML_CODEBLOCKS_NS+" "+Constants.XML_CODEBLOCKS_SCHEMA_URI);

            Node workspaceNode = workspace.getSaveNode(document);
            if (workspaceNode != null) {
                documentElement.appendChild(workspaceNode);
            }

            document.appendChild(documentElement);
            //if (validate) {
            //    validate(document);
            //}

            return document;
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод, который проверяет документ блоков кода на соответствие схеме
     * @param document Документ для проверки
     */
     // @exception MalformedURLException e - Брошен, чтобы указать, что
     // был указан неправильный URL-адрес. Либо никакой юридический протокол
     // не может быть найден в строке спецификации,
     // либо строка не может быть проанализирована.
     // @throws RuntimeException
     // @exception SAXException e - Encapsulate a general SAX error or warning
     // @throws RuntimeException
     // @exception IOException e - Исключение связанное с ошибками во время
     // выполнения операций потоков входа/выхода
     // @throws RuntimeException
    private void validate(Document document) {
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL schemaUrl = this.getClass().getResource("/edu/mit/blocks/codeblocks/codeblocks.xsd");
            Schema schema = schemaFactory.newSchema(schemaUrl);
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        catch (SAXException e) {
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод загружает новую рабочую область на основе спецификаций по умолчанию в
     * файле определения языка. Холст блока не будет иметь никаких активных блоков.
     */
    public void loadFreshWorkspace() {
        if (workspaceLoaded) {
            resetWorkspace();
        }
        if (langDefDirty) {
            loadBlockLanguage(langDefRoot);
        }
        workspace.loadWorkspaceFrom(null, langDefRoot);
        workspaceLoaded = true;
        
    }

    /**
     * Метод загружает проект программирования из указанного пути к файлу. Этот метод предполагает,
     * что файл определения языка уже задан для данного проекта программирования.
     * @param path - Путь к строковому файлу загружаемого проекта программирования
     * @throws java.io.IOException Ошибки потоков входа/выхода
     */
     // @exception ParserConfigurationException e - Указывает на серьезную ошибку конфигурации
     // @throws RuntimeException
     // @exception SAXException e - Encapsulate a general SAX error or warning
     // @throws RuntimeException
    public void loadProjectFromPath(final String path) throws IOException
    {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        final DocumentBuilder builder;
        final Document doc;
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new File(path));

            // XXX here, we could be strict and only allow valid documents...
            // validate(doc);
            final Element projectRoot = doc.getDocumentElement();
            //load the canvas (or pages and page blocks if any) blocks from the save file
            //also load drawers, or any custom drawers from file.  if no custom drawers
            //are present in root, then the default set of drawers is loaded from
            //langDefRoot
            workspace.loadWorkspaceFrom(projectRoot, langDefRoot);
            workspaceLoaded = true;
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод загружает программный проект из указанного элемента.
     * Этот метод предполагает, что файл определения языка уже задан для
     * данного проекта программирования.
     * @param elementToLoad Загружаемый элемент
     */
    public void loadProjectFromElement(Element elementToLoad) {
        workspace.loadWorkspaceFrom(elementToLoad, langDefRoot);
        workspaceLoaded = true;
    }

    /**
     * Загружает проект программирования, указанный в строке projectСontents,
     * которая связана с файлом определения языка, содержащимся в указанном
     * langDefContents. Все блоки, содержащиеся в projectСontents, должны
     * иметь ассоциированный род блоков, определенный в langDefContents.
     *
     * Если langDefContents имеет какие-либо параметры рабочей области,
     * такие как страницы или ящики, а projectСontents также имеет параметры
     * рабочей области, параметры рабочей области в projectContents переопределят
     * параметры рабочей области в langDefContents.
     *
     * Примечание: определение языка, содержащееся в langDefContents,
     * не заменяет файл определения языка по умолчанию, установленный
     * методами setLangDefFilePath () или setLangDefFile ().
     *
     * (ПОХОЖЕ, НИГДЕ НЕ ИСПОЛЬЗУЕТСЯ)
     *
     * @param projectContents Содержимое проекта
     * @param langDefContents Строка XML, определяющая язык содержимого проекта
     */
     // @exception ParserConfigurationException e - Указывает на серьезную ошибку конфигурации
     // @throws RuntimeException
     // @exception SAXException e - Encapsulate a general SAX error or warning
     // @throws RuntimeException
     // @exception IOException e - Исключение связанное с ошибками во время
     // выполнения операций потоков входа/выхода
     // @throws RuntimeException
    public void loadProject(String projectContents, String langDefContents) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder;
        final Document projectDoc;
        final Document langDoc;
        try {
            builder = factory.newDocumentBuilder();
            projectDoc = builder.parse(new InputSource(new StringReader(projectContents)));
            final Element projectRoot = projectDoc.getDocumentElement();
            langDoc = builder.parse(new InputSource(new StringReader(projectContents)));
            final Element langRoot = langDoc.getDocumentElement();
            if (workspaceLoaded) {
                resetWorkspace();
            }
            if (langDefContents == null) {
                loadBlockLanguage(langDefRoot);
            } else {
                loadBlockLanguage(langRoot);
            }
            workspace.loadWorkspaceFrom(projectRoot, langRoot);
            workspaceLoaded = true;

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод сбрасывает все рабочее пространство. Включает в себя все блоки, страницы,
     * ящики и разбитые блоки. Также сбрасывается стек отмены/повтора.
     * Язык (то есть genuses и shapes) не сбрасывается.
     */
    public void resetWorkspace() {
        //clear all pages and their drawers
        //clear all drawers and their content
        //clear all block and renderable block instances
        workspace.reset();
        //clear procedure output information
        ProcedureOutputManager.reset();	//*****

    }

    /**
     * Этот метод создает и раскладывает всю панель рабочего пространства с ее
     * различными компонентами. Рабочая область и языковые данные не
     * загружаются в эту функцию. Следует вызывать только один раз при запуске приложения.
     */
    private void initWorkspacePanel() {
        workspacePanel = new JPanel();
        workspacePanel.setLayout(new BorderLayout());
        workspacePanel.add(workspace, BorderLayout.CENTER);
        isWorkspacePanelInitialized = true;
    }

    /**
     * Метод возвращает JComponent всего рабочего пространства
     * @return workspacePanel
     */
    public JComponent getWorkspacePanel() {
        if (!isWorkspacePanelInitialized) {
            initWorkspacePanel();
        }
        return workspacePanel;
    }

    /**
     * Метод, связанное с "Открытием" процедуры.
     */
    private class OpenAction extends AbstractAction {

        private static final long serialVersionUID = -2119679269613495704L;

        OpenAction() {
            super("Open");
        }

        /**
         * Открытие файла
         * @param e Событие совершённого действия
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(lastDirectory);
            if (fileChooser.showOpenDialog((Component)e.getSource()) == JFileChooser.APPROVE_OPTION) {
                setSelectedFile(fileChooser.getSelectedFile());
                lastDirectory = selectedFile.getParentFile();
                String selectedPath = selectedFile.getPath();
                loadFreshWorkspace();
                try
                {
                	loadProjectFromPath(selectedPath);
                }
                catch (IOException ee)
                {
                	throw new RuntimeException(ee);
                }
            }
        }
    }

    /**
     * Action bound to "Save" button.
     */
    private class SaveAction extends AbstractAction {
        /**Данное поле - идентификатор класса в языке Java, используемый при сериализации
         с использованием стадартного алгоритма. Хранится как числовое значение типа long.*/
        private static final long serialVersionUID = -5540588250535739852L;
        SaveAction() {
            super("Save");
        }

        /**
         * Сохранение файла
         * @param evt Событие совершённого действия
         */
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (selectedFile == null) {
                JFileChooser fileChooser = new JFileChooser(lastDirectory);
                if (fileChooser.showSaveDialog((Component) evt.getSource()) == JFileChooser.APPROVE_OPTION) {
                    setSelectedFile(fileChooser.getSelectedFile());
                    lastDirectory = selectedFile.getParentFile();
                }
            }
            try {
                saveToFile(selectedFile);
            }
            catch (IOException e) {
                JOptionPane.showMessageDialog((Component) evt.getSource(),
                        e.getMessage());
            }
        }
    }

    /**
     * Действие, связанное с кнопкной "Сохранить как..."
     */
    private class SaveAsAction extends AbstractAction {
        /**Данное поле - идентификатор класса в языке Java, используемый при сериализации
         с использованием стадартного алгоритма. Хранится как числовое значение типа long.*/
         private static final long serialVersionUID = 3981294764824307472L;

        private final SaveAction saveAction;

        /**
         * Назначение действия "Сохранить как..."
         * @param saveAction Действие сохранения
         */
        SaveAsAction(SaveAction saveAction) {
            super("Save As...");
            this.saveAction = saveAction;
        }

        /**
         * Совершение действия сохранения
         * @param e Событие совершённого действия
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            selectedFile = null;
            // delegate to save action
            saveAction.actionPerformed(e);
        }
    }

    /**
     * Сохраняет содержимое рабочей области в заданный файл
     * @param file Файл назначения
     * @throws IOException При ошибках сохранения
     */
    private void saveToFile(File file) throws IOException {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(getSaveString());
        }
        finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
    }

    /**
     * Установка выбранного файла в качестве названия
     * @param selectedFile Выбранный файл
     */
    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
        frame.setTitle("WorkspaceDemo - "+selectedFile.getPath());
    }

    /**
     * Возвращает нижнюю панель кнопок
     * @return buttonPanel
     */
    private JComponent getButtonPanel() {
        JPanel buttonPanel = new JPanel();
        // Open
        OpenAction openAction = new OpenAction();
        buttonPanel.add(new JButton(openAction));
        // Save
        SaveAction saveAction = new SaveAction();
        buttonPanel.add(new JButton(saveAction));
        // Save as
        SaveAsAction saveAsAction = new SaveAsAction(saveAction);
        buttonPanel.add(new JButton(saveAsAction));
        return buttonPanel;
    }

    /**
     * Возвращает экземпляр строки поиска, способный выполнять поиск
     * блоков внутри BlockCanvas и ящиков блоков
     * @return sb.getComponent()
     */
    public JComponent getSearchBar() {
        final SearchBar sb = new SearchBar(
                "Search blocks", "Search for blocks in the drawers and workspace", workspace);
        for (SearchableContainer con : getAllSearchableContainers()) {
            sb.addSearchableContainer(con);
        }
        return sb.getComponent();
    }

    /**
     * Возвращает неподдающееся изменению итерируемые объекты из Searchablecontainer
     * @return workspace.getAllSearchableContainers()
     */
    public Iterable<SearchableContainer> getAllSearchableContainers() {
        return workspace.getAllSearchableContainers();
    }

    /**
     * Создаёт графический интерфейс и показывает его.
     * Для обеспечения потокобезопасности этот метод должен быть вызван
     * из потока диспетчеризации событий.
     */
    private void createAndShowGUI() {
        frame = new JFrame("WorkspaceDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 800, 600);
        final SearchBar sb = new SearchBar("Поиск блоков",
                "Search for blocks in the drawers and workspace", workspace);
        for (final SearchableContainer con : getAllSearchableContainers()) {
            sb.addSearchableContainer(con);
        }
        final JPanel topPane = new JPanel();
        sb.getComponent().setPreferredSize(new Dimension(130, 23));
        topPane.add(sb.getComponent());
        frame.add(topPane, BorderLayout.PAGE_START);
        frame.add(getWorkspacePanel(), BorderLayout.CENTER);
        frame.add(getButtonPanel(), BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /**
     * Главный метод класса
     * @param args Аргументы
     */
    public static void main(final String[] args) {
//        if (args.length < 1) {
//            System.err.println("usage: WorkspaceController lang_def.xml");
//            System.exit(1);
//        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            /**
             * Запуск
             */
            @Override public void run() {
                final WorkspaceController wc = new WorkspaceController();
                wc.setLangDefFilePath("D:\\Arduino\\ardublock-master\\openblocks-master\\support\\lang_def.xml");
                wc.loadFreshWorkspace();
                wc.createAndShowGUI();
            }
        });
    }

}
