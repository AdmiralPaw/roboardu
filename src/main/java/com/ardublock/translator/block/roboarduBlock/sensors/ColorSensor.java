package com.ardublock.translator.block.roboarduBlock.sensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ColorSensor extends TranslatorBlock {


    public ColorSensor(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     * @throws BlockException
     */

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException, BlockException {
//        translator.LoadTranslators(this.getClass().getSimpleName());
        translator.CheckClassName(this);

//        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        translator.addSetupCommand("ColorSensorInit();");
        String ret = "ColorSensorRead();";

        return codePrefix + ret + codeSuffix;
    }
}
