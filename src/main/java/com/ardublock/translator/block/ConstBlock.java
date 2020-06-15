package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

/**
 *
 * @author User
 */
public abstract class ConstBlock extends TranslatorBlock
{
	private String code;

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    protected ConstBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
		this.code = "undefined";
	}

    /**
     *
     * @param code
     */
    protected void setCode(String code)
	{
		this.code = code;
	}
	
    /**
     *
     * @return
     */
    @Override
	public String toCode()
	{
		return codePrefix + code + codeSuffix;
	}

}
