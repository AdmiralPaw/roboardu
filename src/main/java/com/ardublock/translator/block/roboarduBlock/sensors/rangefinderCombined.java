package com.ardublock.translator.block.roboarduBlock.sensors;

/*import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class EncoderSimple2 extends TranslatorBlock {
    public EncoderSimple2(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
        String EncoderSimple2 = translator.getNumberVariable("EncoderSimple2");


        if (EncoderSimple2 == null) {
            EncoderSimple2 = translator.buildVariableName("EncoderSimple");
            translator.addNumberVariable("EncoderSimple2", EncoderSimple2);
            translator.addDefinitionCommand("unsigned long long " + EncoderSimple2 + " = 0 ;");
        }

        if (translator.getNumberVariable("EncoderFull2") == null) {
            translator.addDefinitionCommand("\nvoid Encoder_FUNCTION2(){\n" +
                    EncoderSimple2 + "++;\n" +
                    "}\n");
            translator.addSetupCommand("attachInterrupt(3, Encoder_FUNCTION2, RISING);\n");
        }


        return codePrefix + EncoderSimple2 + codeSuffix;
    }
}*/

