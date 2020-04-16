package com.ardublock.ui.listener;

/**
 *
 * @author User
 */
public interface OpenblocksFrameListener {

    /**
     *
     */
    void didSave();

    /**
     *
     */
    void didLoad();

    /**
     *
     * @param source
     */
    void didGenerate(String source);

    /**
     *
     * @param source
     */
    void didVerify(String source);
}
