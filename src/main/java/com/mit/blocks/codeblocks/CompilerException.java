package com.mit.blocks.codeblocks;

import com.mit.blocks.workspace.Workspace;

/**
 *
 * @author User
 */
public class CompilerException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public enum Error {

        /**
         *
         */
        UNSUPPORTED_VALUE
    }
    /** USE_DEBUGGING_MESSAGES is whether the messages are friendly to developers, as opposed to friendly to users. */
    private static final boolean USE_DEBUGGING_MESSAGES = false;
    private Error error;
    private Long illegalBlockID;
    private String label;

    /**
     *
     * @param error
     * @param workspace
     * @param illegalBlockID
     */
    public CompilerException(Error error, Workspace workspace, Long illegalBlockID) {
        this.error = error;
        this.illegalBlockID = illegalBlockID;
        label = workspace.getEnv().getBlock(illegalBlockID).getBlockLabel();
    }

    public String getMessage() {
        StringBuilder ans = new StringBuilder(USE_DEBUGGING_MESSAGES ? "Block " + illegalBlockID + " " + label + ": " : "");
        switch (error) {
            case UNSUPPORTED_VALUE:
                ans.append("Unsupported value.");
                break;
            default:
                ans.append("Unknown error");
        }

        return ans.toString();
    }
}
