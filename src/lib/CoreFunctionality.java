package lib;

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
            try {
                Matrix solution = matrix.getSolG();
                if (IOLib.chooseToWriteToFile()) {
                    ToFile.SPL(solution);
                } else {
                    IOLib.SPLSolution.print(solution);
                }
            } catch (NoSolutionException e) {
                ToKeyboard.printMessage("SPL tidak punya solusi");
            }
        }

        /**
         * Prosedur yang menerima masukan SPL dari pengguna lalu mencetak
         * solusinya. Solusi didapatkan dari eliminasi Gauss-Jordan.
         */
        static public void gaussJordan() {
            Matrix matrix = IOLib.chooseToReadFromFile() ? FromFile.SPL()
                    : FromKeyboard.SPL();
            try {
                Matrix solution = matrix.getSolGJ();
                // ToKeyboard.printMatrix(solution);
                if (IOLib.chooseToWriteToFile()) {
                    ToFile.SPL(solution);
                } else {
                    IOLib.SPLSolution.print(solution);
                }
            } catch (NoSolutionException e) {
                ToKeyboard.printMessage("SPL tidak punya solusi!");
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
                Matrix solution = matrix.getSolInverse();
                if (IOLib.chooseToWriteToFile()) {
                    ToFile.SPL(solution);
                } else {
                    IOLib.SPLSolution.print(solution);
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
            } catch (NoSolutionException e) {
                ToKeyboard.printMessage(
                        "Solusi SPL tidak unik. Tidak bisa diselesaikan dengan kaidah cramer! Berikut solusinya dengan eliminasi Gauss-Jordan :");
                try {
                    Matrix solution = matrix.getSolGJ();
                    if (IOLib.chooseToWriteToFile()) {
                        ToFile.SPL(solution);
                    } else {
                        IOLib.SPLSolution.print(solution);
                    }
                } catch (NoSolutionException e2) {
                    ToKeyboard.printMessage("Matriks tidak punya solusi.");
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

    static public class Inverse {
        // This is not functional yet because the determinantFinder doesn't throw an
        // error yet.
        /**
         * Prosedur yang menerima masukan matriks dari pengguna lalu mencetak
         * inverse-nya. Jika matriks singular maka error ditangkap dan dikeluarkan ke
         * layar bahwa matriks singular.
         */
        static public void adjoin() {
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

        // This is not functional yet because the determinantFinder doesn't throw an
        // error yet.
        /**
         * Prosedur yang menerima masukan matriks dari pengguna lalu mencetak
         * inverse-nya. Jika matriks singular maka error ditangkap dan dikeluarkan ke
         * layar bahwa matriks singular.
         */
        static public void obe() {
            Matrix matrix = IOLib.chooseToReadFromFile() ? FromFile.matrixToInvert() : FromKeyboard.MatrixSquare();

            try {
                Matrix inverse = matrix.getInverseOBE();
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

    static public class Interpolation {

        /**
         * Prosedur untuk menerima input titik2 yang ingin dilakukan interpolasi
         * polinomial. Lalu akan meminta input titik yang ingin diestimasikan.
         */
        static public void polinomial() {
            Matrix polinom = IOLib.chooseToReadFromFile() ? FromFile.pointsInput(
                    "Masukkan nama file berisi matriks dengan 2 kolom yang ingin dicari interpolasi polinomialnya :")
                    : FromKeyboard.Points("jumlah titik");

            // matrix polinom sudah pasti berukuran n x 2
            try {
                Matrix polFunc = polinom.getPolinomialFunction();

                // print hasil fungsi interpolasi
                System.out.println("Hasil interpolasi polinomial:");
                System.out.printf("p%d(x) =", polinom.getNRow() - 1);
                for (int i = polFunc.getNRow() - 1; i >= 0; i--) {
                    System.out.printf(" %f", polFunc.getElmt(i, 0));
                    if (i != 0) {
                        System.out.print("x");
                        if (i > 1) {
                            System.out.printf("^%d", i);
                        }
                        System.out.print(" +");
                    }
                }
                System.out.println();

                // meminta input nilai yang ingin diestimasikan
                while (true) {
                    double x = FromKeyboard.readDouble("point untuk diestimasi");
                    System.out.printf("Nilai dari p%d(%f) = %f\n", polinom.getNRow() - 1, x,
                            polFunc.getValuePolinomial(x));
                    if (!FromKeyboard.readString("Apakah ingin melanjutkan estimasi (y/n)? ").toLowerCase().equals("y"))
                        break;
                }
            } catch (NoSolutionException e) {
                System.out.println("Polinom yang dimasukkan tidak memiliki solusi unik");
            }
        }

        static public void imageScaling() {
            Image image = FromFile.readImage();
            int scaleFactor = FromKeyboard.readIntWithMinimum("kelipatan perbesaran gambar", 1);
            /*
             * membatasi ukuran gambar agar RAM yang dipakai tidak lebih dari 1GB (2^30
             * byte). satu pixel akan memakai 8 byte (double) per r,g,b, dan a atau total 32
             * byte (2^5). Sehingga maksimal ukuran pixel hasil adalah (2^30/2^5) = 2^25
             * pixel
             */
            if (image.getPixelSize() * Math.pow(scaleFactor, 2) > Math.pow(2, 25)) {
                System.out.printf(
                        "Ukuran scaling gambar terlalu besar (%,.0f pixel). Jumlah pixel di gambar hasil tidak boleh lebih dari %,d\n",
                        image.getPixelSize() * Math.pow(scaleFactor, 2), (int) Math.pow(2, 25));
                return;
            }

            ToKeyboard.printMessage("Memperbesar gambar... (akan memakan waktu beberapa saat)");
            image.scale(scaleFactor);

            ToFile.exportImageFile(image);
        }
    }

}
