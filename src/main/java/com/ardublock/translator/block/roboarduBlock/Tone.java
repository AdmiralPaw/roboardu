/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ardublock.translator.block.roboarduBlock;


import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

import java.util.ResourceBundle;

/**
 * @author User
 */
public class Tone extends TranslatorBlock {
    private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

    /**
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public Tone(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    /**
     *
     */

    /**
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
        TranslatorBlock pinBlock = this.getRequiredTranslatorBlockAtSocket(0);
        if (!("A0 A1 A2 A3 8 9 10 11 12").contains(pinBlock.toCode().trim())) {
            throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.Digital_pin_slot"));
        }
        TranslatorBlock freqBlock = this.getRequiredTranslatorBlockAtSocket(1);
        //TODO может засунуть строку ошибки в базу данных?
        int leftLimit = 100;
        int rightLimit = 4000;
        try{
            checkValueInt(freqBlock.toCode(),freqBlock.getBlockID(),uiMessageBundle.getString("ardublock.error_msg.must_be_int"));        
            if ((Integer.parseInt(freqBlock.toCode()) < leftLimit) || (Integer.parseInt(freqBlock.toCode()) > rightLimit)) {
                throw new BlockException(freqBlock.getBlockID(), "Рекомендуемая частота от "
                    + leftLimit + " до " + rightLimit);
            }
        } catch(NumberFormatException e){}
        translator.LoadTranslators(this.getClass().getSimpleName());

        String ret = "PiezoON(" + pinBlock.toCode() + ", " + freqBlock.toCode() + ");\n";
        return ret;
    }
}