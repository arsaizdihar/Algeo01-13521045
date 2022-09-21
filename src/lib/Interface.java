package lib;

import lib.Errors.InvalidMatrixSquareException;

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
     * Prosedur yang menerima masukan SPL dari pengguna lalu mencetak
     * solusinya. Solusi didapatkan dari eliminasi Gauss.
     */
    static public void solveWithGauss() {
        Matrix matrix = FromKeyboard.MatrixSquare();

    }

    /**
     * Prosedur yang menerima masukan SPL dari pengguna lalu mencetak
     * solusinya. Solusi didapatkan dari eliminasi Gauss-Jordan.
     */
    static public void solveWithGaussJordan() {
        Matrix matrix = FromKeyboard.MatrixSquare();

    }

    /**
     * Prosedur yang menerima masukan SPL dari pengguna lalu mencetak
     * solusinya. Solusi didapatkan dari matriks balikan.
     */
    static public void solveWithInverse() {
        Matrix matrix = FromKeyboard.MatrixSquare();

    }

    /**
     * Prosedur yang menerima masukan SPL dari pengguna lalu mencetak
     * solusinya. Solusi didapatkan dari kaidah Cramer.
     */
    static public void solveWithCramer() {
        Matrix matrix = FromKeyboard.MatrixSquare();

    }

    /**
     * Prosedur yang menerima masukan matriks dari pengguna lalu mencetak
     * determinannya.
     */
    static public void computeDeterminant() {
        ToKeyboard.printMessage("Memasukkan matriks");
        Matrix matrix = FromKeyboard.MatrixSquare();

        try {
            double determinant = matrix.getDeterminantCofactor();
            ToKeyboard.printNumber(determinant, "Determinan matriksnya adalah : ");
            ToKeyboard.printMessage("\n");

        } catch (InvalidMatrixSquareException e) {
            throw new RuntimeException();
        }

        // Take input of matrice and do stuff with it accordingly
    }

    // This is not functional yet because the determinantFinder doesn't throw an
    // error yet.
    /**
     * Prosedur yang menerima masukan matriks dari pengguna lalu mencetak
     * inverse-nya. Jika matriks singular maka error ditangkap dan dikeluarkan ke
     * layar bahwa matriks singular.
     */
    static public void computeInverse() {
        Matrix matrix = FromKeyboard.MatrixSquare();

        try {
            Matrix inverse = matrix;
            ToKeyboard.printMatrix(inverse);

        } catch (Exception e) {
            ToKeyboard.printMessage("Matriks adalah singular. Tidak punya inverse");
        }

        // Take input of matrice and do stuff with it accordingly
    }

    /**
     * Fungsi yang mewakili subprogram sub menu pemecahan SPL. Menerima input
     * pilihan submenu pengguna dan menjalankan subprogram yang bersesuaian
     * 
     */
    static public void solveSPL() {
        printMenu(subMenuText);
        int userChoiceSubMenu = FromKeyboard.readNumber("pilihan sub menu", 1, 5);
        switch (userChoiceSubMenu) {
            case 1:
                solveWithGauss();
                // Solve with Gauss and display the result
                break;
            case 2:
                solveWithGaussJordan();
                // Solve with Gauss Jordan and display the result
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
                            solveSPL();
                            programState = "main";

                            // Take input of SPL and do stuff with it accordingly
                            break;
                        case 2:
                            computeDeterminant();
                            programState = "main";
                            break;
                        case 3:
                            computeInverse();
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

                    break;
            }
        }
    }
}
