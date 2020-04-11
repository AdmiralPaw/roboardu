package com.ardublock.translator.block.roboarduBlock.Engine;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Engine_Back extends TranslatorBlock {

    public Engine_Back(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    private static final String MOTORS_DEFINE_PIN
            = "//Для работы с моторами\n"
            + "#define M1_DIR          4\n"
            + "#define M1_PWM          5\n"
            + "#define M2_DIR          7\n"
            + "#define M2_PWM          6";
    private static final String MOTORS_DEFINE_INIT
            = "\n"
            + "void InitMotors()\n"
            + "{\n"
            + "  pinMode(M1_DIR, OUTPUT);\n"
            + "  pinMode(M1_PWM, OUTPUT);\n"
            + "  pinMode(M2_DIR, OUTPUT);\n"
            + "  pinMode(M2_PWM, OUTPUT);\n"
            + "}\n";
    private static final String MOTORS_DEFINE_MOTORS
            = "\n"
            + "void Motors(int Speed1, int Speed2)\n"
            + "{\n"
            + "  if(Speed1 > 255)  Speed1 = 255;\n"
            + "  if(Speed1 < -255) Speed1 = -255;\n"
            + "  if(Speed2 > 255)  Speed2 = 255;\n"
            + "  if(Speed2 < -255) Speed2 = -255;\n"
            + "\n"
            + "  if(Speed1 > 0)\n"
            + "  {\n"
            + "    digitalWrite(M1_DIR, 1);\n"
            + "    analogWrite(M1_PWM, Speed1);\n"
            + "  }\n"
            + "  else\n"
            + "  {\n"
            + "    digitalWrite(M1_DIR, 0);\n"
            + "    analogWrite(M1_PWM, -Speed1); \n"
            + "  }\n"
            + "  \n"
            + "  if(Speed2 > 0)\n"
            + "  {\n"
            + "    digitalWrite(M2_DIR, 1);\n"
            + "    analogWrite(M2_PWM, Speed2);\n"
            + "  }\n"
            + "  else\n"
            + "  {\n"
            + "    digitalWrite(M2_DIR, 0);\n"
            + "    analogWrite(M2_PWM, -Speed2); \n"
            + "  }\n"
            + "}\n";
    private static final String MOTORS_DEFINE_BACK
            = "\n"
            + "void MotorsBack(int Speed)\n"
            + "{\n"
            + "  Motors(-Speed, -Speed);\n"
            + "}\n";

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
        HashMap<String,ArrayList<String>> CommandNameAndCode = new HashMap<String,ArrayList<String>>();
        ArrayList<String> temp = new ArrayList<String>();
        temp.add("sosi");
        temp.add("her");
        CommandNameAndCode.put("void Motors(int Speed1, int Speed2)", temp);
        translator.LoadTranslators(this.getClass().getSimpleName(),CommandNameAndCode);
//        translator.addHeaderDefinition(MOTORS_DEFINE_PIN);
//        translator.addHeaderDefinition(MOTORS_DEFINE_VAR);
//        translator.addDefinitionCommand(MOTORS_DEFINE);
//        translator.addDefinitionCommand(MOTORS_BACK_DEFINE);
        translator.addSetupCommand("InitMotors();");
        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        String val = translatorBlock.toCode();
        if (Double.parseDouble(val) > 100 || Double.parseDouble(val) < -100) {
            throw new BlockException(translatorBlock.getBlockID(), "ARGUMENT_ERROR");
        };
        String ret = "MoveBack(" + translatorBlock.toCode() + ");";
        return codePrefix + ret + codeSuffix;
    }
}
