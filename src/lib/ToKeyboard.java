package lib;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ToKeyboard {

    static public class SPLSolution {
        /**
         * Membentuk variabel yang unik untuk tiap indeksnya dengan penggabungan
         * karakter yang diperbolehkan di dalam fungsi
         * 
         * @param index indeks yang digunakan sebagai seed pembuatan string
         * @return string unik untuk tiap indeks
         */
        static private String parameterVariableMaker(int index) {
            // char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            // 'l', 'm', 'n', 'o', 'p', 'q',
            // 'r',
            // 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
            char[] usedCharacter = {
                    'r',
                    's', 't' };

            index -= 1;
            String createdVariable = "";
            int usedCharacterN = usedCharacter.length;

            int iterationCount = 1;

            while (index >= ((int) Math.pow(usedCharacterN, (iterationCount - 1))) | iterationCount == 1) {
                int addedIndex = (index % (int) ((Math.pow(usedCharacterN, iterationCount))))
                        / ((int) Math.pow(usedCharacterN, (iterationCount - 1)));
                createdVariable = createdVariable
                        + usedCharacter[addedIndex];

                iterationCount += 1;
            }
            // createdVariable += alphabet[index / (usedCharacterN ^ iterationCount)];
            return createdVariable;
        }

        /**
         * Mengecek apakah suatu variabel dalam matriks solusi merupakan variabel
         * parametrik
         * 
         * @param solution matriks solusi yang dihasilkan oleh fungsi getSol dan
         *                 permutasinya
         * @param i        variabel keberapa yang ingin dicek
         * @return apakah variabel ke-i parametrik
         */
        static private boolean isNthVariableParametric(Matrix solution, int i) {
            return solution.getElmt(i, solution.getNCol() - 1) == 0;
        }

        /**
         * Mengecek apakah suatu variabel dalam matriks solusi nilainya
         * bergantung pada
         * variabel parametrik
         * 
         * @param solution matriks solusi yang dihasilkan oleh fungsi getSol dan
         *                 permutasinya
         * @param i        variabel keberapa yang ingin dicek
         * @return apakah variabel ke-i nilainya bergantung pada variabel parametrik.
         *         Jika sendirinya parametrik maka true
         */
        static private boolean isNthVariableContainParametric(Matrix solution, int i) {
            boolean containParametric = isNthVariableParametric(solution, i);
            int j = 0;
            while (j < solution.getNCol() - 2 && !containParametric) {
                if (solution.getElmt(i, j) != 0) {
                    containParametric = true;
                }
                j++;
            }
            return containParametric;
        }

        /**
         * Mengubah matriks solusi menjadi array berisi string solusi variabel yang bisa
         * dibaca manusia
         * 
         * @param solution         matriks solusi yang dihasilkan oleh fungsi getSol dan
         *                         permutasinya
         * @param variableSymbol   simbol untuk variabel SPL
         * @param digitsAfterComma sampai koma keberapa hasil diformat
         * @return array berisi string solusi untuk tiap variabel dengan
         *         masing-masingnya punya contoh format x1 = -3+6d
         */
        static private String[] createSolutionTexts(Matrix solution, String variableSymbol, int digitsAfterComma) {
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
            }

            // Dari string tadi, membentuk baris dengan contoh format berikut : x1 = t + 6
            ArrayList<String> outputLines = new ArrayList<String>();
            for (int i = 0; i < variableValuesString.size(); i++) {
                outputLines.add(String.format("%s%d = %s", variableSymbol, i + 1, variableValuesString.get(i)));
            }

            return outputLines.stream().toArray(String[]::new);
        }

        /**
         * Mengubah matriks solusi menjadi array berisi string solusi variabel yang bisa
         * dibaca manusia dengan angka diformat ke 2 angka di belakang koma
         * 
         * @param solution       matriks solusi yang dihasilkan oleh fungsi getSol dan
         *                       permutasinya
         * @param variableSymbol simbol untuk variabel SPL
         * @return array berisi string solusi untuk tiap variabel dengan
         *         masing-masingnya punya contoh format x1 = -3+6d
         */
        static private String[] createSolutionTexts(Matrix solution, String variableSymbol) {
            return createSolutionTexts(solution, variableSymbol, 2);
        }

        /**
         * Mencetak matriks solusi yang telah diubah menjadi persamaan dan variabel ke
         * layar
         * <p>
         * I.S. matriks solusi terdefinisi, variableSymbol sebuah string
         * <p>
         * F.S. tercetak ke layar matriks solusi yang telah diubah menjadi persamaan
         * dengan variableSymbol sebagai variabelnya
         */
        static public void print(Matrix solution, String variableSymbol) {
            String[] solutionTexts = createSolutionTexts(solution, variableSymbol);
            printMessage(
                    "Solusi matriks adalah seperti di bawah ini. Perlu diingat bahwa variabel (contoh) ab bukanlah a * b melainkan variabel unik tersendiri");
            for (int i = 0; i < solutionTexts.length; i++) {
                printMessage(solutionTexts[i]);
            }
        }

        /**
         * Mencetak matriks solusi yang telah diubah menjadi persamaan dan variabel ke
         * layar
         * <p>
         * I.S. matriks solusi terdefinisi
         * <p>
         * F.S. tercetak ke layar matriks solusi yang telah diubah menjadi persamaan
         * dengan x sebagai variabelnya
         */
        static public void print(Matrix solution) {
            print(solution, "x");
        }
    }

    /**
     * Menambah whitespace ke bagian belakang string
     * 
     * @param string string yang ingin ditambahi whitespace
     * @return string yang bagian belakangnya sudah ditambahi space sebanyak amount
     */
    static private String addWhiteSpace(String string, int amount) {
        for (int i = 0; i < amount; i++) {
            string += " ";
        }

        return string;
    }

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
                cell = addWhiteSpace(cell, mostDigit - cell.length());

                System.out.printf("%s ", cell);
            }
            String cell = numberFormatter.format(outputtedMatrix.getElmt(i, j));
            cell = addWhiteSpace(cell, mostDigit - cell.length());
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
