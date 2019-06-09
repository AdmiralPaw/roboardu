package com.ardublock.translator.block.roboarduBlock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Melody extends TranslatorBlock {

    public static final String MELODY= "void melody(int pin)\n" +
            "{\n" +
            "tone(pin, 392, 350);\n" +
            "delay(350);\n" +
            "tone(pin, 392, 350);\n" +
            "delay(350);\n" +
            "tone(pin, 392, 350);\n" +
            "delay(350);\n" +
            "tone(pin, 311, 250);\n" +
            "delay(250);\n" +
            "tone(pin, 466, 100);\n" +
            "delay(100);\n" +
            "tone(pin, 392, 350);\n" +
            "delay(350);\n" +
            "tone(pin, 311, 250);\n" +
            "delay(250);\n" +
            "tone(pin, 466, 100);\n" +
            "delay(100);\n" +
            "tone(pin, 392, 700);\n" +
            "delay(700);\n" +
            "\n" +
            "tone(pin, 587, 350);\n" +
            "delay(350);\n" +
            "tone(pin, 587, 350);\n" +
            "delay(350);\n" +
            "tone(pin, 587, 350);\n" +
            "delay(350);\n" +
            "tone(pin, 622, 250);\n" +
            "delay(250);\n" +
            "tone(pin, 466, 100);\n" +
            "delay(100);\n" +
            "tone(pin, 369, 350);\n" +
            "delay(350);\n" +
            "tone(pin, 311, 250);\n" +
            "delay(250);\n" +
            "tone(pin, 466, 100);\n" +
            "delay(100);\n" +
            "tone(pin, 392, 700);\n" +
            "delay(700);\n" +
            "\n" +
            "tone(pin, 784, 350);\n" +
            "delay(350);\n" +
            "tone(pin, 392, 250);\n" +
            "delay(250);\n" +
            "tone(pin, 392, 100);\n" +
            "delay(100);\n" +
            "tone(pin, 784, 350);\n" +
            "delay(350);\n" +
            "tone(pin, 739, 250);\n" +
            "delay(250);\n" +
            "tone(pin, 698, 100);\n" +
            "delay(100);\n" +
            "tone(pin, 659, 100);\n" +
            "delay(100);\n" +
            "tone(pin, 622, 100);\n" +
            "delay(100);\n" +
            "tone(pin, 659, 450);\n" +
            "delay(450);\n" +
            "\n" +
            "tone(pin, 415, 150);\n" +
            "delay(150);\n" +
            "tone(pin, 554, 350);\n" +
            "delay(350);\n" +
            "tone(pin, 523, 250);\n" +
            "delay(250);\n" +
            "tone(pin, 493, 100);\n" +
            "delay(100);\n" +
            "tone(pin, 466, 100);\n" +
            "delay(100);\n" +
            "tone(pin, 440, 100);\n" +
            "delay(100);\n" +
            "tone(pin, 466, 450);\n" +
            "delay(450);\n" +
            "\n" +
            "tone(pin, 311, 150);\n" +
            "delay(150);\n" +
            "tone(pin, 369, 350);\n" +
            "delay(350);\n" +
            "tone(pin, 311, 250);\n" +
            "delay(250);\n" +
            "tone(pin, 466, 100);\n" +
            "delay(100);\n" +
            "tone(pin, 392, 750);\n" +
            "delay(750);\n" +
            "delay(5000);\n" +
            "}\n";

    public Melody (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {

        TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
        String pin = tb.toCode();


        translator.addDefinitionCommand(MELODY);
        String ret = "melody(" + pin + ");\n";

        return codePrefix + ret + codeSuffix;
    }

}
