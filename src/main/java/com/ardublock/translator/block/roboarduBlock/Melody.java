package com.ardublock.translator.block.roboarduBlock;

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
public class Melody extends TranslatorBlock {

    private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public Melody (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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

        TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
        String pin = tb.toCode();
        if(!("A0 A1 A2 A3 8 9 10 11 12").contains(pin.trim())) {
            throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.Digital_pin_slot"));
        }

        translator.LoadTranslators(this.getClass().getSimpleName());
        String ret = "PiezoAlarm(" + pin + ");\n";

        return codePrefix + ret + codeSuffix;
    }

}
