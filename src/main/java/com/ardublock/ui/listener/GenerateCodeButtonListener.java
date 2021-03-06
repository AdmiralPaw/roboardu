package com.ardublock.ui.listener;

import com.ardublock.core.Context;
import com.ardublock.translator.AutoFormat;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNameDuplicatedException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import com.ardublock.ui.OpenblocksFrame;
import com.mit.blocks.codeblocks.Block;
import com.mit.blocks.renderable.RenderableBlock;
import com.mit.blocks.workspace.Workspace;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

//<<<<<<< HEAD
//import com.ardublock.ui.OpenblocksFrame;
//=======
//>>>>>>> lerofaCtrlZ

/**
 * @author AdmiralPaw, Ritevi, Aizek
 * Класс, который генерирует код прослушивателя кнопки
 */

public class GenerateCodeButtonListener implements ActionListener {

    //Поле родительской оконной процедуры
    private JFrame parentFrame;

    //Поле контекста
    private Context context;

    //Поле рабочего пространства
    private Workspace workspace;

    //Поле сообщений пользовательского интерфейса
    private ResourceBundle uiMessageBundle;

    /**
     * Метод, генерирующий код прослушивателя кнопки
     * @param frame - Оконная процедура
     * @param context - Контекст
     */
    public GenerateCodeButtonListener(JFrame frame, Context context) {
        this.parentFrame = frame;
        this.context = context;
        workspace = context.getWorkspaceController().getWorkspace();
        uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
    }

    /**
     * Метод, *
     * @param blockId - Идентификатор блока
     */
    private void afterException(Long blockId) {
        Iterable<RenderableBlock> blocks = workspace.getRenderableBlocks();
        for (RenderableBlock renderableBlock2 : blocks) {
            Block block2 = renderableBlock2.getBlock();
            if (block2.getBlockID().equals(blockId)) {
                context.highlightBlock(renderableBlock2);
                break;
            }
        }

    }

