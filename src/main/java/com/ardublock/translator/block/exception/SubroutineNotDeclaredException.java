package com.ardublock.translator.block.exception;

import com.ardublock.core.exception.ArdublockException;

/**
 *
 * @author User
 */
public class SubroutineNotDeclaredException extends ArdublockException
{

	private static final long serialVersionUID = -2621233841585294257L;
	
	private Long blockId;
	
    /**
     *
     * @param blockId
     */
    public SubroutineNotDeclaredException(Long blockId)
	{
		this.blockId = blockId;
	}
	
    /**
     *
     * @return
     */
    public Long getBlockId()
	{
		return this.blockId;
	}
}
