package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class VariableDigitalBlock extends TranslatorBlock
{
	public VariableDigitalBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
	       super(blockId, translator, codePrefix, codeSuffix, label);
    }

    @Override
    public String toCode() {

        if (label.chars().anyMatch(Character::isLetter)) {
            String internalVariableName = translator.getBooleanVariable(label);
            if (internalVariableName == null) {
                internalVariableName = translator.buildVariableName(label);
                translator.addBooleanVariable(label, internalVariableName);
                translator.addDefinitionCommand("bool " + internalVariableName + "= false ;");
            }
            String ret = internalVariableName;
            return codePrefix + ret + codeSuffix;
        }
        return codePrefix + label + codeSuffix;
    }

}
