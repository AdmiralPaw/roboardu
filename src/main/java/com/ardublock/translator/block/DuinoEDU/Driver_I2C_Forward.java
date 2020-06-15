package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class Driver_I2C_Forward  extends TranslatorBlock {

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public Driver_I2C_Forward (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
			String way;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			way = translatorBlock.toCode();
			
			
			translator.addHeaderFile("Wire.h");
			translator.addHeaderFile("MotorI2C.h");
			translator.addDefinitionCommand("//libraries athttp://duinoedu.com/dl/lib/grove/EDU_MotorI2C/ \nMotorI2C mesMoteurs;");
			translator.addSetupCommand("mesMoteurs.brancher();");
			
						
			String ret =  "mesMoteurs.avancer(\""+ way +"\");\n";
			
			return ret ;	
		}
}
