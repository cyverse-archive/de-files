package org.iplantc.files.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.iplantc.files.FilesException;
import org.iplantc.files.service.result.FileAccessUrlResult;
import org.json.JSONObject;

/**
 * Used to obtain information about reference genomes.
 * 
 * @author Dennis Roberts
 */
public class ReferenceGenomeHandler implements FileTypeHandler {

    /**
     * Maps file identifiers (that is, reference genome names) to URLs.
     */
    protected Map<String, String> referenceGenomeUrlMap;

    /**
     * An ordered list of reference genome names.
     */
    protected ArrayList<String> referenceGenomeNames;

    /**
     * Sets the reference genome URL map.
     * 
     * @param referenceGenomeUrlMap the new reference genome URL map.
     */
    public void setReferenceGenomeUrlMap(Map<String, String> referenceGenomeUrlMap) {
        this.referenceGenomeUrlMap = referenceGenomeUrlMap;
        this.referenceGenomeNames = new ArrayList<String>(new TreeSet<String>(referenceGenomeUrlMap.keySet()));
    }

    /**
     * Gets an ordered list of reference genome names.
     * 
     * @return the list of reference genome names.
     */
    public List<String> getRefrenceGenomeNames() {
        return referenceGenomeNames;
    }

    /**
     * Gets the base file access URL for the file with the given identifier.
     * 
     * @param fileId the file identifier.
     * @return the base file access URL.
     */
    protected String getBaseFileAccessUrl(String fileId) {
        String path = referenceGenomeUrlMap.get(fileId);
        if (path == null) {
            throw new FilesException("unrecognized file identifier: " + fileId);
        }
        return path.trim();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JSONObject getFileAccessUrl(String fileId) {
        String baseUrl = getBaseFileAccessUrl(fileId);
        String url = baseUrl + "annotation.gtf" + "  " + baseUrl + "genome.fas";
        return new FileAccessUrlResult(fileId, url, false).toJson();
    }
}
