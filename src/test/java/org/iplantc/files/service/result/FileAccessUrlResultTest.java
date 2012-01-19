package org.iplantc.files.service.result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for org.iplantc.files.service.result.
 * 
 * @author Dennis Roberts
 */
public class FileAccessUrlResultTest {

    /**
     * Verifies that we can create a populated result with specific property values.
     */
    @Test
    public void shouldCreatePopulatedResult() {
        FileAccessUrlResult result = new FileAccessUrlResult("foo", "bar", false);
        assertEquals("foo", result.getFileId());
        assertEquals("bar", result.getUrl());
        assertFalse(result.isTemporary());
    }

    /**
     * Verifies that we can unmarshall a result from a JSON string.
     */
    @Test
    public void shouldUnmarshall() {
        String json
            = "{   \"fileId\":    \"1\",\n"
            + "    \"url\":       \"http://www.foo.bar/baz\",\n"
            + "    \"temporary\": false\n"
            + "}";
        FileAccessUrlResult result = new FileAccessUrlResult(json);
        assertEquals("1", result.getFileId());
        assertEquals("http://www.foo.bar/baz", result.getUrl());
        assertFalse(result.isTemporary());
    }

    /**
     * Verifies that we can marshall a result ot a JSON string.
     */
    @Test
    public void shouldMarshall() {
        FileAccessUrlResult result = new FileAccessUrlResult(createTestResult().toJson());
        assertEquals("1", result.getFileId());
        assertEquals("http://foo.bar.baz/blarg", result.getUrl());
        assertTrue(result.isTemporary());
    }

    /**
     * Verifies that two equal file access URL results are considered to be equal.
     */
    @Test
    public void equalResultsShouldBeEqual() {
        assertTrue(createTestResult().equals(createTestResult()));
    }

    /**
     * Verifies that two results with different file IDs are not considered to be equal.
     */
    @Test
    public void equalsShouldDetectDifferentFileIds() {
        FileAccessUrlResult result1 = createTestResult();
        FileAccessUrlResult result2 = createTestResult();
        result2.setFileId("2");
        assertFalse(result1.equals(result2));
    }

    /**
     * Verifies that two results with different URLs are not considered to be equal.
     */
    @Test
    public void equalsShouldDetectDifferentUrls() {
        FileAccessUrlResult result1 = createTestResult();
        FileAccessUrlResult result2 = createTestResult();
        result2.setUrl("http://foo.bar.org/blarg");
        assertFalse(result1.equals(result2));
    }

    /**
     * Verifies that two results with different temporary flags are not considered to be equal.
     */
    @Test
    public void equalsShouldDetectDifferentTemporaryFlags() {
        FileAccessUrlResult result1 = createTestResult();
        FileAccessUrlResult result2 = createTestResult();
        result2.setIsTemporary(false);
        assertFalse(result1.equals(result2));
    }

    /**
     * Verifies that two equivalent file access URL results have the same hash code.
     */
    @Test
    public void equivalentResultsShouldHaveSameHashCode() {
        assertTrue(createTestResult().hashCode() == createTestResult().hashCode());
    }

    /**
     * Verifies that two file access URL results with different file IDs don't have the same hash code.
     */
    @Test
    public void hashCodeShouldIncludeFileId() {
        FileAccessUrlResult result1 = createTestResult();
        FileAccessUrlResult result2 = createTestResult();
        result2.setFileId("2");
        assertFalse(result1.hashCode() == result2.hashCode());
    }

    /**
     * Verifies that two file access URL results with different URLs don't have the same hash code.
     */
    @Test
    public void hashCodeShouldIncludeUrl() {
        FileAccessUrlResult result1 = createTestResult();
        FileAccessUrlResult result2 = createTestResult();
        result2.setUrl("http://foo.bar.org/blarg");
        assertFalse(result1.hashCode() == result2.hashCode());
    }

    /**
     * Verifies that two file access URL results with different temporary flags don't have the same hash code.
     */
    @Test
    public void hashCodeShouldIncludeTemporaryFlag() {
        FileAccessUrlResult result1 = createTestResult();
        FileAccessUrlResult result2 = createTestResult();
        result2.setIsTemporary(false);
        assertFalse(result1.hashCode() == result2.hashCode());
    }

    /**
     * Creates a test file access URL result with some default values.
     * 
     * @return the result.
     */
    private FileAccessUrlResult createTestResult() {
        FileAccessUrlResult result = new FileAccessUrlResult();
        result.setFileId("1");
        result.setUrl("http://foo.bar.baz/blarg");
        result.setIsTemporary(true);
        return result;
    }
}
