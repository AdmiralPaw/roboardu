package com.ardublock.translator.block.roboarduBlock.sensors;

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
public class Button extends TranslatorBlock
{
    private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public Button(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
        translator.CheckClassName(this);

        TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
        String ButtonPin = tb.toCode();
        if(ButtonPin.trim().equals("13")) {
            throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.Digital_pin_slot"));
        }
        if(ButtonPin.trim().equals("12")) {
            translator.addSetupCommand("pinMode(" + ButtonPin + ", INPUT_PULLUP);");
        }

        String ret = "ButtonRead(" + tb.toCode() + ")";

        return codePrefix + ret + codeSuffix;
    }
}
