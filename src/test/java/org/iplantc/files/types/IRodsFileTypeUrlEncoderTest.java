package org.iplantc.files.types;

import static org.junit.Assert.assertEquals;

import org.iplantc.files.FilesException;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for org.iplantc.files.types.IRodsFileTypeUrlEncoderTest.
 * 
 * @author Dennis Roberts
 */
public class IRodsFileTypeUrlEncoderTest {

    /**
     * The encoder to user for each of the tests.
     */
    private IRodsFileTypeUrlEncoder encoder;
    
    /**
     * Initializes each of the tests.
     */
    @Before
    public void initialize() {
        encoder = new IRodsFileTypeUrlEncoder();
    }
    
    /**
     * Verifies that we can handle a URL that doesn't need to have any of its characters encoded.
     */
    @Test
    public void shouldEncodeValidUrl() {
        encoder.setAdditionalRestrictedCharacterRegex("([-*])");
        String url = "irods://foo.zone:bar@somehost/zone/foo/bar/baz";
        assertEquals(url, encoder.encode(url));
    }

    /**
     * Verifies that we can handle a URL containing characters that can be encoded using java.net.URLEncoder.
     */
    @Test
    public void shouldEncodeUrlWithStandardRestrictedCharacters() {
        String url = "irods://fo^o.zone:ba&r@somehost/zone/foo/bar/baz";
        String expected = "irods://fo%5Eo.zone:ba%26r@somehost/zone/foo/bar/baz";
        assertEquals(expected, encoder.encode(url));
    }

    /**
     * Verifies that we can handle a URL with non-standard restricted characters.
     */
    @Test
    public void shouldEncodeUrlWithNonStandardRestrictedCharacters() {
        encoder.setAdditionalRestrictedCharacterRegex("([-*])");
        String url = "irods://fo-o.zone:ba*r@somehost/zone/foo/bar/baz";
        String expected = "irods://fo%2Do.zone:ba%2Ar@somehost/zone/foo/bar/baz";
        assertEquals(expected, encoder.encode(url));
    }

    /**
     * Verifies that we can handle a URL with both standard and non-standard restricted characters.
     */
    @Test
    public void shouldEncodeUrlWithMixedRestrictedCharacters() {
        encoder.setAdditionalRestrictedCharacterRegex("([-*])");
        String url = "irods://fo-o.zone:ba&r@somehost/zone/foo/bar/baz";
        String expected = "irods://fo%2Do.zone:ba%26r@somehost/zone/foo/bar/baz";
        assertEquals(expected, encoder.encode(url));
    }
    
    /**
     * Verifies that we get an exception if our regular expression captures multiple characters.
     */
    @Test(expected = FilesException.class)
    public void shoulGetExceptionForRegexThatCapturesMultipleCharacters() {
        encoder.setAdditionalRestrictedCharacterRegex("(oo)");
        String url = "irods://foo.zone:bar@somehost/zone/foo/bar/baz";
        encoder.encode(url);
    }

    /**
     * Verifies that we get an exception if our regular expression captures no characters.
     */
    @Test(expected = FilesException.class)
    public void shouldGetExceptionForRegexThatCapturesNoCharacters() {
        encoder.setAdditionalRestrictedCharacterRegex("oo");
        String url = "irods://foo.zone:bar@somehost/zone/foo/bar/baz";
        encoder.encode(url);
    }
}
