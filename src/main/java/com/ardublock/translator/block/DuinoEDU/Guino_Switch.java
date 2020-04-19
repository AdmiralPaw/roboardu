package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class Guino_Switch  extends TranslatorBlock {

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public Guino_Switch (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			
			String Title;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Title = translatorBlock.toCode();
			
			String internalVariableName = translator.getNumberVariable(Title);
			if (internalVariableName == null)
			{
				internalVariableName = label.replace(" ","")+translator.buildVariableName(Title);
				translator.addNumberVariable(Title, internalVariableName);
				translator.addDefinitionCommand("int " + internalVariableName + " = 0 ;");
//				translator.addSetupCommand(internalVariableName + " = 0;");
			}
			
			translator.addHeaderFile("EasyTransfer.h");
			translator.addHeaderFile("EEPROM.h");
			translator.addHeaderFile("Guino.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/dupont/EDU_Guino/");
			translator.addSetupCommand("GUINO_BRANCHER();");
			translator.addGuinoCommand("GUINO_AFFICHER_INTERRUPTEUR("+Title+","+internalVariableName+");");

			return  codePrefix +internalVariableName  + codeSuffix;
			
		}
}
