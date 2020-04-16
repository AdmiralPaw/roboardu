package com.ardublock.translator.block.tinker;

import com.ardublock.translator.Translator;

/**
 *
 * @author User
 */
public class TinkerRelayBlock extends AbstractTinkerWriteDigitalBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public TinkerRelayBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

}
