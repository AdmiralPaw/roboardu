package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ardublock.core.Context;
import com.ardublock.ui.OpenblocksFrame;

/**
 *
 * @author User
 */
public class OpenButtonListener implements ActionListener
{
	private OpenblocksFrame parentFrame;
	
    /**
     *
     * @param frame
     */
    public OpenButtonListener(OpenblocksFrame frame)
	{
		Context.getContext();
		
		this.parentFrame = frame;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		parentFrame.doOpenArduBlockFile();
	}

}
