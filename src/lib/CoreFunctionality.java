package lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

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
            } catch (InvalidMatrixSizeException e) {
                throw new RuntimeException(e);
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

    static public class MLR {
        static private List<Double> betaProducer(Matrix data) {
            Matrix x = data.getCopyMatrixByColumn(0, data.getNCol() - 2);
            Matrix y = data.getCopyMatrixByColumn(data.getNCol() - 1, data.getNCol() - 1);
            Matrix transposedX = x.getCopyMatrixByColumn(0, x.getNCol() - 1);
            transposedX.transpose();
            Matrix xTransposedByY;

            try {
                xTransposedByY = Matrix.multiply(transposedX, y);
            } catch (InvalidMatrixSizeException e) {
                throw new RuntimeException(e);
            }

            Matrix xTransposedByXInverted;
            try {
                xTransposedByXInverted = Matrix.multiply(transposedX, x).getInverseAdjoin();
            } catch (InvalidMatrixSizeException e) {
                throw new RuntimeException(e);
            } catch (NoInverseException e) {
                throw new RuntimeException(e);
            }

            Matrix betaNoZeroMatrix;
            try {
                betaNoZeroMatrix = Matrix.multiply(xTransposedByXInverted, xTransposedByY);
            } catch (InvalidMatrixSizeException e) {
                throw new RuntimeException(e);
            }

            double averageOfBetaAndVariable = 0;
            for (int i = 0; i <= betaNoZeroMatrix.getNRow() - 1; i++) {
                averageOfBetaAndVariable -= betaNoZeroMatrix.getElmt(i, 0);
            }

            double betaZero = y.getAverageOfColumn(0) + averageOfBetaAndVariable;

            List<Double> betaList = new ArrayList<Double>();
            betaList.add(betaZero);
            for (int i = 0; i <= betaNoZeroMatrix.getNRow() - 1; i++) {
                betaList.add(betaNoZeroMatrix.getElmt(i, 0));
            }

            return betaList;
        }

        static private Matrix resultGenerator(List<Double> betaList, Matrix dataToPredict) {
            Matrix resultMatrix = new Matrix(dataToPredict.getNRow(), dataToPredict.getNCol() + 1);
            for (int i = 0; i <= dataToPredict.getNRow() - 1; i++) {
                List<Double> addedRow = Arrays.stream(dataToPredict.getRow(i)).boxed().collect(Collectors.toList());

                double result = 0;
                for (int j = 1; j <= dataToPredict.getNCol() - 1; j++) {
                    result += betaList.get(i) * dataToPredict.getElmt(i, j);
                }

                addedRow.add(result);

                double[] addedRowArray = new double[dataToPredict.getNCol()];

                for (int n = 0; n < dataToPredict.getNCol() - 1; n++) {
                    addedRowArray[n] = addedRow.get(n);
                }
                addedRowArray[dataToPredict.getNCol() - 1] = result;

                resultMatrix.setRow(i, addedRowArray);
            }

            return resultMatrix;

        }

        static public void compute() {
            Matrix[] input = IOLib.chooseToReadFromFile() ? FromFile.MLR() : FromKeyboard.MLR();
            Matrix data = input[0];
            Matrix dataToPredict = input[1];

            List<Double> betaList = betaProducer(data);
            Matrix predictedData = resultGenerator(betaList, dataToPredict);
            if (IOLib.chooseToWriteToFile()) {
                ToFile.MLR(betaList, predictedData);
            } else {
                ToKeyboard.printMLR(betaList, predictedData);
            }
        }
    }

}
