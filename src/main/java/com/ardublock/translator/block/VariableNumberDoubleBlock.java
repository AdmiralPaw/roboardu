package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

/**
 *
 * @author User
 */
public class VariableNumberDoubleBlock extends TranslatorBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public VariableNumberDoubleBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
  {
    super(blockId, translator, codePrefix, codeSuffix, label);
  }

    /**
     *
     * @return
     */
    @Override
    public String toCode() {
        if (label.chars().anyMatch(Character::isLetter)) {
            String internalVariableName = translator.getNumberVariable(label);
            if (internalVariableName == null) {
                internalVariableName = translator.buildVariableName(label);
                translator.addNumberVariable(label, internalVariableName);
                translator.addDefinitionCommand("double " + internalVariableName + " = 0.0 ;");
            }
            return codePrefix + internalVariableName + codeSuffix;
        }
        return codePrefix + label + codeSuffix;
    }

}