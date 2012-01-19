package org.iplantc.files.metadata.mock;

import org.iplantc.files.metadata.FileMetadata;
import org.iplantc.files.persistence.FileSaver;

/**
 * A mock temporary file saver to use for testing.
 * 
 * @author Dennis Roberts
 */
public class MockTemporaryFileSaver implements FileSaver {

    /**
     * The URL to assign to any temporary files we create.
     */
    private String urlToAssign;

    /**
     * Creates a new mock temporary file saver using the given URL.
     * 
     * @param urlToAssign the URL to assign to any temporary files we create.
     */
    public MockTemporaryFileSaver(String urlToAssign) {
        this.urlToAssign = urlToAssign;
    }

    /**
     * {@inheritDoc}
     */
    public String save(FileMetadata metadata) {
        return urlToAssign;
    }
}
