package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

/**
 *
 * @author User
 */
public class WireIsReadBlock extends TranslatorBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public WireIsReadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

    /**
     *
     * @return
     */
    @Override
	public String toCode()
	{
		WireReadBlock.setupWireEnvironment(translator);
		return codePrefix + " __ardublockIsI2cReadOk " + codeSuffix;
	}

}
