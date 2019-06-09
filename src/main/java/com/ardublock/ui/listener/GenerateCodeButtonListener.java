package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ardublock.core.Context;
import com.ardublock.translator.AutoFormat;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNameDuplicatedException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

import com.mit.blocks.codeblocks.Block;
import com.mit.blocks.renderable.RenderableBlock;
import com.mit.blocks.workspace.Workspace;

public class GenerateCodeButtonListener implements ActionListener
{
	private JFrame parentFrame;
	private Context context;
	private Workspace workspace; 
	private ResourceBundle uiMessageBundle;
	
	public GenerateCodeButtonListener(JFrame frame, Context context)
	{
		this.parentFrame = frame;
		this.context = context;
		workspace = context.getWorkspaceController().getWorkspace();
		uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	}

	private void afterException(Long blockId)
	{
		Iterable<RenderableBlock> blocks = workspace.getRenderableBlocks();
		for (RenderableBlock renderableBlock2 : blocks)
		{
			Block block2 = renderableBlock2.getBlock();
			if (block2.getBlockID().equals(blockId))
			{
				context.highlightBlock(renderableBlock2);
				break;
			}
		}

	}
	
	public void actionPerformed(ActionEvent e)
	{
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
		
		
		for (RenderableBlock renderableBlock:renderableBlocks)
		{
			Block block = renderableBlock.getBlock();
			
			if (block.getGenusName().equals("DuinoEDU_Guino_Read"))
			{
				translator.setGuinoProgram(true);
				
			}
			if ((block.getGenusName().equals("DuinoEDU_Guino_Title")) || (block.getGenusName().equals("DuinoEDU_Guino_Slider")) || (block.getGenusName().equals("DuinoEDU_Guino_column")) || (block.getGenusName().equals("DuinoEDU_Guino_switch"))|| (block.getGenusName().equals("DuinoEDU_Guino_pause")) ) 
			{
				translator.setGuinoProgram(true);
			}
			
			
			if (!block.hasPlug() && (Block.NULL.equals(block.getBeforeBlockID())))
			{
				String[] names = new String[]{"loop","loop1","loop2","loop3","program","setup"};
				for(String name : names)
				{
					if(block.getGenusName().equals(name))
					{
						loopBlockSet.add(renderableBlock);
					}
				}


				names = new String[]{"subroutine","subroutine_var"};
				for(String name : names)
				{
					if(block.getGenusName().equals(name))
					{
						String functionName = block.getBlockLabel().trim();
						try
						{
							translator.addFunctionName(block.getBlockID(), functionName);
						}
						catch (SubroutineNameDuplicatedException e1)
						{
							context.highlightBlock(renderableBlock);
							//find the second subroutine whose name is defined, and make it highlight. though it cannot happen due to constraint of OpenBlocks -_-
							JOptionPane.showMessageDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.subroutineNameDuplicated"), "Error", JOptionPane.ERROR_MESSAGE);
							return ;
						}
						subroutineBlockSet.add(renderableBlock);
					}
				}

				names = new String[]{"scoop_task","scoop_loop","scoop_pin_event"};
				for(String name : names)
				{
					if(block.getGenusName().equals(name))
					{
						translator.setScoopProgram(true);
						scoopBlockSet.add(renderableBlock);
					}
				}

			}
		}
		if (loopBlockSet.size() == 0) {
			JOptionPane.showMessageDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.noLoopFound"), "Error", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		if (loopBlockSet.size() > 1) {
			for (RenderableBlock rb : loopBlockSet)
			{
				context.highlightBlock(rb);
			}
			JOptionPane.showMessageDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.multipleLoopFound"), "Error", JOptionPane.ERROR_MESSAGE);
			return ;
		}

		try
		{
			
			for (RenderableBlock renderableBlock : loopBlockSet)
			{
				translator.setRootBlockName("loop");
				Block loopBlock = renderableBlock.getBlock();
				code.append(translator.translate(loopBlock.getBlockID()));
			}
			
			for (RenderableBlock renderableBlock : scoopBlockSet)
			{
				translator.setRootBlockName("scoop");
				Block scoopBlock = renderableBlock.getBlock();
				code.append(translator.translate(scoopBlock.getBlockID()));
			}
			for (RenderableBlock renderableBlock : guinoBlockSet)
			{
				translator.setRootBlockName("guino");
				Block guinoBlock = renderableBlock.getBlock();
				code.append(translator.translate(guinoBlock.getBlockID()));
			}
			
			
			for (RenderableBlock renderableBlock : subroutineBlockSet)
			{
				translator.setRootBlockName("subroutine");
				Block subroutineBlock = renderableBlock.getBlock();
				code.append(translator.translate(subroutineBlock.getBlockID()));
			}
			
			translator.beforeGenerateHeader();
			code.insert(0, translator.genreateHeaderCommand());
		}
		catch (SocketNullException e1)
		{
			e1.printStackTrace();
			success = false;
			Long blockId = e1.getBlockId();
			afterException(blockId);
			JOptionPane.showMessageDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.socketNull"), "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch (BlockException e2)
		{
			e2.printStackTrace();
			success = false;
			Long blockId = e2.getBlockId();
			afterException(blockId);
			JOptionPane.showMessageDialog(parentFrame, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch (SubroutineNotDeclaredException e3)
		{
			e3.printStackTrace();
			success = false;
			Long blockId = e3.getBlockId();
			afterException(blockId);
			JOptionPane.showMessageDialog(parentFrame, uiMessageBundle.getString("ardublock.translator.exception.subroutineNotDeclared"), "Error", JOptionPane.ERROR_MESSAGE);
			
		}
		
		if (success)
		{
			AutoFormat formatter = new AutoFormat();
			String codeOut = code.toString();
			
			if (context.isNeedAutoFormat)
			{
				codeOut = formatter.format(codeOut);
			}
			
			if (!context.isInArduino())
			{
				System.out.println(codeOut);
			}		
			context.didGenerate(codeOut);
		}
	}
}
