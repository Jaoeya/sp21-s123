package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.MyUtils.getObjectFile;
import static gitlet.MyUtils.saveObjectFile;
import static gitlet.Utils.*;


/**
 * @author jiaxing zhou
 * @version 1.0
 */
public class Blob implements Serializable {


    /**
     * this sha1 id will be used as the name to store the blob object
     */

    private String id;
    /**
     * The original file name such as test1.txt
     */
    private File source;


    /**
     * The file which stores the blob object
     */
    private File file;
    /**
     * content from the original source file
     */
    private byte[] content;


    public Blob(File sourceFile) {
        this.source = sourceFile;
        String filePath = sourceFile.getPath();
        this.content = readContents(source);
        this.id = sha1(filePath, content);
        this.file = getObjectFile(id);
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @return object file for blob object
     */
    public File getFile() {
        return file;
    }


    /**
     * save the object
     */
    public void save() {
        saveObjectFile(file, this);
    }

    /**
     * @param file
     * @return
     */
    public static Blob readBlob(File file) {
        Blob blob = readObject(file, Blob.class);
        return blob;
    }


}
