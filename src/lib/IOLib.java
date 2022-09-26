package lib;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class IOLib {
    static public class RowError {
        private boolean containWhiteSpace;
        private boolean columnDiscrepancy;
        private boolean containNaN;
        private boolean empty;

        public RowError() {
            this.containWhiteSpace = false;
            this.containNaN = false;
            this.columnDiscrepancy = false;
            this.empty = false;
        }

        public boolean anyError() {
            return containNaN || containWhiteSpace || columnDiscrepancy || empty;
        }

        public void setError(String errorType, boolean newValue) {
            switch (errorType) {
                case "w":
                    containWhiteSpace = newValue;
                    break;
                case "n":
                    containNaN = newValue;
                    break;
                case "c":
                    columnDiscrepancy = newValue;
                    break;
                case "e":
                    empty = newValue;
                    break;
            }
        }

        public boolean isWhitespace() {
            return containWhiteSpace;
        }

        public boolean isNaN() {
            return containNaN;
        }

        public boolean isColumnDiscrepancy() {
            return columnDiscrepancy;
        }

        public boolean isEmpty() {
            return empty;
        }

        public void reset() {
            containWhiteSpace = false;
            containNaN = false;
            columnDiscrepancy = false;
            empty = false;

        }
    }

    static public RowError checkRow(String rowString, int nCol, boolean colFromInput) {
        RowError rowError = new RowError();

        String[] rowArrayRaw = (rowString.split(" "));

        if (colFromInput) {
            rowError.setError("c", rowArrayRaw.length != nCol);
        }
        int i;
        for (i = 0; i < rowArrayRaw.length; i++) {
            if (rowArrayRaw[i].length() == 0 || rowArrayRaw[i].substring(0, 1) == " ") {
                rowError.setError("w", true);
            }
        }

        try {
            Arrays.stream(rowArrayRaw).mapToDouble(Double::parseDouble).toArray();
            rowError.setError("n", false);
        } catch (NumberFormatException e) {
            rowError.setError("n", true);
        }

        return rowError;

    }

    static public RowError checkRow(String rowString, int nCol) {
        return checkRow(rowString, nCol, true);
    }

    static public boolean chooseToReadFromFile() {
        boolean inputValid = false;
        boolean willReadFromFile = false;
        while (!inputValid) {
            ToKeyboard
                    .printMessage("Tekan 1 untuk menerima input dari file atau 2 untuk menerima input dari keyboard.");
            int input = FromKeyboard.readInt("pilihan anda", 1, 2);
            inputValid = true;
            willReadFromFile = input == 1;

        }
        return willReadFromFile;
    }

    static public boolean chooseToWriteToFile() {
        boolean inputValid = false;
        boolean willWriteToFile = false;
        while (!inputValid) {
            ToKeyboard
                    .printMessage("Tekan 1 untuk mengeluarkan hasil ke file atau 2 untuk mengeluarkan hasil ke CLI.");
            int input = FromKeyboard.readInt("pilihan anda", 1, 2);
            inputValid = true;
            willWriteToFile = input == 1;
        }
        return willWriteToFile;
    }

    /**
     * Menambah whitespace ke bagian belakang string
     * 
     * @param string string yang ingin ditambahi whitespace
     * @return string yang bagian belakangnya sudah ditambahi space sebanyak amount
     */
    static public String addWhiteSpace(String string, int amount) {
        for (int i = 0; i < amount; i++) {
            string = " " + string;
        }

        return string;
    }

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

            // Jujur fungsi ini itu over-engineered.
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
        static public String[] createSolutionTexts(Matrix solution, String variableSymbol, int digitsAfterComma) {
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
                        : (constant == 0 && containParametric ? "" : numberFormatter.format(constant));
                variableValuesString.add(valueString);
            }

            if (nthParameter > 0) {
                // Isi array lagi, kali ini dengan khusus menambahkan parameter ke variabel
                // non-parametrik
                for (int i = 0; i < solution.getNRow(); i++) {
                    for (int j = 0; j < solution.getNCol() - 2; j++) {
                        double examinedCoefficient = solution.getElmt(i, j);
                        // TODO @Fatih20 woi ini apaan
                        ToKeyboard.printNumber(examinedCoefficient);
                        String addedString = "";
                        if (examinedCoefficient != 0 && parameter.containsKey(j)) {
                            ToKeyboard.printMessage("Test");
                            addedString += String.format("%s%s",
                                    (examinedCoefficient == 1 ? "" : numberFormatter.format(examinedCoefficient)),
                                    parameter.get(j));
                        }

                        if ((variableValuesString.get(i) == "" && examinedCoefficient > 0)) {
                            addedString = "+" + addedString;
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
        static public String[] createSolutionTexts(Matrix solution, String variableSymbol) {
            return createSolutionTexts(solution, variableSymbol, 2);
        }

        /**
         * Mencetak matriks solusi yang telah diubah menjadi persamaan dan variabel ke
         * layar
         * <p>
         * I.S. matriks solusi terdefinisi, variableSymbol sebuah string
         * <p>
         * F.S. tercetak ke layar persamaan solusi
         * dengan variableSymbol sebagai variabelnya
         */
        static public void print(Matrix solution, String variableSymbol) {
            String[] solutionTexts = createSolutionTexts(solution, variableSymbol);
            ToKeyboard.printMessage(
                    "Solusi matriks adalah seperti di bawah ini. Perlu diingat bahwa variabel (contoh) ab bukanlah a * b melainkan variabel unik tersendiri");
            for (int i = 0; i < solutionTexts.length; i++) {
                ToKeyboard.printMessage(solutionTexts[i]);
            }
        }

        /**
         * Mencetak matriks solusi yang telah diubah menjadi persamaan dan variabel ke
         * layar
         * <p>
         * I.S. matriks solusi terdefinisi
         * <p>
         * F.S. tercetak ke layar persamaan solusi
         * dengan x sebagai variabelnya
         */
        static public void print(Matrix solution) {
            print(solution, "x");
        }

        /**
         * Mencetak matriks solusi unik menjadi persamaan dan variabel ke
         * layar
         * <p>
         * I.S. matriks solusi memiliki 1 kolom saja berisi nilai variabel ke-i,
         * variableSymbol string terdefinisi
         * <p>
         * F.S. tercetak ke layar persamaan solusi
         * dengan variableSymbol sebagai variabelnya
         */
        static public void printUnique(Matrix solution, String variableSymbol) {
            for (int i = 0; i < solution.getNRow(); i++) {
                String message = String.format("%s%d = %f", variableSymbol, i + 1, solution.getElmt(i, 0));
                ToKeyboard.printMessage(message);
            }
        }

        /**
         * Mencetak matriks solusi unik menjadi persamaan dan variabel ke
         * layar
         * <p>
         * I.S. matriks solusi memiliki 1 kolom saja berisi nilai variabel ke-i,
         * variableSymbol
         * <p>
         * F.S. tercetak ke layar persamaan solusi
         * dengan x sebagai variabelnya
         */
        static public void printUnique(Matrix solution) {
            printUnique(solution, "x");
        }

    }
}
