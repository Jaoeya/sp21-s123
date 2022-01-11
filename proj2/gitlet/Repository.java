package gitlet;


import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static gitlet.Commit.fromFile;
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
    /**
     * The object directory .
     */

    public static final File OBJECTS_DIR = join(GITLET_DIR, "objects");


    /**
     * The index file.
     */
    public static final File INDEX = join(GITLET_DIR, "index");


    /**
     * The HEAD file;
     */
    public static final File HEAD = join(GITLET_DIR, "HEAD");


    /**
     * The refs directory.
     */
    private static final File REFS_DIR = join(GITLET_DIR, "refs");

    /**
     * The heads directory.
     */
    private static final File BRANCH_HEADS_DIR = join(REFS_DIR, "heads");


    /**
     * Default branch name.
     */

    private static final String DEFAULT_BRANCH_NAME = "master";

    private final StagingArea stagingArea;
    /**
     * HEAD ref prefix.
     */
    private static final String HEAD_BRANCH_REF_PREFIX = "ref: refs/heads/";


    /**
     * The current branch name.
     */
    private final String currentBranch;
    /**
     * The commit that HEAD points to.
     */
    private final Commit headCommit;


    /**
     * Files in the current working directory.
     */
    private static final File[] currentFiles;


    static {
        currentFiles = CWD.listFiles();
    }

    public Repository() {


        String HEADFileContent = readContentsAsString(HEAD);
        currentBranch = HEADFileContent.replace(HEAD_BRANCH_REF_PREFIX, "");
        headCommit = getBranchHeadCommit(currentBranch);

        if (!INDEX.exists()) {
            this.stagingArea = new StagingArea();
            stagingArea.save();
        } else {
            this.stagingArea = StagingArea.fromFile();
        }

        stagingArea.setTracked(headCommit.getTracked());

    }


    public static void init() {
        if (GITLET_DIR.exists()) {
            exit("A Gitlet version-control system already exists in the current directory.");
        }
        mkdir(GITLET_DIR);
        mkdir(OBJECTS_DIR);
        mkdir(REFS_DIR);
        mkdir(BRANCH_HEADS_DIR);
        setCurrentBranch(DEFAULT_BRANCH_NAME);
        createInitialCommit();
    }


    /**
     * Set current branch.
     *
     * @param branchName Name of the branch
     */
    private static void setCurrentBranch(String branchName) {
        writeContents(HEAD, HEAD_BRANCH_REF_PREFIX + branchName);
    }

    /**
     * Create an initial commit.
     */
    private static void createInitialCommit() {
        Commit initialCommit = new Commit();
        initialCommit.save();
        setBranchHeadCommit(DEFAULT_BRANCH_NAME, initialCommit.getId());
    }


    /**
     * Get branch head ref file in refs/heads folder.
     *
     * @param branchName Name of the branch
     * @return File instance
     */
    private static File getBranchHeadFile(String branchName) {
        return join(BRANCH_HEADS_DIR, branchName);
    }


    /**
     * Set branch head.
     *
     * @param branchName Name of the branch
     * @param commitId   Commit SHA1 id
     */
    private static void setBranchHeadCommit(String branchName, String commitId) {
        File branchHeadFile = getBranchHeadFile(branchName);
        setBranchHeadCommit(branchHeadFile, commitId);
    }


    /**
     * Set branch head.
     *
     * @param branchHeadFile File instance
     * @param commitId       Commit SHA1 id
     */
    private static void setBranchHeadCommit(File branchHeadFile, String commitId) {
        writeContents(branchHeadFile, commitId);
    }


    public void showStatus() {
        StringBuilder statusBuilder = new StringBuilder();
        statusBuilder.append("=======Branches=======\n");
        statusBuilder.append("*").append(currentBranch()).append("\n");
        statusBuilder.append("=======Staged Files=======").append("\n");
        StagingArea stagingArea = StagingArea.fromFile();
        Map<String, String> map = stagingArea.getAdded();
        for (String key : map.keySet()) {
            statusBuilder.append(key).append("\n");
        }
        statusBuilder.append("=======Staged Files=======").append("\n");

        System.out.println(statusBuilder.toString());

    }


    /**
     * add the File to staging area
     *
     * @param fileName
     */


    /**
     * Add file to the staging area.
     *
     * @param fileName Name of the file
     */
    public void add(String fileName) {
        File file = getFileFromCWD(fileName);
        if (!file.exists()) {
            exit("File does not exist.");
        }
        if (stagingArea.add(file)) {
            stagingArea.save();
        }
    }


    /**
     * Get a File instance from CWD by the name.
     *
     * @param fileName Name of the file
     * @return File instance
     */
    private static File getFileFromCWD(String fileName) {
        return Paths.get(fileName).isAbsolute()
                ? new File(fileName)
                : join(CWD, fileName);
    }


    /**
     * commit the changes
     *
     * @param commitMessage
     */

    public static void commit(String commitMessage) {
//
//        Commit currentCommit = readObject(HEAD, Commit.class);
//
//        StagingArea stagingArea = readObject(INDEX, StagingArea.class);
//        //
//        //save all the changes
//        stagingArea.save();
    }

    public static void checkInit() {
        if (!GITLET_DIR.exists()) {
            exit("Not in an initialized Gitlet directory");
        }
    }


    /**
     * Print log of the current branch.
     */
    public void log() {

        String currentHead = currentBranch();
        StringBuilder logBuilder = new StringBuilder();
        String currentCommitId = getCurrentHeadId(currentHead);
        Commit currentCommit = fromFile(currentCommitId);
        while (true) {
            logBuilder.append(currentCommit.getLog()).append("\n");
            List<String> parentCommitIds = currentCommit.getParents();
            if (parentCommitIds.size() == 0) {
                break;
            }
            String firstParentCommitId = parentCommitIds.get(0);
            currentCommit = fromFile(firstParentCommitId);
        }
        System.out.print(logBuilder);
    }

    /**
     * The current branch name.
     */
    private String currentBranch() {
        String head = readContentsAsString(HEAD);
        return head.replace(HEAD_BRANCH_REF_PREFIX, "");
    }

    /**
     * @param headName
     * @return String: current commit id which head points to
     */

    private String getCurrentHeadId(String headName) {
        File file = join(BRANCH_HEADS_DIR, headName);
        String currentCommitId = readContentsAsString(file);
        return currentCommitId;
    }


    /**
     * Get head commit of the branch.
     *
     * @param branchName Name of the branch
     * @return Commit instance
     */
    private static Commit getBranchHeadCommit(String branchName) {
        File branchHeadFile = getBranchHeadFile(branchName);
        return getBranchHeadCommit(branchHeadFile);
    }

    /**
     * Get head commit of the branch.
     *
     * @param branchHeadFile File instance
     * @return Commit instance
     */
    private static Commit getBranchHeadCommit(File branchHeadFile) {
        String HEADCommitId = readContentsAsString(branchHeadFile);
        return Commit.fromFile(HEADCommitId);
    }

}
