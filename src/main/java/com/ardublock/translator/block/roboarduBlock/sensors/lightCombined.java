package com.ardublock.translator.block.roboarduBlock.sensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;

public class lightCombined extends TranslatorBlock {
    public lightCombined(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() {
        String sensor = "sensorCombined";

        translator.addHeaderDefinition("Wire.h");
        translator.addHeaderDefinition("SparkFun_VL6180X.h");
        translator.addDefinitionCommand("#define VL6180X_ADDRESS 0x29\n" +
                "\n" +
                "VL6180xIdentification identification;\n" +
                "VL6180x " + sensor + "(VL6180X_ADDRESS);\n'\n");
        translator.addHeaderDefinition("const float GAIN_1    = 1.01;");
        translator.addSetupCommand("Wire.begin();\n" +
                "if(" + sensor + ".VL6180xInit() != 0){\n" +
                "    Serial.println(\"FAILED TO INITALIZE\");\n" +
                "  }; \n" +
                "\n" +
                "  " + sensor + ".VL6180xDefautSettings();\n" +
                "  \n" +
                "    delay(1000);");



        return codePrefix + sensor + ".getAmbientLight(GAIN_1)" + codeSuffix;
    }
}
