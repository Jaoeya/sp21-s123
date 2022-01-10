package gitlet;

import static gitlet.MyUtils.*;
/**
 * Driver class for Gitlet, a subset of the Git version-control system.
 *
 * @author TODO
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if (args.length == 0) {
            System.out.println("Please enter a command");
            System.exit(0);
        }
        String firstArg = args[0];
        switch (firstArg) {
            case "init":
                // TODO: handle the `init` command
                Repository.init();
                Repository.setup();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                Repository.checkInit();
                String fileName = args[1];
                validationCommand(args, 2);
                Repository.add(fileName);
                break;
            // TODO: FILL THE REST IN
            case "status":
                Repository.checkInit();
                Repository.showStatus();
                break;
            case "log":
                Repository.checkInit();
                break;

            case "commit":
                Repository.checkInit();
                String commitMessage = args[1];
                Repository.commit(commitMessage);
                break;

            case "checkout":
                Repository.checkInit();
                break;

            default:
                System.out.println("No Command with that name exists");
                System.exit(0);
        }

    }

    /*
     *@param args input string
     *@param n Expected number of String
     */

    private static void validationCommand(String[] args, int n) {
        if (args.length != n) {
            exit("Incorrect operands");
        }
    }
}
