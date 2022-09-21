package lib;

import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class

public class FromKeyboard {

    static private Matrix Matrix(int nRow, int nCol, String message) {
        Scanner inputReceiver = new Scanner(System.in);
        Matrix inputtedMatrix = new Matrix(nRow, nCol);
        System.out.println(message);

        int i;
        for (i = 0; i < nRow; i++) {

            boolean rowInputValid = false;
            boolean inputNotANumber = false;
            boolean inputNotNRow = false;
            while (!rowInputValid) {
                String errorMessage = "";
                System.out.printf("");
                String rowString = inputReceiver.nextLine();
                String[] rowArrayRaw = rowString.split(" ");
                double[] rowArrayTest = new double[0];
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
                } else {
                    double[] rowArray = rowArrayTest.clone();
                    inputtedMatrix.setRow(i, rowArray);
                }
            }

        }

        // inputReceiver.close();
        return inputtedMatrix;
    }

    static public Matrix MatrixOrSPL() {

        Scanner inputReceiver = new Scanner(System.in);
        int m = 0;
        boolean inputMValid = false;
        while (!inputMValid) {
            System.out.print("Enter m : ");
            m = inputReceiver.nextInt();
            inputMValid = m > 0;
            if (!inputMValid) {
                System.out.println("Invalid m, please try again");
            }

        }

        int n = 0;
        boolean inputNValid = false;
        while (!inputNValid) {
            System.out.print("Enter n : ");
            n = inputReceiver.nextInt();
            inputNValid = n > 0;
            if (!inputNValid) {
                System.out.println("Invalid n, please try again");

            }
        }
        inputReceiver.nextLine();

        // inputReceiver.close();

        return Matrix(m, n, "Enter your matrix row by row: ");

    }

    static public Matrix MatrixSquare() {

        Scanner inputReceiver = new Scanner(System.in);

        int n = 0;
        boolean inputNValid = false;
        while (!inputNValid) {
            System.out.print("Enter n : ");
            n = inputReceiver.nextInt();
            inputNValid = n > 0;
            if (!inputNValid) {
                System.out.println("Invalid n, please try again");

            }
        }
        inputReceiver.nextLine();

        // inputReceiver.close();

        return Matrix(2, 2, "Enter your matrix row by row: ");
    }

    static public Matrix Points() {
        Scanner inputReceiver = new Scanner(System.in);

        int n = 0;
        boolean inputNValid = false;
        while (!inputNValid) {
            System.out.print("Enter n : ");
            n = inputReceiver.nextInt();
            inputNValid = n > 0;
            if (!inputNValid) {
                System.out.println("Invalid n, please try again");

            }
        }
        inputReceiver.nextLine();

        return Matrix(n, 2, "Enter your points by row with x and y separated by space");
    }

}
