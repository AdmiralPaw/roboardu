package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class VariableNumberUnsignedLongBlock extends TranslatorBlock
{
  public VariableNumberUnsignedLongBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
  {
    super(blockId, translator, codePrefix, codeSuffix, label);
  }

  @Override
  public String toCode()
  {
      
        if (label.chars().anyMatch(Character::isLetter)) {
            String internalVariableName = translator.getNumberVariable(label);
            if (internalVariableName == null) {
                internalVariableName = translator.buildVariableName(label);
                translator.addNumberVariable(label, internalVariableName);
                translator.addDefinitionCommand("unsigned long " + internalVariableName + " = 0UL ;");
            }
            return codePrefix + internalVariableName + codeSuffix;
        }
        return codePrefix + label + "UL" + codeSuffix;
    }

}
