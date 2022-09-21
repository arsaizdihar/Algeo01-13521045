package lib;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.text.AbstractDocument.Content;

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

    static private String parameterVariableMaker(int index) {
        char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        String createdVariable = "";
        int usedCharacterN = alphabet.length;
        int evaluatedIndex = index;
        int timesReduced = 0;

        while (evaluatedIndex > usedCharacterN) {
            createdVariable += alphabet[timesReduced % usedCharacterN];
            timesReduced += 1;
            evaluatedIndex -= usedCharacterN;
        }

        createdVariable += alphabet[evaluatedIndex];
        return createdVariable;
    }

    static private boolean isNthVariableParametric(Matrix solution, int i) {
        return solution.getElmt(i, solution.getNCol() - 1) == 0;
    }

    static private boolean isNthVariableContainParametric(Matrix solution, int i) {
        boolean containParametric = false;
        int j = 0;
        while (j < solution.getNCol() - 2 && !containParametric) {
            if (solution.getElmt(i, j) != 0) {
                containParametric = true;
            }
            j++;
        }
        return containParametric;
    }

    static private String[] writeSolution(Matrix solution, String variableSymbol, int digitsAfterComma) {
        NumberFormat numberFormatter = NumberFormat.getInstance();
        numberFormatter.setMaximumFractionDigits(digitsAfterComma);

        HashMap<Integer, String> parameter = new HashMap<Integer, String>();
        // Catat parameter yang ada dan buat nama parameternya
        int nthParameter = 0;
        for (int i = solution.getNRow() - 1; i >= 0; i--) {
            if (isNthVariableParametric(solution, i)) {
                nthParameter += 1;
                parameter.put(i, parameterVariableMaker(nthParameter));
            }
        }

        // Inisialisasi array berisi teks nilai konstan (b) dari variabel non-parametrik
        // (abaikan variabel parametrik) dan nama variabel parametrik untuk variabel
        // parametrik
        ArrayList<String> variableValuesString = new ArrayList<String>();
        for (int i = 0; i < solution.getNRow(); i++) {
            double constant = solution.getElmt(i, solution.getNCol() - 2);
            boolean isParametric = isNthVariableParametric(solution, i);
            boolean containParametric = isNthVariableContainParametric(solution, i);
            String valueString = isParametric ? parameter.get(i)
                    : constant == 0 && containParametric ? "" : numberFormatter.format(constant);
            variableValuesString.add(valueString);
        }

        if (nthParameter > 0) {
            // Isi array lagi, kali ini dengan khusus menambahkan parameter ke variabel
            // non-parametrik
            for (int i = 0; i < solution.getNRow(); i++) {
                for (int j = 0; j < solution.getNCol() - 2; j++) {
                    double examinedCoefficient = solution.getElmt(i, j);
                    String addedString = "";
                    if (examinedCoefficient != 0) {
                        addedString += String.format("%s%s",
                                (examinedCoefficient == 1 ? "" : numberFormatter.format(examinedCoefficient)),
                                parameter.get(j));
                    }

                    if ((variableValuesString.get(i) == "" && examinedCoefficient > 0)) {
                        addedString = "+" + addedString;
                    }

                    if (variableValuesString.get(i) == "" && addedString != "") {
                        // addedString = " " + addedString;
                    }

                    String modifiedString = String.format("%s%s", variableValuesString.get(i), addedString);
                    variableValuesString.set(i, modifiedString);
                }
            }
            // Isi array lagi, kali ini dengan khusus menambahkan nama parameter ke variabel
            // parametrik
        }

        // Dari string tadi, membentuk baris dengan contoh format berikut : x1 = t + 6
        ArrayList<String> outputLines = new ArrayList<String>();
        for (int i = 0; i < variableValuesString.size(); i++) {
            outputLines.add(String.format("%s%d = %s", variableSymbol, i + 1, variableValuesString.get(i)));
        }

        return outputLines.stream().toArray(String[]::new);
    }

    static public String[] writeSolution(Matrix solution, String variableSymbol) {
        return writeSolution(solution, variableSymbol, 2);
    }

    /**
     * Prosedur yang menerima masukan SPL dari pengguna lalu mencetak
     * solusinya. Solusi didapatkan dari eliminasi Gauss.
     */
    static public void solveWithGauss() {
        Matrix matrix = FromKeyboard.SPL();
        Matrix solution = matrix.getSolGJ();
        ToKeyboard.printMatrix(solution);
        String[] solutionTexts = writeSolution(solution, "x");
        for (int i = 0; i < solutionTexts.length; i++) {
            ToKeyboard.printMessage(solutionTexts[i]);
        }
    }

    /**
     * Prosedur yang menerima masukan SPL dari pengguna lalu mencetak
     * solusinya. Solusi didapatkan dari eliminasi Gauss-Jordan.
     */
    static public void solveWithGaussJordan() {
        Matrix matrix = FromKeyboard.SPL();
        matrix.getSolG();

    }

    /**
     * Prosedur yang menerima masukan SPL dari pengguna lalu mencetak
     * solusinya. Solusi didapatkan dari matriks balikan.
     */
    static public void solveWithInverse() {
        Matrix matrix = FromKeyboard.SPL();

    }

    /**
     * Prosedur yang menerima masukan SPL dari pengguna lalu mencetak
     * solusinya. Solusi didapatkan dari kaidah Cramer.
     */
    static public void solveWithCramer() {
        Matrix matrix = FromKeyboard.SPL();

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
