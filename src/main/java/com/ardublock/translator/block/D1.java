package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

/**
 *
 * @author User
 */
public class D1 extends TranslatorBlock
	{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public D1(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
			return codePrefix + "1" + codeSuffix;
		}
		
	}