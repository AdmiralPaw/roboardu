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
public class BlinkLed extends TranslatorBlock {

    private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public BlinkLed(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
        if(!("8 9 10 11 A0 A1 A2 A3 13").contains(LedPin.trim())) {
            throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.Digital_pin_slot"));
        }
        tb = this.getRequiredTranslatorBlockAtSocket(1);
        String Count = tb.toCode();
        try{
            checkValueInt(Count,tb.getBlockID(),uiMessageBundle.getString("ardublock.error_msg.must_be_int"));
        }   catch(NumberFormatException e){}
        tb = this.getRequiredTranslatorBlockAtSocket(2);
        String TimeON = tb.toCode();
        try{
            checkValueInt(TimeON,tb.getBlockID(),uiMessageBundle.getString("ardublock.error_msg.must_be_int"));
            if((Integer.parseInt(TimeON)<1) || (Integer.parseInt(TimeON)>10000)){
                throw new BlockException(tb.getBlockID(), "Диапазон допустимых значений [1; 10000]");
            }
        }   catch(NumberFormatException e){}
        tb = this.getRequiredTranslatorBlockAtSocket(3);
        String TimeOFF = tb.toCode();
        try{
            checkValueInt(TimeOFF,tb.getBlockID(),uiMessageBundle.getString("ardublock.error_msg.must_be_int"));       
            if((Integer.parseInt(TimeOFF)<1) || (Integer.parseInt(TimeOFF)>10000)){
                throw new BlockException(tb.getBlockID(), "Диапазон допустимых значений [1; 10000]");
            }
        }   catch(NumberFormatException e){}
        translator.LoadTranslators(this.getClass().getSimpleName());
        translator.addSetupCommand("pinMode(" + LedPin + ", OUTPUT);");
        String ret ="LedBlinks(" + LedPin +", " + Count + ", " + TimeON +", " + TimeOFF + ");\n";

        return codePrefix + ret + codeSuffix;
    }

}

