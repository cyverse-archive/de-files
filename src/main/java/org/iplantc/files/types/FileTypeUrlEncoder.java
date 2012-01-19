package org.iplantc.files.types;

/**
 * Used to encode URLs in a way that is required by the tool that is using
 * the URL to encode the resource.
 * 
 * @author Dennis Roberts
 */
public interface FileTypeUrlEncoder {

    /**
     * Encodes the URL.
     * 
     * @param urlString the URL as a string.
     * @return the encoded URL.
     */
    public String encode(String urlString);
}
