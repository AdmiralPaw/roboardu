package com.ardublock.translator.block.roboarduBlock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

import java.util.ResourceBundle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class ToneImpulse extends TranslatorBlock {
    private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

    /**
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public ToneImpulse(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
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
        //TODO может засунуть строку ошибки в базу данных?
        TranslatorBlock freqBlock = this.getRequiredTranslatorBlockAtSocket(1);
        int leftLimit = 100;
        int rightLimit = 4000;
        if ((Integer.parseInt(freqBlock.toCode()) < leftLimit) || (Integer.parseInt(freqBlock.toCode()) > rightLimit)) {
            throw new BlockException(freqBlock.getBlockID(), "[Ошибка в значении] Частота может быть только от "
                    + leftLimit + " до " + rightLimit);
        }
        TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(2);
        leftLimit = 10;
        rightLimit = 10000;
        String TimeON = tb.toCode();
        if ((Integer.parseInt(TimeON) < leftLimit) || (Integer.parseInt(TimeON) > rightLimit)) {
            throw new BlockException(tb.getBlockID(), "[Ошибка в значении] Время издавания звука может быть только от "
                    + leftLimit + " до " + rightLimit);
        }
        tb = this.getRequiredTranslatorBlockAtSocket(3);
        leftLimit = 10;
        rightLimit = 10000;
        String TimeOFF = tb.toCode();
        if ((Integer.parseInt(TimeOFF) < leftLimit) || (Integer.parseInt(TimeOFF) > rightLimit)) {
            throw new BlockException(tb.getBlockID(), "[Ошибка в значении] Время паузы звука может быть только от "
                    + leftLimit + " до " + rightLimit);
        }
        tb = this.getRequiredTranslatorBlockAtSocket(4);
        leftLimit = 1;
        rightLimit = 100;
        String Count = tb.toCode();
        if ((Integer.parseInt(Count) < leftLimit) || (Integer.parseInt(Count) > rightLimit)) {
            throw new BlockException(tb.getBlockID(), "[Ошибка в значении] Количество повторений может быть только от "
                    + leftLimit + " до " + rightLimit);
        }
        translator.LoadTranslators(this.getClass().getSimpleName());

        String ret = "PiezoBeep(" + pinBlock.toCode() + ", " + freqBlock.toCode() + ", " + TimeON + ", " + TimeOFF + ", " + Count + ");\n";
        return ret;
    }
}
