package org.iplantc.files.persistence;

import java.net.URISyntaxException;

import org.iplantc.files.FilesException;
import org.iplantc.files.metadata.FileMetadata;
import org.iplantc.irodsfile.DownloadToIrods;
import org.iplantc.irodsfile.ExtractIrodsFile;
import org.iplantc.irodsfile.IrodsFileService;

public class IRodsFileSaver implements FileSaver {

    /**
     * Used to extract files from iRODS.
     */
    protected ExtractIrodsFile irodsFileExtractor;

    /**
     * The username to associate temporary files with in iRODS.
     */
    protected String storageUsername;

    /**
     * Used to download files to iRODS.
     */
    DownloadToIrods downloader;

    /**
     * Used to obtain authenticated URLs.
     */
    IrodsFileService irodsFileService;

    /**
     * @param irodsFileExtractor the new file extractor.
     */
    public void setIrodsFileExtractor(ExtractIrodsFile irodsFileExtractor) {
        this.irodsFileExtractor = irodsFileExtractor;
    }

    /**
     * Sets the username to associate temporary files with in iRODS.
     * 
     * @param StorageUsername the new username.
     */
    public void setStorageUsername(String StorageUsername) {
        this.storageUsername = StorageUsername;
    }

    /**
     * Sets the downloader used to download objects to iRODS.
     * 
     * @param downloader the new downloader.
     */
    public void setDownloader(DownloadToIrods downloader) {
        this.downloader = downloader;
    }

    /**
     * Sets the service used to obtain authenticated URLs.
     * 
     * @param irodsFileService the new service.
     */
    public void setIrodsFileService(IrodsFileService irodsFileService) {
        this.irodsFileService = irodsFileService;
    }

    /**
     * {@inheritDoc}
     */
    public String save(FileMetadata metadata) {
        return "";
    }

    /**
     * Gets an authenticated version of the given URI.
     * 
     * @param uri the URI.
     * @return an authenticated version of the same URI.
     */
    protected String getAuthenticatedUri(String uri) {
        try {
            return irodsFileService.getAuthenticatedUri(uri).toString();
        }
        catch (URISyntaxException e) {
            throw new FilesException("unable to get an authenticated URI", e);
        }
    }

    
}
