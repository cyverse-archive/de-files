package org.iplantc.files.metadata;

/**
 * Used to retrieve file metadata.
 * 
 * @author Dennis Roberts
 */
public interface FileMetadataFetcher {

    /**
     * Retrieves the metadata for the given file identifier.
     * 
     * @param fileId the file identifier.
     * @return the metadata or null if the file can't be found.
     */
    public FileMetadata getFileMetadata(String fileId);
}
