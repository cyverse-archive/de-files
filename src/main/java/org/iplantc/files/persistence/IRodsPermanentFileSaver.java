package org.iplantc.files.persistence;

import java.io.IOException;
import java.net.URISyntaxException;

import org.iplantc.files.FilesException;
import org.iplantc.files.metadata.FileMetadata;

import edu.sdsc.grid.io.irods.IRODSFile;

public class IRodsPermanentFileSaver extends IRodsFileSaver {

    /**
     * {@inheritDoc}
     */
    @Override
    public String save(FileMetadata metadata) {
        try {
            String uniqueName = metadata.getName();
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
}
