package com.ardublock.util;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Класс, работающий с защищённой хэш-картой, с её настройками, созданием и использованием
 * @author AdmiralPaw, Ritevi, Aizek
 * @param <K> Ключ (Key)
 * @param <V> Значение (Value)
 */
public class SafeHashMap<K, V>  extends HashMap<K,V> implements Map<K,V>, Cloneable, Serializable
{
	/**Данное поле - идентификатор класса в языке Java, используемый при сериализации
    с использованием стадартного алгоритма. Хранится как числовое значение типа long.*/
	private static final long serialVersionUID = -731362984994602016L;

	/**
	 * Защищённая хэш-карта
	 */
	public SafeHashMap()
	{
		super();
	}

	/**
	 * Метод для получения размера
	 * @return super.size()
	 */
	@Override
	public synchronized int size()
	{
		return super.size();
	}

	/**
	 * Метод, определяющий заполненость
	 * @return super.isEmpty()
	 */
	@Override
	public synchronized boolean isEmpty()
	{
		return super.isEmpty();
	}

	/**
	 * Метод для получения ключа
	 * @param key Ключ словаря
	 * @return super.get(key)
	 */
	@Override
	public synchronized V get(Object key)
	{
		return super.get(key);
	}

	/**
	 * Метод, дающий информацию о содержании ключа в словаре
	 * @param key Искомый ключ
	 * @return super.containsKey(key)
	 */
	@Override
	public synchronized boolean containsKey(Object key) 
	{
		return super.containsKey(key);
	}

	/**
	 * Метод для вставки в словарь новой пары ключа-значения
	 * @param key Ключ
	 * @param value Значение
	 * @return super.put(key, value)
	 */
	@Override
	public synchronized V put(K key, V value)
	{
		return super.put(key, value);
	}

	/**
	 * Метод для вставки всех пар разом в словарь
	 * @param m Словарь пар
	 */
	@Override
	public synchronized void putAll(Map<? extends K, ? extends V> m)
	{
		super.putAll(m);
	}

	/**
	 * Метод для удаления определённого ключа из словаря
	 * @param key Удаляемый ключ
	 * @return super.remove(key)
	 */
	@Override
	public synchronized V remove(Object key)
	{
		return super.remove(key);
	}

	/**
	 * Метод для очистки
	 */
	@Override
	public synchronized void clear()
	{
		super.clear();
	}

	/**
	 * Метод, дающий информацию о содержании в словаре определённого значения
	 * @param value Искомое значение
	 * @return super.containsValue(value)
	 */
	@Override
	public synchronized boolean containsValue(Object value)
	{
		return super.containsValue(value);
	}

	/**
	 * Метод для установки (формирования) определённого набора ключей
	 * @return super.keySet()
	 */
	@Override
	public synchronized Set<K> keySet()
	{
		return super.keySet();
	}

	/**
	 * Метод для определения определённой коллекции значений
	 * @return super.values()
	 */
	@Override
	public synchronized Collection<V> values()
	{
		return super.values();
	}

	/**
	 * Метод устанавливающий набор записей словаря
	 * @return super.entrySet()
	 */
	@Override
	public synchronized Set<java.util.Map.Entry<K, V>> entrySet()
	{
		return super.entrySet();
	}

}
