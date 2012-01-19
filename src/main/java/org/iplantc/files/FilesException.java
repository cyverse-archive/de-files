package org.iplantc.files;

/**
 * The base class used for all exceptions thrown by classes in this library.
 */
public class FilesException extends RuntimeException {

    /**
     * The version identifier for serialized versions of this class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new empty exception.
     */
    public FilesException() {
        super();
    }

    /**
     * Creates a new exception with the given detail message.
     * 
     * @param msg the detail message.
     */
    public FilesException(String msg) {
        super(msg);
    }

    /**
     * Creates a new exception with the given cause.
     * 
     * @param cause the cause of this exception.
     */
    public FilesException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new exception with the given detail message and cause.
     * 
     * @param msg the detail message.
     * @param cause the cause of this exception.
     */
    public FilesException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
