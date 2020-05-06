package com.ardublock.translator.block.exception;

import com.ardublock.core.exception.ArdublockException;

/**
 *
 * @author User
 */
public class SocketNullException extends ArdublockException
{

	private Long blockId;
	
    /**
     *
     * @param blockId
     */
    public SocketNullException(Long blockId)
	{
		this.blockId = blockId;
	}
	
    /**
     *
     * @return
     */
    public Long getBlockId()
	{
		return blockId;
	}
	private static final long serialVersionUID = -3386587749080938964L;

}
