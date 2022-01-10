package capers;

import java.io.File;
import java.io.IOException;

import static capers.Utils.*;

/**
 * A repository for Capers
 *
 * @author TODO
 * The structure of a Capers Repository is as follows:
 * <p>
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 * - dogs/ -- folder containing all of the persistent data for dogs
 * - story -- file containing the current story
 * <p>
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /**
     * Current Working Directory.
     */
    static final File CWD = new File(System.getProperty("user.dir"));

    /**
     * Main metadata folder.
     */
    static final File CAPERS_FOLDER = null; // TODO Hint: look at the `join`
    //      function in Utils

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     * <p>
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     * - dogs/ -- folder containing all of the persistent data for dogs
     * - story -- file containing the current story
     */
    public static void setupPersistence() {
        //String cwd = System.getProperty("user.dir");
        File f = Utils.join(CWD, ".capers");
        File base = f;
        f.mkdir();
        f = Utils.join(base, "story");
        f.mkdir();
        f = Utils.join(base, "dogs");
        f.mkdir();
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     *
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        // TODO
        File f = Utils.join(CWD, ".capers/story/story.txt");
        StringBuilder story = new StringBuilder();
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        story.append(Utils.readContentsAsString(f));
        story.append(text);
        Utils.writeContents(f, story.toString() + "\n");
        System.out.println(story);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        // TODO
        Dog dog = new Dog(name, breed, age);
        File object = Utils.join(CWD, ".capers/dogs/" + name);
        Utils.writeObject(object, dog);
        System.out.println(dog);

    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     *
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        // TODO
        Dog dog;
        File f = Utils.join(CWD, ".capers/dogs/" + name);
        dog = Dog.fromFile(f.getAbsolutePath());
        dog.haveBirthday();
        dog.saveDog();
    }
}
