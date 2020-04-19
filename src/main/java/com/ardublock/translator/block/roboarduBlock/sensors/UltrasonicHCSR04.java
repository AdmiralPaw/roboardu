package com.ardublock.translator.block.roboarduBlock.sensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class UltrasonicHCSR04 extends TranslatorBlock {

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public UltrasonicHCSR04(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

        String sensor = "Ultrasonic";

        translator.addHeaderFile("NewPing.h");
        translator.addDefinitionCommand("#define TRIGGER_PIN  A4\n"
                + "#define ECHO_PIN     A5\n"
                + "#define MAX_DISTANCE 80\n"
                + "NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);\n\n");

        return codePrefix + "sonar.ping_cm()" + codeSuffix;
    }
}
