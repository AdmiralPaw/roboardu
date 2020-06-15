package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ardublock.core.Context;
import com.ardublock.ui.OpenblocksFrame;

/**
 * @author AdmiralPaw, Ritevi, Aizek
 * Класс, который сохраняет прослушиватель кнопки
 */
public class SaveButtonListener implements ActionListener
{
	private OpenblocksFrame parentFrame;

    /**
     * Метод для сохранения прослушивателя кнопки
     * @param frame - Оконная процедура
     */
    public SaveButtonListener(OpenblocksFrame frame)
	{
		Context.getContext();
		parentFrame = frame;
	}

	/**
	 * Метод, сохраняющий файл сгенерированного кода Arduino из блоков
	 * @param e - Событие совершённого действия
	 */
	public void actionPerformed(ActionEvent e)
	{
		parentFrame.doSaveArduBlockFile();
	}


}
