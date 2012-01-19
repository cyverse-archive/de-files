package org.iplantc.files.service.result;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.ObjectUtils;

/**
 * The results of a file access URL query.
 * 
 * @author Dennis Roberts
 */
public class FileAccessUrlResult {

    /**
     * The file identifier.
     */
    private String fileId;

    /**
     * The URL used to access the file.
     */
    private String url;

    /**
     * True if a temporary copy of the file has been created as a result of the service call.
     */
    private boolean temporary;

    /**
     * Gets the file identifier.
     * 
     * @return the file identifier.
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * Sets the file identifier.
     * 
     * @param fileId the file identifier.
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * Gets the URL used to access the file.
     * 
     * @return the URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL used to access the file.
     * 
     * @param url the URL.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns a flag indicating whether or not the file is temporary.
     * 
     * @return true if the file was created as a result of the service call.
     */
    public boolean isTemporary() {
        return temporary;
    }

    /**
     * Sets the temporary flag.
     * 
     * @param temporary true if the file was created as a result of the service call.
     */
    public void setIsTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    /**
     * Creates a new empty file access URL result.
     */
    public FileAccessUrlResult() {
        setFileId(null);
        setUrl(null);
        setIsTemporary(false);
    }

    /**
     * Creates a new file access URL result with the given property values.
     * 
     * @param fileId the file identifier.
     * @param url the URL used to access the file.
     * @param temporary true if the file should be treated as a temporary file.
     */
    public FileAccessUrlResult(String fileId, String url, boolean temporary) {
        setFileId(fileId);
        setUrl(url);
        setIsTemporary(temporary);
    }

    /**
     * Creates a new file access URL result from the given JSON string.
     * 
     * @param jsonString the JSON string.
     * @return the file access URL result.
     */
    public FileAccessUrlResult(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);
            initializeFromJson(json);
        }
        catch (JSONException e) {
            throw new ServiceResultException("invalid file access URL result JSON string", e);
        }
    }

    /**
     * Creates a new file access URL result from the given JSON object.
     * 
     * @param jsonString the JSON string.
     * @return the file access URL result.
     */
    public FileAccessUrlResult(JSONObject json) {
        try {
            initializeFromJson(json);
        }
        catch (JSONException e) {
            throw new ServiceResultException("invalid file access URL result JSON object", e);
        }
    }

    /**
     * Initializes the file access URL result from the given JSON object.
     * 
     * @param json the JSON object.
     * @throws JSONException if one of the required fields is missing.
     */
    private void initializeFromJson(JSONObject json) throws JSONException {
        setFileId(json.getString("fileId"));
        setUrl(json.getString("url"));
        setIsTemporary(json.getBoolean("temporary"));
    }

    /**
     * Creates a JSON representation of the object.
     * 
     * @return a JSON object.
     */
    public JSONObject toJson() {
        try {
            JSONObject json = new JSONObject();
            json.put("fileId", fileId);
            json.put("url", url);
            json.put("temporary", temporary);
            return json;
        }
        catch (JSONException e) {
            throw new ServiceResultException("unable to marshall file access URL result to JSON", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object otherObject) {
        if (otherObject instanceof FileAccessUrlResult) {
            FileAccessUrlResult other = (FileAccessUrlResult) otherObject;
            if (!StringUtils.equals(fileId, other.getFileId())) {
                return false;
            }
            if (!StringUtils.equals(url, other.getUrl())) {
                return false;
            }
            if (temporary != other.isTemporary()) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hashCode = FileAccessUrlResult.class.getName().hashCode();
        hashCode += ObjectUtils.nullSafeHashCode(fileId);
        hashCode += ObjectUtils.nullSafeHashCode(url);
        hashCode += ObjectUtils.nullSafeHashCode(temporary);
        return hashCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return toJson().toString();
    }
}
