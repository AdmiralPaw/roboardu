package com.ardublock.translator.block.roboarduBlock.Engine;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class Engine_RightTurn extends TranslatorBlock
{

	public Engine_RightTurn(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{   
            super(blockId, translator, codePrefix, codeSuffix, label);
	}
	private static final String MOTORS_DEFINE_PIN = "#define M_DIR_PIN_2               4\n" +
"#define M_DIR_PIN_1               7\n" +
"#define M_SPEED_PIN_2             5\n" +
"#define M_SPEED_PIN_1             6";
        private static final String MOTORS_DEFINE_VAR = "" + 
                "int SpeedMotorLeft, SpeedMotorRight;\n";
        
        private static final String MOTORS_DEFINE = "" +
                "void InitMotors()\n" +
                "{\n" +
                "  pinMode(M_DIR_PIN_1, OUTPUT);\n" +
                "  pinMode(M_DIR_PIN_2, OUTPUT);\n" +
                "  pinMode(M_SPEED_PIN_1, OUTPUT);\n" +
                "  pinMode(M_SPEED_PIN_2, OUTPUT);\n" +
                "\n" +
                "  SpeedMotorLeft = 0;\n" +
                "  SpeedMotorRight = 0;\n" +
                "}\n" +
                "\n" +
                "void Motor1Left(int Speed)\n" +
                "{\n" +
                "  int Dir = 0;\n" +
                "\n" +
                "  if(Speed > 100)  Speed = 100;\n" +
                "  if(Speed < -100)  Speed = -100;\n" +
                "\n" +
                "  Speed = map(Speed,-100,100,-255,255);\n" +
                "  if(Speed < 0)\n" +
                "  {\n" +
                "    Dir = 1;\n" +
                "    Speed *= -1;\n" +
                "  }\n" +
                "\n" +
                "  digitalWrite(M_DIR_PIN_1, Dir);\n" +
                "  analogWrite(M_SPEED_PIN_1, Speed);\n" +
                "\n" +
                "  SpeedMotorLeft = Speed;\n" +
                "}\n" +
                "\n" +
                "void Motor2Right(int Speed)\n" +
                "{\n" +
                "  int Dir = 0;\n" +
                "\n" +
                "  if(Speed > 100)  Speed = 100;\n" +
                "  if(Speed < -100)  Speed = -100;\n" +
                "\n" +
                "  Speed = map(Speed,-100,100,-255,255);\n" +
                "  if(Speed < 0)\n" +
                "  {\n" +
                "    Dir = 1;\n" +
                "    Speed *= -1;\n" +
                "  }\n" +
                "\n" +
                "  digitalWrite(M_DIR_PIN_2, Dir);\n" +
                "  analogWrite(M_SPEED_PIN_2, Speed);\n" +
                "\n" +
                "  SpeedMotorRight = Speed;\n" +
                "}\n" +
                "\n" +
                "void Motors(int Speed1Left, int Speed2Right)\n" +
                "{\n" +
                "  Motor1Left(Speed1Left);\n" +
                "  Motor2Right(Speed2Right);\n" +
                "}\n";
        private static final String MOTORS_RIGHT_DEFINE = "" +
                "void MotorsRight(int Speed)\n" +
                "{\n" +
                "  Motors(-Speed, Speed);\n" +
                "}\n";
        
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
            translator.addHeaderDefinition(MOTORS_DEFINE_PIN);
            translator.addHeaderDefinition(MOTORS_DEFINE_VAR);
            translator.addDefinitionCommand(MOTORS_DEFINE);
            translator.addDefinitionCommand(MOTORS_RIGHT_DEFINE);
            translator.addSetupCommand("InitMotors();");
            TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
            String ret = "MotorsRight(" + translatorBlock.toCode() + ");";
            return codePrefix + ret + codeSuffix;
        }
}
