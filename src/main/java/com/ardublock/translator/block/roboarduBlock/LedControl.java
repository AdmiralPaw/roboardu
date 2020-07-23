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
public class LedControl extends TranslatorBlock {
    
    private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public LedControl(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
        translator.LoadTranslators(this.getClass().getSimpleName());
        TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
        String LedPin = tb.toCode();
        //TODO цифровые порты тоже нужны
        if(!("8 9 10 11 A0 A1 A2 A3 13").contains(LedPin.trim())) {
            throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.Digital_pin_slot"));
        }
        tb = this.getRequiredTranslatorBlockAtSocket(1);
        String led_state = tb.toCode();
        if(!led_state.equals("HIGH") && !led_state.equals("LOW")){
            throw new BlockException(tb.getBlockID(), "Значение должно быть высокий или низкий");
        }

        translator.addSetupCommand("pinMode(" + LedPin + ", OUTPUT);");

        String ret ="LedControl(" + LedPin +", " + led_state + ");\n";
        return codePrefix + ret + codeSuffix;
    }

}