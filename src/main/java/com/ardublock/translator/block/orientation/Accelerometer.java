package com.ardublock.translator.block.orientation;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Accelerometer extends TranslatorBlock
{
    public Accelerometer (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        String Accel="Accel";
        String AccX;
        String AccY;
        String AccZ;
        translator.addHeaderFile("I2Cdev.h");
        translator.addHeaderFile("MPU6050.h");

        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        AccX=translatorBlock.toCode();
        translatorBlock=this.getRequiredTranslatorBlockAtSocket(1);
        AccY=translatorBlock.toCode();
        translatorBlock=this.getRequiredTranslatorBlockAtSocket(2);
        AccZ=translatorBlock.toCode();

        translator.addDefinitionCommand("MPU6050 "+Accel+";");
        translator.addSetupCommand(Accel+".initialize();\ndelay(100);");


        return codePrefix + Accel + ".getAcceleration(" + AccX + ", " + AccY + ", " + AccZ + ")\n" + codeSuffix;
    }
}
