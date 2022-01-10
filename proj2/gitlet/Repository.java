package gitlet;


import java.io.File;

import static gitlet.Utils.*;
import static gitlet.MyUtils.*;

// TODO: any imports you need here

/**
 * Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /**
     * The current working directory.
     */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /**
     * The .gitlet directory.
     */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /* TODO: fill in the rest of this class. */

    public static void setup() {
        File stageFolder = join(GITLET_DIR, "stage");
        if (!stageFolder.exists()) {
            stageFolder.mkdir();
        }
        File commitFolder = join(GITLET_DIR, "commit");
        if (!commitFolder.exists()) {
            commitFolder.mkdir();
        }
    }

    public static void init() {
        if (GITLET_DIR.exists()) {
            exit("A Gitlet version-control system already exists in the current directory.");
        }
        GITLET_DIR.mkdir();
        commit("initial commit");
    }

    public static void showStatus() {
        System.out.println("=======Branches=======");
        System.out.println("=======Staged Files=======");
        System.out.println("=======Removed Files=======");
        System.out.println("=======Notifications Not Staged For Commits=======");
        System.out.println("=======Untracked Files=======");

    }


    public static void add(String fileName) {
        File file = new File(fileName);
    }

    public static void commit(String commitMessage) {

    }

    public static void checkInit() {
        if (!GITLET_DIR.exists()) {
            exit("Not in an initialized Gitlet directory");
        }
    }
}
