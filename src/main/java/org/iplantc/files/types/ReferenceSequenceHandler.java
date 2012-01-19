package org.iplantc.files.types;

import org.iplantc.files.service.result.FileAccessUrlResult;
import org.json.JSONObject;

/**
 * Used to obtain information about reference annotations. This means the
 * url has appended to it "genomes.gtf".
 * 
 * @author Dennis Roberts & Juan Antonio Raygoza
 */
public class ReferenceSequenceHandler extends ReferenceGenomeHandler implements FileTypeHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public JSONObject getFileAccessUrl(String fileId) {
        String url = getBaseFileAccessUrl(fileId) + "genome.fas";
        return new FileAccessUrlResult(fileId, url, false).toJson();
    }
}
