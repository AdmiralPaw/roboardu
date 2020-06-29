package com.mit.blocks.workspace;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс PageChangeEventmanager отвечает за обработку всех событий изменения страницы,
 * инициируемых Pages, и уведомляет слушателей изменения страницы, когда такое событие инициируется.
 * Cобытие изменение страницы генерируется путем вызова диспетчера через статический
 * метод PageChangeEventManager.notifyListeners(). Объект может подписаться на события изменения страницы,
 * делегируя себя через статический метод PageChangeEventManager.addPageChangeListener().
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class PageChangeEventManager {

    /** A NON-REPEATING set of page-change listeners */
    private static Set<PageChangeListener> observers = new HashSet<PageChangeListener>();

    /**
     * @param l - the listener to be added
     *
     * @requires l != null
     * @modifies the set of observers handled by this event manager
     * @effects subscribes a new listener to page changed events
     * @throws RuntimeException if l is null
     */
    public static void addPageChangeListener(PageChangeListener l) {
        if (l == null) {
            throw new RuntimeException("May not subsribe a null listener to PageChanged events");
        }
        observers.add(l);
    }

    /**
     * @requires none
     * @modifies all subscribing page change listeners
     * @effects notifies all observers of Page Changed events to update themselves
     */
    public static void notifyListeners() {
        for (PageChangeListener l : observers) {
            l.update();
        }
    }
}
