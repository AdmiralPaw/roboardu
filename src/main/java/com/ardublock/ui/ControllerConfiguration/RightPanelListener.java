/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ardublock.ui.ControllerConfiguration;

import com.mit.blocks.codeblocks.Block;
import com.mit.blocks.codeblocks.BlockConnector;
import com.mit.blocks.controller.WorkspaceController;
import com.mit.blocks.renderable.ConnectorTag;
import com.mit.blocks.renderable.RenderableBlock;
import com.mit.blocks.workspace.Workspace;
import com.mit.blocks.workspace.WorkspaceEvent;
import com.mit.blocks.workspace.WorkspaceListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Ritevi
 */
public class RightPanelListener implements WorkspaceListener {
    private Workspace workspace;
    Map<String,String> suitableDict;
    Map<String,List<String>> PinAndModulesMap;
    private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
    
    public RightPanelListener(Workspace workspace) {
		this.workspace = workspace;
                PinAndModulesMap= new HashMap<String,List<String>>();
	}
    
    @Override
    public void workspaceEventOccurred(WorkspaceEvent event) {
        suitableDict = WorkspaceController.translateRightPanel;
//        if (event.getEventType() == WorkspaceEvent.BLOCKS_CONNECTED) {
//            RenderableBlock b1 = workspace.getEnv().getRenderableBlock(event.getSourceLink().getSocket().getBlockID());
//            if(b1.isFatherLoop("loop")){
////                String PlugPin="",moduleName="";
////                PlugPin = b1.getBlock().getBlockLabel().toLowerCase();
////                moduleName = b1.getFatherPlugBlock().getBlock().getGenusName();
////                String TranslateName = translateNormalToRightPanel(moduleName);
////                if(TranslateName!=null){
////                    workspace.controller.setModuleOnPin(PlugPin, TranslateName);
////                }
//                
//                
//                Map<String,String> PinsAndModules= new HashMap<String,String>();
//                PinsAndModules = getPinsRecursive(b1.getBlock());
//                for(String ButtonPin:PinsAndModules.keySet()){   
//                    String TranslateName = translateNormalToRightPanel(PinsAndModules.get(ButtonPin));
//                    if(TranslateName!=null){
//                        this.putInPinsList(ButtonPin, TranslateName);
//                        workspace.controller.setModuleOnPin(ButtonPin, TranslateName);
//                    }
//                }                
//            }          
//        }
//        if (event.getEventType() == WorkspaceEvent.BLOCK_GENUS_CHANGED) {
//            RenderableBlock plug = workspace.getEnv().getRenderableBlock(event.getSourceBlockID());
//            RenderableBlock rb2 = workspace.getEnv().getRenderableBlock(plug.plugTag.getSocket().getBlockID());
//            RenderableBlock father = plug.getFatherPlugBlock();
//            if(father.isFatherLoop("loop")){
//                String TranslateName = translateNormalToRightPanel(rb2.getBlock().getGenusName());
//                if(TranslateName!=null){
//                    String ButtonPin = plug.getBlock().getBlockLabel().toLowerCase();
//                    String OldButtonPin = event.getoldBlockLabel().toLowerCase();
//                    
//                    deactivatePin(OldButtonPin,TranslateName);
//                    activatePin(ButtonPin,TranslateName);
//                }
//            }           
//        }
//        if (event.getEventType() == WorkspaceEvent.BLOCKS_DISCONNECTED) {
//            RenderableBlock b1 = workspace.getEnv().getRenderableBlock(event.getSourceLink().getPlugBlockID());
//            RenderableBlock father = workspace.getEnv().getRenderableBlock(event.getSourceLink().getSocketBlockID());
//            if(father.isFatherLoop("loop")){
//                Map<String,String> PinsAndModules= new HashMap<String,String>();
//                PinsAndModules = getPinsRecursive(b1.getBlock());
//                for(String ButtonPin:PinsAndModules.keySet()){
//                    this.removeFromPinsList(ButtonPin, ButtonPin);
//                    String TranslateName = translateNormalToRightPanel(PinsAndModules.get(ButtonPin));
//                    if(TranslateName!=null){
//                        deactivatePin(ButtonPin,TranslateName);
//                    }
//                }                
//            }
//        }
////        if (event.getEventType() == WorkspaceEvent.BLOCK_REMOVED) {
////            Block rb1 = workspace.getEnv().getBlock(event.getSourceBlockID());
////            String ButtonPin = rb1.getBlockLabel().toLowerCase(); 
////            workspace.controller.setModuleOnPin(ButtonPin, "start");           
////        } 
        if (event.getEventType() == WorkspaceEvent.BLOCKS_CONNECTED || 
                event.getEventType() == WorkspaceEvent.BLOCKS_DISCONNECTED || 
                    event.getEventType() == WorkspaceEvent.BLOCK_GENUS_CHANGED) {
            this.clearPins();
            Map<String,String> PinsAndModules;
            PinsAndModules = getPinsRecursive(getRoot().get(0).getBlock());
            PinsAndModules.keySet().forEach((ButtonPin) -> {
                String TranslateName = translateNormalToRightPanel(PinsAndModules.get(ButtonPin));
                if (TranslateName!=null) {                   
                    workspace.controller.setModuleOnPin(ButtonPin, TranslateName);
                }
            });            
        }
 
    } 

