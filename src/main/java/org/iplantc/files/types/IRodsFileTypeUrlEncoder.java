package org.iplantc.files.types;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.iplantc.files.FilesException;

/**
 * Used to encode iRODS URLs in a way that is required by Jargon.
 * 
 * @author Dennis Roberts
 */
public class IRodsFileTypeUrlEncoder implements FileTypeUrlEncoder {

    /**
     * A regular expression pattern that will match any additional restricted characters.
     */
    private Pattern additionalRestrictedCharacterRegex = null;

    /**
     * Sets the regular expression pattern that will match any additional restricted characters.
     * 
     * @param regex the new regular expression pattern.
     */
    public void setAdditionalRestrictedCharacterRegex(String regex) {
        additionalRestrictedCharacterRegex = Pattern.compile(regex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String encode(String urlString) {
        String[] components = urlString.split("/");
        if (components.length > 2) {
            urlString = encodeSelectedUrlComponents(components);
        }
        return urlString;
    }

    /**
     * Encodes the components of the URL where we expect restricted characters to lurk.
     * 
     * @param components the URL components.
     * @return the URL components with selected components encoded.
     */
    private String encodeSelectedUrlComponents(String[] components) {
        String[] connectInfo = components[2].split("@");
        if (connectInfo.length > 1) {
            connectInfo[0] = encodeUserInfo(connectInfo);
        }
        components[2] = StringUtils.join(connectInfo, "@");
        return StringUtils.join(components, "/");
    }

    /**
     * Encodes the user information component of the URL.
     * 
     * @param connectInfo the connection information.
     * @return the encoded user information.
     */
    private String encodeUserInfo(String[] connectInfo) {
        String[] userAndPassword = connectInfo[0].split(":");
        for (int i = 0; i < userAndPassword.length; i++) {
            userAndPassword[i] = encodeComponent(userAndPassword[i]);
        }
        return StringUtils.join(userAndPassword, ":");
    }

    /**
     * Encodes a single component from a URL.
     * 
     * @param component the component to be encoded.
     * @return the encoded URL component.
     */
    private String encodeComponent(String component) {
        try {
            String encodedComponent = URLEncoder.encode(component, "UTF-8");
            return replaceAdditionalRestrictedCharacters(encodedComponent);
        }
        catch (UnsupportedEncodingException e) {
            throw new FilesException("INTERNAL ERROR: UTF-8 isn't supported");
        }
    }

    /**
     * Replaces any additional restricted characters that have been specified in the configuration.
     * 
     * @param component the component that we want to encode.
     * @return the encoded version of the component.
     */
    private String replaceAdditionalRestrictedCharacters(String component) {
        if (additionalRestrictedCharacterRegex != null) {
            StringBuffer buffer = new StringBuffer();
            Matcher matcher = additionalRestrictedCharacterRegex.matcher(component);
            while (matcher.find()) {
                String match = matcher.group();
                validateMatch(match);
                matcher.appendReplacement(buffer, "%" + asciiCharToHex(match.charAt(0)));
            }
            matcher.appendTail(buffer);
            component = buffer.toString();
        }
        return component;
    }

    /**
     * Converts the given character to a hexadecimal string containing exactly two digits.  Only characters that can
     * be represented as a two-character hexadecimal number are permitted.
     * 
     * @param c the character to convert.
     * @return the hexadecimal string.
     */
    private String asciiCharToHex(char c) {
        String hexString = Integer.toHexString(c).toUpperCase();
        if (hexString.length() > 2) {
            throw new FilesException("invalid character matched by regular expression");
        }
        while (hexString.length() < 2) {
            hexString = '0' + hexString;
        }
        return hexString;
    }

    /**
     * Validates a match made by the additional restricted character regular expression.
     * 
     * @param match the match.
     */
    private void validateMatch(String match) {
        int matchLength = StringUtils.length(match);
        if (matchLength != 1) {
            String msg = matchLength + " characters matched by restricted character"
                + " regular expression.  Expected 1.  Please check the configuration";
            throw new FilesException(msg);
        }
    }
}
