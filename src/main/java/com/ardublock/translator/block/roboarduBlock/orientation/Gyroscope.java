package com.ardublock.translator.block.roboarduBlock.orientation;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Gyroscope extends TranslatorBlock {
    public Gyroscope (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        String Accel="Accel";
        String GyroX;
        String GyroY;
        String GyroZ;
        translator.addHeaderFile("I2Cdev.h");
        translator.addHeaderFile("MPU6050.h");

        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        GyroX=translatorBlock.toCode();
        translatorBlock=this.getRequiredTranslatorBlockAtSocket(1);
        GyroY=translatorBlock.toCode();
        translatorBlock=this.getRequiredTranslatorBlockAtSocket(2);
        GyroZ=translatorBlock.toCode();

        translator.addDefinitionCommand("MPU6050 "+Accel+";");
        translator.addSetupCommand(Accel+".initialize();\ndelay(100);");


        return codePrefix + Accel + ".getRotation(&" + GyroX + ", &" + GyroY + ", &" + GyroZ + ");\n" + codeSuffix;
    }
}
