package lib;

public class Matrix {
    int[][] contents;

    public Matrix(int nRow, int nCol) {
        this.contents = new int[nRow][nCol];
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
