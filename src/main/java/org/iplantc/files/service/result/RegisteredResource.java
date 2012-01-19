package org.iplantc.files.service.result;

import org.iplantc.files.FilesException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Represents a resource that has been registered in a user's workspace.
 * 
 * @author Dennis Roberts
 */
public abstract class RegisteredResource {

    /**
     * The resource identifier.
     */
    private long id;
    
    /**
     * The resource identifier of the parent folder.
     */
    private long parentFolderId;
    
    /**
     * The resource name.
     */
    private String name;
    
    /**
     * Gets the resource type.
     * 
     * @return the type of the resource.
     */
    public abstract String getType();

    /**
     * Sets the resource identifier.
     * 
     * @param id the new resource identifier.
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Gets the resource identifier.
     * 
     * @return the resource identifier.
     */
    public long getId() {
        return id;
    }
    
    /**
     * Sets the parent folder identifier.
     * 
     * @param parentFolderId the new parent folder identifier.
     */
    public void setParentFolderId(long parentFolderId) {
        this.parentFolderId = parentFolderId;
    }

    /**
     * Gets the parent folder identifier.
     * 
     * @return the parent folder identifier.
     */
    public long getParentFolderId() {
        return parentFolderId;
    }

    /**
     * Sets the name.
     * 
     * @param name the new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name.
     * 
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Initializes the registered resource with the given field values.
     * 
     * @param id the registered resource identifier.
     * @param parentFolderId the resource identifier of the parent folder.
     * @param name the name of the registered resource.
     */
    protected RegisteredResource(long id, long parentFolderId, String name) {
        setId(id);
        setParentFolderId(parentFolderId);
        setName(name);
    }

    /**
     * Produces a JSON object representing the resource.
     * 
     * @return the JSON object.
     */
    public JSONObject toJson() throws FilesException {
        try {
            JSONObject json = new JSONObject();
            json.put("id", getId());
            json.put("parentFolderId", getParentFolderId());
            json.put("name", getName());
            json.put("type", getType());
            return json;
        }
        catch (JSONException e) {
            throw new FilesException("unable to convert to JSON", e);
        }
    }
}
