package com.ardublock.translator.block.roboarduBlock.encoder;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class EncoderSimple extends TranslatorBlock{
    public EncoderSimple (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        String EncoderSimple=translator.getNumberVariable("EncoderSimple");


        if (EncoderSimple == null)
        {
            EncoderSimple = translator.buildVariableName("EncoderSimple");
            translator.addNumberVariable("EncoderSimple", EncoderSimple);
            translator.addDefinitionCommand("int " + EncoderSimple + " = 0 ;");
        }

        if(translator.getNumberVariable("EncoderFull")==null){
            translator.addDefinitionCommand("\nvoid Encoder_FUNCTION(){\n" +
                    EncoderSimple+"++;\n"+
                    "}\n");
            translator.addSetupCommand("attachInterrupt(2, Encoder_FUNCTION, RISING);\n");
        }


        return codePrefix + EncoderSimple + codeSuffix;
    }
}
