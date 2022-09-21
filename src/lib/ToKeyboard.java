package lib;

import java.text.NumberFormat;

public class ToKeyboard {

    static public void Matrix(Matrix outputtedMatrix, int digitAfterComma) {
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

    static public void Matrix(Matrix outputtedMatrix) {
        Matrix(outputtedMatrix, 2);
    }
}
