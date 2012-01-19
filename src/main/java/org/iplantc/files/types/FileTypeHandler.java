package org.iplantc.files.types;

import org.json.JSONObject;

/**
 * Used to obtain information about a specific type of file.
 * 
 * @author Dennis Roberts
 */
public interface FileTypeHandler {

    /**
     * Obtains a URL that can be used to access a file.
     * 
     * @param fileId the file identifier.
     * @return a JSON object in the format, {"fileId":id,"url":url,"temporary":flag}
     */
    public JSONObject getFileAccessUrl(String fileId);
}
