package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

/**
 *
 * @author User
 */
public class VariableDigitalBlock extends TranslatorBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public VariableDigitalBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
	       super(blockId, translator, codePrefix, codeSuffix, label);
    }

    /**
     *
     * @return
     */
    @Override
    public String toCode() {

            String internalVariableName = translator.getBooleanVariable(label);
            if (internalVariableName == null) {
                internalVariableName = translator.buildVariableName(label);
                translator.addBooleanVariable(label, internalVariableName);
                translator.addHeaderDefinition("bool " + internalVariableName + "= false ;");
            }
            String ret = internalVariableName;
            return codePrefix + ret + codeSuffix;
    }

}
