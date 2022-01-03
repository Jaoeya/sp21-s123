package deque;

/**
 * @author jiaxing zhou
 * @version 1.0
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("------Complementation------");
        System.out.println(7 % 4);
        System.out.println((-7) % 4);
        System.out.println(7 % (-4));
        System.out.println((-7) % (-4));

        System.out.println();
        System.out.println("------Modulo Operation------");
        System.out.println(Math.floorMod(7, 4));
        System.out.println(Math.floorMod(-7, 4));
        System.out.println(Math.floorMod(7, -4));
        System.out.println(Math.floorMod(-7, -4));
    }
}
