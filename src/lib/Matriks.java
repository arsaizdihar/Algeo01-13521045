package lib;

import java.util.Scanner;

public class Matriks {
    int[][] contents;

    Matriks(int nRow, int nCol) {
        this.contents = new int[nRow][nCol];
    }

    public void print() {
        System.out.print("[");
        for (int i = 0; i < getNRow(); i++) {
            System.out.print("\n  [");
            for (int j = 0; j < getNCol(); j++) {
                System.out.print(getElmt(i, j));
                if (j != getNCol() - 1)
                    System.out.print(",");
            }
            System.out.print("]");
            if (i != getNRow() - 1)
                System.out.print(",");
        }
        System.out.println("\n]");
    }

    public static Matriks ReadMatriks() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("row: ");
        int nRow = scanner.nextInt();
        System.out.print("col: ");
        int nCol = scanner.nextInt();
        Matriks matriks = new Matriks(nRow, nCol);
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                System.out.printf("[%d][%d]: ", i, j);
                int val = scanner.nextInt();
                matriks.setElmt(i, j, val);
            }
        }
        scanner.close();
        return matriks;
    }

    public int getNRow() {
        return contents.length;
    }

    public int getNCol() {
        if (getNRow() == 0)
            return 0;
        return contents[0].length;
    }

    public int getElmt(int row, int col) {
        return contents[row][col];
    }

    public void setElmt(int row, int col, int val) {
        contents[row][col] = val;
    }
}
