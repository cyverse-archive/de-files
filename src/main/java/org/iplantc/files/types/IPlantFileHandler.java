package org.iplantc.files.types;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;

import org.iplantc.files.FilesException;
import org.iplantc.files.metadata.FileMetadata;
import org.iplantc.files.metadata.FileMetadataFetcher;
import org.iplantc.files.persistence.FileSaver;
import org.iplantc.files.service.result.FileAccessUrlResult;
import org.json.JSONObject;

/**
 * Used to fetch information about files that have metadata in the iPlant database.
 * 
 * @author Dennis Roberts
 */
public class IPlantFileHandler implements FileTypeHandler {

    /**
     * Used to fetch the file metadata.
     */
    private FileMetadataFetcher metadataFetcher;

    /**
     * Used to save temporary copies of files that are stored in the database.
     */
    private FileSaver temporaryFileSaver;

    /**
     * Sets the file metadata fetcher.
     * 
     * @param metadataFetcher the new file metadata fetcher.
     */
    public void setFileMetadataFetcher(FileMetadataFetcher metadataFetcher) {
        this.metadataFetcher = metadataFetcher;
    }

    /**
     * Sets the temporary file saver.
     * 
     * @param temporaryFileSaver the new temporary file saver.
     */
    public void setTemporaryFileSaver(FileSaver temporaryFileSaver) {
        this.temporaryFileSaver = temporaryFileSaver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSONObject getFileAccessUrl(String fileId) {
        FileMetadata metadata = metadataFetcher.getFileMetadata(fileId);
        if (metadata == null) {
            throw new FilesException("no metadata found for: " + fileId);
        }
        String url = metadata.getUrl();
        boolean temporary = false;
        if (metadata.getUrl() == null) {
            temporary = true;
            url = temporaryFileSaver.save(metadata);
        }

        return new FileAccessUrlResult(fileId, decodeString(removeUserInfo(url)), temporary).toJson();
    }

    /**
     * Removes the user information from a URL.
     * 
     * @param url the URL to modify.
     * @return the URL without the user info.
     */
    private String removeUserInfo(String url) {
        try {
            URI uri = new URI(url);
            return new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(),
                uri.getFragment()).toString();
        }
        catch (URISyntaxException e) {
            throw new FilesException("unable to extract the path from the URL", e);
        }
    }

    /**
     * Decodes a string.
     * 
     * @param str the string to decode.
     * @return the decoded string.
     */
    private String decodeString(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new FilesException("unable to decode the string", e);
        }
    }
}
