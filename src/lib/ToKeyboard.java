package lib;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ToKeyboard {

    /**
     * Mencetak matriks ke
     * layar
     * <p>
     * I.S. matriks terdefinisi, digitAfterComma integer
     * <p>
     * F.S. tercetak ke layar matriks dengan lebar tiap selnya seragam untuk semua
     * sel dan tiap selnya diformat dengan angka di belakang koma sebanyak
     * digitAfterComma
     */
    static public void printMatrix(Matrix outputtedMatrix, int digitAfterComma) {
        int m = outputtedMatrix.getNRow();
        int n = outputtedMatrix.getNCol();

        NumberFormat numberFormatter = NumberFormat.getInstance();
        numberFormatter.setMaximumFractionDigits(digitAfterComma);
        int mostDigit = outputtedMatrix.getMostDigit();
        int i;
        for (i = 0; i < m; i++) {
            int j;
            for (j = 0; j < n - 1; j++) {
                String cell = numberFormatter.format(outputtedMatrix.getElmt(i, j));
                cell = IOLib.addWhiteSpace(cell, mostDigit - cell.length());

                System.out.printf("%s ", cell);
            }
            String cell = numberFormatter.format(outputtedMatrix.getElmt(i, j));
            cell = IOLib.addWhiteSpace(cell, mostDigit - cell.length());
            System.out.printf("%s", cell);
            System.out.printf("%n");
        }
    }

    /**
     * Mencetak matriks ke
     * layar
     * <p>
     * I.S. matriks terdefinisi
     * <p>
     * F.S. tercetak ke layar matriks dengan lebar tiap selnya seragam untuk semua
     * sel dan tiap selnya diformat dengan angka di belakang koma sebanyak
     * 2
     */
    static public void printMatrix(Matrix outputtedMatrix) {
        printMatrix(outputtedMatrix, 2);
    }

    /**
     * Mencetak angka double bersama sebuah pesan ke layar
     * <p>
     * I.S. number double terdefinisi, message terdefinisi
     * <p>
     * F.S. tercetak ke layar angka message dan number tanpa dipisahkan spasi
     */
    static public void printNumber(double number, String message) {
        System.out.printf("%s%f%n", message, number);
    }

    /**
     * Mencetak angka double
     * <p>
     * I.S. number double terdefinisi
     * <p>
     * F.S. tercetak ke layar angka
     */
    static public void printNumber(double number) {
        System.out.printf("%f%n", number);
    }

    /**
     * Mencetak sebuah string
     * <p>
     * I.S. message terdefinisi
     * <p>
     * F.S. tercetak ke layar sebuah string
     */
    static public void printMessage(String message) {
        System.out.printf("%s%n", message);
    }
}
