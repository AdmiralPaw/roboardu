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
            String EncoderSimple=translator.getNumberVariable("EncoderSimple");
            String EncoderFull=translator.getNumberVariable("EncoderFull");

            if (EncoderFull == null)
            {
                EncoderFull = translator.buildVariableName("EncoderFull");
                translator.addNumberVariable("EncoderFull", EncoderFull);
                translator.addDefinitionCommand("int " + EncoderFull + " = 0 ;");
            }
            if (EncoderSimple == null)
            {
                EncoderSimple = translator.buildVariableName("EncoderSimple");
                translator.addNumberVariable("EncoderSimple", EncoderSimple);
                translator.addDefinitionCommand("int " + EncoderSimple + " = 0 ;");
            }

            translator.addDefinitionCommand("\nvoid Encoder_FUNCTION(){\n" +
                EncoderSimple+"++;\n"+
                EncoderFull+"="+EncoderSimple+"/20;\n"+
                "}\n");
        translator.addSetupCommand("attachInterrupt(2, Encoder_FUNCTION, RISING);\n");


        return codePrefix + EncoderFull + codeSuffix;
    }
}
