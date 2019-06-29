package com.ardublock.translator.block.roboarduBlock.sensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LineFinder extends TranslatorBlock {
    public LineFinder(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        String pin = translatorBlock.toCode();
        translator.addDefinitionCommand("int LineFinder(int Pin){\n" +
                "  int value = analogRead(Pin);\n" +
                "  value = map(value,0,1024,0,100);\n" +
                "  return value;\n" +
                "}");



        return codePrefix + "LineFinder(" + pin + ")" + codeSuffix;
    }
}
