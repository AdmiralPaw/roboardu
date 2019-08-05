package com.mit.blocks.workspace;

import com.mit.blocks.renderable.RenderableBlock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class BlocksKeeper {
    private ArrayList<ArrayList<RenderableBlock>> undoList;
    private ArrayList<ArrayList<RenderableBlock>> redoList;
    private static int maxSize;
    private Page parent;

    public BlocksKeeper(Page p) {
        undoList = new ArrayList<ArrayList<RenderableBlock>>();
        redoList = new ArrayList<ArrayList<RenderableBlock>>();
        parent = p;
    }

    public Collection<RenderableBlock> undoAct() {
        if (undoList.size() > 0) {
            ArrayList<RenderableBlock> screen = undoList.get(undoList.size() - 1);
            ArrayList<RenderableBlock> currentScreen = getScreen(parent.getBlocks());
            if (undoList.size() > 1 && equalsCollection(screen, currentScreen))
            {
                screen = undoList.get(undoList.size()-2);
                currentScreen = undoList.get(undoList.size()-1);
                undoList.remove(undoList.size()-2);
            }
            undoList.remove(undoList.size() - 1);
            redoList.add(currentScreen);
            //System.out.println(String.valueOf(undoList.size()) + ":" + String.valueOf(redoList.size()));
            return screen;
        }


        return null;

    }

    public Collection<RenderableBlock> redoAct() {
        if (redoList.size() > 0) {
            Collection<RenderableBlock> screen = redoList.get(redoList.size() - 1);
            redoList.remove(redoList.size() - 1);
            undoList.add(getScreen(parent.getBlocks()));
            //System.out.println(String.valueOf(undoList.size()) + ":" + String.valueOf(redoList.size()));
            return screen;
        }

        return null;
    }

    public void addAct(Collection<RenderableBlock> blocks) {
        ArrayList<RenderableBlock> screen = getScreen(blocks);
        if (undoList.size()!=0)
        {
            if (!equalsCollection(undoList.get(undoList.size()-1), screen))
            {
                undoList.add(screen);
                redoList.clear();
                System.out.println("Act saved: " + undoList.size());
            }
        }
        else
        {
            undoList.add(screen);
            redoList.clear();
        }
        normalizeListSize();
        //System.out.println("Act saved: " + undoList.size());
    }

    public ArrayList<RenderableBlock> getScreen(Collection<RenderableBlock> blocks) {
        HashMap<Long, Long> alreadyAdd = new HashMap<Long, Long>();
        ArrayList<RenderableBlock> screen = new ArrayList<RenderableBlock>();
        for (RenderableBlock block : blocks) {
            long blockID = block.getBlockID();
            if (!alreadyAdd.containsKey(blockID) && !block.hasBlockParent()) {
                ArrayList<Long> idList = block.getIDList();
                for (Long id : idList) {
                    alreadyAdd.put(id, 1l);
                }
                screen.add(block.cloneThisForKeeper(block, null));
            }
        }
        return screen;
    }

    public boolean equalsCollection(ArrayList<RenderableBlock> a, ArrayList<RenderableBlock> b)
    {
        if (a.size()!= b.size())
        {
            return false;
        }

        for (int i=0;i<a.size();i++)
        {
            if (!a.get(i).equals(b.get(i)))
            {
                return false;
            }
        }

        return true;
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
