package lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import lib.IOLib.RowError;

public class FromFile {
    static private File getFile() {
        File retrievedFile = new File("");
        boolean fileFound = false;
        while (!fileFound) {
            String filename = FromKeyboard.readString();
            try {
                retrievedFile = new File(filename);
                Scanner fileReader = new Scanner(retrievedFile);
                fileFound = true;
                fileReader.close();
            } catch (FileNotFoundException e) {
                ToKeyboard.printMessage("File tidak ditemukan. Tolong coba lagi.");
            }

        }
        return retrievedFile;
    }

    static private String[] readFile() {
        File inputtedFile = getFile();
        ArrayList<String> linesInFile = new ArrayList<String>();
        try {
            Scanner fileReader = new Scanner(inputtedFile);
            while (fileReader.hasNextLine()) {
                String lineInFile = fileReader.nextLine();
                linesInFile.add(lineInFile);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return linesInFile.stream().toArray(String[]::new);
    }

    static private RowError isFileContentValid(String[] rowStrings) {
        RowError rowError = new RowError();
        if (rowStrings.length == 0) {
            rowError.setError("e", true);
            return rowError;
        }
        int nCol = rowStrings[0].split(" ").length;
        int i = 0;
        while (i < rowStrings.length && rowError.anyError()) {
            rowError = IOLib.checkRow(rowStrings[i], nCol);
            if (!rowError.anyError()) {
                i++;
            }
        }

        return rowError;
    }

    static private Matrix rowStringsToMatrix(String[] rowStrings) {
        int nCol = rowStrings[0].split(" ").length;
        Matrix inputtedMatrix = new Matrix(rowStrings.length, nCol);

        for (int i = 0; i < rowStrings.length; i++) {
            double[] convertedRow = new double[0];
            convertedRow = Arrays.stream(rowStrings[i].split(" ")).mapToDouble(Double::parseDouble).toArray();
            inputtedMatrix.setRow(i, convertedRow);
        }
        return inputtedMatrix;
    }

    static private Matrix Matrix() {
        String[] rowStrings = readFile();
        RowError fileContentError = isFileContentValid(rowStrings);
        while (fileContentError.anyError()) {
            String errorMessage = "";
            if (fileContentError.isColumnDiscrepancy()) {
                errorMessage = "Jumlah kolom tidak seragam.";
            }

            if (fileContentError.isNaN()) {
                errorMessage = "Terdapat elemen yang bukan angka.";
            }

            if (fileContentError.isWhitespace()) {
                errorMessage = "Terdapat spasi yang berlebih.";
            }

            if (fileContentError.isEmpty()) {
                errorMessage = "File kosong.";
            }

            errorMessage += " "
                    + "Perbaiki file anda dan coba lagi. Tekan tombol apapun dan enter jika sudah untuk mencoba lagi.";
            ToKeyboard.printMessage(errorMessage);
            FromKeyboard.readString();
            rowStrings = readFile();
            fileContentError = isFileContentValid(rowStrings);

        }
        return rowStringsToMatrix(rowStrings);
    }

    static private Matrix MatrixSquare() {
        Matrix inputtedMatrix = Matrix();
        boolean matrixIsASquare = inputtedMatrix.isSquare();
        while (!matrixIsASquare) {
            String errorMessage = "Matriks bukan berbentuk persegi.";
            errorMessage += " "
                    + "Perbaiki file anda dan coba lagi. Tekan tombol apapun dan enter jika sudah untuk mencoba lagi.";
            ToKeyboard.printMessage(errorMessage);
            inputtedMatrix = Matrix();
            matrixIsASquare = inputtedMatrix.isSquare();

        }

        return inputtedMatrix;
    }

    static public Matrix pointsInput(String message) {
        ToKeyboard.printMessage(message);
        Matrix input;
        do {
            input = Matrix();
            if (!input.isPoints()) {
                ToKeyboard.printMessage(
                        "Isi file tidak sesuai dengan format points. Pastikan file berisi 2 kolom tiap baris (x dan y).\nPerbaiki file anda dan coba lagi.");
            }
        } while (!input.isPoints());
        return input;
    }

    static public Matrix matrixInput(String message, boolean isSquare) {
        ToKeyboard.printMessage(message);
        return isSquare ? MatrixSquare() : Matrix();
    }

    static public Matrix SPL() {
        String filePrompt = "Masukkan nama file berisi matriks augmented yang merepresentasikan SPL : ";
        return FromFile.matrixInput(filePrompt, false);
    }

    static public Matrix matrixToDetermine() {
        String filePrompt = "Masukkan nama file berisi matriks persegi yang ingin dicari determinannya : ";
        return FromFile.matrixInput(filePrompt, true);
    }

    static public Matrix matrixToInvert() {
        String filePrompt = "Masukkan nama file berisi matriks persegi yang ingin dicari inverse-nya : ";
        return FromFile.matrixInput(filePrompt, true);
    }

    static public Matrix[] MLR() {
        ToKeyboard.printMessage(
                "File berisi data yang sudah ada dan data yang ingin diprediksi nilai y-nya adalah dipisah dan dibedakan.");
        Matrix data = FromFile.matrixInput("Masukkan nama file berisi data riil yang sudah ada : ", false);
        Matrix predictedData = FromFile
                .matrixInput("Masukkan nama file berisi data variabel x yang ingin diprediksi : ", false);
        Matrix[] returnValue = { data, predictedData };
        return returnValue;
    }

    static public Image readImage() {
        System.out.println("Masukkan nama file gambar yang ingin diperbesar: ");
        while (true) {
            try {
                return new Image(FromFile.getFile());
            } catch (IOException e) {
                System.out.println("Gagal memproses gambar di file. Pastikan tipe file adalah gambar.");
            }
        }
    }

}
