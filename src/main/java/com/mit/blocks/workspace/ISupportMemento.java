package com.mit.blocks.workspace;

/**
 * Странный класс* (Похоже, работает с состояниями программы)
 * @author AdmiralPaw, Ritevi, Aizek
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
