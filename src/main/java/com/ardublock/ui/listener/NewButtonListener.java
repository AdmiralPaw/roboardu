package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ardublock.core.Context;
import com.ardublock.ui.OpenblocksFrame;

/**
 * @author AdmiralPaw, Ritevi, Aizek
 * Класс прослушивателя для получения событий новой кнопки
 */
public class NewButtonListener implements ActionListener
{
	//Поле родительской оконной процедуры
	private OpenblocksFrame parentFrame;
	
    /**
     * Метод, прослушивающий события новой кнопки
     * @param frame - оконная процедура
     */
    public NewButtonListener(OpenblocksFrame frame)
	{
		Context.getContext();
		
		this.parentFrame = frame;
	}

	/**
	 * Метод, создающий новый файл сгенерированного кода Arduino из блоков
	 * @param e - Событие совершённого действия
	 */
	public void actionPerformed(ActionEvent e)
	{   
		parentFrame.doNewArduBlockFile();
	}

}
