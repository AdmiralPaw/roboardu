package com.ardublock.translator.block.roboarduBlock.Engine;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class Engine_ForwardDistance extends TranslatorBlock {

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public Engine_ForwardDistance(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
        translator.CheckClassName(this);
//        translator.addHeaderDefinition(MOTORS_DEFINE_PIN);
//        translator.addHeaderDefinition(ENCODER_DEFINE_CONST);
//        translator.addHeaderDefinition(ENCODER_DEFINE_VARS);
//
//        translator.addDefinitionCommand(ENCODER_DEFINE_INIT);
//        translator.addDefinitionCommand(ENCODER_DEFINE_INC);
//        translator.addDefinitionCommand(ENCODER_DEFINE_RESET);
//        translator.addDefinitionCommand(ENCODER_DEFINE_GET_DISTANCE);
//        translator.addDefinitionCommand(MOTORS_DEFINE_INIT);
//        translator.addDefinitionCommand(MOTORS_DEFINE_MOTORS);
//        translator.addDefinitionCommand(MOTORS_DEFINE_FORWARD);
//        translator.addDefinitionCommand(MOTORS_DEFINE_STOP);
//        translator.addDefinitionCommand(MOTORS_DEFINE_FORWARD_DISTANCE);

        translator.addSetupCommand("InitEnc();");
        translator.addSetupCommand("InitMotors();");

        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        String val = translatorBlock.toCode();
        if (Double.parseDouble(val) > 100 || Double.parseDouble(val) < -100) {
            throw new BlockException(translatorBlock.getBlockID(), "ARGUMENT_ERROR");
        };
        String ret = "MoveForwardByEncoder(" + translatorBlock.toCode() + ", ";
        translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
        val = translatorBlock.toCode();
        try {
            Integer.parseInt(val);
        } catch (NumberFormatException e) {
            throw new BlockException(translatorBlock.getBlockID(), "ARGUMENT_MUST_BE_INTEGER");
        }
        ret = ret + translatorBlock.toCode() + ");";
        return codePrefix + ret + codeSuffix;
    }
}
