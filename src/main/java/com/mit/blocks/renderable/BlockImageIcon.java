package com.mit.blocks.renderable;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Класс BlockImageIcon содержит информацию о значках изображений, нарисованных
 * в пределах RenderableBlock (Визуализируемого блока. Эта информация включает
 * в себя следующее:
 * - Экземпляр ImageIcon для рисования
 * - Расположение изображения, представляющее точное расположение ImageIcon на блоке
 * визуализации. (На каждое место изображения приходится только один значок
 * блочного изображения. Это усиливается внутри карты изображений блоков в
 * экземпляре рода блоков.)
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class BlockImageIcon extends JLabel {

    private static final long serialVersionUID = 328149080423L;

    /**
     * ImageLocation specifies the relative location of this BlockImageIcon within the visible
     * Block instance of this BlockGenus.  
     * 
     * Note: there is only one BlockImageIcon per ImageLocation within a Block
     */
    public enum ImageLocation {

        /**
         *
         */
        CENTER,

        /**
         *
         */
        EAST,

        /**
         *
         */
        WEST,

        /**
         *
         */
        NORTH,

        /**
         *
         */
        SOUTH,

        /**
         *
         */
        SOUTHEAST,

        /**
         *
         */
        SOUTHWEST,

        /**
         *
         */
        NORTHEAST,

        /**
         *
         */
        NORTHWEST;

        /**
         *
         * @param s
         * @return
         */
        public static ImageLocation getImageLocation(String s) {
            for (ImageLocation loc : values()) {
                if (loc.toString().equalsIgnoreCase(s)) {
                    return loc;
                }
            }

            return null;
        }
    }
    private ImageIcon blockImageIcon;
    private ImageLocation location;
    private boolean isEditable;
    private boolean wrapText;

    /**
     * Constructs a new BlockImageIcon from the specified parameters.  
     * @param blockImageIcon ImageIcon to draw on a block
     * @param location ImageLocation of this ImageIcon
     * @param isEditable if true, this instance may be replaced at the specified ImageLocation
     * @param wrapText if true, the block labels of the RendearbleBlock will not overlap the ImageIcon drawn on it at 
     * the specified location
     */
    public BlockImageIcon(ImageIcon blockImageIcon, ImageLocation location, boolean isEditable, boolean wrapText) {
        super(blockImageIcon);
        setPreferredSize(new Dimension(blockImageIcon.getIconWidth(), blockImageIcon.getIconHeight()));
        setSize(new Dimension(blockImageIcon.getIconWidth(), blockImageIcon.getIconHeight()));
        setMinimumSize(new Dimension(blockImageIcon.getIconWidth(), blockImageIcon.getIconHeight()));
        this.blockImageIcon = blockImageIcon;
        this.location = location;

        this.isEditable = isEditable;
        this.wrapText = wrapText;
    }

    /**
     *
     * @return
     */
    public ImageIcon getImageIcon() {
        return blockImageIcon;
    }

    /**
     *
     * @param icon
     */
    public void setImageIcon(ImageIcon icon) {
        blockImageIcon = icon;
        super.setIcon(icon);
    }

    /**
     *
     * @return
     */
    public ImageLocation getImageLocation() {
        return location;
    }

    /**
     *
     * @return
     */
    public boolean isEditable() {
        return isEditable;
    }

    /**
     *
     * @return
     */
    public boolean wrapText() {
        return wrapText;
    }
}
