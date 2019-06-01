package com.ardublock.translator.block.roboarduBlock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ObstacleSensor extends TranslatorBlock {


    public ObstacleSensor (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {


        TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
        String pin = tb.toCode();

        String ret = "analogRead(" + pin + ")";

        return codePrefix + ret + codeSuffix;
    }
}
