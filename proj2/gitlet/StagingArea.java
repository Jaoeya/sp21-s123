package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.readObject;
import static gitlet.MyUtils.*;

/**
 * @author jiaxing zhou
 * @version 1.0
 */
public class StagingArea implements Serializable {


    /**
     * The added files Map with file path as key and SHA1 id as value.
     */
    private final Map<String, String> added = new HashMap<>();

    /**
     * The removed files Set with file path as key.
     */
    private final Set<String> removed = new HashSet<>();

    /**
     * The tracked files Map with file path as key and SHA1 id as value.
     */
    private transient Map<String, String> tracked;


    /**
     * @param file
     * @return boolean value if the file is added to the staging area
     */

    public boolean add(File file) {

        String filePath = file.getPath();
        Blob blob = new Blob(file);
        String blobId = blob.getId();
        String trackedBlobId = tracked.get(filePath);

        if (trackedBlobId != null) {
            if (trackedBlobId.equals(blobId)) {
                if (added.remove(filePath) != null) {
                    return true;
                }
                return removed.remove(filePath);
            }
        }

        String prevBlobId = added.put(filePath, blobId);
        if (blobId.equals(prevBlobId)) {
            return false;
        }

        if (!blob.getFile().exists()) {
            blob.save();
        }
        return true;






      /*  File objectFile = getObjectFile(blobId);
        if (!objectFile.exists()) {
            StagingArea stageArea = StagingArea.fromFile();
            //stageArea.add(file.getName(),blobId );
            stageArea.save();
        }
        ///added.put(filePath, id);
        return true;*/
    }


    /**
     * Tells whether the staging area is clean,
     * which means no file is added, modified, or removed.
     *
     * @return true if is clean
     */
    public boolean isClean() {
        return added.isEmpty() && removed.isEmpty();
    }


    /**
     * get the index file for staging object
     *
     * @return StagingArea Object
     */

    public static StagingArea fromFile() {
        return readObject(Repository.INDEX, StagingArea.class);
    }


    /**
     * @return
     */
    public Map<String, String> getAdded() {
        return added;
    }


    public void save() {
        saveObjectFile(Repository.INDEX, this);
    }

    /**
     * Set tracked files.
     *
     * @param filesMap Map with file path as key and SHA1 id as value.
     */
    public void setTracked(Map<String, String> filesMap) {
        tracked = filesMap;
    }

}
