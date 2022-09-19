package lib;

import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class

public class Interface {

    private static String[] mainMenuText = new String[] {
            "Sistem Persamaaan Linier",
            "Determinan",
            "Matriks balikan",
            "Interpolasi Polinom",
            "Interpolasi Bicubic",
            "Regresi linier berganda",
            "Keluar"
    };

    // Kemungkinan besar ini bakal ditaruh di dalam fungsi untuk SPL
    private static String[] subMenuText = new String[] {
            "Metode eliminasi Gauss",
            "Metode eliminasi Gauss-Jordan",
            "Metode matriks balikan",
            "Kaidah Cramer",
            "Kembali ke main menu",
    };

    static public void printMenu(String[] menu) {

        for (int i = 0; i < menu.length; i++) {
            System.out.printf("%d. %s\n", i + 1, menu[i]);
        }
        System.out.println("\n");
    }

    // static public class
    static public void mainEventLoop() {
        String programState = "main";
        Integer backToMainOption[] = { 1, 2, 3, 4, 5, 6 };
        int userChoice = 0;
        while (programState != "exited") {
            switch (programState) {
                case "main":
                    printMenu(mainMenuText);
                    programState = "mainEntering";
                    break;
                case "mainEntering":
                    Scanner userChoiceReceiver = new Scanner(System.in);
                    userChoice = userChoiceReceiver.nextInt();
                    userChoiceReceiver.close();
                    programState = "mainEntered";
                    break;
                case "mainEntered":
                    switch (userChoice) {
                        case 1:
                            // Take input of SPL and do stuff with it accordingly
                            break;
                        case 2:
                            // Take input of matrice and do stuff with it accordingly
                            break;
                        case 3:
                            // Take input of matrice and do stuff with it accordingly
                            break;
                        case 4:
                            // Take input of a set of points and do stuff with it accordingly
                            break;
                        case 5:
                            // Take input of a set of points and do stuff with it accordingly
                            break;
                        case 6:
                            // Take input of a set of points and do stuff with it accordingly
                            break;
                        case 7:
                            programState = "exited";
                            break;
                        default:
                            System.out.println("Not a valid input!");
                            programState = "main";
                            break;
                    }
                    if (Arrays.asList(backToMainOption).contains(userChoice)) {
                        programState = "main";
                    }
                    break;
            }
        }
    }
}
