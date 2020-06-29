package com.ardublock.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Класс, генерирующий оконную процедуру, графику, кнопки, функции и т.д.
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class PropertiesReader
{

    /**Поле словаря считывателя свойств*/
    public static Map<String, PropertiesReader> propertiesReaderMap = new SafeHashMap<String, PropertiesReader>();

    /**Поле стандартного файла свойств*/
    public static final String DEFAULT_PROPERTIES_FILE = "heqichen.properties";

    /**Поле имени файла*/
	private String filename;

	/**Поле свойств*/
	private Properties p;

	/**
	 * Метод, считывающий свойства указанного файла
	 * @param filename Имя файла
	 */
	 //@exception IOException e - Исключение вызываемое при невозможности открыть указанный файл
	private PropertiesReader(String filename)
	{
		this.filename = filename; 
		InputStream is = PropertiesReader.class.getClassLoader().getResourceAsStream(this.filename);
		if (is == null)
		{
			System.out.println("no file found: " + this.filename);
		}
		p = new Properties();
		try
		{
			p.load(is);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    /**
     * Метод для получения значения свойств
     * @param key Ключ, по которому будут выданы свойства
     * @return getValue(key, DEFAULT_PROPERTIES_FILE)
     */
    public static String getValue(String key)
	{
		return getValue(key, DEFAULT_PROPERTIES_FILE);
	}
	
    /**
     * Метод для получения значения свойств
     * @param key Ключ, по которому будут выданы свойства
     * @param file Файл для считывания свойств
     * @return propertiesReader.readValue(key)
     */
    public static String getValue(String key, String file)
	{
		PropertiesReader propertiesReader = PropertiesReader.getPropertiesReader(file);
		return propertiesReader.readValue(key);
	}

	/**
	 * Метод для считывания значения
	 * @param key Ключ, по которому будут считаны свойства
	 * @return p.getProperty(key)
	 */
	private String readValue(String key)
	{
		return p.getProperty(key);
	}

	/**
	 * Метод для получения свойств считывателя
	 * @param filename Файл для считывания свойств
	 * @return propertiesReader
	 */
	private static PropertiesReader getPropertiesReader(String filename)
	{
		PropertiesReader propertiesReader;
		propertiesReader = propertiesReaderMap.get(filename);
		if (propertiesReader == null)
		{
			synchronized (PropertiesReader.class)
			{
				propertiesReader = propertiesReaderMap.get(filename);
				if (propertiesReader == null)
				{
					propertiesReader = new PropertiesReader(filename);
					propertiesReaderMap.put(filename, propertiesReader);
				}
			}
		}
		return propertiesReader;
	}
}
