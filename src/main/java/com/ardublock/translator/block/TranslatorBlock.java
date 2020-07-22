package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.adaptor.BlockAdaptor;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 *
 * @author User
 */
abstract public class TranslatorBlock
{

    /**
     *
     * @return
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     * @throws BlockException
     */
    abstract public String toCode() throws SocketNullException, SubroutineNotDeclaredException, BlockException;
	
    /**
     *
     */
    protected Long blockId;
	
	private BlockAdaptor blockAdaptor;
	
    /**
     *
     */
    protected Translator translator;
	
    /**
     *
     */
    protected String label;

    /**
     *
     */
    protected String comment;
	
    /**
     *
     */
    protected String codePrefix;

    /**
     *
     */
    protected String codeSuffix;
	
    /**
     *
     * @param blockId
     * @param translator
     */
    protected TranslatorBlock(Long blockId, Translator translator)
	{
		this.blockId = blockId;
		this.translator = translator;
		this.blockAdaptor = translator.getBlockAdaptor();
		this.codePrefix = "";
		this.codeSuffix = "";
		this.label = "";
	}
	
    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     */
    protected TranslatorBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix)
	{
		this.blockId = blockId;
		this.translator = translator;
		this.blockAdaptor = translator.getBlockAdaptor();
		this.codePrefix = codePrefix;
		this.codeSuffix = codeSuffix;
		this.label = "";
	}
	
    /**
     *
     * @param blockId
     * @param translator
     * @param codePrefix
     * @param codeSuffix
     * @param label
     */
    public TranslatorBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		this.blockId = blockId;
		this.translator = translator;
		this.blockAdaptor = translator.getBlockAdaptor();
		this.codePrefix = codePrefix;
		this.codeSuffix = codeSuffix;
		this.label = label;
	}
	
    /**
     *
     * @return
     */
    protected Translator getTranslator()
	{
		return translator;
	}
	
    /**
     *
     * @return
     */
    public TranslatorBlock nextTranslatorBlock()
	{
		return this.nextTranslatorBlock("", "");
	}
	
    /**
     *
     * @param codePrefix
     * @param codeSuffix
     * @return
     */
    protected TranslatorBlock nextTranslatorBlock(String codePrefix, String codeSuffix)
	{
		return blockAdaptor.nextTranslatorBlock(this.translator, blockId, codePrefix, codeSuffix);
	}
	
    /**
     *
     * @param i
     * @return
     */
    protected TranslatorBlock getTranslatorBlockAtSocket(int i)
	{
		return this.getTranslatorBlockAtSocket(i, "", "");
	}
	
    /**
     *
     * @param i
     * @param codePrefix
     * @param codeSuffix
     * @return
     */
    protected TranslatorBlock getTranslatorBlockAtSocket(int i, String codePrefix, String codeSuffix)
	{
		return blockAdaptor.getTranslatorBlockAtSocket(this.translator, blockId, i, codePrefix, codeSuffix);
	}
	
    /**
     *
     * @param i
     * @return
     * @throws SocketNullException
     */
    protected TranslatorBlock getRequiredTranslatorBlockAtSocket(int i) throws SocketNullException
	{
		return this.getRequiredTranslatorBlockAtSocket(i, "", "");
	}
	
    /**
     *
     * @param i
     * @param codePrefix
     * @param codeSuffix
     * @return
     * @throws SocketNullException
     */
    protected TranslatorBlock getRequiredTranslatorBlockAtSocket(int i, String codePrefix, String codeSuffix) throws SocketNullException
	{
		TranslatorBlock translatorBlock = blockAdaptor.getTranslatorBlockAtSocket(this.translator, blockId, i, codePrefix, codeSuffix);
		if (translatorBlock == null)
		{
			throw new SocketNullException(blockId);
		}
		return translatorBlock;
	}
	
    /**
     *
     * @param comment
     */
    protected void setComment(String comment)
	{
		this.comment = comment;
	}
	
    /**
     *
     * @return
     */
    protected String getComment()
	{
		return this.comment;
	}

    /**
     *
     * @return
     */
    public Long getBlockID(){
            return this.blockId;
        }

    /**
     *
     * @throws SocketNullException
     * @throws SubroutineNotDeclaredException
     */
    public void onTranslateBodyFinished() throws SocketNullException, SubroutineNotDeclaredException{}
    
    
    protected void checkValueInt(String val,long blockID,String msgError) throws BlockException,NumberFormatException {
        try {
            Double.parseDouble(val);
            try {
                Integer.parseInt(val);
            } catch (NumberFormatException e) {
                throw new BlockException(blockID, msgError);
            }
        } catch (NumberFormatException|BlockException e) {
            throw e;
        } 
    }
	
}
