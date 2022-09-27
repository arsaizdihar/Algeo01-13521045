package lib.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import lib.Image;
import lib.Matrix;

public class ToFile {

    static public class CreateFileCondition {
        private boolean createFileSuccessful;
        private boolean fileAlreadyExists;

        public CreateFileCondition() {
            this.createFileSuccessful = false;
            this.fileAlreadyExists = false;
        }

        public void setCreateSuccessful(boolean newValue) {
            createFileSuccessful = newValue;
        }

        public void setCreateSuccessful() {
            createFileSuccessful = true;
        }

        public void setFileExists(boolean newValue) {
            fileAlreadyExists = newValue;
        }

        public void setFileExists() {
            fileAlreadyExists = true;
        }

        public boolean otherError() {
            return !createFileSuccessful && !fileAlreadyExists;
        }

        public boolean isSuccessful() {
            return createFileSuccessful;
        }

        public boolean isExists() {
            return fileAlreadyExists;
        }

    }

    private static CreateFileCondition createFileSuccessful(String filename) {
        CreateFileCondition createFileCondition = new CreateFileCondition();
        try {
            File outputtedFile = new File(filename);
            if (outputtedFile.createNewFile()) {
                createFileCondition.setCreateSuccessful();
            } else {
                createFileCondition.setFileExists();
            }
        } catch (IOException e) {
        }
        return createFileCondition;
    }

    private static String getValidFilename() {
        String filename = FromKeyboard.readString();
        CreateFileCondition createFileCondition = createFileSuccessful(filename);
        while (!createFileCondition.isSuccessful()) {
            String errorMessage = "";

            if (createFileCondition.isExists()) {
                errorMessage = "File dengan nama sama sudah ada. Coba ulangi lagi dengan nama file lain.";
            }

            if (createFileCondition.otherError()) {
                errorMessage = "File tidak dapat dibuat karena suatu hal. Mohon coba lagi.";
            }
            ToKeyboard.printMessage(errorMessage);
            filename = FromKeyboard.readString();
            createFileCondition = createFileSuccessful(filename);
        }

        return filename;
    }

    public static void writeToFile(String[] rowStrings) {
        String filename = getValidFilename();

        try {
            FileWriter fileWriter = new FileWriter(filename, true);
            for (int i = 0; i < rowStrings.length; i++) {
                fileWriter.write(rowStrings[i] + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            String errorMessage = "File tidak dapat ditulis karena suatu hal. Mohon coba lagi.";
            ToKeyboard.printMessage(errorMessage);

        }

    }

    public static void SPL(Matrix solution) {
        String[] solutionArray = IOLib.SPLSolution.createSolutionTexts(solution, "x");
        ToKeyboard.printMessage("Masukkan nama file yang ingin diisi dengan solusi yang telah didapatkan :");
        writeToFile(solutionArray);
    }

    public static void determinant(double determinant) {
        ToKeyboard.printMessage("Masukkan nama file yang ingin diisi dengan determinan yang telah didapatkan :");
        String[] outputArray = { String.format("Determinan matriks adalah : %f", determinant) };
        writeToFile(outputArray);
    }

    public static String[] matrixToRowString(Matrix outputtedMatrix, int digitAfterComma) {
        NumberFormat numberFormatter = NumberFormat.getInstance();
        numberFormatter.setMaximumFractionDigits(digitAfterComma);
        int mostDigit = outputtedMatrix.getMostDigit();
        int m = outputtedMatrix.getNRow();
        int n = outputtedMatrix.getNCol();
        int i;
        ArrayList<String> rowList = new ArrayList<String>();
        for (i = 0; i < m; i++) {
            int j;
            String line = "";
            for (j = 0; j < n - 1; j++) {
                String cell = numberFormatter.format(outputtedMatrix.getElmt(i, j));
                cell = IOLib.addWhiteSpace(cell, mostDigit - cell.length());
                line += String.format("%s ", cell);
            }
            String cell = numberFormatter.format(outputtedMatrix.getElmt(i, j));
            cell = IOLib.addWhiteSpace(cell, mostDigit - cell.length());
            line += String.format("%s", cell);
            rowList.add(line);

        }

        return rowList.stream().toArray(String[]::new);
    }

    public static String[] matrixToRowString(Matrix outputtedMatrix) {
        return matrixToRowString(outputtedMatrix, 2);
    }

    public static void inverse(Matrix invertedMatrix) {
        ToKeyboard
                .printMessage("Masukkan nama file yang ingin diisi dengan invers dari matriks yang telah dimasukkan :");
        writeToFile(matrixToRowString(invertedMatrix));
    }

    public static void MLR(List<Double> betaList, Matrix predictedData) {
        ArrayList<String> rowOfTexts = new ArrayList<String>();
        rowOfTexts.add(IOLib.MLR.createEquationText(betaList));
        for (int i = 0; i < predictedData.getNRow(); i++) {
            rowOfTexts.add(IOLib.MLR.createResultText(predictedData.getRow(i)));
        }

        writeToFile((rowOfTexts.stream().toArray(String[]::new)));
    }

    public static void exportImageFile(Image img) {
        ToKeyboard.printMessage("Masukkan nama file gambar yang ingin diexport :");
        String filename = getValidFilename();
        File file = new File(filename);
        BufferedImage buffImg = img.getBufferedImage();
        try {
            ImageIO.write(buffImg, "png", file);
            System.out.printf("Gambar berhasil diexport ke file %s\n", filename);
        } catch (IOException e) {
            String errorMessage = "Gambar tidak dapat ditulis karena suatu hal. Mohon coba lagi.";
            ToKeyboard.printMessage(errorMessage);
        }
    }
}
