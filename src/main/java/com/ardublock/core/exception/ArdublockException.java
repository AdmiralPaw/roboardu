package com.ardublock.core.exception;

/**
 * Класс для отлавливания ошибок связанных с генерацией	кода Arduino из блоков
 * RuntimeException-это суперкласс тех исключений, которые могут быть брошены во
 * время нормальной работы виртуальной машины Java
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class ArdublockException extends Exception //RuntimeException
{

	/**Данное поле - идентификатор класса в языке Java, используемый при сериализации
    с использованием стадартного алгоритма. Хранится как числовое значение типа long.*/
	private static final long serialVersionUID = -1006562884406802985L;

}
