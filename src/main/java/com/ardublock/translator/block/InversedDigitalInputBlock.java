package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

/**
 *
 * @author User
 */
public class InversedDigitalInputBlock extends DigitalInputBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public InversedDigitalInputBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

}
