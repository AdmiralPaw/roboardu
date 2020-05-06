package com.mit.blocks.codeblockutil;

/**
 *
 * @author User
 */
public interface ExplorerEvent {

    /**
     *
     * @return
     */
    public int getEventType();

    /**
     *
     * @return
     */
    public Explorer getSource();
}
