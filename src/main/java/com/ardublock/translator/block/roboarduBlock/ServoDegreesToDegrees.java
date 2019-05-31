package com.ardublock.translator.block.roboarduBlock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

import java.util.ResourceBundle;

public class ServoDegreesToDegrees extends TranslatorBlock {

    private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");

    public static final String SERVO_DEGREES_TO_DEGREES = "void SetServoPos(Servo ServoMotor, int Pos, int Speed)\n" +
            "{\n" +
            "  int PosOld = ServoMotor.read();\n" +
            "\n" +
            "  if(Speed > 100) Speed = 100;\n" +
            "  \n" +
            "  if(Pos - PosOld > 0)\n" +
            "  {\n" +
            "    for(int i = PosOld; i < Pos; i++)\n" +
            "    {\n" +
            "      ServoMotor.write(i);\n" +
            "      delay(Speed);\n" +
            "    }\n" +
            "  }\n" +
            "  else\n" +
            "  {\n" +
            "    for(int i = PosOld; i > Pos; i--)\n" +
            "    {\n" +
            "      ServoMotor.write(i);\n" +
            "      delay(Speed);\n" +
            "    }    \n" +
            "  }\n" +
            "}";

    public ServoDegreesToDegrees(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
    {
        super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() throws SocketNullException, SubroutineNotDeclaredException
    {
        TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);

        String servoSpecs = "";

        String pinNumber = tb.toCode();

        //****** Bit long w but easy to see what's happening. Any other invalid pins? *********
        if ( ! ("2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32/"
                + " 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53").contains(pinNumber.trim()) )
        {
            throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.Digital_pin_slot"));
        }

        String servoName = "servo_pin_" + pinNumber;

        translator.addHeaderFile("Servo.h");
        translator.addDefinitionCommand("Servo " + servoName + ";");
        translator.addDefinitionCommand(SERVO_DEGREES_TO_DEGREES);
        translator.addSetupCommand(servoName + ".attach(" + pinNumber + servoSpecs + ");");

        tb = this.getRequiredTranslatorBlockAtSocket(1);
        String start_angle = tb.toCode();
        tb = this.getRequiredTranslatorBlockAtSocket(2);
        String end_angle = tb.toCode();
        tb = this.getRequiredTranslatorBlockAtSocket(3);
        String time = tb.toCode();
        String ret = servoName + ".write(" + start_angle + ");\n" + "SetServoPos(" + servoName + ", " + end_angle + ", " + time + ");\n";

        return codePrefix + ret + codeSuffix;
    }

}
