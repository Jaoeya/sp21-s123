package gitlet;


import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static gitlet.Utils.*;


/**
 * @author jiaxing zhou
 * @version 1.0
 */
public class MyUtils {

    public static void exit(String msg) {
        System.out.println(msg);
        System.exit(0);
    }

    /**
     * @param dir
     */
    public static void mkdir(File dir) {
        if (!dir.mkdir()) {
            throw new IllegalArgumentException(String.format("mkdir: %s: Failed to create.", dir.getPath()));
        }
    }


    public static String getTimeStamp() {
        Date timeStamp = new Date();
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        return dateFormat.format(timeStamp);
    }


    /**
     * Get a File instance with the path generated from SHA1 id in the objects folder.
     *
     * @param id SHA1 id
     * @return File instance
     */
    public static File getObjectFile(String id) {
        String dirName = getObjectDirName(id);
        String fileName = getObjectFileName(id);
        return join(Repository.OBJECTS_DIR, dirName, fileName);
    }


    /**
     * Get directory name from SHA1 id in the objects folder.
     *
     * @param id SHA1 id
     * @return Name of the directory
     */
    public static String getObjectDirName(String id) {
        return id.substring(0, 2);
    }

    /**
     * Get file name from SHA1 id.
     *
     * @param id SHA1 id
     * @return Name of the file
     */
    public static String getObjectFileName(String id) {
        return id.substring(2);
    }

    /**
     * Save the serializable object to the file path.
     * Create a parent directory if not exists.
     *
     * @param file File instance
     * @param obj  Serializable object
     */
    public static void saveObjectFile(File file, Serializable obj) {
        File dir = file.getParentFile();
        if (!dir.exists()) {
            mkdir(dir);
        }
        writeObject(file, obj);
    }


}
