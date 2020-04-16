package com.mit.blocks.codeblockutil;

/**
 *
 * @author User
 */
public class GlassExplorerEvent implements ExplorerEvent {

    /**
     *
     */
    public final static int SLIDING_CONTAINER_FINISHED_OPEN = 1;

    /**
     *
     */
    public final static int SLIDING_CONTAINER_FINISHED_CLOSED = 2;
    private int eventType;
    private GlassExplorer ge = null;

    /**
     *
     * @param ge
     * @param event
     */
    public GlassExplorerEvent(GlassExplorer ge, int event) {
        this.ge = ge;
        this.eventType = event;
    }

    /**
     *
     * @return
     */
    public int getEventType() {
        return this.eventType;
    }

    /**
     *
     * @return
     */
    public Explorer getSource() {
        return this.ge;
    }
}
