package lib;

import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class

public class FromKeyboard {

    /**
     * Fungsi baca angka generik. Membaca sebuah angka yang dimasukkan user.
     * Mengulangi input jika angka yang dimasukkan berada di luar range yang
     * diberikan
     * 
     * @param numberName nama angka yang dibaca. Teks digunakan untuk user prompt.
     * @param lowerBound batas minimum angka yang boleh
     * @param upperBound batas maksimum angka yang boleh
     * @return angka masukan pengguna yang sudah sesuai dengan keperluan
     */
    static private int readNumber(String numberName, double lowerBound, double upperBound) {
        Scanner inputReceiver = new Scanner(System.in);
        int inputtedNumber = 0;
        boolean inputValid = false;
        while (!inputValid) {
            System.out.format("Enter %s : ", numberName);
            inputtedNumber = inputReceiver.nextInt();
            inputValid = inputtedNumber >= lowerBound && inputtedNumber <= upperBound;
            if (!inputValid) {
                System.out.format("Invalid %s, please try again", numberName);
            }

        }

        return inputtedNumber;
    }

    /**
     * Fungsi baca angka generik. Membaca sebuah angka yang dimasukkan user.
     * Mengulangi input jika angka yang dimasukkan berada di bawah lowerBound yang
     * diberikan
     * 
     * @param numberName nama angka yang dibaca. Teks digunakan untuk user prompt.
     * @param lowerBound batas minimum angka yang boleh
     * @return angka masukan pengguna yang sudah sesuai dengan keperluan
     */
    static private int readNumberWithMinimum(String numberName, int lowerBound) {
        return readNumber(numberName, lowerBound, Double.MAX_VALUE);
    }

    /**
     * Fungsi baca angka generik. Membaca sebuah angka yang dimasukkan user.
     * Mengulangi input jika angka yang dimasukkan berada di atas upperBound yang
     * diberikan
     * 
     * @param numberName nama angka yang dibaca. Teks digunakan untuk user prompt.
     * @param upperBound batas maksimum angka yang boleh
     * @return angka masukan pengguna yang sudah sesuai dengan keperluan
     */
    static private int readNumberWithMaximum(String numberName, int upperBound) {
        return readNumber(numberName, Double.MIN_VALUE, upperBound);

    }

    /**
     * Fungsi baca baris generik. Membaca satu baris angka double dengan tiap elemen
     * dipisahkan spasi.
     * Mengulangi input jika kolom yang dimasukkan jumlahnya tidak
     * sesuai atau ada elemen yang bukan angka
     * 
     * @param nCol jumlah kolom yang ingin dibaca
     * @return matriks berisi double sepanjanng nCol yang nilainya didapatkan dari
     *         masukan pengguna
     */
    static private double[] readRow(int nCol) {
        Scanner inputReceiver = new Scanner(System.in);
        double[] rowArrayTest = new double[0];
        boolean rowInputValid = false;
        boolean inputNotANumber = false;
        boolean inputNotNRow = false;
        while (!rowInputValid) {
            String errorMessage = "";
            System.out.printf("");
            String rowString = inputReceiver.nextLine();
            String[] rowArrayRaw = rowString.split(" ");
            inputNotNRow = rowArrayRaw.length != nCol;
            try {
                rowArrayTest = Arrays.stream(rowArrayRaw).mapToDouble(Double::parseDouble).toArray();
            } catch (NumberFormatException e) {
                inputNotANumber = false;
            }

            if (inputNotNRow) {
                errorMessage = "Length of the row is not the number of matrix column.";
            }

            if (inputNotANumber) {
                errorMessage = "The inputted row contains not a valid number.";
            }

            rowInputValid = !inputNotANumber && !inputNotNRow;

            if (!rowInputValid) {
                System.out.printf("%s Please try again%n", errorMessage);
            }
        }

        return rowArrayTest;
    }

    /**
     * Fungsi baca matriks generik. Membaca matriks baris per baris dengan elemen
     * antara kolom dipisahkan spasi.
     * Mengulangi input salah satu baris jika kolom yang dimasukkan jumlahnya tidak
     * sesuai atau ada elemen yang bukan angka
     * 
     * @param nRow jumlah baris yang ingin dibaca
     * @param nCol jumlah kolom yang ingin dibaca
     * @return matriks baru dengan ukuran nRow x nCol yang nilainya didapatkan dari
     *         masukan pengguna
     */
    static private Matrix Matrix(int nRow, int nCol, String message) {
        Matrix inputtedMatrix = new Matrix(nRow, nCol);
        System.out.println(message);

        int i;
        for (i = 0; i < nRow; i++) {
            double[] row = readRow(nCol);
            inputtedMatrix.setRow(i, row);
        }
        return inputtedMatrix;

    }

