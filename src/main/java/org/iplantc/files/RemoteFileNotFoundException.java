package org.iplantc.files;

/**
 * Thrown when a remote file (for example a file in iRODS) that we expect to find isn't found.
 * 
 * @author Dennis Roberts
 */
public class RemoteFileNotFoundException extends FilesException {

    /**
     * The version identifier for serialized versions of this class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new empty exception.
     */
    public RemoteFileNotFoundException() {
        super();
    }

    /**
     * Creates a new exception with the given detail message.
     * 
     * @param msg the detail message.
     */
    public RemoteFileNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Creates a new exception with the given cause.
     * 
     * @param cause the cause of this exception.
     */
    public RemoteFileNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new exception with the given detail message and cause.
     * 
     * @param msg the detail message.
     * @param cause the cause of this exception.
     */
    public RemoteFileNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
