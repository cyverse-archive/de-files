package org.iplantc.files.metadata;

/**
 * Represents metadata associated with a file.
 * 
 * @author Dennis Roberts
 */
public interface FileMetadata {

    /**
     * Gets the name associated with the file.
     * 
     * @return the file name.
     */
    public String getName();

    /**
     * Gets the URL that can be used to access the file if the file is stored remotely.
     * 
     * @return the URL or null if the file is stored in the database.
     */
    public String getUrl();
    
    /**
     * Gets the contents of the file as they would appear if they were exported to a file.
     * 
     * @return the file contents.
     */
    public String getContents();
}
