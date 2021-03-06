package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class Keyboard_Release extends TranslatorBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public Keyboard_Release(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
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
		
		String Letter;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Letter = translatorBlock.toCode();
		translator.addDefinitionCommand("//Keyboard Modifiers at http://arduino.cc/en/Reference/KeyboardModifiers");
		translator.addSetupCommand("Keyboard.begin();");
		
		String ret;

		if(Letter.replace("\"","").length()>1){
    		ret="Keyboard.release("+Letter.replace("\"","")+");\n";
    	}else{
    		ret="Keyboard.release("+Letter.replace("\"","\'")+");\n";
    	}
		return ret;

	}
}
