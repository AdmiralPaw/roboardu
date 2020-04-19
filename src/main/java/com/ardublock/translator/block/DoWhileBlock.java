package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
public class DoWhileBlock extends TranslatorBlock
{

    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public DoWhileBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
  {
    super(blockId, translator);
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
    String ret = "do\n{\n ";
    TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
    translatorBlock = getTranslatorBlockAtSocket(0);
    while (translatorBlock != null)
    {
      ret = ret + translatorBlock.toCode();
      translatorBlock = translatorBlock.nextTranslatorBlock();
    }

    ret = ret + "}\nwhile(";
    translatorBlock = getTranslatorBlockAtSocket(1);
    if (translatorBlock == null) throw new SocketNullException(blockId);
    ret = ret + translatorBlock.toCode();

    ret = ret + ");\n";
    return ret;
  }
}
