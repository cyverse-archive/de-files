package org.iplantc.files.metadata.mock;

import java.util.HashMap;
import java.util.Map;

import org.iplantc.files.metadata.FileMetadata;
import org.iplantc.files.metadata.FileMetadataFetcher;

/**
 * A mock file metadata fetcher for testing.
 * 
 * @author Dennis Roberts
 */
public class MockFileMetadataFetcher implements FileMetadataFetcher {

    /**
     * Maps file IDs to file metadata.
     */
    private Map<String, FileMetadata> metadataMap = new HashMap<String, FileMetadata>();

    /**
     * Adds file metadata to the file metadata map.
     * 
     * @param fileId the file identifier.
     * @param metadata the file metadata.
     */
    public void addMetadata(String fileId, FileMetadata metadata) {
        metadataMap.put(fileId, metadata);
    }

    /**
     * {@inheritDoc}
     */
    public FileMetadata getFileMetadata(String fileId) {
        return metadataMap.get(fileId);
    }
}
