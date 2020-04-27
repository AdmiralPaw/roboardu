package com.ardublock.translator.block.roboarduBlock.sensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class TouchSensor extends TranslatorBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public TouchSensor(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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

        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        String ret = "TouchSensorRead(" + translatorBlock.toCode() + ")";

        return codePrefix + ret + codeSuffix;
    }
}
