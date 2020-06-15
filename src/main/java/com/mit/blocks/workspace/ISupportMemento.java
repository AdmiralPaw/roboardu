package com.mit.blocks.workspace;

/**
 * @author AdmiralPaw, Ritevi, Aizek
 * Странный класс* (Похоже, работает с состояниями программы)
 */
public interface ISupportMemento {

    /**
     *
     * @return
     */
    public Object getState();

    /**
     *
     * @param memento
     */
    public void loadState(Object memento);
}
