package capers;

import java.io.File;

/**
 * @author jiaxing zhou
 * @version 1.0
 */
public class Test {
    public static void main(String[] args) {
        File f = new File("dummy.txt");
        Utils.writeContents(f,"dummy.txt");
    }
}
