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
public class Engine_ForwardDistance extends TranslatorBlock {

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public Engine_ForwardDistance(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    private static final String MOTORS_DEFINE_PIN
            = "//Для работы с моторами\n"
            + "#define M1_DIR          4\n"
            + "#define M1_PWM          5\n"
            + "#define M2_DIR          7\n"
            + "#define M2_PWM          6";
    private static final String ENCODER_DEFINE_CONST
            = "#define ENCODER_K_DIST  1";
    private static final String ENCODER_DEFINE_VARS
            = "unsigned long EncoderCount1, EncoderCount2;\n";
    private static final String ENCODER_DEFINE_INIT
            = "\n"
            + "void InitEnc()\n"
            + "{\n"
            + "  pinMode(2, INPUT_PULLUP);\n"
            + "  pinMode(3, INPUT_PULLUP);  \n"
            + "  attachInterrupt(0, nEncoder1, CHANGE);\n"
            + "  attachInterrupt(1, nEncoder2, CHANGE);\n"
            + "}\n";
    private static final String ENCODER_DEFINE_INC
            = "\n"
            + "void nEncoder1()\n"
            + "{\n"
            + "  EncoderCount1++;\n"
            + "}\n"
            + "\n"
            + "void nEncoder2()\n"
            + "{\n"
            + "  EncoderCount2++;\n"
            + "}"
            + "\n";
    private static final String ENCODER_DEFINE_RESET
            = "\n"
            + "void Encoder1ToNull()\n"
            + "{\n"
            + "  EncoderCount1 = 0;\n"
            + "}\n"
            + "\n"
            + "void Encoder2ToNull()\n"
            + "{\n"
            + "  EncoderCount2 = 0;\n"
            + "}\n"
            + "\n"
            + "void EncodersToNull()\n"
            + "{\n"
            + "  EncoderCount1 = 0;\n"
            + "  EncoderCount2 = 0;\n"
            + "}"
            + "\n";
    private static final String ENCODER_DEFINE_GET_DISTANCE
            = "\n"
            + "unsigned  long GetDistanceFromEnc1()\n"
            + "{\n"
            + "  return (double)EncoderCount1 * (double)ENCODER_K_DIST;\n"
            + "}\n"
            + "\n"
            + "unsigned  long GetDistanceFromEnc2()\n"
            + "{\n"
            + "  return (double)EncoderCount2 * (double)ENCODER_K_DIST;\n"
            + "}"
            + "\n";
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
    private static final String MOTORS_DEFINE_FORWARD
            = "\n"
            + "//Ехать вперед\n"
            + "void MoveForward(int Speed)\n"
            + "{\n"
            + "  Motors(Speed, Speed);\n"
            + "}\n";
    private static final String MOTORS_DEFINE_STOP = ""
            + "\n"
            + "void Stop()\n"
            + "{\n"
            + "  Motors(0, 0);\n"
            + "}\n";
    private static final String MOTORS_DEFINE_FORWARD_DISTANCE
            = "\n"
            + "void MoveForwardByEncoder(int Speed, unsigned long Distance)\n"
            + "{\n"
            + "  EncodersToNull();\n"
            + "  MoveForward(Speed);\n"
            + "\n"
            + "  while(1)\n"
            + "  {\n"
            + "    if(GetDistanceFromEnc1() > Distance) break;\n"
            + "    if(GetDistanceFromEnc2() > Distance) break;\n"
            + "    Serial.print(\"1\");\n"
            + "  }\n"
            + "  \n"
            + "  Stop();\n"
            + "}\n";

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
        translator.CheckClassName(this);
//        translator.addHeaderDefinition(MOTORS_DEFINE_PIN);
//        translator.addHeaderDefinition(ENCODER_DEFINE_CONST);
//        translator.addHeaderDefinition(ENCODER_DEFINE_VARS);
//
//        translator.addDefinitionCommand(ENCODER_DEFINE_INIT);
//        translator.addDefinitionCommand(ENCODER_DEFINE_INC);
//        translator.addDefinitionCommand(ENCODER_DEFINE_RESET);
//        translator.addDefinitionCommand(ENCODER_DEFINE_GET_DISTANCE);
//        translator.addDefinitionCommand(MOTORS_DEFINE_INIT);
//        translator.addDefinitionCommand(MOTORS_DEFINE_MOTORS);
//        translator.addDefinitionCommand(MOTORS_DEFINE_FORWARD);
//        translator.addDefinitionCommand(MOTORS_DEFINE_STOP);
//        translator.addDefinitionCommand(MOTORS_DEFINE_FORWARD_DISTANCE);

        translator.addSetupCommand("InitEnc();");
        translator.addSetupCommand("InitMotors();");

        TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
        String val = translatorBlock.toCode();
        if (Double.parseDouble(val) > 100 || Double.parseDouble(val) < -100) {
            throw new BlockException(translatorBlock.getBlockID(), "ARGUMENT_ERROR");
        };
        String ret = "MoveForwardByEncoder(" + translatorBlock.toCode() + ", ";
        translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
        val = translatorBlock.toCode();
        try {
            Integer.parseInt(val);
        } catch (NumberFormatException e) {
            throw new BlockException(translatorBlock.getBlockID(), "ARGUMENT_MUST_BE_INTEGER");
        }
        ret = ret + translatorBlock.toCode() + ");";
        return codePrefix + ret + codeSuffix;
    }
}
