package com.ardublock.translator.block.roboarduBlock.encoder;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class EncoderFull extends TranslatorBlock {
    public EncoderFull (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        String nEncoder1=translator.getNumberVariable("nEncoder1");

        if (nEncoder1 == null)
        {
            nEncoder1 = translator.buildVariableName("nEncoder1");
            translator.addNumberVariable("nEncoder1", nEncoder1);
            translator.addDefinitionCommand("unsigned long long " + nEncoder1 + " = 0 ;");
        }

        translator.addDefinitionCommand("\nvoid Encoder1(){\n" +
                nEncoder1+"++;\n"+
                "}\n");
        translator.addSetupCommand("attachInterrupt(2, Encoder1, CHANGE);\n");


        return codePrefix + nEncoder1 + "/40" + codeSuffix;
    }
}