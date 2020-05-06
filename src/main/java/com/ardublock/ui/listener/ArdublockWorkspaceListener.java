package com.ardublock.ui.listener;

import com.ardublock.core.Context;
import com.ardublock.ui.OpenblocksFrame;

import com.mit.blocks.workspace.WorkspaceEvent;
import com.mit.blocks.workspace.WorkspaceListener;

/**
 *
 * @author User
 */
public class ArdublockWorkspaceListener implements WorkspaceListener
{
	private Context context;
	private OpenblocksFrame frame;

    /**
     *
     * @param frame
     */
    public ArdublockWorkspaceListener(OpenblocksFrame frame)
	{
		context = Context.getContext();
		this.frame = frame;
	}
	
	public void workspaceEventOccurred(WorkspaceEvent event)
	{
		if (!context.isWorkspaceChanged())
		{
			context.setWorkspaceChanged(true);
			context.setWorkspaceEmpty(false);
			String title = frame.makeFrameTitle();
			if (frame != null)
			{
				frame.setTitle(title);
			}
		}
		context.resetHightlightBlock();

	}
}