    /**
     *
     * @param e - Событие совершённого действия
     * @exception SocketNullException e1
     * @exception BlockException e2
     * @exception SubroutineNotDeclaredException e3
     */
    public void actionPerformed(ActionEvent e) {
        if (parentFrame instanceof OpenblocksFrame)
        {
            ((OpenblocksFrame) parentFrame).getContext().getWorkspace().deactiveLabelWidget();
        }
        boolean success;
        success = true;
        Translator translator = new Translator(workspace);
        translator.reset();

        Iterable<RenderableBlock> renderableBlocks = workspace.getRenderableBlocks();

        Set<RenderableBlock> loopBlockSet = new HashSet<RenderableBlock>();
        Set<RenderableBlock> subroutineBlockSet = new HashSet<RenderableBlock>();
        Set<RenderableBlock> scoopBlockSet = new HashSet<RenderableBlock>();
        Set<RenderableBlock> guinoBlockSet = new HashSet<RenderableBlock>();
        StringBuilder code = new StringBuilder();

        for (RenderableBlock renderableBlock : renderableBlocks) {
            Block block = renderableBlock.getBlock();

            if (block.getGenusName().equals("DuinoEDU_Guino_Read")) {
                translator.setGuinoProgram(true);

            }
            if ((block.getGenusName().equals("DuinoEDU_Guino_Title")) || (block.getGenusName().equals("DuinoEDU_Guino_Slider")) || (block.getGenusName().equals("DuinoEDU_Guino_column")) || (block.getGenusName().equals("DuinoEDU_Guino_switch")) || (block.getGenusName().equals("DuinoEDU_Guino_pause"))) {
                translator.setGuinoProgram(true);
            }

            if (!block.hasPlug() && (Block.NULL.equals(block.getBeforeBlockID()))) {
                String[] names = new String[]{"loop", "loop1", "loop2", "loop3", "program", "setup"};
                for (String name : names) {
                    if (block.getGenusName().equals(name)) {
                        loopBlockSet.add(renderableBlock);
                    }
                }

                names = new String[]{"subroutine", "subroutine_var"};
                for (String name : names) {
                    if (block.getGenusName().equals(name)) {
                        String functionName = block.getBlockLabel().trim();
                        try {
                            translator.addFunctionName(block.getBlockID(), functionName);
                        } catch (SubroutineNameDuplicatedException e1) {
                            context.highlightBlock(renderableBlock);
                            //find the second subroutine whose name is defined, and make it highlight. though it cannot happen due to constraint of OpenBlocks -_-
                            //JOptionPane.showMessageDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.subroutineNameDuplicated"), "Error", JOptionPane.ERROR_MESSAGE);
                            workspace.getErrWindow().setErr(
                                    "Error",
                                    uiMessageBundle.getString("ardublock.translator.exception.subroutineNameDuplicated"));
                            return;
                        }
                        subroutineBlockSet.add(renderableBlock);
                    }
                }

                names = new String[]{"scoop_task", "scoop_loop", "scoop_pin_event"};
                for (String name : names) {
                    if (block.getGenusName().equals(name)) {
                        translator.setScoopProgram(true);
                        scoopBlockSet.add(renderableBlock);
                    }
                }

            }
        }
        if (loopBlockSet.size() == 0) {
            //JOptionPane.showMessageDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.noLoopFound"), "Error", JOptionPane.ERROR_MESSAGE);
            workspace.getErrWindow().setErr(
                                    "Error",
                                    uiMessageBundle.getString("ardublock.translator.exception.noLoopFound"));
            return;
        }
        if (loopBlockSet.size() > 1) {
            for (RenderableBlock rb : loopBlockSet) {
                context.highlightBlock(rb);
            }
            //JOptionPane.showMessageDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.multipleLoopFound"), "Error", JOptionPane.ERROR_MESSAGE);
            workspace.getErrWindow().setErr(
                                    "Error",
                                    uiMessageBundle.getString("ardublock.translator.exception.multipleLoopFound"));
            return;
        }

        try {

            for (RenderableBlock renderableBlock : loopBlockSet) {
                translator.setRootBlockName("loop");
                Block loopBlock = renderableBlock.getBlock();
                code.append(translator.translate(loopBlock.getBlockID()));
            }

            for (RenderableBlock renderableBlock : scoopBlockSet) {
                translator.setRootBlockName("scoop");
                Block scoopBlock = renderableBlock.getBlock();
                code.append(translator.translate(scoopBlock.getBlockID()));
            }
            for (RenderableBlock renderableBlock : guinoBlockSet) {
                translator.setRootBlockName("guino");
                Block guinoBlock = renderableBlock.getBlock();
                code.append(translator.translate(guinoBlock.getBlockID()));
            }

            for (RenderableBlock renderableBlock : subroutineBlockSet) {
                translator.setRootBlockName("subroutine");
                Block subroutineBlock = renderableBlock.getBlock();
                code.append(translator.translate(subroutineBlock.getBlockID()));
            }

            translator.beforeGenerateHeader();
            code.insert(0, translator.genreateHeaderCommand());
        } catch (SocketNullException e1) {
            //e1.printStackTrace();
            success = false;
            Long blockId = e1.getBlockId();
            afterException(blockId);
            //JOptionPane.showMessageDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.socketNull"), "Error", JOptionPane.ERROR_MESSAGE);
            workspace.getErrWindow().setErr(
                                    "Error",
                                    uiMessageBundle.getString("ardublock.translator.exception.socketNull"));
        } catch (BlockException e2) {
            //e2.printStackTrace();
            success = false;
            Long blockId = e2.getBlockId();
            afterException(blockId);
            //JOptionPane.showMessageDialog(parentFrame, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            workspace.getErrWindow().setErr("Error",e2.getMessage());
        } catch (SubroutineNotDeclaredException e3) {
            //e3.printStackTrace();
            success = false;
            Long blockId = e3.getBlockId();
            afterException(blockId);
            //JOptionPane.showMessageDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.subroutineNotDeclared"), "Error", JOptionPane.ERROR_MESSAGE);
            workspace.getErrWindow().setErr(
                                    "Error",
                                    uiMessageBundle.getString("ardublock.translator.exception.subroutineNotDeclared"));
        }

        if (success) {
            workspace.getErrWindow().reset();
            AutoFormat formatter = new AutoFormat();
            String codeOut = code.toString();

            if (context.isNeedAutoFormat) {
                codeOut = formatter.format(codeOut);
            }

            if (!context.isInArduino()) {
                System.out.println(codeOut);
            }

            if (e.getActionCommand() == "UPLOAD_CODE")
            {
                context.didGenerate(codeOut);
            }
            else if (e.getActionCommand() == "VERIFY_CODE")
            {
                context.didVerify(codeOut);
            }
        }
    }
}
