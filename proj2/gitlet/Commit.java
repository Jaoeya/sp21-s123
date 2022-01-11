package gitlet;

// TODO: any imports you need here


import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.MyUtils.*;
import static gitlet.Utils.readObject;
import static gitlet.Utils.sha1;


/**
 * Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     * <p>
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * sha1 id for this commit
     */

    private final String id;

    /**
     * timestamp info for commit
     */
    private final Date date;


    /**
     * The tracked files Map with file path as key and SHA1 id as value.
     */

    private final Map<String, String> tracked;


    private final List<String> parents;

    private final File file;

    /**
     * The message of this Commit.
     */

    private final String message;


    public Commit(String message, Map<String, String> tracked, List<String> parents) {
        this.date = new Date();
        this.message = message;
        this.tracked = tracked;
        this.parents = parents;
        id = generateId();
        this.file = getObjectFile(id);
    }


    public Commit() {
        this.date = new Date(0);
        this.message = "initial commit";
        this.parents = new ArrayList<>();
        this.tracked = new HashMap<>();
        id = generateId();
        this.file = getObjectFile(id);
    }

    public List<String> getParents() {
        return parents;
    }


    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }


    public Map<String, String> getTracked() {
        return tracked;
    }

    /**
     * @return
     */
    private String generateId() {
        return sha1(getTimestamp(), message, parents.toString(), tracked.toString());
    }

    public String getTimestamp() {
        // Thu Jan 1 00:00:00 1970 +0000
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        return dateFormat.format(date);
    }


    /**
     * Save this Commit instance to file in objects folder.
     */
    public void save() {
        saveObjectFile(file, this);
    }

    /**
     * @param id
     * @return read the commit object from file
     */

    public static Commit fromFile(String id) {
        return readObject(getObjectFile(id), Commit.class);
    }


    /**
     * Get the commit log.
     *
     * @return Log content
     */
    public String getLog() {
        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("===").append("\n");
        logBuilder.append("commit").append(" ").append(id).append("\n");
        if (parents.size() > 1) {
            logBuilder.append("Merge:");
            for (String parent : parents) {
                logBuilder.append(" ").append(parent, 0, 7);
            }
            logBuilder.append("\n");
        }
        logBuilder.append("Date:").append(" ").append(getTimestamp()).append("\n");
        logBuilder.append(message).append("\n");
        return logBuilder.toString();
    }

}
