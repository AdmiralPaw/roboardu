package com.ardublock.translator.adaptor;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;

/**
 *
 * @author User
 */
public interface BlockAdaptor 
{

    /**
     *
     * @param translator
     * @param blockId
     * @param codePrefix
     * @param codeSuffix
     * @return
     */
    public TranslatorBlock nextTranslatorBlock(Translator translator, Long blockId, String codePrefix, String codeSuffix);

    /**
     *
     * @param translator
     * @param blockId
     * @param i
     * @param codePrefix
     * @param codeSuffix
     * @return
     */
    public TranslatorBlock getTranslatorBlockAtSocket(Translator translator, Long blockId, int i, String codePrefix, String codeSuffix);
}
