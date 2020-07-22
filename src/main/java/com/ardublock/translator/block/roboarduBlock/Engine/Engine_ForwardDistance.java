package com.ardublock.translator.block.roboarduBlock.Engine;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import java.util.ResourceBundle;

/**
 *
 * @author User
 */
public class Engine_ForwardDistance extends TranslatorBlock {

    
    private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

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
        try{
            if (Double.parseDouble(val) > 255 || Double.parseDouble(val) < -255) {
                throw new BlockException(translatorBlock.getBlockID(), uiMessageBundle.getString("ardublock.error_msg.out_of_range").replace("?", -255 +"; "+255));
            };
        } catch(NumberFormatException err){
            
        }
        String ret = "MoveForwardByEncoder(" + translatorBlock.toCode() + ", ";
        translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
        val = translatorBlock.toCode();
        checkValueInt(val,translatorBlock.getBlockID(),uiMessageBundle.getString("ardublock.error_msg.must_be_int"));  
        ret = ret + translatorBlock.toCode() + ");";
        return codePrefix + ret + codeSuffix;
    }
}
