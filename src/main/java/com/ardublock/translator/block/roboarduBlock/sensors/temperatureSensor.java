package com.ardublock.translator.block.roboarduBlock.sensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class temperatureSensor extends TranslatorBlock {
    public temperatureSensor(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        String pin = translatorBlock.toCode();
        String tempSens = "tempSens";
        translator.addHeaderFile("OneWire.h");
        translator.addDefinitionCommand("OneWire " + tempSens + "(" + pin + ");");

        translator.addDefinitionCommand("double catchTemperature(OneWire &oneW){\n"
                + "  byte data[2];\n"
                + "\n"
                + "  oneW.reset();\n"
                + "  oneW.write(0xCC);\n"
                + "  oneW.write(0x44);\n"
                + "  \n"
                + "  delay(1000);\n"
                + "  \n"
                + "  oneW.reset();\n"
                + "  oneW.write(0xCC);\n"
                + "  oneW.write(0xBE);\n"
                + "\n"
                + "  \n"
                + "  data[0] = oneW.read();\n"
                + "  data[1] = oneW.read();\n"
                + "\n"
                + "  return ((data[1] << 8) | data[0]) * 0.0625;\n"
                + "}");



        return codePrefix + "catchTemperature(" + tempSens + ")" + codeSuffix;
    }
}
