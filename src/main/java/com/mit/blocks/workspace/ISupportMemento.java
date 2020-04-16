package com.mit.blocks.workspace;

/**
 *
 * @author User
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
