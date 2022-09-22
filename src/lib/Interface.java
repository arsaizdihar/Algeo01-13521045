package lib;

public class Interface {

    private static final String[] mainMenuText = new String[] {
            "Sistem Persamaaan Linier",
            "Determinan",
            "Matriks balikan",
            "Interpolasi Polinom",
            "Interpolasi Bicubic",
            "Regresi linier berganda",
            "Keluar"
    };

    // Kemungkinan besar ini bakal ditaruh di dalam fungsi untuk SPL
    private static final String[] subMenuText = new String[] {
            "Metode eliminasi Gauss",
            "Metode eliminasi Gauss-Jordan",
            "Metode matriks balikan",
            "Kaidah Cramer",
            "Kembali ke main menu",
    };

    /**
     * Mencetak menu yang diberikan beserta nomornya.
     * 
     * @param menu array berisi pilihan menu
     */
    static public void printMenu(String[] menu) {

        for (int i = 0; i < menu.length; i++) {
            System.out.printf("%d. %s\n", i + 1, menu[i]);
        }
        System.out.println("\n");
    }

    /**
     * Fungsi yang mewakili subprogram sub menu pemecahan SPL. Menerima input
     * pilihan submenu pengguna dan menjalankan subprogram yang bersesuaian
     * 
     */
    static public void menuSPL() {
        printMenu(subMenuText);
        int userChoiceSubMenu = FromKeyboard.readNumber("pilihan sub menu", 1, 5);
        switch (userChoiceSubMenu) {
            case 1:
                CoreFunctionality.solveSPL.gauss();
                break;
            case 2:
                CoreFunctionality.solveSPL.gaussJordan();
                break;
            case 3:
                // Solve with inverted matrix and display the result
                break;
            case 4:
                // Solve with Cramer and display the result
                break;
            case 5:
                break;

        }
    }

    /**
     * Fungsi yang mewakili main event loop program. Menampilkan menu,
     * menerima input pilihan menu user,
     * menjalankan fungsi yang berupa subprogram
     * 
     */
    static public void mainEventLoop() {
        String programState = "main";
        int userChoice = 0;
        while (programState != "exited") {
            switch (programState) {
                case "main":
                    printMenu(mainMenuText);
                    programState = "mainEntering";
                    break;
                case "mainEntering":
                    userChoice = FromKeyboard.readNumber("pilihan menu", 1, 7);
                    // ToKeyboard.printMessage("\n");
                    programState = "mainEntered";
                    break;
                case "mainEntered":
                    switch (userChoice) {
                        case 1:
                            menuSPL();
                            FromKeyboard.EnterToContinue();
                            programState = "main";
                            break;
                        case 2:
                            CoreFunctionality.computeDeterminant();
                            FromKeyboard.EnterToContinue();
                            programState = "main";
                            break;
                        case 3:
                            CoreFunctionality.computeInverse();
                            FromKeyboard.EnterToContinue();
                            programState = "main";
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
                            ToKeyboard.printMessage("Good luck tubesnya!");
                            programState = "exited";
                            break;
                        default:
                            System.out.println("Not a valid input!");
                            programState = "main";
                            break;

                    }

                    break;
            }
        }
    }
}
