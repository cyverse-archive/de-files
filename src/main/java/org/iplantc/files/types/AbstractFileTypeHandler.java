package org.iplantc.files.types;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;

import org.iplantc.files.FilesException;
import org.json.JSONObject;

public abstract class AbstractFileTypeHandler implements FileTypeHandler
{

	@Override
	public abstract JSONObject getFileAccessUrl(String fileId);

    /**
     * Removes the user information from a URL.
     * 
     * @param url the URL to modify.
     * @return the URL without the user info.
     */
    protected String removeUserInfo(String url) {
        try {
            URI uri = new URI(url);
            return new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), uri.getPath(), uri.getQuery(),
                uri.getFragment()).toString();
        }
        catch (URISyntaxException e) {
            throw new FilesException("unable to extract the path from the URL", e);
        }
    }

    /**
     * Decodes a string.
     * 
     * @param str the string to decode.
     * @return the decoded string.
     */
    protected String decodeString(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new FilesException("unable to decode the string", e);
        }
    }
}
