package org.iplantc.files.persistence;

import java.io.IOException;
import java.net.URISyntaxException;

import org.iplantc.files.FilesException;
import org.iplantc.files.metadata.FileMetadata;

import edu.sdsc.grid.io.irods.IRODSFile;

public class IRodsTemporaryFileSaver extends IRodsFileSaver {

    /**
     * {@inheritDoc}
     */
    public String save(FileMetadata metadata) {
        try {
            String uniqueName = assignUniqueName(metadata.getName());
            String contents = metadata.getContents();
            IRODSFile irodsFile = downloader.downloadFile(storageUsername, uniqueName, contents);
            String uri = irodsFileExtractor.irodsFileToUri(irodsFile);
            return getAuthenticatedUri(uri);
        }
        catch (IOException e) {
            throw new FilesException("unable to persist file in iRODS", e);
        }
        catch (URISyntaxException e) {
            throw new FilesException("unable to persist file in iRODS", e);
        }
    }

    /**
     * Assigns a unique name to the temporary file that we're about to create.
     * 
     * @param originalName the original file name.
     * @return the unique name as a string.
     */
    private String assignUniqueName(String originalName) {
        int dotPosition = originalName.indexOf('.');
        String[] components = new String[2];
        if (dotPosition >= 0) {
            components[0] = originalName.substring(0, dotPosition);
            components[1] = originalName.substring(dotPosition);
        }
        else {
            components[0] = originalName;
            components[1] = "";
        }
        return components[0] + "_" + System.currentTimeMillis() + components[1];
    }

}
