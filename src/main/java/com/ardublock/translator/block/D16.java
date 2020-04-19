package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

/**
 *
 * @author User
 */
public class D16 extends TranslatorBlock
	{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public D16(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
		{
			super(blockId, translator, codePrefix, codeSuffix, label);
		}

    /**
     *
     * @return
     * @throws SocketNullException
     */
    @Override
		public String toCode() throws SocketNullException {
			return codePrefix + "16" + codeSuffix;
		}
		
	}