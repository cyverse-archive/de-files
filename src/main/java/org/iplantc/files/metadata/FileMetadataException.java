package org.iplantc.files.metadata;

import org.iplantc.files.FilesException;

/**
 * Indicates a problem encountered while retrieving file metadata.
 * 
 * @author Dennis Roberts
 */
public class FileMetadataException extends FilesException {

    /**
     * A number used to identify serialized instances of this version of this class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new file metadata exception with no detail message or cause.
     */
    public FileMetadataException() {
        super();
    }

    /**
     * Creates a new file metadata exception with the given detail message.
     * 
     * @param msg the detail message.
     */
    public FileMetadataException(String msg) {
        super(msg);
    }

    /**
     * Creates a new file metadata exception with the given cause.
     * 
     * @param cause the cause of the exception.
     */
    public FileMetadataException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new file metadata exception with the given detail message and cause.
     * 
     * @param msg the detail message.
     * @param cause the cause of the exception.
     */
    public FileMetadataException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
