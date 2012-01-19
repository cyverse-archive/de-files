package org.iplantc.files.persistence;

import org.iplantc.files.metadata.FileMetadata;

/**
 * Used to save temporary copies of files.
 * 
 * @author Dennis Roberts
 */
public interface FileSaver {

    /**
     * Saves a copy of the file with the given metadata and returns a URL that can be used to access the file. 
     * 
     * @param metadata the file metadata.
     * @return a URL that can be used to access the file.
     */
    public String save(FileMetadata metadata);
}
