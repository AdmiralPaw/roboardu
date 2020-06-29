package com.ardublock.ui.listener;

import com.ardublock.core.Context;
import com.ardublock.ui.OpenblocksFrame;

import com.mit.blocks.workspace.WorkspaceEvent;
import com.mit.blocks.workspace.WorkspaceListener;

/**
 * @author AdmiralPaw, Ritevi, Aizek
 * Класс прослушивателя для получения событий в рабочей области, которая генерирует код Arduino из блоков
 */
public class ArdublockWorkspaceListener implements WorkspaceListener
{
	//Поле контекста
	private Context context;

	//Поле оконной процедуры
	private OpenblocksFrame frame;

    /**
     * Метод, прослушивающий события в рабочей области
     * @param frame - оконная процедура
     */
    public ArdublockWorkspaceListener(OpenblocksFrame frame)
	{
		context = Context.getContext();
		this.frame = frame;
	}

	/**
	 * Метод, указывающий на то, что произошло событие рабочей области
	 * @param event - Объект события рабочей области, содержащий информацию о вызванном событии
	 */
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
