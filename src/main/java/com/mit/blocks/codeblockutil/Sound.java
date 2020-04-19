package com.mit.blocks.codeblockutil;

import javax.sound.sampled.Clip;

/**
 *
 * @author User
 */
public class Sound {

    private Clip clip;

    /**
     *
     * @param clip
     */
    public Sound(Clip clip) {
        this.clip = clip;
    }

    /**
     *
     */
    public void play() {
        if (SoundManager.isSoundEnabled()) {
            clip.setFramePosition(0);
            clip.loop(0);
        }
    }
}
