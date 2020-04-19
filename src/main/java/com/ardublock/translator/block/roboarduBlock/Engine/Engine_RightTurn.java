package com.ardublock.translator.block.roboarduBlock.Engine;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class Engine_RightTurn extends TranslatorBlock {

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public Engine_RightTurn(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }
    
    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
        translator.CheckClassName(this);

        translator.addSetupCommand("InitMotors();");
        
        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        String val = translatorBlock.toCode();
        if (Double.parseDouble(val) > 100 || Double.parseDouble(val) < -100) {
            throw new BlockException(translatorBlock.getBlockID(), "ARGUMENT_ERROR");
        };
        String ret = "MoveRight(" + translatorBlock.toCode() + ");";
        return codePrefix + ret + codeSuffix;
    }
}