    public String translateNormalToRightPanel(String name){
        return suitableDict.get(name);
    }
    
    public Map<String,String> getPinsRecursive(Block block){ 
        Map<String,String> PinsAndModules= new HashMap<String,String>();
        if(block!=null){
            Iterable<BlockConnector> list = null;
            String pin="",module="";
            Block temp = block;
            while(temp!=null){
                list = temp.getSockets();
                if(list.iterator().hasNext()){
                    for (BlockConnector oriSocket : list) {
                        PinsAndModules.putAll(getPinsRecursive(workspace.getEnv().getBlock(oriSocket.getBlockID())));
                    }
                } else {
                    pin = temp.getBlockLabel().toLowerCase();
                    RenderableBlock someBlock = workspace.getEnv().getRenderableBlock(temp.getBlockID());
                    module = someBlock.getFatherPlugBlock().getGenus();
                    PinsAndModules.put(pin, module);
                }
                temp = workspace.getEnv().getBlock(temp.getAfterBlockID());
            };
            return PinsAndModules;
        } else {
            return PinsAndModules;
        }        
    }
    
    
    public void putInPinsList(String pin, String ModuleName){
        List<String> tempModules = new ArrayList<String>();
        if(PinAndModulesMap.containsKey(pin)){            
            tempModules.addAll(PinAndModulesMap.get(pin));
            tempModules.add(ModuleName);
        } else{
            tempModules.add(ModuleName);           
        }
        PinAndModulesMap.put(pin, tempModules);
    }
    
    public void removeFromPinsList(String pin, String ModuleName){
        List<String> tempModules = new ArrayList<String>();
        if(PinAndModulesMap.containsKey(pin)){            
            tempModules.addAll(PinAndModulesMap.get(pin));
            tempModules.remove(ModuleName);
            PinAndModulesMap.put(pin, tempModules);
        } else{
            PinAndModulesMap.remove(pin);
        }       
    }
    
    public boolean existInPinsList(String pin, String ModuleName){
        List<String> tempModules = new ArrayList<String>();
        if(PinAndModulesMap.containsKey(pin)){            
            tempModules.addAll(PinAndModulesMap.get(pin));
            return tempModules.contains(ModuleName);
        } else{
            return false;           
        }
    }
    
    public void activatePin(String pin, String ModuleName){
        this.putInPinsList(pin, ModuleName);
        workspace.controller.setModuleOnPin(pin, ModuleName);
    }
    
    public void deactivatePin(String pin, String ModuleName){
        this.removeFromPinsList(pin, ModuleName);
        if(!existInPinsList(pin,ModuleName)){
            workspace.controller.setModuleOnPin(pin, "start");            
        }
    }
    
    
    public List<RenderableBlock> getRoot(){
        Iterable<RenderableBlock> renderableBlocks = workspace.getRenderableBlocks();
        List<RenderableBlock> loopBlockSet = new ArrayList<RenderableBlock>();
        for (RenderableBlock renderableBlock : renderableBlocks) {
            Block block = renderableBlock.getBlock();
            if (!block.hasPlug() && (Block.NULL.equals(block.getBeforeBlockID()))) {
                String[] names = new String[]{"loop", "loop1", "loop2", "loop3", "program", "setup"};
                for (String name : names) {
                    if (block.getGenusName().equals(name)) {
                        loopBlockSet.add(renderableBlock);
                    }
                }
            }
        }
        return loopBlockSet;
    }
    
    public void clearPins(){
        workspace.controller.setModuleOnPin("a0", "start");
        workspace.controller.setModuleOnPin("a1", "start");
        workspace.controller.setModuleOnPin("a2", "start");
        workspace.controller.setModuleOnPin("a3", "start");
        workspace.controller.setModuleOnPin("d1", "start");
        workspace.controller.setModuleOnPin("d2", "start");
        workspace.controller.setModuleOnPin("d3", "start");
        workspace.controller.setModuleOnPin("d4", "start");
        workspace.controller.setModuleOnPin("d5", "start");
        workspace.controller.setModuleOnPin("d6", "start");
        workspace.controller.setModuleOnPin("d7", "start");
        workspace.controller.setModuleOnPin("d8", "start");
        workspace.controller.setModuleOnPin("d9", "start");
        workspace.controller.setModuleOnPin("d10", "start");
        workspace.controller.setModuleOnPin("d11", "start");
        workspace.controller.setModuleOnPin("d12", "start");
        
    }
    
}
