package com.mit.blocks.workspace;

import com.mit.blocks.renderable.RenderableBlock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class BlocksKeeper {
    private ArrayList<Collection<RenderableBlock>> undoList;
    private ArrayList<Collection<RenderableBlock>> redoList;
    private static int maxSize;
    private Page parent;

    public BlocksKeeper(Page p) {
        undoList = new ArrayList<Collection<RenderableBlock>>();
        redoList = new ArrayList<Collection<RenderableBlock>>();
        parent = p;
    }

    public Collection<RenderableBlock> undoAct() {
        if (undoList.size() > 0) {
            Collection<RenderableBlock> screen = undoList.get(undoList.size() - 1);
            undoList.remove(undoList.size() - 1);
            redoList.add(getScreen(parent.getBlocks()));
            System.out.println(String.valueOf(undoList.size()) + ":" + String.valueOf(redoList.size()));
            return screen;
        }


        return null;

    }

    public Collection<RenderableBlock> redoAct() {
        if (redoList.size() > 0) {
            Collection<RenderableBlock> screen = redoList.get(redoList.size() - 1);
            redoList.remove(redoList.size() - 1);
            undoList.add(getScreen(parent.getBlocks()));
            System.out.println(String.valueOf(undoList.size()) + ":" + String.valueOf(redoList.size()));
            return screen;
        }

        return null;
    }

    public void addAct(Collection<RenderableBlock> blocks) {
        undoList.add(getScreen(blocks));
        redoList.clear();
        normalizeListSize();
        System.out.println(undoList.size());
    }

    public Collection<RenderableBlock> getScreen(Collection<RenderableBlock> blocks) {
        HashMap<Long, Long> alreadyAdd = new HashMap<Long, Long>();
        Collection<RenderableBlock> screen = new ArrayList<RenderableBlock>();
        for (RenderableBlock block : blocks) {
            long blockID = block.getBlockID();
            if (!alreadyAdd.containsKey(blockID) && !block.hasBlockParent()) {
                ArrayList<Long> idList = block.getIDList();
                for (Long id : idList) {
                    alreadyAdd.put(id, 1l);
                }
                screen.add(block.cloneThisNoParent(block));
            }
        }
        return screen;
    }


    public void normalizeListSize() {
        if (undoList.size() > maxSize) {
            undoList.remove(0);
            normalizeListSize();
        }
    }

    public static void setSize(int s) {
        maxSize = s;
    }

}
