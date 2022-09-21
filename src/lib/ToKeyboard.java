package lib;

import java.text.NumberFormat;

public class ToKeyboard {

    static public void printMatrix(Matrix outputtedMatrix, int digitAfterComma) {
        int m = outputtedMatrix.getNRow();
        int n = outputtedMatrix.getNCol();

        NumberFormat numberFormatter = NumberFormat.getInstance();
        numberFormatter.setMaximumFractionDigits(digitAfterComma);
        int i;
        for (i = 0; i < m; i++) {
            int j;
            for (j = 0; j < n - 1; j++) {
                System.out.printf("%s ", numberFormatter.format(outputtedMatrix.getElmt(i, j)));
                System.out.printf(" ");
            }

            System.out.printf("%s", numberFormatter.format(outputtedMatrix.getElmt(i, j)));
            System.out.printf("%n");
        }
    }

    static public void printMatrix(Matrix outputtedMatrix) {
        printMatrix(outputtedMatrix, 2);
    }

    static public void printNumber(double number, String message) {
        System.out.printf("%s%f%n", message, number);
    }

    static public void printNumber(double number) {
        System.out.printf("%f%n", number);
    }

    static public void printMessage(String message) {
        System.out.printf("%s%n", message);
    }
}
