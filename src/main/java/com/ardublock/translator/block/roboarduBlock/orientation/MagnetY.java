/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ardublock.translator.block.roboarduBlock.orientation;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class MagnetY extends TranslatorBlock {

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public MagnetY(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    /**
     *
     */
    public static final String MAGNET_FUNC = "float callMagnetY(MPU9250 &Accel){\n"
            + "  Accel.readSensor();\n"
            + "  return Accel.getMagY_uT();\n"
            + "}";

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
        String Accel = "Accel";
        translator.addHeaderFile("MPU9250.h");
        
        translator.addDefinitionCommand(MAGNET_FUNC);

        translator.addDefinitionCommand("MPU9250 " + Accel + "(Wire, 0x68);");
        translator.addSetupCommand(Accel + ".begin();\ndelay(100);"
                + "  " + Accel + ".setAccelRange(MPU9250::ACCEL_RANGE_8G);\n"
                + "  " + Accel + ".setGyroRange(MPU9250::GYRO_RANGE_500DPS);\n"
                + "  " + Accel + ".setDlpfBandwidth(MPU9250::DLPF_BANDWIDTH_20HZ);\n"
                + "  " + Accel + ".setSrd(19);\n");

        return codePrefix + "callMagnetY(Accel)" + codeSuffix;
    }
}