package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ardublock.core.Context;
import com.ardublock.ui.OpenblocksFrame;

/**
 *
 * @author User
 */
public class SaveButtonListener implements ActionListener
{
	private OpenblocksFrame parentFrame;

    /**
     *
     * @param frame
     */
    public SaveButtonListener(OpenblocksFrame frame)
	{
		Context.getContext();
		parentFrame = frame;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		parentFrame.doSaveArduBlockFile();
	}


}
