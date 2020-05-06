package com.ardublock.translator.block.tinker;

import com.ardublock.translator.Translator;

/**
 *
 * @author User
 */
public class TinkerTouchBlock extends AbstractTinkerReadDigitalBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public TinkerTouchBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

}
