package org.iplantc.files.metadata.mock;

import org.iplantc.files.metadata.FileMetadata;

/**
 * Mock file metadata for testing.
 * 
 * @author Dennis Roberts
 */
public class MockFileMetadata implements FileMetadata {

    /**
     * The name of the file.
     */
    private String name;

    /**
     * The URL used to access the file.
     */
    private String url;

    /**
     * The file contents.
     */
    private String contents;

    /**
     * {@inheritDoc}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the file name.
     * 
     * @param name the new file name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL used to access the file.
     * 
     * @param url the new URL.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * {@inheritDoc}
     */
    public String getContents() {
        return contents;
    }

    /**
     * Sets the file contents.
     * 
     * @param contents the new file contents.
     */
    public void setContents(String contents) {
        this.contents = contents;
    }
}