    /**
     * Fungsi baca matriks berbentuk persegi. Membaca jumlah baris yang diinginkan,
     * lalu membaca matriks baris per baris dengan elemen
     * antara kolom dipisahkan spasi.
     * Mengulangi input jumlah baris jika nilainya < 1.
     * Mengulangi input salah satu baris jika kolom yang dimasukkan jumlahnya tidak
     * sesuai atau ada elemen yang bukan angka
     * 
     * @return matriks baru dengan ukuran persegi yang sesuai dengan masukan
     *         pengguna
     */
    static public Matrix MatrixSquare() {
        int n = readNumberWithMinimum("jumlah baris", 1);

        return Matrix(n, n, "Masukkan matriks baris per baris dengan tiap elemen dipisahkan spasi");
    }

    /**
     * Fungsi baca kumpulan titik. Membaca jumlah titik yang ingin dibaca lalu
     * membaca tiap titik satu per satu dengan x dan y dipisahkan spasi
     * Mengulangi input jumlah baris jika nilainya < 1.
     * Mengulangi input salah satu baris jika kolom yang dimasukkan jumlahnya tidak
     * sesuai atau ada elemen yang bukan angka
     * 
     * @return matriks dengan 2 kolom. kolom pertamanya adalah x dan kolom keduanya
     *         adalah y
     */
    static public Matrix Points() {
        int n = readNumberWithMinimum("jumlah variabel", 1);

        return Matrix(n, 2, "Masukkan titik baris per baris dengan x dan y dipisahkan spasi");
    }

    /**
     * Fungsi baca kumpulan persamaan linear.
     * Persamaan memiliki format elemen terakhir dalam satu baris adalah b sementara
     * elemen sisanya adalah koefisien variabel atau a
     * Meminta input jumlah persamaan dan
     * jumlah
     * variabel dari pengguna. Mengulangi kedua input tersebut jika < 1.
     * Mengulangi input salah satu persaman jika elemen yang dimasukkan jumlahnya
     * tidak
     * sesuai atau ada elemen yang bukan angka
     * 
     * @return matriks dengan jumlah persamaan x (jumlah variabel + 1). Kolom
     *         terakhir adalah nilai b sementara kolom lainnya adalah koefisien
     *         variabel pada persamana
     */
    static public Matrix SPL() {
        int m = readNumberWithMinimum("jumlah persamaan", 1);
        int n = readNumberWithMinimum("jumlah variabel", 1);

        return Matrix(m, n + 1,
                "Masukkan SPL baris per baris dengan koefisien variabel dipisahkan spasi dan elemen terakhir sebagai b");

    }

    /**
     * Fungsi baca matriks. Meminta input jumlah baris dan kolom dari pengguna.
     * Mengulangi kedua input tersebut jika jumlah baris atau jumlah kolom < 1.
     * Mengulangi input salah satu baris jika elemen yang dimasukkan jumlahnya
     * tidak
     * sesuai atau ada elemen yang bukan angka
     * 
     * @return matriks dengan jumlah persamaan x (jumlah variabel + 1). Kolom
     *         terakhir adalah nilai b sementara kolom lainnya adalah koefisien
     *         variabel pada persamana
     */
    static public Matrix Matrix() {
        int m = readNumberWithMinimum("jumlah baris", 1);
        int n = readNumberWithMinimum("jumlah kolom", 1);

        return Matrix(m, n,
                "Masukkan matriks baris per baris dengan tiap elemen dipisahkan spasi");

    }

    /**
     * Fungsi baca kumpulan data MLR.
     * Data memiliki format elemen terakhir dalam satu baris adalah f(x1i, ... xni)
     * sementara
     * elemen sisanya adalah variabel x1i...xni
     * Meminta input jumlah sampel dan
     * jumlah
     * variabel dari pengguna. Mengulangi kedua input tersebut jika < 1.
     * Mengulangi input salah satu sampel jika elemen yang dimasukkan jumlahnya
     * tidak
     * sesuai atau ada elemen yang bukan angka
     * 
     * @return matriks dengan jumlah sampel x (jumlah variabel + 1). Kolom
     *         terakhir adalah nilai f() sementara kolom lainnya adalah nilai
     *         variabel
     */
    static public Matrix MLR() {
        int m = readNumberWithMinimum("jumlah sampel", 1);
        int n = readNumberWithMinimum("jumlah variabel", 1);

        return Matrix(m, n + 1,
                "Masukkan sampel baris per baris dengan elemen terakhir adalah y dan elemen sisanya adalah variabel x");

    }
}
