package com.ardublock.translator.adaptor;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;

public interface BlockAdaptor 
{
	 TranslatorBlock nextTranslatorBlock(Translator translator, Long blockId, String codePrefix, String codeSuffix);
	 TranslatorBlock getTranslatorBlockAtSocket(Translator translator, Long blockId, int i, String codePrefix, String codeSuffix);
}
