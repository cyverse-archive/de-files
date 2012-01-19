package org.iplantc.files.metadata.contents;

/**
 * Used to get the contents of a file from the database.  Some transformation may be necessary to convert the file
 * from the format used internally to the format used externally.
 * 
 * @author Dennis Roberts
 */
public interface FileContents {

    /**
     * Gets the contents of the file, after any required transformation has been done.
     * 
     * @return the file contents as a string.
     */
    public String getFileContents();
}
