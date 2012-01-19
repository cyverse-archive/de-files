package org.iplantc.files.metadata;

public class BarcodeFileMetadata implements FileMetadata {

    private String name;
    private String contents;
    private String url;
    private long workspaceId;
    
    @Override
    public String getContents() {

        return contents;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	public long getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(long workspaceId) {
		this.workspaceId = workspaceId;
	}
    
    
}
