package com.ardublock.translator.block.roboarduBlock.Engine;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import java.util.ResourceBundle;

/**
 *
 * @author User
 */
public class Engine_Stop extends TranslatorBlock {

    
    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public Engine_Stop(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
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
        
        String ret = "Stop();";
        return codePrefix + ret + codeSuffix;
    }
}
