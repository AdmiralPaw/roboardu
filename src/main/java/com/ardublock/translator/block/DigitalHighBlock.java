package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

/**
 *
 * @author User
 */
public class DigitalHighBlock extends ConstBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public DigitalHighBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
		this.setCode("HIGH");
	}
}
