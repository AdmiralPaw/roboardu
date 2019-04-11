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
        String EncoderSimple2=translator.getNumberVariable("EncoderSimple2");
        String EncoderFull2=translator.getNumberVariable("EncoderFull2");

        if (EncoderFull2 == null)
        {
            EncoderFull2 = translator.buildVariableName("EncoderFull2");
            translator.addNumberVariable("EncoderFull2", EncoderFull2);
            translator.addDefinitionCommand("unsigned long long " + EncoderFull2 + " = 0 ;");
        }
        if (EncoderSimple2 == null)
        {
            EncoderSimple2 = translator.buildVariableName("EncoderSimple2");
            translator.addNumberVariable("EncoderSimple2", EncoderSimple2);
            translator.addDefinitionCommand("unsigned long long " + EncoderSimple2 + " = 0 ;");
        }

        translator.addDefinitionCommand("\nvoid Encoder_FUNCTION2(){\n" +
                EncoderSimple2+"++;\n"+
                EncoderFull2+"="+EncoderSimple2+"/20;\n"+
                "}\n");
        translator.addSetupCommand("attachInterrupt(3, Encoder_FUNCTION2, RISING);\n");


        return codePrefix + EncoderFull2 + codeSuffix;
    }
}
