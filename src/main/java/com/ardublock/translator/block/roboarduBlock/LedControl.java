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
        String pinNumber = tb.toCode();
        if(!("A0 A1 A2 A3/").contains(pinNumber.trim())) {
            throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.Digital_pin_slot"));
        }
        tb = this.getRequiredTranslatorBlockAtSocket(1);
        String led_state = tb.toCode();
        if(Integer.parseInt(led_state)!=0 && Integer.parseInt(led_state)!=1){
            throw new BlockException(tb.getBlockID(), "ARGUMENT_ERROR");
        }
        translator.addSetupCommand("InitBoard();");
        //TODO add check for D13 pin in setup
        String ret ="LedControl(" + pinNumber +", " + led_state + ");\n";

        return codePrefix + ret + codeSuffix;
    }

}