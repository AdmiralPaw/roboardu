package com.ardublock.translator.block;

import java.util.ResourceBundle;
import com.ardublock.translator.Translator;

import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class ServoDefaultBlock extends TranslatorBlock {

    private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public ServoDefaultBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    /**
     *
     * @return @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
        TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);

        String servoSpecs = "";

        String pinNumber = tb.toCode();
        if (!("2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32/"
                + " 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53").contains(pinNumber.trim())) {
            throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.Digital_pin_slot"));
        }
        String servoName = "servo_pin_" + pinNumber;

        tb = this.getRequiredTranslatorBlockAtSocket(1);
        String val = tb.toCode();
        if (Integer.parseInt(val) > 180 || Integer.parseInt(val) < 0) {
            throw new BlockException(tb.getBlockID(), uiMessageBundle.getString("ardublock.error_msg.Argument_error"));
        };
        String ret = "ServoMove(" + servoName + "," + tb.toCode() + ");";
        translator.LoadTranslators(this.getClass().getSimpleName());
        translator.addHeaderDefinition("Servo " + servoName + ";");
        translator.addSetupCommand(servoName + ".attach(" + pinNumber + servoSpecs + ");");
        return ret;
    }

}
