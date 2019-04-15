package com.ardublock.translator.block.roboarduBlock.orientation;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class AccelerometerModule extends TranslatorBlock {
    public AccelerometerModule (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        String Accel="Accel";
        translator.addHeaderFile("I2Cdev.h");
        translator.addHeaderFile("MPU6050.h");


        translator.addDefinitionCommand("MPU6050 "+Accel+";");
        translator.addSetupCommand(Accel+".initialize();\ndelay(100);");


        return codePrefix + "sqrt("+ Accel + ".getAccelerationX()"+
                Accel + ".getAccelerationY()"+ 
                Accel + ".getAccelerationZ()" + ")" + codeSuffix;
    }
}
