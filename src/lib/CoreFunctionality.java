package lib;

import lib.Errors.InvalidMatrixSizeException;
import lib.Errors.InvalidMatrixSquareException;
import lib.Errors.NoInverseException;
import lib.Errors.NoSolutionException;

public class CoreFunctionality {
    static public class solveSPL {
        /**
         * Prosedur yang menerima masukan SPL dari pengguna lalu mencetak
         * solusinya. Solusi didapatkan dari eliminasi Gauss.
         */
        static public void gauss() {
            Matrix matrix = IOLib.chooseToReadFromFile() ? FromFile.SPL()
                    : FromKeyboard.SPL();
            Matrix solution = matrix.getSolG();
            // ToKeyboard.printMatrix(solution);
            if (IOLib.chooseToWriteToFile()) {
                ToFile.SPL(solution);
            } else {
                IOLib.SPLSolution.print(solution);
            }
        }

        /**
         * Prosedur yang menerima masukan SPL dari pengguna lalu mencetak
         * solusinya. Solusi didapatkan dari eliminasi Gauss-Jordan.
         */
        static public void gaussJordan() {
            Matrix matrix = IOLib.chooseToReadFromFile() ? FromFile.SPL()
                    : FromKeyboard.SPL();
            Matrix solution = matrix.getSolGJ();
            // ToKeyboard.printMatrix(solution);
            if (IOLib.chooseToWriteToFile()) {
                ToFile.SPL(solution);
            } else {
                IOLib.SPLSolution.print(solution);
            }
        }

        /**
         * Prosedur yang menerima masukan SPL dari pengguna lalu mencetak
         * solusinya. Solusi didapatkan dari matriks balikan.
         */
        static public void inverse() {
            Matrix matrix = IOLib.chooseToReadFromFile() ? FromFile.SPL()
                    : FromKeyboard.SPL(); // Get solution inverse
            try {
                Matrix inverse = matrix.getInverseAdjoin();
                if (IOLib.chooseToWriteToFile()) {
                    ToFile.inverse(inverse);
                } else {
                    ToKeyboard.printMatrix(inverse);
                }
            } catch (NoInverseException e) {
                ToKeyboard.printMessage("Matriks ini singular. Tidak punya inverse");
            }
            // IOLib.SPLSolution.print(solution);

        }

        /**
         * Prosedur yang menerima masukan SPL dari pengguna lalu mencetak
         * solusinya. Solusi didapatkan dari kaidah Cramer.
         */
        static public void cramer() {
            Matrix matrix = IOLib.chooseToReadFromFile() ? FromFile.SPL()
                    : FromKeyboard.SPL();
            try {
                Matrix solution = matrix.getSolCramer();
                if (IOLib.chooseToWriteToFile()) {
                    ToFile.SPL(solution);
                } else {
                    IOLib.SPLSolution.printUnique(solution);
                }
            } catch (InvalidMatrixSizeException e) {
            } catch (NoSolutionException e) {
                ToKeyboard.printMessage(
                        "Solusi SPL tidak unik. Tidak bisa diselesaikan dengan kaidah cramer! Berikut solusinya dengan eliminasi Gauss-Jordan :");
                Matrix solution = matrix.getSolGJ();
                if (IOLib.chooseToWriteToFile()) {
                    ToFile.SPL(solution);
                } else {
                    IOLib.SPLSolution.print(solution);
                }
            }
        }
    }

    /**
     * Prosedur yang menerima masukan matriks dari pengguna lalu mencetak
     * determinannya.
     */
    static public void computeDeterminant() {
        Matrix matrix = IOLib.chooseToReadFromFile() ? FromFile.matrixToDetermine()
                : FromKeyboard.MatrixSquare();
        try {
            double determinant = matrix.getDeterminantCofactor();
            if (IOLib.chooseToWriteToFile()) {
                ToFile.determinant(determinant);
            } else {
                ToKeyboard.printNumber(determinant, "Determinan matriksnya adalah : ");
                ToKeyboard.printMessage("\n");
            }

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
        Matrix matrix = IOLib.chooseToReadFromFile() ? FromFile.matrixToInvert() : FromKeyboard.MatrixSquare();

        try {
            Matrix inverse = matrix.getInverseAdjoin();
            if (IOLib.chooseToWriteToFile()) {
                ToFile.inverse(inverse);
            } else {
                ToKeyboard.printMatrix(inverse);
            }

        } catch (NoInverseException e) {
            ToKeyboard.printMessage("Matriks adalah singular. Tidak punya inverse");
        }

        // Take input of matrice and do stuff with it accordingly
    }
}
