package org.iplantc.files.service.result;

/**
 * Represents a folder that has been registered in the user's workspace.
 * 
 * @author Dennis Roberts
 */
public class RegisteredFolder extends RegisteredResource {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "folder";
    }

    /**
     * Initializes the registered folder with the given field values.
     * 
     * @param id the registered resource identifier.
     * @param parentFolderId the resource identifier of the parent folder.
     * @param name the name of the registered resource.
     */
    public RegisteredFolder(long id, long parentFolderId, String name) {
        super(id, parentFolderId, name);
    }
}
