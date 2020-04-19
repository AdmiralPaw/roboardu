package com.ardublock.translator.block.tinker;

import com.ardublock.translator.Translator;

/**
 *
 * @author User
 */
public class TinkerThermistorBlock extends AbstractTinkerReadAnalogBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public TinkerThermistorBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

}
