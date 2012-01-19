package org.iplantc.files.service.result;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents the results of file registration.
 * 
 * @author Dennis Roberts
 */
public class RegistrationResult {

    /**
     * Used to log error messages.
     */
    private static final Logger LOG = Logger.getLogger(RegistrationResult.class);

    /**
     * The identifier of the root of the directory tree that was registered.
     */
    private Long destFolderId;

    /**
     * The list of registered resources.
     */
    private List<RegisteredResource> registeredResources;

    /**
     * Initializes an empty registration result.
     */
    public RegistrationResult() {
        destFolderId = null;
        registeredResources = new LinkedList<RegisteredResource>();
    }

    /**
     * Initializes a populated registration result.
     * 
     * @param destFolderId the destination folder ID.
     * @param registeredResources the list of registered resources.
     */
    public RegistrationResult(Long destFolderId, List<RegisteredResource> registeredResources) {
        this.destFolderId = destFolderId;
        this.registeredResources = registeredResources;
    }

    /**
     * @return the destination folder ID.
     */
    public Long getDestFolderId() {
        return destFolderId;
    }

    /**
     * @return the list of registered resources.
     */
    public List<RegisteredResource> getRegisteredResources() {
        return registeredResources;
    }

    /**
     * Produces a JSON object representing a file registration result.
     * 
     * @return the JSON object.
     * @throws JSONException if an invalid JSON field name is used.
     */
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("destFolderId", destFolderId);
        json.put("registeredResources", registeredResources);
        return json;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        try {
            return toJson().toString();
        }
        catch (JSONException e) {
            LOG.warn("unable to convert object to JSON", e);
            return super.toString();
        }
    }
}
