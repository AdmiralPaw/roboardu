package com.ardublock.translator.block.roboarduBlock.encoder;


import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class EncoderFull2 extends TranslatorBlock {
    public EncoderFull2 (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        String nEncoder2=translator.getNumberVariable("nEncoder2");

        if (nEncoder2 == null)
        {
            nEncoder2 = translator.buildVariableName("nEncoder2");
            translator.addNumberVariable("nEncoder2", nEncoder2);
            translator.addDefinitionCommand("unsigned long long " + nEncoder2 + " = 0 ;");
        }

        translator.addDefinitionCommand("\nvoid Encoder2(){\n" +
                nEncoder2+"++;\n"+
                "}\n");
        translator.addSetupCommand("attachInterrupt(3, Encoder2, CHANGE);\n");


        return codePrefix + nEncoder2 + "/40" + codeSuffix;
    }
}