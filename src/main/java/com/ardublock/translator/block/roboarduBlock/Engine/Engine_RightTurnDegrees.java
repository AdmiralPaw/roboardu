package com.ardublock.translator.block.roboarduBlock.Engine;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class Engine_RightTurnDegrees extends TranslatorBlock
{

	public Engine_RightTurnDegrees(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{   
            super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
        private static final String MOTORS_DEFINE_PIN = "" +
                "#define M_DIR_PIN_1               4\n" +
                "#define M_DIR_PIN_2               7\n" +
                "#define M_SPEED_PIN_1             5\n" +
                "#define M_SPEED_PIN_2             6\n";
        private static final String ENCODER_DEFINE_PIN = "" +
                "#define ENCODER_PIN_1             2\n" +
                "#define ENCODER_PIN_2             3\n";
        private static final String ENCODER_DEFINE_SWITCH = "" +
                "#define ON                        1\n" +
                "#define OFF                       0\n";
        private static final String ENCODER_DEFINE_VAR = "" +
                "unsigned long long nEncoder1, nEncoder2;\n";
        private static final String MOTORS_DEFINE_VAR = "" + 
                "int SpeedMotor1, SpeedMotor2;\n";        
        private static final String ENCODER_DEFINE = "" +
                "void InitEnc(int fEn)\n" +
                "{\n" +
                "  if(fEn == ON)\n" +
                "  {\n" +
                "    attachInterrupt(ENCODER_PIN_1, Encoder1, CHANGE);\n" +
                "    attachInterrupt(ENCODER_PIN_2, Encoder2, CHANGE);\n" +
                "  }\n" +
                "  if(fEn == OFF)\n" +
                "  {\n" +
                "    detachInterrupt(ENCODER_PIN_1);\n" +
                "    detachInterrupt(ENCODER_PIN_2);\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "void Encoder1()\n" +
                "{\n" +
                "  nEncoder1++;\n" +
                "}\n" +
                "\n" +
                "void Encoder2()\n" +
                "{\n" +
                "  nEncoder2++;\n" + 
                "}\n";
        private static final String MOTORS_DEFINE = "" +
                "void InitMotors()\n" +
                "{\n" +
                "  pinMode(M_DIR_PIN_1, OUTPUT);\n" +
                "  pinMode(M_DIR_PIN_2, OUTPUT);\n" +
                "  pinMode(M_SPEED_PIN_1, OUTPUT);\n" +
                "  pinMode(M_SPEED_PIN_2, OUTPUT);\n" +
                "\n" +
                "  SpeedMotor1 = 0;\n" +
                "  SpeedMotor2 = 0;\n" +
                "}\n" +
                "\n" +
                "void Motor1(int Speed)\n" +
                "{\n" +
                "  int Dir = 0;\n" +
                "\n" +
                "  if(Speed > 100)  Speed = 100;\n" +
                "  if(Speed < -100)  Speed = -100;\n" +
                "\n" +
                "  map(Speed,-100,100,-255,255);\n" +
                "  if(Speed < 0)\n" +
                "  {\n" +
                "    Dir = 1;\n" +
                "    Speed *= -1;\n" +
                "  }\n" +
                "\n" +
                "  digitalWrite(M_DIR_PIN_1, Dir);\n" +
                "  analogWrite(M_SPEED_PIN_1, Speed);\n" +
                "\n" +
                "  SpeedMotor1 = Speed;\n" +
                "}\n" +
                "\n" +
                "void Motor2(int Speed)\n" +
                "{\n" +
                "  int Dir = 0;\n" +
                "\n" +
                "  if(Speed > 100)  Speed = 100;\n" +
                "  if(Speed < -100)  Speed = -100;\n" +
                "\n" +
                "  map(Speed,-100,100,-255,255);\n" +
                "  if(Speed < 0)\n" +
                "  {\n" +
                "    Dir = 1;\n" +
                "    Speed *= -1;\n" +
                "  }\n" +
                "\n" +
                "  digitalWrite(M_DIR_PIN_2, Dir);\n" +
                "  analogWrite(M_SPEED_PIN_2, Speed);\n" +
                "\n" +
                "  SpeedMotor2 = Speed;\n" +
                "}\n" +
                "\n" +
                "void Motors(int Speed1, int Speed2)\n" +
                "{\n" +
                "  Motor1(Speed1);\n" +
                "  Motor2(Speed2);\n" +
                "}\n";
        private static final String MOTORS_RIGHT_DEGREES_DEFINE = "" +
                "void MotorsRightDegrees(int Speed, int Deegree)\n" +
                "{\n" +
                "  unsigned long long nEncoder1Start = nEncoder1;\n" +
                "  unsigned long long nEncoder2Start = nEncoder2;\n" +
                "\n" +
                "  unsigned long long TimeStart = millis();\n" +
                "\n" +
                "  Motors(Speed,-Speed);\n" +
                "\n" +
                "  while(nEncoder1 - nEncoder1Start < Deegree*0.3 || nEncoder2 - nEncoder2Start < Deegree*0.3)\n" +
                "  {\n" +
                "    delay(1);\n" +
                "  }\n" +
                "\n" +
                "  MotorsStop();\n" +
                "}\n";

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
            translator.addHeaderDefinition(MOTORS_DEFINE_PIN);
            translator.addHeaderDefinition(ENCODER_DEFINE_PIN);
            translator.addHeaderDefinition(ENCODER_DEFINE_SWITCH);
            translator.addHeaderDefinition(MOTORS_DEFINE_VAR);
            translator.addHeaderDefinition(ENCODER_DEFINE_VAR);
            translator.addDefinitionCommand(ENCODER_DEFINE);
            translator.addDefinitionCommand(MOTORS_DEFINE);
            
            translator.addDefinitionCommand(MOTORS_RIGHT_DEGREES_DEFINE);
            translator.addSetupCommand("InitMotors();");
            translator.addSetupCommand("InitEnc(ON);");
            TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
            String ret = "MotorsRightDegrees(" + translatorBlock.toCode() + ", ";
            translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
            ret = ret + translatorBlock.toCode() + ");";
            return codePrefix + ret + codeSuffix;
        }
}
