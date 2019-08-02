package com.mit.blocks.workspace;

import com.mit.blocks.renderable.RenderableBlock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class BlockKeeper {
    private ArrayList<Collection<RenderableBlock>> undoList;
    private ArrayList<Collection<RenderableBlock>> redoList;
    private boolean isEditedAfterUndo;

    public BlockKeeper() {
        undoList = new ArrayList<Collection<RenderableBlock>>();
        redoList = new ArrayList<Collection<RenderableBlock>>();
    }

    public Collection<RenderableBlock> undoAct() {
        if (undoList.size() > 0) {
            Collection<RenderableBlock> screen = undoList.get(undoList.size() - 1);
            undoList.remove(undoList.size() - 1);
            System.out.println(undoList.size());
            redoList.add(screen);
            return screen;
        }


        return null;

    }

    public Collection<RenderableBlock> redoAct() {
        if (redoList.size() > 0) {
            Collection<RenderableBlock> screen = redoList.get(redoList.size() - 1);
            redoList.remove(redoList.size() - 1);
            System.out.println(redoList.size());
            undoList.add(screen);
            return screen;
        }

        return null;
    }

    public void addAct(Collection<RenderableBlock> blocks) {
        HashMap<Long, Long> alreadyAdd = new HashMap<Long, Long>();
        Collection<RenderableBlock> screen = new ArrayList<RenderableBlock>();
        for (RenderableBlock block : blocks) {
            long blockID = block.getBlockID();
            if (!alreadyAdd.containsKey(blockID)) {
                ArrayList<Long> idList = block.getIDList();
                for (Long id : idList) {
                    alreadyAdd.put(id, 1l);
                }
                screen.add(block.cloneThisNoParent(block));

            }
        }
        if (screen.size() > 0) {
            undoList.add(screen);
            redoList.clear();
        }
        System.out.println(undoList.size());

    }

}
