package org.iplantc.files.service;

import java.util.Map;

import org.apache.log4j.Logger;
import org.iplantc.files.FilesException;
import org.iplantc.files.service.result.FileAccessUrlResult;
import org.iplantc.files.types.FileTypeHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Provides a way to obtain information about files.
 * 
 * @author Dennis Roberts
 */
public class FileInfoService {

	private static final Logger LOG = Logger.getLogger(FileInfoService.class);

	/**
	 * The file type used for files that have metadata in the iPlant database.
	 */
	private static final String DATABASE_FILE_TYPE = "File";

	/**
	 * Maps file types to file type handlers.
	 */
	Map<String, FileTypeHandler> fileTypeHandlerMap;

	/**
	 * Sets the map used to identify the appropriate file type handler for a given file type.
	 * 
	 * @param fileTypeHandlerMap the new file type handler map.
	 */
	public void setFileTypeHandlerMap(Map<String, FileTypeHandler> fileTypeHandlerMap) {
		this.fileTypeHandlerMap = fileTypeHandlerMap;
	}

	/**
	 * Determines whether or not the given type of file can be handled by the file info service.
	 * 
	 * @param fileType the file type.
	 * @return true if the file can be handled by the file info service.
	 */
	public boolean canHandleFileType(String fileType) {
		return fileTypeHandlerMap.get(fileType) == null ? false : true;
	}

	/**
	 * Obtains the URL for a single file.
	 * The difference is that we will be sending
	 * a single JSON object and not being embedded within
	 * an "input" key-array. this is intended for clarity
	 * at the time of resolution.
	 * 
	 * @param jsonString - a JSON string containing the file ID and type.
	 * @return the same JSON string with the URLs plugged in.
	 * 
	 */
	public String getSingleFileAccessUrl(String jsonString) {
		LOG.debug("getting file access urls for: " + jsonString);
		try {


			JSONObject fileDescriptor = new JSONObject(jsonString);
			String fileId = getFileId(fileDescriptor);
			LOG.debug("File ID: " + fileId);
			String fileType = fileDescriptor.getString("type");
			LOG.debug("File Type: " + fileType);
			JSONObject urlInfo = getFileTypeHandler(fileType).getFileAccessUrl(fileId);
			LOG.debug("URL Info: " + urlInfo);
			fileDescriptor.put("urlInfo", urlInfo);

			String result = fileDescriptor.toString();
			LOG.debug(result);
			return result;
		}
		catch (JSONException e) {
			throw new FilesException("invalid input JSON", e);
		}
	}

	/**
	 * Obtains a URL that can be used to access multiple files.
	 * 
	 * @param jsonString - a JSON string containing the file IDs and types.
	 * @return the same JSON string with the URLs plugged in.
	 */
	public String getFilesAccessUrls(String jsonString) {
		LOG.debug("getting file access urls for: " + jsonString);
		try {
			JSONObject json = new JSONObject(jsonString);
			JSONArray files = json.getJSONArray("input");
			for (int i = 0; i < files.length(); i++) {
				JSONObject fileDescriptor = files.getJSONObject(i);
				String fileId = getFileId(fileDescriptor);
				LOG.debug("File ID: " + fileId);
				String fileType = fileDescriptor.getString("type");
				LOG.debug("File Type: " + fileType);
				JSONObject urlInfo = getFileTypeHandler(fileType).getFileAccessUrl(fileId);
				LOG.debug("URL Info: " + urlInfo);
				fileDescriptor.put("urlInfo", urlInfo);
			}
			String result = files.toString();
			LOG.debug(result);
			return result;
		}
		catch (JSONException e) {
			throw new FilesException("invalid input JSON", e);
		}
	}

	/**
	 * Gets the file identifier from the given file descriptor.  The file identifier might be found in the name field
	 * or in the id field, depending on the type of file.
	 * 
	 * @param fileDescriptor the file descriptor.
	 * @return the file identifier.
	 * @throws JSONException if neither file identifier field is defined.
	 */
	private String getFileId(JSONObject fileDescriptor) throws JSONException {
		return fileDescriptor.optString("name");
	}

	/**
	 * Obtains a URL that can be used to access a file. If no such URL exists (for example if the file resides in a
	 * database) then the file contents are moved to a location where a URL can be used to access the file and a flag
	 * indicating that the file is temporary is returned to the caller along with the URL.
	 * 
	 * @param fileId the file identifier.
	 * @return a JSON string in the format, {"fileId":id,"url":url,"temporary":flag}
	 */
	public String getFileAccessUrl(String fileId) {
		return getFileTypeHandler(DATABASE_FILE_TYPE).getFileAccessUrl(fileId).toString();
	}

	/**
	 * Formats a file access URL result.
	 * 
	 * @param fileId the file identifier.
	 * @param url the URL used to access the file.
	 * @param temporary true if the file should be treated as temporary.
	 * @return a JSON string in the format, {"fileId":id,"url":url,"temporary":flag}
	 */
	public String formatFileAccessUrl(Long fileId, String url, Boolean temporary) {
		return new FileAccessUrlResult(fileId.toString(), url, temporary.booleanValue()).toJson().toString();
	}

	/**
	 * Attempts to generate a unique filename by appending the current time to the given name (before any file name
	 * extensions).
	 * 
	 * @param originalName the name of the file.
	 * @return the base name of the file followed by an underscore, the current timestamp and the file name extension.
	 */
	public String assignUniqueName(String originalName) {
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

	/**
	 * Determines the file type handler for the given file type.
	 * 
	 * @param fileType the file type to get the handler for.
	 * @return the file type handler.
	 * @throws FilesException if no file type handler is available for the file type.
	 */
	private FileTypeHandler getFileTypeHandler(String fileType) {
		FileTypeHandler fileTypeHandler = fileTypeHandlerMap.get(fileType);
		if (fileTypeHandler == null) {
			throw new FilesException("unrecognized file type: " + fileType);
		}
		return fileTypeHandler;
	}
}
