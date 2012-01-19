package org.iplantc.files.types;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.iplantc.files.FilesException;
import org.iplantc.files.metadata.BarcodeFileMetadata;
import org.iplantc.files.persistence.IRodsPermanentFileSaver;
import org.iplantc.files.service.result.FileAccessUrlResult;
import org.iplantc.persistence.dao.hibernate.HibernateWorkspaceDao;
import org.iplantc.persistence.dto.user.User;
import org.iplantc.persistence.dto.workspace.Workspace;
import org.json.JSONException;

public class BarcodeFileHandler extends AbstractFileTypeHandler implements FileTypeHandler {

    /**
     * The Hibernate session factory.
     */
    SessionFactory sessionFactory;

    /**
     * Used to save temporary copies of files that are stored in the database.
     */
    private IRodsPermanentFileSaver permanentFileSaver;

    /**
     * @param sessionFactory the Hibernate session factory.
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Sets the temporary file saver.
     * 
     * @param temporaryFileSaver the new temporary file saver.
     */
    public void setPermanentFileSaver(IRodsPermanentFileSaver FileSaver) {
        this.permanentFileSaver = FileSaver;
    }

    /**
     * TODO: this code does not work with the current data storage mechanism; update it so that it does.
     * 
     * @param barcodeInfo the barcode information string.
     * @return the file access URL information.
     */
    @Override
    public org.json.JSONObject getFileAccessUrl(String barcodeInfo) {
        return handleTemporaryFile(barcodeInfo);
    }

    private org.json.JSONObject handleTemporaryFile(String barcodeInfo) throws FilesException {
        try {
            BarcodeFileMetadata metadata = new BarcodeFileMetadata();

            int firstComma = barcodeInfo.indexOf(',');
            metadata.setWorkspaceId(Long.parseLong(barcodeInfo.substring(0, firstComma)));

            Workspace workspace = retrieveWorkspace(metadata.getWorkspaceId());
			User user = workspace.getUser();

            permanentFileSaver.setStorageUsername(user.getUsername());
            System.out.println(user.getUsername());
            int secondComma = barcodeInfo.indexOf(',', firstComma + 1);
            metadata.setName(barcodeInfo.substring(firstComma + 1, secondComma));
            metadata.setContents(barcodeInfo.substring(secondComma + 1, barcodeInfo.length()));
            String url = permanentFileSaver.save(metadata);
            metadata.setUrl(url);

            String dest = url.substring(0, url.lastIndexOf("/") + 1);

            System.out.println(dest);

            List<String> uri = new LinkedList<String>();

            uri.add(url);

            System.out.println(url);

            org.json.JSONObject result = new FileAccessUrlResult("0", decodeString(removeUserInfo(url)), true).toJson();

            String[] elements = url.split("/");
            result.put("file_name", elements[elements.length - 1]);
            return result;

        }
        catch (JSONException ex) {
            throw new FilesException(ex);
        }
        catch (Exception ex2) {
            throw new FilesException(ex2);
        }

    }

    private Workspace retrieveWorkspace(long workspaceId) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Workspace workspace = new HibernateWorkspaceDao(session).findById(workspaceId);
            tx.commit();
            return workspace;
        }
        catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
        finally {
            session.close();
        }
    }
}
