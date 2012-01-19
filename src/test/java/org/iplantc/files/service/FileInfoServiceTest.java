package org.iplantc.files.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.iplantc.files.FilesException;
import org.iplantc.files.metadata.FileMetadata;
import org.iplantc.files.metadata.FileMetadataFetcher;
import org.iplantc.files.metadata.mock.MockFileMetadata;
import org.iplantc.files.metadata.mock.MockFileMetadataFetcher;
import org.iplantc.files.metadata.mock.MockTemporaryFileSaver;
import org.iplantc.files.service.result.FileAccessUrlResult;
import org.iplantc.files.types.FileTypeHandler;
import org.iplantc.files.types.IPlantFileHandler;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for org.iplantc.files.service.FileInfoService.
 * 
 * @author Dennis Roberts
 */
public class FileInfoServiceTest {

    /**
     * The URL to assign to temporary copies of files.
     */
    private static final String TEMPORARY_FILE_URL = "http://www.foo.bar/baz";

    /**
     * The JSON strings representing our test data, indexed by file ID.
     */
    private static final HashMap<String, String> TEST_JSON = new HashMap<String, String>();
    static {
        TEST_JSON.put("1", "{\"fileId\":\"1\",\"url\":\"http://foo/bar/baz/blarg\",\"temporary\":false}");
        TEST_JSON.put("2", "{\"fileId\":\"2\",\"url\":\"http://foo.bar.baz/blarg\",\"temporary\":false}");
        TEST_JSON.put("3", "{\"fileId\":\"3\",\"url\":\"" + TEMPORARY_FILE_URL + "\",\"temporary\":true}");
    }

    /**
     * The file handler used for each of the tests.
     */
    private IPlantFileHandler handler;

    /**
     * The file information service.
     */
    private FileInfoService service;

    /**
     * Initializes each of the tests.
     */
    @Before
    public void initialize() {
        service = new FileInfoService();
        service.setFileTypeHandlerMap(createFileTypeHandlerMap());
    }

    /**
     * Creates a new file type handler map to use for testing.
     * 
     * @return the new file type handler map.
     */
    private Map<String, FileTypeHandler> createFileTypeHandlerMap() {
        handler = new IPlantFileHandler();
        handler.setFileMetadataFetcher(createMetadataFetcher());
        handler.setTemporaryFileSaver(new MockTemporaryFileSaver(TEMPORARY_FILE_URL));
        Map<String, FileTypeHandler> fileTypeHandlerMap = new HashMap<String, FileTypeHandler>();
        fileTypeHandlerMap.put("File", handler);
        return fileTypeHandlerMap;
    }

    /**
     * Creates the file metadata fetcher to use in each of the tests.
     * 
     * @return the file metadata fetcher.
     */
    private FileMetadataFetcher createMetadataFetcher() {
        MockFileMetadataFetcher fetcher = new MockFileMetadataFetcher();
        for (String fileId : TEST_JSON.keySet()) {
            fetcher.addMetadata(fileId, buildMetadataFrom(new FileAccessUrlResult(TEST_JSON.get(fileId))));
        }
        return fetcher;
    }

    /**
     * Creates a file metadata object from a file access URL result.
     * 
     * @param result the file access URL result.
     * @return the metadata object.
     */
    private FileMetadata buildMetadataFrom(FileAccessUrlResult result) {
        MockFileMetadata metadata = new MockFileMetadata();
        metadata.setName("name" + result.getFileId());
        metadata.setUrl(result.isTemporary() ? null : result.getUrl());
        return metadata;
    }

    /**
     * Verifies that we can get the URL of a file that we know about.
     */
    @Test
    public void availableFileShouldReturnUrl() {
        FileAccessUrlResult expected = new FileAccessUrlResult(TEST_JSON.get("1"));
        assertEquals(expected, new FileAccessUrlResult(service.getFileAccessUrl("1")));
    }

    /**
     * Verifies that we get a FilesException for an unknown file identifier.
     */
    @Test(expected = FilesException.class)
    public void shouldGetNullForUnknownFileId() {
        service.getFileAccessUrl("unknown");
    }

    /**
     * Verifies that a temporary copy of a file is created if the file doesn't have a URL.
     */
    @Test
    public void shouldGetTemporaryFileCopy() {
        FileAccessUrlResult expected = new FileAccessUrlResult(TEST_JSON.get("3"));
        assertEquals(expected, new FileAccessUrlResult(service.getFileAccessUrl("3")));
    }
}
