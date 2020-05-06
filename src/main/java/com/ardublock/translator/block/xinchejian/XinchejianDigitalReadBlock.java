package com.ardublock.translator.block.xinchejian;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class XinchejianDigitalReadBlock extends TranslatorBlock
{

    /**
     *
     */
    public static final String XINCHEJIAN_DIGITAL_IO_DEFINITION = "#define XINCHEJIAN_MOTORSHIELD_S1_PIN	7\n#define XINCHEJIAN_MOTORSHIELD_S2_PIN	8\n#define XINCHEJIAN_MOTORSHIELD_S3_PIN	12\n#define XINCHEJIAN_MOTORSHIELD_S4_PIN	13\n\n\nbool __ardublock_xinchejian_ms_digitalRead(int pinNumber)\n{\nbool res = false;\nswitch (pinNumber)\n{\ncase (1):\n{\npinMode(XINCHEJIAN_MOTORSHIELD_S1_PIN, INPUT);\nres = digitalRead(XINCHEJIAN_MOTORSHIELD_S1_PIN);\nbreak;\n}\ncase (2):\n{\npinMode(XINCHEJIAN_MOTORSHIELD_S2_PIN, INPUT);\nres = digitalRead(XINCHEJIAN_MOTORSHIELD_S2_PIN);\nbreak;\n}\ncase (3):\n{\npinMode(XINCHEJIAN_MOTORSHIELD_S3_PIN, INPUT);\nres = digitalRead(XINCHEJIAN_MOTORSHIELD_S1_PIN);\nbreak;\n}\ncase (4):\n{\npinMode(XINCHEJIAN_MOTORSHIELD_S4_PIN, INPUT);\nres = digitalRead(XINCHEJIAN_MOTORSHIELD_S1_PIN);\nbreak;\n}\ndefault:\n{\nres = false;\nbreak;\n}\n}\nreturn res;\n}\n\nvoid __ardublock_xinchejian_ms_digitalWrite(int pinNumber, bool status)\n{\nswitch (pinNumber)\n{\ncase (1):\n{\npinMode(XINCHEJIAN_MOTORSHIELD_S1_PIN, OUTPUT);\ndigitalWrite(XINCHEJIAN_MOTORSHIELD_S1_PIN, status);\nbreak;\n}\ncase (2):\n{\npinMode(XINCHEJIAN_MOTORSHIELD_S2_PIN, OUTPUT);\ndigitalWrite(XINCHEJIAN_MOTORSHIELD_S2_PIN, status);\nbreak;\n}\ncase (3):\n{\npinMode(XINCHEJIAN_MOTORSHIELD_S3_PIN, OUTPUT);\ndigitalWrite(XINCHEJIAN_MOTORSHIELD_S3_PIN, status);\nbreak;\n}\ncase (4):\n{\npinMode(XINCHEJIAN_MOTORSHIELD_S4_PIN, OUTPUT);\ndigitalWrite(XINCHEJIAN_MOTORSHIELD_S4_PIN, status);\nbreak;\n}\ndefault:\n{\n\n}\n}\n}\n\n";

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public XinchejianDigitalReadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
		translator.addDefinitionCommand(XINCHEJIAN_DIGITAL_IO_DEFINITION);
		
		String ret = "__ardublock_xinchejian_ms_digitalRead( ";
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + tb.toCode();
		ret = ret +  " )";
		return codePrefix + ret + codeSuffix;
	}
	
}
