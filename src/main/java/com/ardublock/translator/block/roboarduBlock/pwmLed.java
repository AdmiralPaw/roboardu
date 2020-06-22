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
public class pwmLed extends TranslatorBlock {

    private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public pwmLed(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
        String LedPin = tb.toCode();
        if(!("A0 A1 A2 A3 13").contains(LedPin.trim())) {
            throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.Digital_pin_slot"));
        }
        if(LedPin.equals("13")){
            translator.addSetupCommand("pinMode("+LedPin+", OUTPUT);");
        }
        tb = this.getRequiredTranslatorBlockAtSocket(1);
        String Bright = tb.toCode();
        if((Integer.parseInt(Bright)<0) || (Integer.parseInt(Bright)>255)){
            throw new BlockException(tb.getBlockID(), "Диапазон допустимых значений [0; 255]");
        }
        translator.LoadTranslators(this.getClass().getSimpleName());

        String ret ="LedBrightness(" + LedPin +", " + Bright + ");\n";

        return codePrefix + ret + codeSuffix;
    }

}