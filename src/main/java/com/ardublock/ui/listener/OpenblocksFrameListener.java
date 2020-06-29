package com.ardublock.ui.listener;

/**
 * Интерфейс прослушивателя оконной процедуры
 * @author AdmiralPaw, Ritevi, Aizek
 */
public interface OpenblocksFrameListener {

    /**
     * Метод, проверяющий произведено ли сохранение
     */
    void didSave();

    /**
     * Метод, проверяющий произведена ли загрузка
     */
    void didLoad();

    /**
     * Метод, проверяющий произведена ли генерация
     * @param source Исходный код
     */
    void didGenerate(String source);

    /**
     * Метод, проверяющий произведена ли проверкка
     * @param source Исходный код
     */
    void didVerify(String source);

    /**
     * Метод для получения информации о тексте
     */
    void getInfoText();
}
