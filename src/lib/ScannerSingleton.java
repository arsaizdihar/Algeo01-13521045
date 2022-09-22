package lib;

import java.util.Scanner;

public class ScannerSingleton {
    private Scanner scanner;
    private static ScannerSingleton singleton = null;
    // private boolean alreadyClosed;

    private ScannerSingleton() {
        // alreadyClosed = false;
        scanner = new Scanner(System.in);
    }

    public static ScannerSingleton getInstance() {
        if (singleton == null) {
            singleton = new ScannerSingleton();
        }
        return singleton;
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public int nextInt() {
        return scanner.nextInt();
    }
}
