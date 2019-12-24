package com.ardublock.translator.block.roboarduBlock.sensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Ultrasonic extends TranslatorBlock {

    public Ultrasonic(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

        String sensor = "Ultrasonic";

        translator.addHeaderFile("NewPing.h");
        translator.addDefinitionCommand("#define TRIGGER_PIN  A4\n"
                + "#define ECHO_PIN     A5\n"
                + "#define MAX_DISTANCE 200"
                + "NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);\n\n");

        return codePrefix + "sonar.ping_cm()" + codeSuffix;
    }
}
