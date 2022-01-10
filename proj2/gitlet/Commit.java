package gitlet;

// TODO: any imports you need here

import javafx.scene.input.DataFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author TODO
 */
public class Commit {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * The message of this Commit.
     */
    private String message;

    /* TODO: fill in the rest of this class. */
    private String sha1;

    private Date timeStamp;

    private Commit preCommit;

    private Map<String, Blob> mapFiles;


    public Commit(String message, String sha1, Date timeStamp) {
        this.message = message;
        this.sha1 = sha1;
        this.timeStamp = timeStamp;

    }

    /*
     *@param
     *@return Date String
     *
     *
     */

    private String getTimeStamp() {
        timeStamp = new Date();
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.ENGLISH);
        return dateFormat.format(timeStamp);
    }

}
