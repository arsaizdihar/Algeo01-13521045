package lib;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ToKeyboard {

    static public class SPLSolution {
        static public String parameterVariableMaker(int index) {
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

        static private boolean isNthVariableParametric(Matrix solution, int i) {
            return solution.getElmt(i, solution.getNCol() - 1) == 0;
        }

        static private boolean isNthVariableContainParametric(Matrix solution, int i) {
            boolean containParametric = false;
            int j = 0;
            while (j < solution.getNCol() - 2 && !containParametric) {
                if (solution.getElmt(i, j) != 0) {
                    containParametric = true;
                }
                j++;
            }
            return containParametric;
        }

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

        static private String[] createSolutionTexts(Matrix solution, String variableSymbol) {
            return createSolutionTexts(solution, variableSymbol, 2);
        }

        static public void print(Matrix solution) {
            String[] solutionTexts = createSolutionTexts(solution, "x");
            printMessage(
                    "Solusi matriks adalah seperti di bawah ini. Perlu diingat bahwa variabel (contoh) ab bukanlah a * b melainkan variabel unik tersendiri");
            for (int i = 0; i < solutionTexts.length; i++) {
                printMessage(solutionTexts[i]);
            }
        }
    }

    static private String addWhiteSpace(String string, int amount) {
        for (int i = 0; i < amount; i++) {
            string += " ";
        }

        return string;
    }

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
