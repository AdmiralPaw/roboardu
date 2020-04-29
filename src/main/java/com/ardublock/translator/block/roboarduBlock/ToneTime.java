/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ardublock.translator.block.roboarduBlock;


import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import java.util.ResourceBundle;

/**
 *
 * @author User
 */
public class ToneTime extends TranslatorBlock
{
    private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public ToneTime(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
        
    /**
     *
     */

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    @Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock pinBlock = this.getRequiredTranslatorBlockAtSocket(0);
                 if(!("A0 A1 A2 A3 8 9 10 11 12").contains(pinBlock.toCode().trim())) {
                    throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.Digital_pin_slot"));
                }
		TranslatorBlock freqBlock = this.getRequiredTranslatorBlockAtSocket(1);
                if((Integer.parseInt(freqBlock.toCode())<100) || (Integer.parseInt(freqBlock.toCode())>3000)){
                    throw new BlockException(freqBlock.getBlockID(), "ARGUMENT_ERROR");
                }
		TranslatorBlock timeBlock = this.getRequiredTranslatorBlockAtSocket(2);
                if((Integer.parseInt(timeBlock.toCode())<1) || (Integer.parseInt(timeBlock.toCode())>10)){
                    throw new BlockException(timeBlock.getBlockID(), "ARGUMENT_ERROR");
                }
                translator.LoadTranslators(this.getClass().getSimpleName());
		
		String ret = "PiezoTone(" + pinBlock.toCode() + ", " + freqBlock.toCode() + ", " + timeBlock.toCode() + ");\n";
		return ret;
	}
}
