package org.iplantc.files.service.result;

/**
 * Represents a file that has been registered in the user's workspace.
 * 
 * @author Dennis Roberts
 */
public class RegisteredFile extends RegisteredResource {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return "file";
    }

    /**
     * Initializes the registered file with the given field values.
     * 
     * @param id the registered resource identifier.
     * @param parentFolderId the resource identifier of the parent folder.
     * @param name the name of the registered resource.
     */
    public RegisteredFile(long id, long parentFolderId, String name) {
        super(id, parentFolderId, name);
    }
}
