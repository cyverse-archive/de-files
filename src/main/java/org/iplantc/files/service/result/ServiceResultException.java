package org.iplantc.files.service.result;

import org.iplantc.files.FilesException;

/**
 * Indicates a problem with a service result.
 * 
 * @author Dennis Roberts
 */
public class ServiceResultException extends FilesException {

    /**
     * The version identifier for serialized instances of this version of this class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new empty service result exception.
     */
    public ServiceResultException() {
        super();
    }

    /**
     * Creates a new service result exception with the given detail message.
     * 
     * @param msg the detail message.
     */
    public ServiceResultException(String msg) {
        super(msg);
    }

    /**
     * Creates a new service result exception with the given cause.
     * 
     * @param cause the cause of the new exception.
     */
    public ServiceResultException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new service result exception with the given detail message and cause.
     * 
     * @param msg the detail message.
     * @param cause the cause of the new exception.
     */
    public ServiceResultException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
