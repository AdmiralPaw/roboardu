package com.ardublock.translator.block.exception;

import com.ardublock.core.exception.ArdublockException;

/**
 *
 * @author User
 */
public class SubroutineNameDuplicatedException extends ArdublockException
{

	private static final long serialVersionUID = 882306487358983819L;
	
	private Long blockId;

    /**
     *
     * @return
     */
    public Long getBlockId() {
		return blockId;
	}
	
    /**
     *
     * @param blockId
     */
    public SubroutineNameDuplicatedException(Long blockId)
	{
		this.blockId = blockId;
	}

}
