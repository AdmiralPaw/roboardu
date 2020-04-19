/**************************************************
Description   : 
Author	  : Leo Yan
Created 	  : 2014/6
**************************************************/

package com.ardublock.translator.block.drawing;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class TimePara extends TranslatorBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public TimePara(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator);
	}

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    @Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret;
		TranslatorBlock childBlock;	

		ret = "{PALETTE_PIN_VIRTUAL_TIME, FUNCTION_SENSOR_VIRTUALTIME },";


		childBlock = getTranslatorBlockAtSocket(0);
		if (childBlock != null)
		{
			ret += childBlock.toCode() + ",";
		}
		else
		{
			ret += "0,";
		}
		
		ret += "RELATION_EQUAL";
		
		return ret;
	}
}
