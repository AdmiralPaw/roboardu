package com.ardublock.translator.block.roboarduBlock.orientation;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class AccelGyroMagnet extends TranslatorBlock
{
    public AccelGyroMagnet (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
        String GyroX;
        String GyroY;
        String GyroZ;
        String MagX;
        String MagY;
        String MagZ;
        translator.addHeaderFile("I2Cdev.h");
        translator.addHeaderFile("MPU6050.h");

        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        AccX=translatorBlock.toCode();
        translatorBlock=this.getRequiredTranslatorBlockAtSocket(1);
        AccY=translatorBlock.toCode();
        translatorBlock=this.getRequiredTranslatorBlockAtSocket(2);
        AccZ=translatorBlock.toCode();
        translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
        GyroX=translatorBlock.toCode();
        translatorBlock=this.getRequiredTranslatorBlockAtSocket(4);
        GyroY=translatorBlock.toCode();
        translatorBlock=this.getRequiredTranslatorBlockAtSocket(5);
        GyroZ=translatorBlock.toCode();
        translatorBlock = this.getRequiredTranslatorBlockAtSocket(6);
        MagX=translatorBlock.toCode();
        translatorBlock=this.getRequiredTranslatorBlockAtSocket(7);
        MagY=translatorBlock.toCode();
        translatorBlock=this.getRequiredTranslatorBlockAtSocket(8);
        MagZ=translatorBlock.toCode();

        translator.addDefinitionCommand("MPU6050 "+Accel+";");
        translator.addSetupCommand(Accel+".initialize();\ndelay(100);");


        return codePrefix + Accel + ".getMotion9(&" + AccX + ", &" + AccY + ", &" + AccZ +
                ", &" + GyroX +", &" + GyroY +", &" + GyroZ +
                ", &" + MagX +", &" + MagY +", &" + MagZ + ");\n" + codeSuffix;
    }
}
