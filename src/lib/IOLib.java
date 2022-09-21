package lib;

import java.util.Arrays;

public class IOLib {
    static public class RowError {
        private boolean containWhiteSpace;
        private boolean columnDiscrepancy;
        private boolean containNaN;

        public RowError() {
            this.containWhiteSpace = false;
            this.containNaN = false;
            this.columnDiscrepancy = false;
        }

        public boolean anyError() {
            return containNaN || containWhiteSpace || columnDiscrepancy;
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

        public void reset() {
            containWhiteSpace = false;
            containNaN = false;
            columnDiscrepancy = false;
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

        double[] rowArrayTest = new double[0];
        try {
            rowArrayTest = Arrays.stream(rowArrayRaw).mapToDouble(Double::parseDouble).toArray();
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
                    .printMessage("Tekan f untuk menerima input dari file atau k untuk menerima input dari keyboard.");
            String input = FromKeyboard.readString();
            inputValid = input == "f" || input == "k";
            if (!inputValid) {
                ToKeyboard.printMessage("Bukan input yang valid. Masukkan input yang benar!");
            } else {
                willReadFromFile = input == "f";
            }
        }
        return willReadFromFile;
    }
}
