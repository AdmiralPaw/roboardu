package com.mit.blocks.renderable;

import com.mit.blocks.codeblocks.Block;
import com.mit.blocks.codeblocks.JComponentDragHandler;
import com.mit.blocks.workspace.Workspace;
import com.mit.blocks.workspace.WorkspaceWidget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * FactoryRenderableBlock extends RenderableBlock and is used within FactoryBlockDrawers.
 * Unlike its superclass RenderableBlock, FactoryRenderableBlock does not move or 
 * connect to any blocks.  Instead it has one function only, to produce new RenderableBlocks
 * and their associated Block instances.  It's block labels are also uneditable.
 * 
 * When a mouse is pressed over a FactoryRenderableBlock, a new RenderableBlock instance is 
 * created on top of it to receive further mouse events and a new Block instance is created
 * in the background.  
 */
public class FactoryRenderableBlock extends RenderableBlock implements Cloneable {

    private static final long serialVersionUID = 1L;
    //the RenderableBlock to produce
    private RenderableBlock createdRB = null;
    private boolean createdRB_dragged = false;
    //we have this instance of the dragHandler so that we can use it mouse entered
    //mouseexited methods to change the cursor appropriately, so that we can make it 
    //"seem" that this block is draggable
    private JComponentDragHandler dragHandler;
    
    private WorkspaceWidget wWidget;
    
    

    /**
     * Constructs a new FactoryRenderableBlock instance.
     * @param workspace The workspace in use
     * @param widget the parent widget of this
     * @param blockID the Long ID of its associated Block instance
     */
    public FactoryRenderableBlock(Workspace workspace, WorkspaceWidget widget, Long blockID) {
        super(workspace, widget, blockID);
        this.setBlockLabelUneditable();
        this.wWidget=widget;
        
        dragHandler = new JComponentDragHandler(workspace, this);

    }
    
    public FactoryRenderableBlock clone() throws CloneNotSupportedException{
        return (FactoryRenderableBlock) super.clone();
    }
    
    /**
     *
     * @return
     */
    public FactoryRenderableBlock deepClone(){
        Block newBlock = new Block(workspace, this.getGenus(), false);
        FactoryRenderableBlock block = new FactoryRenderableBlock(workspace, this.wWidget, newBlock.getBlockID());
        return block;
    }

    /**
     * Returns a new RenderableBlock instance (and creates its associated Block) instance of the same genus as this.
     * @return a new RenderableBlock instance with a new associated Block instance of the same genus as this.
     */
    public RenderableBlock createNewInstance() {
        return BlockUtilities.cloneBlock(workspace.getEnv().getBlock(super.getBlockID()));
    }

    ///////////////////
    //MOUSE EVENTS (Overriding mouse events in super)
    ///////////////////
    public void mousePressed(MouseEvent e) {
        this.requestFocus();
        //create new renderable block and associated block
        createdRB = createNewInstance();
        //add this new rb to parent component of this
        this.getParent().add(createdRB, 0);
        //set the parent widget of createdRB to parent widget of this
        //createdRB not really "added" to widget (not necessary to since it will be removed)
        createdRB.setParentWidget(this.getParentWidget());
        //set the location of new rb from this 
        createdRB.setLocation(this.getX(), this.getY());
        //send the event to the mousedragged() of new block
        MouseEvent newE = SwingUtilities.convertMouseEvent(this, e, createdRB);
        createdRB.mousePressed(newE);
        mouseDragged(e); // immediately make the RB appear under the mouse cursor
        workspace.getPageNamed("Main").saveScreen();
    }

    public void mouseDragged(MouseEvent e) {
        if (createdRB != null) {
            //translate this e to a MouseEvent for createdRB
            MouseEvent newE = SwingUtilities.convertMouseEvent(this, e, createdRB);
            createdRB.mouseDragged(newE);
            createdRB_dragged = true;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (createdRB != null) {
            if (!createdRB_dragged) {
                Container parent = createdRB.getParent();
                parent.remove(createdRB);
                parent.validate();
                parent.repaint();
            } else {
                //translate this e to a MouseEvent for createdRB
                MouseEvent newE = SwingUtilities.convertMouseEvent(this, e, createdRB);
                createdRB.mouseReleased(newE);
            }
            createdRB_dragged = false;
        }
    }

    public void mouseEntered(MouseEvent e) {
        dragHandler.mouseEntered(e);
    }

    public void mouseExited(MouseEvent e) {
        dragHandler.mouseExited(e);
    }

    public void mouseClicked(MouseEvent e) {
    }

    /**
     *
     * @param e
     */
    public void startDragging(MouseEvent e) {

    }

    /**
     *
     * @param e
     * @param w
     */
    public void stopDragging(MouseEvent e, WorkspaceWidget w) {
    }

    public void setZoomLevel(double newZoom){
    }

    /**
     *
     * @param newZoom
     */
    public void OneSetZoomLevel(double newZoom)
    {
        super.setZoomLevel(newZoom);
    }
    
}
