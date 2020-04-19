package com.ardublock.translator.block.roboarduBlock.encoder;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class EncoderSimple2 extends TranslatorBlock{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public EncoderSimple2 (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    private static final String ENCODER_DEFINE_PIN = ""
            + "#define ENCODER_PIN_1             2\n"
            + "#define ENCODER_PIN_2             3\n";
    private static final String ENCODER_DEFINE_SWITCH = ""
            + "#define ON                        1\n"
            + "#define OFF                       0\n";
    private static final String ENCODER_DEFINE_VAR = ""
            + "unsigned long long nEncoder1, nEncoder2;\n";
    private static final String ENCODER_DEFINE = ""
            + "void InitEnc(int fEn)\n"
            + "{\n"
            + "  if(fEn == ON)\n"
            + "  {\n"
            + "    attachInterrupt(digitalPinToInterrupt(ENCODER_PIN_1), Encoder1, CHANGE);\n"
            + "    attachInterrupt(digitalPinToInterrupt(ENCODER_PIN_2), Encoder2, CHANGE);\n"
            + "  }\n"
            + "  if(fEn == OFF)\n"
            + "  {\n"
            + "    detachInterrupt(digitalPinToInterrupt(ENCODER_PIN_1));\n"
            + "    detachInterrupt(digitalPinToInterrupt(ENCODER_PIN_2));\n"
            + "  }\n"
            + "}\n"
            + "\n"
            + "void Encoder1()\n"
            + "{\n"
            + "  nEncoder1++;\n"
            + "}\n"
            + "\n"
            + "void Encoder2()\n"
            + "{\n"
            + "  nEncoder2++;\n"
            + "}\n";

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        String nEncoder2="nEncoder2";


        translator.addHeaderDefinition(ENCODER_DEFINE_PIN);
        translator.addHeaderDefinition(ENCODER_DEFINE_SWITCH);
        translator.addHeaderDefinition(ENCODER_DEFINE_VAR);
        translator.addDefinitionCommand(ENCODER_DEFINE);
        translator.addSetupCommand("InitEnc(ON);");


        return codePrefix + nEncoder2 + codeSuffix;
    }
}
