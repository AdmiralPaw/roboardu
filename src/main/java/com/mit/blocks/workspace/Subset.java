package com.mit.blocks.workspace;

import java.awt.Color;

import com.mit.blocks.renderable.RenderableBlock;

/**
 * An Immuateble class identifying a subset's properties and blocks
 * @author An Ho
 */
public class Subset {

    private String name;
    private Color color;
    private Iterable<RenderableBlock> blocks;

    /**
     *
     * @param name
     * @param color
     * @param blocks
     */
    public Subset(String name, Color color, Iterable<RenderableBlock> blocks) {
        this.name = name;
        this.color = color;
        this.blocks = blocks;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @return
     */
    public Iterable<RenderableBlock> getBlocks() {
        return blocks;
    }
}
