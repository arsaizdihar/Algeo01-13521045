package lib;

public class Matrix {
    private int[][] contents;

    /**
     * Matriks konstruktor. Menginisiasi matriks dengan ukuran baris x kolom.
     * 
     * @param nRow jumlah baris
     * @param nCol jumlah kolom
     * @return matriks baru dengan ukuran nRow x nCol
     */
    public Matrix(int nRow, int nCol) {
        // ALGORITMA
        this.contents = new int[nRow][nCol];
    }

    /*** SELEKTOR ***/

    /**
     * 
     * @return jumlah baris pada matriks
     */
    public int getNRow() {
        // ALGORITMA
        return contents.length;
    }

    /**
     * 
     * @return jumlah kolom pada matriks, 0 jika matriks kosong
     */
    public int getNCol() {
        if (getNRow() == 0)
            return 0;
        return contents[0].length;
    }

    /**
     * 
     * @param rowIdx indeks baris yang ingin diakses
     * @return baris indeks ke-rowIdx
     */
    public int[] getRow(int rowIdx) {
        return contents[rowIdx];
    }

    /**
     * 
     * @param colIdx indeks kolom yang ingin diakses
     * @return kolom indeks ke-colIdx
     */
    public int[] getCol(int colIdx) {
        // KAMUS LOKAL
        int[] col;

        // ALGORITMA
        col = new int[getNRow()];
        for (int i = 0; i < getNRow(); i++) {
            col[i] = contents[i][colIdx];
        }
        return col;
    }

    /**
     * 
     * @param rowIdx indeks baris yang ingin diakses
     * @param colIdx indeks kolom yang ingin diakses
     * @return elemen matriks pada indeks (rowIdx, colIdx)
     */
    public int getElmt(int rowIdx, int colIdx) {
        // ALGORITMA
        return contents[rowIdx][colIdx];
    }

    /**
     * Mengubah elemen matriks pada indeks (rowIdx, colIdx) menjadi val
     * <p>
     * I.S: matriks terdefinisi, rowIdx dan colIdx merupakan indeks yang valid
     * <p>
     * F.S: elemen matriks pada indeks (rowIdx, colIdx) menjadi val
     * 
     * @param rowIdx indeks baris yang ingin diubah
     * @param colIdx indeks kolom yang ingin diubah
     * @param val    nilai baru
     */
    public void setElmt(int rowIdx, int colIdx, int val) {
        contents[rowIdx][colIdx] = val;
    }
}
