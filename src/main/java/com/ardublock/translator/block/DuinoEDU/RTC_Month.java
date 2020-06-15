package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class RTC_Month  extends TranslatorBlock {

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public RTC_Month (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
				
			translator.addHeaderFile("Wire.h");
			translator.addHeaderFile("DS1307.h");
			translator.addDefinitionCommand("DS1307 clock;");
			translator.addSetupCommand("clock.brancher();");
			
			return codePrefix +"clock.lireMois()"+ codeSuffix;
		}
}
