package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ardublock.core.Context;
import com.ardublock.ui.OpenblocksFrame;

/**
 * @author AdmiralPaw, Ritevi, Aizek
 * Класс открывающий прослушиватель кнопки
 */
public class OpenButtonListener implements ActionListener
{
	//Поле родительской оконной процедуры
	private OpenblocksFrame parentFrame;
	
    /**
     * Метод, открывающий прослушиватель кнопки
     * @param frame - Оконная процедура
     */
    public OpenButtonListener(OpenblocksFrame frame)
	{
		Context.getContext();
		
		this.parentFrame = frame;
	}

	/**
	 * Метод, открывающий файл, сгенерированного кода Arduino из блоков
	 * @param e - Событие совершённого действия
	 */
	public void actionPerformed(ActionEvent e)
	{
		parentFrame.doOpenArduBlockFile();
	}

}
