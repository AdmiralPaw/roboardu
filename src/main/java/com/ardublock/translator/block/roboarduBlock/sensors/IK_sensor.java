/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ardublock.translator.block.roboarduBlock.sensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class IK_sensor extends TranslatorBlock
{

    /**
     *
     */
    public static final String PINMODE_INPUT = "void pinmode_input(int pin){\n"
            + "  pinMode(pin, INPUT);\n"
            + "}";

    /**
     *
     */
    public static final String GET_IJ_DISTANCE_FUNC_DEFINE
            = "double get_IK_distance(int pin){\n"
            + "  double temp = 65*pow((analogRead(pin)*0.0048828125),-1.10);\n"
            + "  return temp;\n"
            + "}";

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public IK_sensor(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        String pin = translatorBlock.toCode();
        translator.addDefinitionCommand(GET_IJ_DISTANCE_FUNC_DEFINE);
        translator.addDefinitionCommand(PINMODE_INPUT);
        
        translator.addSetupCommand("pinmode_input(" + pin + ");");

        String ret = "get_IK_distance(";
        ret = ret + pin + ")";
        return codePrefix + ret + codeSuffix;
    }
}