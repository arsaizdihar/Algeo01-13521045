package lib;

import java.util.Arrays;
// import java.util.Scanner; // Import the Scanner class

import lib.IOLib.RowError;

public class FromKeyboard {

    static ScannerSingleton scanner = ScannerSingleton.getInstance();

    static public String readString() {
        String input = scanner.nextLine();
        return input;
    }

    /**
     * Fungsi baca angka generik. Membaca sebuah angka yang dimasukkan user.
     * Mengulangi input jika angka yang dimasukkan berada di luar range yang
     * diberikan
     * 
     * @param numberName nama angka yang dibaca. Teks digunakan untuk user prompt.
     * @param lowerBound batas minimum angka yang boleh
     * @param upperBound batas maksimum angka yang boleh
     * @return integer angka masukan pengguna yang sudah sesuai dengan keperluan
     */
    static public int readInt(String numberName, int lowerBound, int upperBound) {
        int inputtedNumber = 0;
        boolean inputValid = false;
        while (!inputValid) {
            System.out.format("Masukkan %s : ", numberName);
            inputtedNumber = scanner.nextInt();
            inputValid = inputtedNumber >= lowerBound && inputtedNumber <= upperBound;
            if (!inputValid) {
                System.out.format("Bukan %s yang valid, tolong coba lagi%n", numberName);
            }
        }

        scanner.nextLine();

        return inputtedNumber;
    }

    /**
     * Fungsi baca angka generik. Membaca sebuah angka yang dimasukkan user.
     * Mengulangi input jika angka yang dimasukkan berada di bawah lowerBound yang
     * diberikan
     * 
     * @param numberName nama angka yang dibaca. Teks digunakan untuk user prompt.
     * @param lowerBound batas minimum angka yang boleh
     * @return integer angka masukan pengguna yang sudah sesuai dengan keperluan
     */
    static public int readIntWithMinimum(String numberName, int lowerBound) {
        return readInt(numberName, lowerBound, Integer.MAX_VALUE);
    }

    /**
     * Fungsi baca angka generik. Membaca sebuah angka yang dimasukkan user.
     * Mengulangi input jika angka yang dimasukkan berada di atas upperBound yang
     * diberikan
     * 
     * @param numberName nama angka yang dibaca. Teks digunakan untuk user prompt.
     * @param upperBound batas maksimum angka yang boleh
     * @return integer angka masukan pengguna yang sudah sesuai dengan keperluan
     */
    static public int readIntWithMaximum(String numberName, int upperBound) {
        return readInt(numberName, Integer.MIN_VALUE, upperBound);

    }

    /**
     * Fungsi baca angka generik. Membaca sebuah angka yang dimasukkan user.
     * 
     * @param numberName nama angka yang dibaca. Teks digunakan untuk user prompt.
     * @return double angka masukan pengguna yang sudah sesuai dengan keperluan
     */
    static public double readDouble(String numberName) {
        System.out.format("Masukkan %s : ", numberName);
        double inputtedNumber = scanner.nextDouble();

        scanner.nextLine();

        return inputtedNumber;
    }

    /**
     * Fungsi baca string generik. Membaca sebuah string yang dimasukkan user.
     * 
     * @param prompt nama string yang dibaca. Teks digunakan untuk user prompt.
     * @return String masukan pengguna
     */
    static public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
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
        // Scanner inputReceiver = new Scanner(System.in);
        double[] rowArrayTest = new double[0];
        boolean rowInputValid = false;
        while (!rowInputValid) {

            String errorMessage = "";
            System.out.printf("");
            String rowString = scanner.nextLine();
            RowError rowError = IOLib.checkRow(rowString, nCol);

            if (rowError.isColumnDiscrepancy()) {
                errorMessage = "Panjang baris tidak sama dengan jumlah kolom.";
            }

            if (rowError.isNaN()) {
                errorMessage = "Baris mengandung angka yang tidak valid.";
            }

            if (rowError.isWhitespace()) {
                errorMessage = "Baris kelebihan spasi.";
            }

            rowInputValid = !rowError.anyError();

            if (!rowInputValid) {
                System.out.printf("%s Tolong coba lagi%n", errorMessage);
            } else {
                rowArrayTest = Arrays.stream(rowString.split(" ")).mapToDouble(Double::parseDouble).toArray();
            }
        }

        return rowArrayTest;
    }

    static public void EnterToContinue() {
        System.out.printf("Tekan enter untuk kembali ke menu utama ");
        scanner.nextLine();
        ToKeyboard.printMessage("");
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
        int n = readIntWithMinimum("jumlah baris dan kolom", 1);

        return Matrix(n, n, "Masukkan matriks baris per baris dengan tiap elemen dipisahkan spasi");
    }

    /**
     * Fungsi baca kumpulan titik. Membaca jumlah titik yang ingin dibaca lalu
     * membaca tiap titik satu per satu dengan x dan y dipisahkan spasi
     * Mengulangi input jumlah baris jika nilainya < 1.
     * Mengulangi input salah satu baris jika kolom yang dimasukkan jumlahnya tidak
     * sesuai atau ada elemen yang bukan angka
     * 
     * @param prompt string yang ingin ditampilkan saat meminta jumlah baris
     * @return matriks dengan 2 kolom. kolom pertamanya adalah x dan kolom keduanya
     *         adalah y
     */
    static public Matrix Points(String prompt) {
        int n = readIntWithMinimum(prompt, 1);

        return Matrix(n, 2, "Masukkan titik baris per baris dengan x dan y dipisahkan spasi");
    }

    /**
     * Memanggil fungsi this.Points dengan propmt "jumlah variabel"
     */
    static public Matrix Points() {
        return Points("jumlah variabel");
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
        int m = readIntWithMinimum("jumlah persamaan", 1);
        int n = readIntWithMinimum("jumlah variabel", 1);

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
        int m = readIntWithMinimum("jumlah baris", 1);
        int n = readIntWithMinimum("jumlah kolom", 1);

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
    static public Matrix[] MLR() {
        int dataCount = readIntWithMinimum("jumlah sampel", 1);
        int variableCount = readIntWithMinimum("jumlah variabel", 1);

        Matrix data = Matrix(dataCount, variableCount + 1,
                "Masukkan sampel baris per baris dengan elemen terakhir adalah y dan elemen sisanya adalah variabel x");

        ToKeyboard.printMessage("Sekarang masukkan data yang ingin diprediksi");

        int predictedDataCount = readIntWithMinimum("jumlah sampel", 1);

        Matrix predictedData = Matrix(predictedDataCount, variableCount,
                "Masukkan data yang ingin diprediksi baris per baris dengan tiap elemen sebagai variabel x");

        Matrix[] returnValue = { data, predictedData };
        return returnValue;

    }
}
