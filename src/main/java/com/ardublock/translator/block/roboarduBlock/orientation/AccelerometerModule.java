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

    public static final String ACCEL_FUNCX = "float callAccelX(MPU9250 &Accel){\n"
            + "  Accel.readSensor();\n"
            + "  return Accel.getAccelX_mss();\n"
            + "}";
    
    public static final String ACCEL_FUNCY = "float callAccelY(MPU9250 &Accel){\n"
            + "  Accel.readSensor();\n"
            + "  return Accel.getAccelY_mss();\n"
            + "}";
    
    public static final String ACCEL_FUNCZ = "float callAccelZ(MPU9250 &Accel){\n"
            + "  Accel.readSensor();\n"
            + "  return Accel.getAccelZ_mss();\n"
            + "}";

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
        String Accel = "Accel";
        translator.addHeaderFile("MPU9250.h");
        
        translator.addDefinitionCommand(ACCEL_FUNCX);
        translator.addDefinitionCommand(ACCEL_FUNCY);
        translator.addDefinitionCommand(ACCEL_FUNCZ);

        translator.addDefinitionCommand("MPU9250 " + Accel + "(Wire, 0x68);");
        translator.addSetupCommand(Accel + ".begin();\ndelay(100);"
                + "  " + Accel + ".setAccelRange(MPU9250::ACCEL_RANGE_8G);\n"
                + "  " + Accel + ".setGyroRange(MPU9250::GYRO_RANGE_500DPS);\n"
                + "  " + Accel + ".setDlpfBandwidth(MPU9250::DLPF_BANDWIDTH_20HZ);\n"
                + "  " + Accel + ".setSrd(19);\n");

        return codePrefix + "sqrt(" + "pow(callAccelX(Accel),2) + "
                + "pow(callAccelY(Accel),2) + "
                + "pow(callAccelZ(Accel),2))" + codeSuffix;
    }
}
