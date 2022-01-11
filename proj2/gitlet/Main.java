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
                validationCommand(args, 1);
                Repository.init();

                break;
            case "add":
                validationCommand(args, 2);
                Repository.checkInit();
                String fileName = args[1];
                new Repository().add(fileName);
                break;
            case "status":
                validationCommand(args, 1);
                Repository.checkInit();
                new Repository().showStatus();
                break;
            case "log":
                validationCommand(args, 1);
                Repository.checkInit();
                new Repository().log();
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

    /**
     * @param args
     * @param n
     */

    private static void validationCommand(String[] args, int n) {
        if (args.length != n) {
            exit("Incorrect operands");
        }
    }
}
