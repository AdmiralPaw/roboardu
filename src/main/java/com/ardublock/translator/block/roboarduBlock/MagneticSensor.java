package com.ardublock.translator.block.roboarduBlock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MagneticSensor extends TranslatorBlock {

    public static final String MAGNET_SENSOR= "int magnetSensor(int Pin){\n" +
            "int value = analogRead(Pin);\n" +
            "map(value, 0, 1024, -100, 100);\n" +
            "return value;\n" +
            "}\n";

    public MagneticSensor (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {


        TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
        String pin = tb.toCode();


        translator.addDefinitionCommand(MAGNET_SENSOR);

        String ret ="magnetSensor(" + pin + ")\n";

        return codePrefix + ret + codeSuffix;
    }

}
