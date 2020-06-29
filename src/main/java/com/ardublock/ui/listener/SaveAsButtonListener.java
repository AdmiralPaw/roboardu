package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ardublock.core.Context;
import com.ardublock.ui.OpenblocksFrame;

/**
 * Класс, который сохраняет объект как прослушиватель кнопки
 * @author AdmiralPaw, Ritevi, Aizek
 */
public class SaveAsButtonListener implements ActionListener
{
	/**Поле родительской оконной процедуры*/
	private OpenblocksFrame parentFrame;
	
    /**
	 * Метод для сохранения объекта как прослушиватель кнопки
     * @param frame - Оконная процедура
     */
    public SaveAsButtonListener(OpenblocksFrame frame)
	{
		Context.getContext();
		
		this.parentFrame = frame;
	}

	/**
	 * Метод, сохраняющий файл сгенерированного кода Arduino из блоков
	 * @param e Событие совершённого действия
	 */
	public void actionPerformed(ActionEvent e)
	{
		parentFrame.doSaveAsArduBlockFile();
	}

}
